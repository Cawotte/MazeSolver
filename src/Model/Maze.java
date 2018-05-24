package Model;

import java.lang.reflect.Array;
import java.util.*;

public class Maze extends Observable {

    private int height;
    private int width;

    private boolean enableGraphics = true;

    private Coord centralPos;
    private Coord playerPos;
    private Coord startPos;
    private Coord exitPos;

    private int[][] maze;

    private int[][] stepCount;

    /*
        La valeur de la case défini son contenu
        0 = vide
        1 = obstacle
        2 = joueur/robot.
     */


    public Maze(int h, int w){

        this.height = h;
        this.width = w;
        this.maze = new int[width][height];
        this.centralPos = new Coord(width/2, height/2);
        this.playerPos = centralPos;

        initMaze();
        //generateMazeRecBac();

    }

    public void initMaze() {

        //On initialise toute les cases vide.
        for (int i = 0; i<this.height; i++)
            for (int j = 0; j<this.width; j++)
                maze[i][j] = 0;

        //We set the player at the central pos.
        maze[centralPos.x][centralPos.y] = 2;

        if ( enableGraphics ) {
            setChanged();
            notifyObservers();
        }

    }
    /**
     * Initialize the labyrinth with obstacles, with the percentage given in argument, from 0 to 100.
     * @param obstacleRate
     */
    public void generateMaze(int obstacleRate) {

        Coord pos;
        Random rand = new Random();

        //We check if the argument is rational
        if ( obstacleRate <= 0 )
            return; //The maze stay empty
        else if ( obstacleRate >= 100 )
            System.out.println("The given percentage is too high ! There can't be 100% or more obstacles !");

        //We count the number of obstacles to put in the maze
        int  nbObstacles = (int) ((double)(height*width*obstacleRate) / 100d);
        //debug
        System.out.println("nbObstacles = " + nbObstacles + ", rate = " + obstacleRate + ", nb tiles = " + height * width);

        //We empty all case in case there were a previous maze.
        for (int i = 0; i<this.height; i++)
            for (int j = 0; j<this.width; j++)
                maze[i][j] = 0;

        resetPlayerPos();

        //We now make a list of all possible coordonates and pick from them, with the exception of the central position, our starting point.
        ArrayList<Coord> possiblePos = new ArrayList<Coord>();
        for ( int i = 0; i < width; i++)
            for ( int j = 0; j < height; j++)
                if ( i != width/2 || j != height/2 )
                    possiblePos.add(new Coord(i,j));

        //We now randomly pick Coords from the list and add obstacles to the maze, removing the one we are picking from the list to not select them again.
        for ( int i = 0; i < nbObstacles; i++) {
            pos = possiblePos.get( rand.nextInt(possiblePos.size()) );
            maze[pos.x][pos.y] = 1;
            possiblePos.remove(pos);
        }

        //TODO : Function to check the solvability of a maze.

        if ( enableGraphics ) {
            setChanged();
            notifyObservers();
        }

        System.out.println("done");
        //printMaze();


    }

    public void generateMazeRecBac() {

        Random rand = new Random();

        //We fill the maze with obstacles, we will dig the labyrinth out of it.
        for (int i = 0; i<this.height; i++)
            for (int j = 0; j<this.width; j++)
                maze[i][j] = 1;

        //We choose a random starting point for the generation
        int x = rand.nextInt(width-2)+2;
        int y = rand.nextInt(height-2)+2;
        Coord rootPos = new Coord(x - (1 - x%2),y - ( 1 - y%2));


        Stack<Coord> stack = new Stack<>();
        stack.push(rootPos);
        RecBack(stack);


        //We determine an entry and an exit.
        //entry
        x = rand.nextInt(width-1);
        x += (1 - x%2);
        startPos = new Coord(x, 0);
        setCase(startPos, 0);
        //exit
        x = rand.nextInt(width-1);
        x += (1 - x%2);
        exitPos = new Coord(x, height-1);
        setCase(exitPos, 0);

        playerPos = startPos.clone();
        setCase(playerPos, 2);

        System.out.println("done");


        if ( enableGraphics ) {
            setChanged();
            notifyObservers();
        }

        stepCount = new int[width][height];
        for (int i = 0; i<this.height; i++)
            for (int j = 0; j<this.width; j++)
                stepCount[i][j] = 0;

        System.out.println("Minimal number of move : " + minimalMove(startPos) );
        System.out.println("exit pos " + exitPos.toString());
        printTab(stepCount);
        //printMaze();

    }

    public void RecBack(Stack<Coord> stack) {

        ArrayList<Direction> randDir;
        Direction dir;
        Coord peekCase;


        if ( stack.isEmpty() )
            return;

        //We remove the wall piece at the current cell.
        setCase( stack.peek(), 0);

        //We explore in a new direction
        randDir = Direction.randDirs();

        //For each possible direction chosen at random
        for ( int i = 0; i < randDir.size(); i++) {

            dir = randDir.get(i);
            //We look at the case two cells in the given direction.
            peekCase = stack.peek().add(dir).add(dir);
            if (peekCase.x > 0 && peekCase.x < width-1 && peekCase.y > 0 && peekCase.y < height-1
                && getCase(peekCase) == 1) {

                //We remove the wall on the middle case, we progress with jump of 2 cases to leave walls.
                setCase(stack.peek().add(dir), 0);
                //We push the new one.
                stack.push(peekCase);
                RecBack(stack);
            }
        }

        //If no case in all direction are valid, we pop the current one.
        stack.pop();

    }


    public int minimalMove(Coord pos, int useless) {

        /*frontier = Queue()
        frontier.put(start )
        visited = {}
        visited[start] = True

        while not frontier.empty():
           current = frontier.get()
           for next in graph.neighbors(current):
              if next not in visited:
                 frontier.put(next)
                 visited[next] = True
         */

        Coord currentPos;

        LinkedList<Coord> frontier = new LinkedList<>();
        frontier.add(startPos);
        ArrayList<Coord> explored = new ArrayList<>();
        explored.add(startPos);

        while ( !frontier.isEmpty() ) {

            currentPos = frontier.getFirst();

            if ( currentPos.equals(exitPos) ) {
                return 1;
            }

            for ( Coord adjPos : emptyNeighbors(currentPos) ) {
                if ( !explored.contains(adjPos) ) {

                }
            }
        }

        return 1;
    }

    /**
     * Return the minimal number of deplacement needed to complete the labyrinth
     * @return
     */
    public int minimalMove(Coord pos) {


        int nbStep = 0;
        int minNbStep = -1;
        Coord adjPos;

        System.out.println(pos.toString() + " ");
        if ( pos.equals(exitPos) ) {
            System.out.println("\nexit!");
            stepCount[pos.x][pos.y] = 1;
            //System.out.println(" step:" + stepCount[pos.x][pos.y]);
            return 0;
        }

        stepCount[pos.x][pos.y] = -1;

        //For each direction
        for ( Direction dir : Direction.directions() ) {

            //We explore the case in this direction
            adjPos = pos.add(dir);

            //If it's inbound and the adj case is empty and it isn't explored yet
            if ( inBound(adjPos) && getCase(adjPos) == 0 && stepCount[adjPos.x][adjPos.y] == 0) {


                //We reiterate the algorithm toward this direction, removing the opposite dir to avoid infinite loops
                nbStep = minimalMove( adjPos );


                if ( ( nbStep != -1 && ( stepCount[pos.x][pos.y] <= 0 || nbStep + 1 < stepCount[pos.x][pos.y] ))) {
                    //printTab(stepCount);
                    //System.out.print(" replaced! ");
                    stepCount[pos.x][pos.y] = nbStep + 1;
                }

            }

        }


        //printTab(stepCount);
        //System.out.println(" step:" + stepCount[pos.x][pos.y]);
        return stepCount[pos.x][pos.y];
    }


    public ArrayList<Coord> emptyNeighbors(Coord pos) {

        ArrayList<Coord> neighbors = new ArrayList<>();
        Coord adjPos;
        for ( Direction dir : Direction.directions() ) {

            adjPos = pos.add(dir);
            if ( inBound(adjPos) && getCase(adjPos) == 0 ) {
                neighbors.add(adjPos);
            }

        }

        return neighbors;
    }
    /**
     * Move the player/robot to the given direction. Return true if the move was possible and made, false otherwise.
     * @param dir Enum which is a couple of int determining the direction
     */
    public boolean move(Direction dir) {

        Coord newPos = new Coord(playerPos.x + dir.x , playerPos.y + dir.y);
        //Check if the movement is possible

        if ( newPos.x > width || newPos.x < 0 || newPos.y > height || newPos.y < 0) {
            return false;
        }

        //if there's an obstacle, nada
        if (  maze[newPos.x][newPos.y] == 1 )
            return false;

        //if it's okay we proceed to the movement.
        maze[playerPos.x][playerPos.y] = 0;
        maze[newPos.x][newPos.y] = 2;

        ArrayList<Coord> changedCases = new ArrayList<>();
        changedCases.add(playerPos); changedCases.add(newPos);
        playerPos = newPos;

        if ( enableGraphics ) {
            setChanged();
            notifyObservers(changedCases);
        }
        if ( playerExited() ) {
            setChanged();
            notifyObservers(true);
        }

        return true;
    }

    public boolean moveAt(Coord pos) {

        //Is movement legit ?
        if ( pos.x > width || pos.x < 0 || pos.y > height || pos.y < 0
            || getCase(pos) == 1)
            return false;

        //It's legit, let's move
        maze[playerPos.x][playerPos.y] = 0;
        maze[pos.x][pos.y] = 2;

        ArrayList<Coord> changedCases = new ArrayList<>();
        changedCases.add(playerPos); changedCases.add(pos);
        playerPos = pos;

        if ( enableGraphics ) {
            setChanged();
            notifyObservers(changedCases);
        }

        return true;
    }

    /**
     * Reset the player's position to the center of the maze.
     */
    public void resetPlayerPos() {
        maze[playerPos.x][playerPos.y] = 0;
        playerPos = centralPos;
        maze[playerPos.x][playerPos.y] = 2;
    }

    public void printMaze() {

        //Ligne bordure au sommet.
        System.out.print("  -");
        for ( int i = 0; i < width; i++ )
            System.out.print("---");
        System.out.println("-");

        for (int[] line : maze ) {
            System.out.print("  |");
            for (int j = 0; j < width; j++) {
                switch (line[j]) {
                    case 0:
                        System.out.print("   ");
                        break;
                    case 1:
                        System.out.print(" X ");
                        break;
                    case 2:
                        System.out.print(" O ");
                        break;

                }
            }
            System.out.println("|");
        }

        //Ligne bordure au pied.
        System.out.print("  -");
        for ( int i = 0; i < width; i++ )
            System.out.print("---");
        System.out.println("-");

    }

    public void printTab(int[][] tab) {
        //Ligne bordure au sommet.
        System.out.print("  -");
        for ( int i = 0; i < width; i++ )
            System.out.print("---");
        System.out.println("-");

        for (int[] line : tab ) {
            System.out.print("  |");
            for (int j = 0; j < width; j++) {
                if ( line[j] == 0 )
                    System.out.print("   ");
                else if ( line[j] == -1 )
                    System.out.print(" X ");
                else
                    System.out.print(" " + line[j] + " ");

            }
            System.out.println("|");
        }

        //Ligne bordure au pied.
        System.out.print("  -");
        for ( int i = 0; i < width; i++ )
            System.out.print("---");
        System.out.println("-");
    }

    public void resetMaze() {
        maze[playerPos.x][playerPos.y] = 0;
        maze[startPos.x][startPos.y] = 2;

        ArrayList<Coord> changedCases = new ArrayList<>();
        changedCases.add(playerPos); changedCases.add(startPos);
        playerPos = startPos;

        if ( enableGraphics ) {
            setChanged();
            notifyObservers(changedCases);
        }
    }

    public boolean inBound(Coord pos) {
        return pos.x >= 0 && pos.y >= 0 && pos.x < width && pos.y < height;
    }

    //Booleans

    public boolean playerExited() {
        return playerPos.equals(exitPos);
    }

    public static int rand(int a, int b) {
        return new Random().nextInt(b-a) + a;
    }

    //Accesseurs

    public int[][] getMaze(){
        return maze;
    }


    public int getCase(int x, int y) { return maze[x][y]; }

    public int getCase(Coord coor) {
        return maze[coor.x][coor.y];
    }

    public void setCase(Coord coor, int val) {
        maze[coor.x][coor.y] = val;
    }

    public void setCase(int x, int y, int val) {
        maze[x][y] = val;
    }

    public Coord getCentralPos() {
        return centralPos;
    }

    public Coord getPlayerPos() {
        return playerPos;
    }

    public boolean isEnableGraphics() {
        return enableGraphics;
    }

    public void setEnableGraphics(boolean enableGraphics) {
        this.enableGraphics = enableGraphics;
    }

    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
}
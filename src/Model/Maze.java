package Model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

public class Maze extends Observable {

    private int height;
    private int width;

    private boolean enableGraphics;

    private Coord centralPos;
    private Coord playerPos;
    private int[][] maze;
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

        //On initialise toute les cases vide.
        for (int i = 0; i<this.height; i++)
            for (int j = 0; j<this.width; j++)
                maze[i][j] = 0;

        //We set the player at the central pos.
        maze[centralPos.x][centralPos.y] = 2;

    }

    /**
     * Initialize the labyrinth with obstacles, with the percentage given in argument, from 0 to 100.
     * @param obstacleRate
     */
    public void initMaze(int obstacleRate) {

        Coord pos;
        Random rand = new Random();

        //We check if the argument is rational
        if ( obstacleRate <= 0 )
            return; //The maze stay empty
        else if ( obstacleRate >= 100 )
            System.out.println("The given percentage is too high ! There can't be 100µ% or more obstacles !");

        //We count the number of obstacles to put in the maze
        int nbObstacles = (height * width) * ( obstacleRate / 100 );
        //debug
        System.out.println("nbObstacles = " + nbObstacles + ", rate = " + obstacleRate + ", nb tiles = " + height * width);

        //We empty all case in case there were a previous maze.
        for (int i = 0; i<this.height; i++)
            for (int j = 0; j<this.width; j++)
                maze[i][j] = 0;

        //We now make a list of all possible coordonates and pick from them, with the exception of the central position, our starting point.
        ArrayList<Coord> possiblePos = new ArrayList<Coord>();
        for ( int i = 0; i < height; i++)
            for ( int j = 0; j < width; j++)
                if ( i != width/2 && j != height/2 )
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


    }


    /**
     * Move the player/robot to the given direction. Return true if the move was possible and made, false otherwise.
     * @param dir Enum which is a couple of int determining the direction
     */
    public boolean deplacer(Direction dir) {

        Coord newPos = new Coord(playerPos.x + dir.x , playerPos.y + dir.y);
        //Check if the movement is possible
        if ( newPos.x >= width || newPos.x < 0 || newPos.y > height || newPos.y < 0)
            return false;

        //if it's okay we proceed to the movement.
        maze[playerPos.x][playerPos.y] = 0;
        maze[newPos.x][newPos.y] = 2;

        if ( enableGraphics ) {
            setChanged();
            notifyObservers();
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

    //Accesseurs

    public int[][] getMaze(){
        return maze;
    }

    public int getCase(int x, int y) { return maze[x][y]; }

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
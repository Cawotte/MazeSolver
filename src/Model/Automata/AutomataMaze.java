package Model.Automata;

import Model.Coord;
import Model.Direction;
import Model.Maze;

public class AutomataMaze extends Automata {

    Maze currentMaze;

    public AutomataMaze(int nbState) {
        super(nbState, (int)Math.pow(2,4), 4);
    }

    public AutomataMaze(int nbState, int nbInput, int nbOutput) {

        super(nbState, nbInput, nbOutput);

    }

    //input / output processing.


    /**
     * Generate a binary value with the content of the 8 cases around the given pos in the given maze. The value is used as an input
     * for automatas.
     * @param pos
     * @param maze
     * @return
     */
    public int input8(Coord pos, Maze maze) {

        int width = maze.getWidth();
        int height = maze.getHeight();

        Coord adjPos;

        int binaryInput = 0;

        for ( int i = -1; i <= 1; i++) {
            for ( int j = -1; j <= 1; j++) {

                adjPos = pos.add(i, j);

                if ( adjPos.x >= 0 && adjPos.y >= 0 && adjPos.x < width && adjPos.y < height
                        && maze.getCase(adjPos) == 0 && !(adjPos.equals(pos)) )
                    binaryInput++;

                binaryInput = binaryInput << 1;

            }
        }

        return binaryInput;
    }

    /**
     * Generate a binary value with the content of the 8 cases around the given pos in the given maze. The value is used as an input
     * for automatas.
     * @param pos
     * @param maze
     * @return
     */
    public int input4(Coord pos, Maze maze) {

        int width = maze.getWidth();
        int height = maze.getHeight();

        Coord adjPos;

        int binaryInput = 0;

        for ( int i = -1; i <= 1; i++) {
            for ( int j = -1; j <= 1; j++) {

                //if one and only one of the two value is equal to 0 (XOR)
                //if it's a stricly vertical or horizontal direction
                if ( i == 0 ^ j == 0 )  {
                    adjPos = pos.add(i, j);
                    //If the value are in-bound and it's not an obstacle
                    if ( adjPos.x >= 0 && adjPos.y >= 0 && adjPos.x < width && adjPos.y < height
                            && maze.getCase(adjPos) == 0 )
                        binaryInput++;

                    binaryInput = binaryInput << 1;

                }

            }
        }

        return binaryInput;
    }


    public Direction output(int output) {

        switch (output) {
            case 0:
                return Direction.UP;
            case 1:
                return Direction.RIGHT;
            case 2:
                return Direction.DOWN;
            case 3:
                return Direction.LEFT;
            default:
                System.out.println("oopsy daisy!");
                return Direction.RIGHT;
        }

    }

    public Direction next(Coord playerPos, Maze maze) {

        int input = input4(playerPos, maze);
        return output( next(input) );
    }

    //resets

    /**
     * Reset the automata and the maze with the initial player pos.
     */
    public void resetMaze() {
        currentState = initialState;
        currentMaze.resetMaze();
    }

    /**
     * Reset the automata and put it at the start of the new maze, given in argument
     * @param maze
     */
    public void resetMaze(Maze maze) {
        currentState = initialState;
        currentMaze = maze;
        currentMaze.resetMaze();
    }

    //Getters/Setters


    public Maze getCurrentMaze() {
        return currentMaze;
    }

    public void setCurrentMaze(Maze currentMaze) {
        this.currentMaze = currentMaze;
    }
}

package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public enum Direction {
    DOWN(1,0),
    UP(-1,0),
    RIGHT(0,1),
    LEFT(0,-1);

    public int x,y;

    Direction(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Coord coor() {
        return new Coord(x, y);
    }

    /**
     * Return an array with the 4 possibles directions in a random order.
     * @return
     */
    public static ArrayList<Direction> randDirs() {

        ArrayList<Direction> randDir = new ArrayList<Direction>(Arrays.asList(UP, DOWN, LEFT, RIGHT));
        Collections.shuffle(randDir);
        return randDir;
    }

    public static ArrayList<Direction> directions() {
        return new ArrayList<Direction>(Arrays.asList(UP, DOWN, LEFT, RIGHT));
    }

    /**
     * Renvoie la direction opposée ( UP = DOWN, RIGHT = LEFT, ... )
     * @return Direction
     */
    public Direction opposee() {
        switch (this) {
            case RIGHT:
                return LEFT;
            case LEFT:
                return RIGHT;
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            default:
                return LEFT;
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}

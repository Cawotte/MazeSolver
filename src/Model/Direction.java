package Model;

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

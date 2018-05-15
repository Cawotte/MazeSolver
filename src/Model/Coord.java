package Model;

import java.util.ArrayList;
import java.util.Random;

public class Coord {

    public int x, y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coord add(Coord coor) {
        return new Coord(this.x + coor.x, this.y + coor.y);
    }

    public Coord add(Direction dir) {
        return new Coord(this.x + dir.x, this.y + dir.y);
    }

    public static Coord randCoor(int xbound, int ybound) {
        Random rand = new Random();
        int x = rand.nextInt(xbound);
        int y = rand.nextInt(ybound);
        return new Coord(x,y);
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}

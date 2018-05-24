package Model;

import java.util.ArrayList;
import java.util.Objects;
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

    public Coord add(int x, int y) {
        return new Coord(this.x + x, this.y + y);
    }

    public static Coord randCoor(int xbound, int ybound) {
        Random rand = new Random();
        int x = rand.nextInt(xbound);
        int y = rand.nextInt(ybound);
        return new Coord(x,y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public Coord clone() {
        return new Coord(x,y);
    }

    /*public boolean equals(Coord coor) {
        return ( this.x == coor.x && this.y == coor.y);
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return x == coord.x &&
                y == coord.y;
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y);
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

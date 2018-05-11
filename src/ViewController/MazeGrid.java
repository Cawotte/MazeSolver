package VueControleur;


import Model.Maze;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MazeGrid extends BorderPane{

    private int height;
    private int width;
    private Maze p;
    private Rectangle[][] tab;
    private GridPane gridP;

    public MazeGrid(int a, int b, int rectSize , boolean reversed) {
        this.height = a;
        this.width = b;

        reversed = false;

        // initialisation du modèle que l'on souhaite utiliser
        p = new Maze(width, height);

        // permet de placer les differents boutons dans une grille
        gridP = new GridPane();

        if (reversed)
            tab = new Rectangle[height][width];
        else
            tab = new Rectangle[width][height];


        // création des boutons et placement dans la grille
        for(int i = 0; i < height; i++)
            for(int j = 0; j < width; j++){
                tab[i][j] = new Rectangle();
                tab[i][j].setHeight(rectSize);
                tab[i][j].setWidth(rectSize);
                tab[i][j].setFill(Color.WHITE);
                if (reversed)
                    gridP.add(tab[i][j], i, j);
                else
                    gridP.add(tab[i][j], j, i);

            }

        //CENTER
        gridP.setGridLinesVisible(true);
        this.setCenter(gridP);
        this.setPadding(new Insets(10, 20, 10, 20));

    }


    public Maze getP() {
        return p;
    }

    public Rectangle[][] getTab() {
        return tab;
    }

    /*public int getLargeur() {
        return height;
    } */

    /*public int getWidth() {
        return width;
    } */

    public GridPane getGridP() {
        return gridP;
    }
}

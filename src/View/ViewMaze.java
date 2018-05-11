package View;

import Model.Coord;
import Model.Direction;
import Model.Maze;
import ViewController.PopupFinPartie;
import ViewController.MazeGrid;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class ViewMaze extends Application implements Observer {


    private Maze maze;

    private MazeGrid mazeView;
    private int mazeViewWidth = 1100;
    private int mazeViewHeight = 700;

    private int mazeSize = 40;

    private PopupFinPartie popupFinPartie;



    /*
    Actions possible du joueur de Blokus dans la vue :
        -> Cliquer sur une pièce de sa liste de pièce (affiché à côté) pour la définir comme pièce active
        -> Cliquer sur le maze pour poser la pièce active
            -> Feedback si la pose n'est pas possible.
            -> Affichage de la pièce en hoover du maze ?
            -> Lorsqu'une pièce est posée, on passe au joueur suivant.
        -> Bouton abandonner. (Si on n'implémente pas de vérification de condition de victoire).
    */


    @Override
    public void start(Stage primaryStage){

        maze = new Maze(mazeSize, mazeSize);

        //int rectangleSize = ( (mazeViewWidth+mazeViewHeight) / 2 ) / 30;
        double scaling =  20d / (double)mazeSize;
        int rectangleSize = (int) (30 * scaling);

        //ectangleSize = Math.min(   (int) ((double)mazeViewHeight / (double)mazeHeight),  (int) ((double)mazeViewWidth / (double)mazeWidth));
        System.out.println("rect size =" + rectangleSize);
        mazeView = new MazeGrid(mazeSize, mazeSize, rectangleSize, false);

        maze.addObserver(this);


        //------------------------------------------
        //----- POPUP VICTOIRE
        popupFinPartie = new PopupFinPartie(primaryStage);


        //-----------------------------------------
        //Préparation de la scene de la grille de jeu.
        //-----------------------------------------

        //---------------------------------------------------------------------
        // ------------ TOP -- Le Titre

        //region titre
        Text titre = new Text("--- MAZE ---");
        titre.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        titre.setFill(Color.MEDIUMPURPLE);
        mazeView.setAlignment(titre, Pos.CENTER);
        mazeView.setTop(titre);
        //endregion

        //---------------------------------------------------------------------
        //------------- RIGHT --

            //NADA

        //---------------------------------------------------------------------
        //------------- LEFT -- Miscelannous buttons

        //region BUTTONS
        VBox vbox = new VBox();

        Text textChoix = new Text("   Actions : ");
        textChoix.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
        vbox.setPadding(new Insets(60, 0, 10, 25));
        vbox.getChildren().add(textChoix);

        Button initMaze = new Button("Generate Maze");
        initMaze.setOnMouseClicked(event -> {
            maze.generateMaze(50);
        });

        initMaze.setPadding(new Insets(10));
        vbox.setMargin(initMaze, new Insets(10, 5, 5, 40));
        vbox.getChildren().add(initMaze);

        //Pour éviter d'avoir un changement de taille de la région lorsqu'on la changera avec la liste de joueurs.
        vbox.setMinWidth(200);

        mazeView.setLeft(vbox);
        //endregion

        //---------------------------------------------------------------------
        // ------------ CENTER -- Maze

        //region Maze Jeu


        //mazeView.getGridP().setGridLinesVisible(true);
        mazeView.getGridP().setPadding(new Insets(10, 0, 10, 20));


        //endregion

        //---------------------------------------------------------------------
        // ------------ BOTTOM --

        //Résout le problème de gap entre le maze de jeu et les listes de pièces.
        mazeView.setMinSize(1100, 700);



        //---------------------------------------------------------------------
        //----- SCENE

        Scene scene = new Scene(mazeView, 1100, 700);

        movementEvents(scene);

        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.setTitle("MAZE");
        primaryStage.setScene(scene);
        primaryStage.show();


        //---------------------------------------------------------------------
        //----- EVENTS

    }


    public boolean inBound(int i, int j, int iMax, int jMax) {
        return ( i >= 0 && j >= 0 && i < iMax && j < jMax);
    }

    private void movementEvents(Scene scene) {

        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent key) {
                Direction dir = null;
                switch (key.getCode()) {
                    case LEFT:
                        dir = Direction.LEFT;
                        break;
                    case RIGHT:
                        dir = Direction.RIGHT;
                        break;
                    case UP:
                        dir = Direction.UP;
                        break;
                    case DOWN:
                        dir = Direction.DOWN;
                        break;
                }
                if (dir != null)
                    maze.move(dir);
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {


        //If the observed object is a maze, we refresh it View.
        if (o instanceof Maze) {

            //We refresh only the given coordinates
            if ( arg instanceof ArrayList ) {

                ArrayList<Coord> coordToUpdate = (ArrayList<Coord>)arg;
                for ( Coord pos : coordToUpdate ) {
                    switch ( maze.getCase(pos.x,pos.y) ) {
                        case 0:
                            mazeView.getTab()[pos.x][pos.y].setFill(Color.WHITE);
                            break;
                        case 1:
                            mazeView.getTab()[pos.x][pos.y].setFill(Color.RED);
                            break;
                        case 2:
                            mazeView.getTab()[pos.x][pos.y].setFill(Color.BLUE);
                            break;
                    }
                }
            }
            else if ( arg instanceof Boolean ) {
                if ( (boolean) arg ) {
                    popupFinPartie.setTextPopup("You got out of the maze !");
                    popupFinPartie.afficherPopup();
                }
            }

            else {
                //Full refresh of the grid
                for (int i = 0; i < maze.getHeight(); i++ ) {
                    for (int j = 0; j < maze.getWidth(); j++) {

                        switch ( maze.getCase(i,j) ) {
                            case 0:
                                mazeView.getTab()[i][j].setFill(Color.WHITE);
                                break;
                            case 1:
                                mazeView.getTab()[i][j].setFill(Color.RED);
                                break;
                            case 2:
                                mazeView.getTab()[i][j].setFill(Color.BLUE);
                                break;
                        }

                    }
                }

            }

        }



    }

    public static void main(String[] args) {
        launch(args);
    }
}
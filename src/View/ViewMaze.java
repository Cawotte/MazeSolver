package View;

import Model.Coord;
import Model.Direction;
import Model.Maze;
import VueControleur.PopupFinPartie;
import VueControleur.MazeGrid;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class VueControleurBlokus extends Application implements Observer {


    private Maze maze = new Maze(20, 20);


    private MazeGrid mazeView = new MazeGrid(20, 20, 30, false);

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
            maze.initMaze(50);
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

        //Le centre est déjà initialisé quand on crée MazeGrid !
        mazeView.getGridP().setGridLinesVisible(true);
        mazeView.getGridP().setPadding(new Insets(10, 0, 10, 20));


        //endregion

        //---------------------------------------------------------------------
        // ------------ BOTTOM --

        //Résout le problème de gap entre le maze de jeu et les listes de pièces.
        mazeView.setMinSize(1100, 700);



        //---------------------------------------------------------------------
        //----- SCENE

        Scene scene = new Scene(mazeView, 1100, 700);

        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.setTitle("MAZE");
        primaryStage.setScene(scene);
        primaryStage.show();


        //---------------------------------------------------------------------
        //----- EVENTS

        movementEvents(scene);

    }


    public boolean inBound(int i, int j, int iMax, int jMax) {
        return ( i >= 0 && j >= 0 && i < iMax && j < jMax);
    }

    private void movementEvents(Scene scene) {

        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent key){
                if (key.getCode().equals(KeyCode.UP)) {
                    maze.deplacer( Direction.UP );
                }
                else if (key.getCode().equals(KeyCode.RIGHT)) {
                    maze.deplacer( Direction.RIGHT );
                }
                else if (key.getCode().equals(KeyCode.DOWN)) {
                    maze.deplacer( Direction.DOWN );
                }
                else if (key.getCode().equals(KeyCode.LEFT)) {
                    maze.deplacer( Direction.LEFT );
                }
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
package ViewController;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopupFinPartie extends Stage {

    private VBox popupVBox;
    private Text textPopup;
    private Button btQuitter;

    public PopupFinPartie(Stage primaryStage) {


        //Le contenu du Popup : Un message de victoire et un bouton pour quitter.
        popupVBox = new VBox();

        //Bouton pour quitter
        btQuitter = new Button("Quitter le jeu");
        btQuitter.setPadding(new Insets(10));
        btQuitter.setOnMouseClicked(event -> {
            close();
            primaryStage.close();
        });

        //Message de Victoire
        textPopup = new Text();
        textPopup.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));

        //On ajoute le bouton et le texte a la vbox.
        popupVBox.setAlignment(Pos.CENTER);
        popupVBox.setSpacing(20);
        popupVBox.getChildren().addAll(textPopup, btQuitter);

        //---------------
        //On prépare le popup : C'est une nouvelle scène sur un nouveau stage.
        Scene popupScene = new Scene(popupVBox, 200, 120);

        close();
        setScene(popupScene);
        //Cette ligne sert à rendre le stage un popup.
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Bravo !");
        setOnCloseRequest(e -> Platform.exit());

    }


    /**
     * Modifie le texte du Popup affiché au dessus du bouton.
     * @param text Nouveau texte du popup.
     */
    public void setTextPopup(String text) {
        textPopup.setText(text);
    }

    /**
     * Affiche le popup. Il empêche toute interaction avec les autres fenêtres,
     * et à sa fermeture, le programme se termine.
     */
    public void afficherPopup() {
        showAndWait();
    }

}

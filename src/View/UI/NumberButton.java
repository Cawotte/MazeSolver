package View.UI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class NumberButton extends HBox {

    private int value = 0;

    private TextField textNumber;
    private Button leftButton;
    private Button rightButton;

    public NumberButton(int value) {

        super(8);

        this.value = value;

        this.textNumber = new TextField("" + value);
        this.textNumber.setPrefWidth(60);
        // ------ This code is not mine
        // force the field to be numeric only
        textNumber.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    //textNumber.setText(newValue.replaceAll("[^\\d]", ""));
                    textNumber.setText(oldValue);
                }
                else {
                    updateValue();
                }
            }
        });
        // --------------

        //Decrease button
        this.leftButton = new Button("-");
        this.leftButton.prefWidth(10);
        this.leftButton.setOnMouseClicked(event -> {
            this.value--;
            updateTextField();
        });

        //Increase button
        this.rightButton = new Button("+");
        this.rightButton.prefWidth(10);
        this.rightButton.setOnMouseClicked(event -> {
            this.value++;
            updateTextField();

        });

        this.getChildren().addAll(leftButton, textNumber, rightButton);
    }

    private void updateTextField() {
        this.textNumber = new TextField("" + value);
    }

    private void updateValue() {
        this.value = Integer.valueOf(textNumber.getText());
    }

    public int getValue() {
        return value;
    }

}

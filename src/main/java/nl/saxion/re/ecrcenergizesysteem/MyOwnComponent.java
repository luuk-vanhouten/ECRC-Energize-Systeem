package nl.saxion.re.ecrcenergizesysteem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import static java.lang.Math.floor;

public class MyOwnComponent {
    @FXML
    private Pane panePicture;
    @FXML
    private TextField lengthCalculator;
    @FXML
    private TextField widthCalculator;
    @FXML
    private Label answer;


    @FXML
    private void onCalculateButtonPressed() {
        Double i = (floor(Double.parseDouble(lengthCalculator.getText()) / 175.4)) * (floor(Double.parseDouble(widthCalculator.getText()) / 109.6));
        Double j = (floor(Double.parseDouble(lengthCalculator.getText()) / 109.6)) * (floor(Double.parseDouble(widthCalculator.getText()) / 175.4));

        if (i > j) {
            answer.setText(i + " landscape");
        } else if (j > i) {
            answer.setText(j + " portrait");
        }

        i = 0.0;
        j = 0.0;
    }

    @FXML
    private void onPreviousButtonPressed() {

    }

    @FXML
    private void onNextButtonPressed() {
    }

}

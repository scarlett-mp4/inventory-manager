package us.scarandtay.csproj;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainCon {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
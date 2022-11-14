package us.scarandtay.csproj.controller;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import us.scarandtay.csproj.Main;

public class MetroPane {
    private double x=0, y=0;
    public Button minimizeButton;
    public Button xButton;

    public void dragEvent(MouseEvent mouseEvent) {
        Parent root = Main.getInstance().root;
        
    }

    public void pressedEvent(MouseEvent mouseEvent) {
        Parent root = Main.getInstance().root;
    }
}

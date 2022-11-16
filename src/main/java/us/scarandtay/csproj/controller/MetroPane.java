package us.scarandtay.csproj.controller;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import us.scarandtay.csproj.Main;

public class MetroPane {
    public Button closeButton;
    public Button minimizeButton;
    private double x=0, y=0;

    public void mouseDragged(MouseEvent mouseEvent) {
        Main.getInstance().stage.setX(mouseEvent.getScreenX() - x);
        Main.getInstance().stage.setY(mouseEvent.getScreenY() - y);
    }

    public void mousePressed(MouseEvent mouseEvent) {
        x = mouseEvent.getSceneX();
        y = mouseEvent.getSceneY();
    }

    public void buttonCloseEvent(MouseEvent mouseEvent) {
        Main.getInstance().stage.close();
        Main.getInstance().queuedThread.interrupt();
        Platform.exit();
        System.exit(0);
    }

    public void buttonMinimizeEvent(MouseEvent mouseEvent) {
        Main.getInstance().stage.setIconified(true);
    }
}

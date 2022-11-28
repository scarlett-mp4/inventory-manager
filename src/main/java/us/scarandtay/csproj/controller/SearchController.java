package us.scarandtay.csproj.controller;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import us.scarandtay.csproj.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    public Button closeButton;
    public Button minimizeButton;
    private double x = 0, y = 0;

    public SearchController() {
        Main.getInstance().searchController = this;
    }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void homeTabClicked(MouseEvent mouseEvent) {
        Main.getInstance().stage.setScene(Main.getInstance().home);
        Main.getInstance().homeController.refresh(Main.getInstance().homeController.choiceBox.getValue());
    }

    public void addTabClicked(MouseEvent mouseEvent) {
        Main.getInstance().stage.setScene(Main.getInstance().add);
    }
}

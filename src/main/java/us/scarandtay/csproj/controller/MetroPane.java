package us.scarandtay.csproj.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArrayBase;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import us.scarandtay.csproj.Main;

import java.util.ArrayList;
import java.util.List;

public class MetroPane {
    public Button closeButton;
    public Button minimizeButton;
    public Button testButton;
    public ListView<Button> home_list;
    private double x = 0, y = 0;

    public void testButton(MouseEvent mouseEvent) {
        Button b = new Button("hey bitch");
        List<Button> list = new ArrayList<>(home_list.getItems());
        list.add(b);
        ObservableList<Button> obList = FXCollections.observableList(list);
        home_list.setItems(obList);
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
}

package us.scarandtay.csproj.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import us.scarandtay.csproj.Main;
import us.scarandtay.csproj.utils.Category;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HomeController implements Initializable {
    public Button closeButton;
    public Button minimizeButton;
    public Button testButton;
    public ListView<Button> home_list;
    public ChoiceBox<Category> choiceBox;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Category> list = new ArrayList<>(Arrays.asList(Category.values()));
        choiceBox.setItems(FXCollections.observableList(list));
        choiceBox.setValue(Category.ALL);
    }

    public void addTabClicked(MouseEvent mouseEvent) {
        Main.getInstance().stage.setScene(Main.getInstance().add);
    }

    public void searchTabClicked(MouseEvent mouseEvent) {
        Main.getInstance().stage.setScene(Main.getInstance().search);
    }
}
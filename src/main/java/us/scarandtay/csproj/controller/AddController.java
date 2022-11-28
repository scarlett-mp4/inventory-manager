package us.scarandtay.csproj.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import us.scarandtay.csproj.Main;
import us.scarandtay.csproj.utils.Category;

import java.net.URL;
import java.util.*;

public class AddController implements Initializable {
    public Button closeButton;
    public Button minimizeButton;
    public Button button__image;
    public TextField button__name;
    public TextField button__brand;
    public TextField button__price;
    public DatePicker button__date;
    public ChoiceBox<String> button__stock;
    public Button button__create;
    private double x = 0, y = 0;

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
        List<String> list = new ArrayList<>();
        list.add("In Stock");
        list.add("Out of Stock");
        button__stock.setItems(FXCollections.observableList(list));
        button__stock.setValue("In Stock");
    }

    public void searchTabClicked(MouseEvent mouseEvent) {
        Main.getInstance().stage.setScene(Main.getInstance().search);
    }

    public void homeTabClicked(MouseEvent mouseEvent) {
        Main.getInstance().stage.setScene(Main.getInstance().home);
    }
}

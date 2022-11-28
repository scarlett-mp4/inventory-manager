package us.scarandtay.csproj.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import us.scarandtay.csproj.Main;
import us.scarandtay.csproj.api.Api;
import us.scarandtay.csproj.utils.Category;
import us.scarandtay.csproj.utils.ListableItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public Button closeButton;
    public Button minimizeButton;
    public Button refreshButton;
    public ListView<Pane> home_list;
    public ChoiceBox<Category> choiceBox;
    private double x = 0, y = 0;

    public HomeController() {
        Main.getInstance().homeController = this;
    }

    public void refreshButtonClicked(MouseEvent mouseEvent) {
        refresh(choiceBox.getValue());
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
        refresh(choiceBox.getValue());

        choiceBox.setOnAction((actionEvent -> {
            refresh(choiceBox.getValue());
        }));
    }

    public void addTabClicked(MouseEvent mouseEvent) {
        Main.getInstance().stage.setScene(Main.getInstance().add);
    }

    public void searchTabClicked(MouseEvent mouseEvent) {
        Main.getInstance().stage.setScene(Main.getInstance().search);
        Main.getInstance().searchController.refresh(Main.getInstance().searchController.searchBar.getText());
    }

    public void refresh(Category category) {
        List<Pane> paneList = new ArrayList<>();
        for (ListableItem item : Api.getItems()) {
            if (category.equals(Category.ALL))
                paneList.add(item.createPane());
            else if (category == item.getCategory())
                paneList.add(item.createPane());
        }
        home_list.setItems(FXCollections.observableList(paneList));
    }
}

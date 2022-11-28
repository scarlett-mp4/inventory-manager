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

//          _________   ________________________  ____
//         \_   ___ \ /   _____/\_____  \   _  \/_   |
//         /    \  \/ \_____  \  /  ____/  /_\  \|   |
//         \     \____/        \/       \  \_/   \   |
//          \______  /_______  /\_______ \_____  /___|
//                 \/        \/         \/     \/
//                      Inventory Manager
//        Written by: Scarlett Kadan & Taylor Washington
public class HomeController implements Initializable {

    // Window "top-bar" definitions
    public Button closeButton;              // "x" button node
    public Button minimizeButton;           // "-" button node
    private double x = 0, y = 0;            // Window position

    // Home scene definitions
    public Button refreshButton;            // refresh button node
    public ChoiceBox<Category> choiceBox;   // choice box node
    public ListView<Pane> home_list;        // ListableItem.createPane() list

    // - Constructor -
    // Defines the homeController variable in main as
    // this instance. Useful for refreshing home_list when
    // switching tabs.
    public HomeController() {
        Main.getInstance().homeController = this;
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

    public void refreshButtonClicked() {
        refresh(choiceBox.getValue());
    }


    // ----- Tab Handlers -----

    // Sets stage to add scene
    public void addTabClicked(MouseEvent mouseEvent) {
        Main.getInstance().stage.setScene(Main.getInstance().add);
    }

    // Sets stage to search scene
    public void searchTabClicked(MouseEvent mouseEvent) {
        Main.getInstance().stage.setScene(Main.getInstance().search);
        Main.getInstance().searchController.refresh(Main.getInstance().searchController.searchBar.getText());
    }


    // ----- Window handlers -----

    // Window drag handler
    // Updates the windows position
    public void mouseDragged(MouseEvent mouseEvent) {
        Main.getInstance().stage.setX(mouseEvent.getScreenX() - x);
        Main.getInstance().stage.setY(mouseEvent.getScreenY() - y);
    }

    // Initializes x and y for dragEvent
    public void mousePressed(MouseEvent mouseEvent) {
        x = mouseEvent.getSceneX();
        y = mouseEvent.getSceneY();
    }

    // Adds functionality to close button
    public void buttonCloseEvent() {
        Main.getInstance().stage.close();
        Main.getInstance().queuedThread.interrupt();
        Platform.exit();
        System.exit(0);
    }

    // Adds functionality to minimize button
    public void buttonMinimizeEvent() {
        Main.getInstance().stage.setIconified(true);
    }
}
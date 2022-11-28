package us.scarandtay.csproj.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import us.scarandtay.csproj.Main;
import us.scarandtay.csproj.api.Api;
import us.scarandtay.csproj.utils.ListableItem;

import java.net.URL;
import java.util.ArrayList;
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
public class SearchController implements Initializable {

    // Window "top-bar" definitions
    public Button closeButton;            // "x" button node
    public Button minimizeButton;        // "-" button node
    private double x = 0, y = 0;         // Window position

    // Search scene definitions
    public TextField searchBar;          // search bar node
    public ListView<Pane> search_list;   // ListableItem.createPane() list

    // - Constructor -
    // Defines the searchController variable in main as
    // this instance. Useful for refreshing search_list when
    // switching tabs.
    public SearchController() {
        Main.getInstance().searchController = this;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh(searchBar.getText());
        searchBar.setOnKeyTyped((actionEvent -> {
            refresh(searchBar.getText());
        }));
    }

    public void refresh(String contains) {
        List<Pane> paneList = new ArrayList<>();
        for (ListableItem item : Api.getItems()) {
            if (item.getName().toLowerCase().contains(contains))
                paneList.add(item.createPane());
        }
        search_list.setItems(FXCollections.observableList(paneList));
    }


    // ----- Tab Handlers -----

    // Sets stage to home scene
    public void homeTabClicked() {
        Main.getInstance().stage.setScene(Main.getInstance().home);
        Main.getInstance().homeController.refresh(Main.getInstance().homeController.choiceBox.getValue());
    }

    // Sets stage to add scene
    public void addTabClicked() {
        Main.getInstance().stage.setScene(Main.getInstance().add);
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
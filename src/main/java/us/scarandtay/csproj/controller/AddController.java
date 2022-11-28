package us.scarandtay.csproj.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.joda.time.LocalDate;
import us.scarandtay.csproj.Main;
import us.scarandtay.csproj.api.Api;
import us.scarandtay.csproj.utils.Category;

import java.io.File;
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
public class AddController implements Initializable {

    // Window "top-bar" definitions
    public Button closeButton;                      // "x" button node
    public Button minimizeButton;                   // "-" button node
    private double x = 0, y = 0;                    // Window position

    // Add scene definitions
    public Label errorLabel;                        // Error label node
    public Button button__image;                    // Image node
    public TextField button__name;                  // Name node
    public TextField button__brand;                 // Brand node
    public ImageView button__image__view;           // Image view node
    public TextField button__price;                 // Price node
    public DatePicker button__date;                 // Date node
    public ChoiceBox<String> button__stock;         // Stock node
    public ChoiceBox<Category> button__category;    // Category node
    public Button button__create;                   // Create button node
    private FileChooser fileChooser;                // File chooser variable
    private File selectedImage;                     // To-be ListableItem image


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> list = new ArrayList<>();
        list.add("In Stock");
        list.add("Out of Stock");
        button__stock.setItems(FXCollections.observableList(list));
        button__stock.setValue("In Stock");

        List<Category> categories = new ArrayList<>();
        for (Category c : Category.values()) {
            if (c != Category.ALL)
                categories.add(c);
        }
        button__category.setItems(FXCollections.observableList(categories));

        errorLabel.setText("");

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
        EventHandler<ActionEvent> event = e -> {
            File file = fileChooser.showOpenDialog(Main.getInstance().stage);
            if (file != null) {
                selectedImage = file;
                button__image__view.setImage(new Image(file.toURI().toString()));
            }
        };

        button__image.setOnAction(event);
    }

    public void createButtonClicked(MouseEvent mouseEvent) {
        try {
            if (selectedImage == null)
                selectedImage = new File("src/main/resources/us/scarandtay/icons/unknown-photo-logo.png");
            if (button__name.getText().length() <= 0) {
                errorLabel.setText("· You must enter a product name.");
                return;
            }
            if (button__brand.getText().length() <= 0) {
                errorLabel.setText("· You must enter a product brand.");
                return;
            }
            if (button__price.getText().length() <= 0) {
                errorLabel.setText("· You must enter a price.");
                return;
            }
            if (button__date.getValue() == null) {
                errorLabel.setText("· You must enter an expiration date.");
                return;
            }

            double price = Double.parseDouble(button__price.getText());
            boolean inStock = !button__stock.getValue().equals("Out of Stock");
            LocalDate jodaDate = new LocalDate(button__date.getValue().toString());

            Api.createItem(button__name.getText(), button__brand.getText(), button__category.getValue(),
                    jodaDate, selectedImage, price, inStock).queue();

            button__name.setText("");
            button__brand.setText("");
            button__price.setText("");
            button__date.setValue(java.time.LocalDate.now());
            button__category.setValue(null);
            button__stock.setValue("In Stock");
            button__image__view.setImage(new Image(new File("src/main/resources/us/scarandtay/icons/unknown-photo-logo.png").toURI().toString()));

            errorLabel.setText("· Item Created!");
        } catch (Exception e) {
            errorLabel.setText("· Please make sure the fields are valid!");
            e.printStackTrace();
        }
    }


    // ----- Tab Handlers -----

    // Sets stage to search scene
    public void searchTabClicked(MouseEvent mouseEvent) {
        Main.getInstance().stage.setScene(Main.getInstance().search);
        Main.getInstance().searchController.refresh(Main.getInstance().searchController.searchBar.getText());
    }

    // Sets stage to home scene
    public void homeTabClicked(MouseEvent mouseEvent) {
        Main.getInstance().stage.setScene(Main.getInstance().home);
        Main.getInstance().homeController.refresh(Main.getInstance().homeController.choiceBox.getValue());
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
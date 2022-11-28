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

public class AddController implements Initializable {
    public Label errorLabel;
    public Button closeButton;
    public Button minimizeButton;
    public Button button__image;
    public TextField button__name;
    public TextField button__brand;
    public ImageView button__image__view;
    public TextField button__price;
    public DatePicker button__date;
    public ChoiceBox<String> button__stock;
    public ChoiceBox<Category> button__category;
    public Button button__create;
    private FileChooser fileChooser;
    private File selectedImage;
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

    public void searchTabClicked(MouseEvent mouseEvent) {
        Main.getInstance().stage.setScene(Main.getInstance().search);
    }

    public void homeTabClicked(MouseEvent mouseEvent) {
        Main.getInstance().stage.setScene(Main.getInstance().home);
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


            errorLabel.setText("· Item Created!");
        } catch (Exception e) {
            errorLabel.setText("· Please make sure the fields are valid!");
            e.printStackTrace();
        }
    }
}

package us.scarandtay.csproj.utils;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import org.joda.time.LocalDate;
import us.scarandtay.csproj.Main;
import us.scarandtay.csproj.api.Api;

import java.io.File;
import java.text.DecimalFormat;
import java.util.UUID;

public class ListableItem {

    private final String name;
    private final String brand;
    private final Category category;
    private final LocalDate expirationDate;
    private final UUID uuid;
    private final double price;
    private final boolean inStock;
    private File image;


    public ListableItem(String name, String brand, Category category, LocalDate expirationDate, File image, double price, boolean inStock) {
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.expirationDate = expirationDate;
        this.image = image;
        this.price = price;
        this.inStock = inStock;
        this.uuid = UUID.randomUUID();
        Main.getInstance().memoryItemsList.add(this);
    }

    public ListableItem(String name, String brand, Category category, LocalDate expirationDate, File image, double price, boolean inStock, UUID uuid) {
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.expirationDate = expirationDate;
        this.image = image;
        this.price = price;
        this.inStock = inStock;
        this.uuid = uuid;
        Main.getInstance().memoryItemsList.add(this);
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public double getPrice() {
        return price;
    }

    public boolean isInStock() {
        return inStock;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File file) {
        image = file;
    }

    public UUID getUUID() {
        return uuid;
    }

    public Pane createPane() {
        Pane pane = new Pane();
        pane.setPrefHeight(50.0);
        pane.setPrefWidth(20.0);
        pane.setStyle("-fx-background-color: transparent; " +
                "-fx-border-radius: 10px;" +
                "-fx-border-color: #6c6c6c;");

        ImageView productImage = new ImageView(image.toURI().toString());
        productImage.setFitHeight(50);
        productImage.setFitWidth(50);

        Label title = new Label(getName());
        title.setStyle("-fx-font-size: 14px;" +
                "-fx-text-fill: #ffffff;" +
                "-fx-font-weight: bold;");
        title.setLayoutX(60);
        title.setLayoutY(5);

        Label brand = new Label(getBrand());
        brand.setStyle("-fx-font-size: 14px;" +
                "-fx-text-fill: #c9c9c9;");
        brand.setLayoutX(60);
        brand.setMaxWidth(70);
        brand.setLayoutY(24);

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        Label price = new Label("$" + decimalFormat.format(getPrice()));
        if (isInStock())
            price.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: #1aff00;");
        else
            price.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: #ff0000;");
        price.setLayoutX(130);
        price.setLayoutY(24);


        Label category = new Label("Found in: " + getCategory().toString());
        category.setPrefWidth(500);
        category.setStyle("-fx-font-size: 11px;" +
                "-fx-text-fill: #ffffff;" +
                "-fx-text-alignment: right;" +
                "-fx-tile-alignment: center;" +
                "-fx-page-information-alignment: center;");
        category.setLayoutX(440);
        category.setLayoutY(3);
        category.setAlignment(Pos.CENTER_RIGHT);
        category.setTextAlignment(TextAlignment.RIGHT);

        Label sellBy = new Label("Sell By: " + getExpirationDate().toString());
        sellBy.setPrefWidth(500);
        sellBy.setStyle("-fx-font-size: 11px;" +
                "-fx-text-fill: #ffffff;" +
                "-fx-text-alignment: right;" +
                "-fx-tile-alignment: center;" +
                "-fx-page-information-alignment: center;");
        sellBy.setLayoutX(440);
        sellBy.setLayoutY(17);
        sellBy.setAlignment(Pos.CENTER_RIGHT);
        sellBy.setTextAlignment(TextAlignment.RIGHT);

        Label remove = new Label("(Click to remove item)");
        remove.setPrefWidth(110);
        remove.setStyle("-fx-font-size: 11px;" +
                "-fx-text-fill: #b7b7b7;" +
                "-fx-text-alignment: right;" +
                "-fx-tile-alignment: center;" +
                "-fx-page-information-alignment: center;");
        remove.setLayoutX(832);
        remove.setLayoutY(30);
        remove.setAlignment(Pos.CENTER_RIGHT);
        remove.setTextAlignment(TextAlignment.RIGHT);

        remove.setOnMouseEntered((mouseEvent) -> {
            remove.setStyle("-fx-font-size: 11px;" +
                    "-fx-text-fill: #ff4444;" +
                    "-fx-text-alignment: right;" +
                    "-fx-tile-alignment: center;" +
                    "-fx-page-information-alignment: center;");
        });

        remove.setOnMouseExited((mouseEvent) -> {
            remove.setStyle("-fx-font-size: 11px;" +
                    "-fx-text-fill: #b7b7b7;" +
                    "-fx-text-alignment: right;" +
                    "-fx-tile-alignment: center;" +
                    "-fx-page-information-alignment: center;");
        });

        remove.setOnMouseClicked((mouseEvent) -> {
            Api.removeItem(uuid).queue();
            try {
                Thread.sleep(5);
                Main.getInstance().homeController.refresh(Main.getInstance().homeController.choiceBox.getValue());
                Main.getInstance().searchController.refresh(Main.getInstance().searchController.searchBar.getText());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });


        pane.getChildren().add(productImage);
        pane.getChildren().add(title);
        pane.getChildren().add(brand);
        pane.getChildren().add(price);
        pane.getChildren().add(category);
        pane.getChildren().add(sellBy);
        pane.getChildren().add(remove);
        return pane;
    }
}

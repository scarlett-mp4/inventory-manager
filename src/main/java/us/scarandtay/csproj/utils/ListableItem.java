package us.scarandtay.csproj.utils;

import org.joda.time.LocalDate;
import us.scarandtay.csproj.Main;

import java.io.File;
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
}

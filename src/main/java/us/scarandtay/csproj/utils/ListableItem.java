package us.scarandtay.csproj.utils;

import java.time.LocalDate;

public class ListableItem {

    private final String name;
    private final String brand;
    private final Category category;
    private final LocalDate expirationDate;
    private final double price;
    private final boolean inStock;


    public ListableItem(String name, String brand, Category category, LocalDate expirationDate, double price, boolean inStock) {
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.expirationDate = expirationDate;
        this.price = price;
        this.inStock = inStock;
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
}

package us.scarandtay.csproj.api;

import org.joda.time.LocalDate;
import us.scarandtay.csproj.Main;
import us.scarandtay.csproj.utils.Category;
import us.scarandtay.csproj.utils.ListableItem;

import java.io.File;
import java.util.ArrayList;

public class Api {

    private static void create(String name, String brand, Category category, LocalDate expirationDate, File image, double price, boolean inStock) {
        ListableItem item = new ListableItem(name, brand, category, expirationDate, image, price, inStock);
        Main.getInstance().save.addItem(item);
    }

    public static ArrayList<ListableItem> getItems() {
        return Main.getInstance().memoryItemsList;
    }

    public static QueueAction createItem(String name, String brand, Category category, LocalDate expirationDate, File image, double price, boolean inStock) {
        return new QueueAction() {
            @Override
            public void queue() {
                Main.getInstance().fileQueue.add(() -> {
                    create(name, brand, category, expirationDate, image, price, inStock);
                });
            }

            @Override
            public void complete() {

            }
        };
    }

}

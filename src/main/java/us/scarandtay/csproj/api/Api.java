package us.scarandtay.csproj.api;

import org.joda.time.LocalDate;
import us.scarandtay.csproj.Main;
import us.scarandtay.csproj.utils.Category;
import us.scarandtay.csproj.utils.ListableItem;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class Api {

    public static ArrayList<ListableItem> getItems() {
        return Main.getInstance().memoryItemsList;
    }

    public static ArrayList<ListableItem> getItems(Category category) {
        ArrayList<ListableItem> list = new ArrayList<>();
        if (Main.getInstance().memoryItemsList.size() > 0)
            for (ListableItem item : Main.getInstance().memoryItemsList)
                if (item.getCategory() == category)
                    list.add(item);
        return list;
    }

    public static QueueAction createItem(String name, String brand, Category category, LocalDate expirationDate, File image, double price, boolean inStock) {
        return () -> Main.getInstance().fileQueue.add(() -> {
            ListableItem item = new ListableItem(name, brand, category, expirationDate, image, price, inStock);
            Main.getInstance().save.addItem(item);
            System.out.println("Item Queued");
        });
    }

    public static QueueAction removeItem(UUID uuid) {
        return () -> Main.getInstance().fileQueue.add(() -> {
            ListableItem item = null;
            for (ListableItem i : Main.getInstance().memoryItemsList) {
                if (i.getUUID() == uuid)
                    item = i;
            }

            Main.getInstance().save.removeItem(item);
            Main.getInstance().memoryItemsList.remove(item);
            System.out.println("Item Queued");
        });
    }

}

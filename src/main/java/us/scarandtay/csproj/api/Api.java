package us.scarandtay.csproj.api;

import org.joda.time.LocalDate;
import us.scarandtay.csproj.Main;
import us.scarandtay.csproj.utils.Category;
import us.scarandtay.csproj.utils.ListableItem;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

//          _________   ________________________  ____
//         \_   ___ \ /   _____/\_____  \   _  \/_   |
//         /    \  \/ \_____  \  /  ____/  /_\  \|   |
//         \     \____/        \/       \  \_/   \   |
//          \______  /_______  /\_______ \_____  /___|
//                 \/        \/         \/     \/
//                      Inventory Manager
//        Written by: Scarlett Kadan & Taylor Washington
public class Api {

    // Returns a list of ListableItems from memory.
    public static ArrayList<ListableItem> getItems() {
        return Main.getInstance().memoryItemsList;
    }

    // Create a ListableItem
    // Adds to memory and config
    // > Queued
    public static QueueAction createItem(String name, String brand, Category category, LocalDate expirationDate, File image, double price, boolean inStock) {
        return () -> Main.getInstance().fileQueue.add(() -> {
            ListableItem item = new ListableItem(name, brand, category, expirationDate, image, price, inStock);
            Main.getInstance().save.addItem(item);
        });
    }

    // Remove a ListableItem
    // Removes from memory and config
    // > Queued
    public static QueueAction removeItem(UUID uuid) {
        return () -> Main.getInstance().fileQueue.add(() -> {
            ListableItem item = null;
            for (ListableItem i : Main.getInstance().memoryItemsList) {
                if (i.getUUID() == uuid)
                    item = i;
            }

            Main.getInstance().save.removeItem(item);
            Main.getInstance().memoryItemsList.remove(item);
        });
    }
}
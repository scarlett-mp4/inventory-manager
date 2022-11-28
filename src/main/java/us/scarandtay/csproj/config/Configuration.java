package us.scarandtay.csproj.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.joda.time.LocalDate;
import us.scarandtay.csproj.Launcher;
import us.scarandtay.csproj.Main;
import us.scarandtay.csproj.utils.Category;
import us.scarandtay.csproj.utils.ListableItem;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

//          _________   ________________________  ____
//         \_   ___ \ /   _____/\_____  \   _  \/_   |
//         /    \  \/ \_____  \  /  ____/  /_\  \|   |
//         \     \____/        \/       \  \_/   \   |
//          \______  /_______  /\_______ \_____  /___|
//                 \/        \/         \/     \/
//                      Inventory Manager
//        Written by: Scarlett Kadan & Taylor Washington
//
// Configuration/save solution.
// Utilizes Google's GSON library.
public class Configuration {

    // Private variables
    private final File config;          // Base .json file
    private final File resourceFolder;  // Folder where "config" lives

    // - Constructor -
    // Defines the config and resourceFolder.
    public Configuration(String path) {
        // Dynamically finds path of where to store the config.
        // Backslashes are placed with slashes and passed to the file,
        // This allows for Linux and Mac compatibility.
        Path parentPath = Path.of(String.valueOf(Launcher.class.getProtectionDomain().
                getCodeSource().getLocation()).substring(6)).getParent();
        String resourceFolderPath = parentPath.toString() + "\\configuration";
        String configPath = resourceFolderPath + "\\" + path;
        resourceFolderPath = "/" + resourceFolderPath.replaceAll("\\\\", "/");
        configPath = "/" + configPath.replaceAll("\\\\", "/");

        // Files are defined with paths
        this.resourceFolder = new File(resourceFolderPath);
        this.config = new File(configPath);
    }

    // Removes the item from the config file
    public void removeItem(ListableItem item) {
        // Possibility of failing, so try/catch is necessary
        try {
            // Reads the JSON currently in the file and grab the items array
            // File has not been modified yet
            JsonObject base = Main.getInstance().gson.fromJson(Files.readString(config.toPath()), JsonObject.class);
            JsonArray arr = base.getAsJsonArray("items");
            boolean isFileModified = false;

            // Attempts to find the requested item for deletion
            if (arr.size() > 0) {
                for (int i = 0; i < arr.size(); i++) {
                    JsonObject object = arr.get(i).getAsJsonObject();
                    // If found, file is removed from JSON and file is queued to be modified
                    if (Objects.equals(item.getUUID().toString(), object.get("uuid").getAsString())) {
                        arr.remove(i);
                        isFileModified = true;

                    }
                }
            }

            // If modified, current JSON in config file will be overwritten
            // with new JSON data.
            if (isFileModified) {
                JsonObject newObj = new JsonObject();
                newObj.add("items", arr);

                FileWriter writer = new FileWriter(config);
                writer.write(newObj.toString());
                writer.close();
            } else {
                System.out.println("An item that doesn't exist was attempted to be removed!");
            }
        } catch (Exception e) {     // If any exception is encountered, a stacktrace will print.
            e.printStackTrace();
        }
    }

    // Adds an item to the config file
    public void addItem(ListableItem item) {
        // Possibility of failing, so try/catch is necessary
        try {
            // Reads the JSON currently in the file and grab the items array
            JsonObject base = Main.getInstance().gson.fromJson(Files.readString(config.toPath()), JsonObject.class);
            JsonArray arr = base.getAsJsonArray("items");

            // If ListableItem is found in array, it will be removed.
            if (arr.size() > 0) {
                for (int i = 0; i < arr.size(); i++) {
                    JsonObject object = arr.get(i).getAsJsonObject();
                    if (Objects.equals(item.getUUID().toString(), object.get("uuid").getAsString())) {
                        arr.remove(i);
                    }
                }
            }

            // Sets a default image if nothing is set.
            if (item.getImage() == null) {
                File file = new File("src/main/resources/us/scarandtay/icons/circle-plus-solid.png");
                item.setImage(file);
            }

            // Writes the JSON Object and adds it to the array.
            JsonObject itemObject = new JsonObject();
            itemObject.addProperty("name", item.getName());
            itemObject.addProperty("brand", item.getBrand());
            itemObject.addProperty("category", item.getCategory().toString());
            itemObject.addProperty("expirationDate", item.getExpirationDate().toString());
            itemObject.addProperty("image", item.getImage().getAbsolutePath());
            itemObject.addProperty("price", item.getPrice());
            itemObject.addProperty("inStock", item.isInStock());
            itemObject.addProperty("uuid", item.getUUID().toString());
            arr.add(itemObject);

            // Writes the new JSON Object to the config file
            JsonObject newObj = new JsonObject();
            newObj.add("items", arr);
            FileWriter writer = new FileWriter(config);
            writer.write(newObj.toString());
            writer.close();
        } catch (Exception e) {     // If any exception is encountered, a stacktrace will print.
            e.printStackTrace();
        }
    }

    // Initializes the config
    public void initConfig() {
        // Possibility of failing, so try/catch is necessary
        try {
            // If resourceFolder does not exist, make it.
            if (!resourceFolder.exists())
                resourceFolder.mkdir();

            // IF config file does not exist, make it and add a default JSON Object.
            if (!config.exists()) {
                config.createNewFile();
                FileWriter writer = new FileWriter(config);
                JsonObject obj = new JsonObject();
                obj.add("items", new JsonArray());
                writer.write(obj.toString());
                writer.close();
            }
        } catch (Exception e) {     // If any exception is encountered, a stacktrace will print.
            e.printStackTrace();
        }

        // Possibility of failing, so try/catch is necessary
        // Next try block adds the ListableItems in config to memory.
        try {
            // Reads the JSON currently in the file and grab the items array
            JsonObject base = Main.getInstance().gson.fromJson(Files.readString(config.toPath()), JsonObject.class);
            JsonArray arr = base.getAsJsonArray("items");

            // Each item from config is read, created, and added to memory.
            // This allows for fast item sorting.
            if (arr.size() > 0) {
                for (int i = 0; i < arr.size(); i++) {
                    JsonObject object = arr.get(i).getAsJsonObject();
                    String name = object.get("name").getAsString();
                    String brand = object.get("brand").getAsString();
                    Category category = Category.valueOf(object.get("category").getAsString());
                    File image = new File(object.get("image").getAsString());
                    LocalDate expirationDate = LocalDate.parse(object.get("expirationDate").getAsString());
                    UUID uuid = UUID.fromString(object.get("uuid").getAsString());
                    double price = object.get("price").getAsDouble();
                    boolean inStock = object.get("inStock").getAsBoolean();

                    new ListableItem(name, brand, category, expirationDate, image, price, inStock, uuid);
                }
            }
        } catch (Exception e) {     // If any exception is encountered, a stacktrace will print.
            e.printStackTrace();
        }
    }
}
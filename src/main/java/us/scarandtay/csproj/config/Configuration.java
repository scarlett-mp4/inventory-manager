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

public class Configuration {
    private final File config;
    private final File resourceFolder;

    public Configuration(String path) {
        Path parentPath = Path.of(String.valueOf(Launcher.class.getProtectionDomain().
                getCodeSource().getLocation()).substring(6)).getParent();
        String resourceFolderPath = parentPath.toString() + "\\configuration";
        String configPath = resourceFolderPath + "\\" + path;
        resourceFolderPath = "/" + resourceFolderPath.replaceAll("\\\\", "/");
        configPath = "/" + configPath.replaceAll("\\\\", "/");

        this.resourceFolder = new File(resourceFolderPath);
        this.config = new File(configPath);
    }

    public boolean itemExists(ListableItem item) {
        try {
            JsonObject base = Main.getInstance().gson.fromJson(Files.readString(config.toPath()), JsonObject.class);
            JsonArray arr = base.getAsJsonArray("items");

            if (arr.size() > 0) {
                for (int i = 0; i < arr.size(); i++) {
                    JsonObject object = arr.get(i).getAsJsonObject();
                    if (Objects.equals(item.getUUID().toString(), object.get("uuid").getAsString()))
                        return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void removeItem(ListableItem item) {
        try {
            JsonObject base = Main.getInstance().gson.fromJson(Files.readString(config.toPath()), JsonObject.class);
            JsonArray arr = base.getAsJsonArray("items");
            boolean isFileModified = false;

            if (arr.size() > 0) {
                for (int i = 0; i < arr.size(); i++) {
                    JsonObject object = arr.get(i).getAsJsonObject();
                    if (Objects.equals(item.getUUID().toString(), object.get("uuid").getAsString())) {
                        arr.remove(i);
                        isFileModified = true;

                    }
                }
            }

            if (isFileModified) {
                JsonObject newObj = new JsonObject();
                newObj.add("items", arr);

                FileWriter writer = new FileWriter(config);
                writer.write(newObj.toString());
                writer.close();
            } else {
                System.out.println("An item that doesn't exist was attempted to be removed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addItem(ListableItem item) {
        try {
            JsonObject base = Main.getInstance().gson.fromJson(Files.readString(config.toPath()), JsonObject.class);
            JsonArray arr = base.getAsJsonArray("items");

            if (arr.size() > 0) {
                for (int i = 0; i < arr.size(); i++) {
                    JsonObject object = arr.get(i).getAsJsonObject();
                    if (Objects.equals(item.getUUID().toString(), object.get("uuid").getAsString())) {
                        arr.remove(i);
                    }
                }
            }

            if (item.getImage() == null) {
                File file = new File("src/main/resources/us/scarandtay/icons/circle-plus-solid.png");
                item.setImage(file);
            }

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

            JsonObject newObj = new JsonObject();
            newObj.add("items", arr);

            FileWriter writer = new FileWriter(config);
            writer.write(newObj.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initConfig() {
        try {
            if (!resourceFolder.exists())
                resourceFolder.mkdir();
            if (!config.exists()) {
                config.createNewFile();
                FileWriter writer = new FileWriter(config);
                JsonObject obj = new JsonObject();
                obj.add("items", new JsonArray());
                writer.write(obj.toString());
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            JsonObject base = Main.getInstance().gson.fromJson(Files.readString(config.toPath()), JsonObject.class);
            JsonArray arr = base.getAsJsonArray("items");

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package us.scarandtay.csproj;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import us.scarandtay.csproj.config.Configuration;
import us.scarandtay.csproj.controller.AddController;
import us.scarandtay.csproj.controller.HomeController;
import us.scarandtay.csproj.controller.SearchController;
import us.scarandtay.csproj.utils.ListableItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

//          _________   ________________________  ____
//         \_   ___ \ /   _____/\_____  \   _  \/_   |
//         /    \  \/ \_____  \  /  ____/  /_\  \|   |
//         \     \____/        \/       \  \_/   \   |
//          \______  /_______  /\_______ \_____  /___|
//                 \/        \/         \/     \/
//                      Inventory Manager
//        Written by: Scarlett Kadan & Taylor Washington
public class Main extends Application {

    // Main Instance; Defined in application initialization.
    private static Main instance;
    public static Main getInstance() {
        return instance;
    }

    // Public variables
    public Configuration save = new Configuration("save.json");     // Save configuration
    public LinkedList<Runnable> fileQueue = new LinkedList<>();          // List of runnables to combat concurrent file modification
    public Thread queuedThread;                                          // Thread where runnables are ran
    public ArrayList<ListableItem> memoryItemsList = new ArrayList<>();  // Inventory items kept here in memory
    public Scene home;                                                   // The home scene
    public Scene add;                                                    // The add scene
    public Scene search;                                                 // The search scene
    public HomeController homeController;                                // Controls home scene nodes
    public SearchController searchController;                            // Controls search scene nodes
    public Gson gson;                                                    // Google Json (GSON) instance for storage
    public Stage stage;                                                  // JavaFX main stage (window)

    // In the beginning, there was public static void main(String[] args)...
    // Initializes JavaFX project (runs "start" method)
    public static void main(String[] args) {
        launch();
    }

    // Initializes and displays stage to user
    // Initializes and starts backend applications
    @Override
    public void start(Stage stage) throws IOException {
        // Defines undeclared public variables
        instance = this;
        gson = new GsonBuilder().create();
        this.stage = stage;
        initConfig();
        initQueue();

        // Defines undeclared public scenes
        home = new Scene(FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("home.fxml"))), 1100, 600);
        add = new Scene(FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("add.fxml"))), 1100, 600);
        search = new Scene(FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("search.fxml"))), 1100, 600);

        // Sets a transparent background for rounded borders
        home.setFill(Color.TRANSPARENT);
        add.setFill(Color.TRANSPARENT);
        search.setFill(Color.TRANSPARENT);

        // Finalizes and shows stage to user
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setTitle("CS201 Item Manager");
        stage.setScene(home);
        stage.show();
    }

    // Initializes the config
    private void initConfig() {
        save.initConfig();
    }

    // Initializes the queue
    private void initQueue() {
        // Below, a new thread is created where it constantly checks
        // to see if there is a runnable of index [0] in fileQueue.
        // If found, the runnable is ran and then removed from the list.
        queuedThread = new Thread(() -> {
            while (true) {
                try {
                    if (fileQueue.get(0) != null) {
                        fileQueue.get(0).run();
                        fileQueue.remove(0);
                    }
                } catch (Exception ignored) {
                }
            }
        });
        queuedThread.start();
    }
}
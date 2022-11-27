package us.scarandtay.csproj;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import us.scarandtay.csproj.config.Configuration;
import us.scarandtay.csproj.utils.ListableItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

public class Main extends Application {
    private static Main instance;
    public Configuration save = new Configuration("save.json");
    public LinkedList<Runnable> fileQueue = new LinkedList<>();
    public Thread queuedThread;
    public ArrayList<ListableItem> memoryItemsList = new ArrayList<>();
    public Scene home;
    public Scene add;
    public Scene search;
    public Gson gson;
    public Stage stage;

    public static void main(String[] args) {
        launch();
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void start(Stage stage) throws IOException {
        instance = this;
        gson = new GsonBuilder().create();
        this.stage = stage;
        initConfig();
        initQueue();

        home = new Scene(FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("home.fxml"))), 1100, 600);
        add = new Scene(FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("add.fxml"))), 1100, 600);
        search = new Scene(FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("search.fxml"))), 1100, 600);

        home.setFill(Color.TRANSPARENT);
        add.setFill(Color.TRANSPARENT);
        search.setFill(Color.TRANSPARENT);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setTitle("CS201 Item Manager");
        stage.setScene(home);
        stage.show();
    }

    private void initConfig() {
        save.initConfig();
    }

    private void initQueue() {
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
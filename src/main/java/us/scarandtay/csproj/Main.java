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
import org.joda.time.LocalDate;
import us.scarandtay.csproj.api.Api;
import us.scarandtay.csproj.config.Configuration;
import us.scarandtay.csproj.utils.Category;
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

    public static void main(String[] args) {
        launch();
    }

    public static Main getInstance() {
        return instance;
    }
    private double windowPositionX, windowPositionY;
    public Parent root;
    public Gson gson;

    @Override
    public void start(Stage stage) throws IOException {
        instance = this;
        gson = new GsonBuilder().create();
        initConfig();
        initQueue();

        this.root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("metroPane.fxml")));
        Scene scene = new Scene(root, 1100, 600);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setTitle("CS201 Item Manager");
        stage.setScene(scene);
        stage.show();


    }

    private void initConfig() {
        save.initConfig();
    }

    private void initQueue() {
        new Thread(() -> {
            while (true) {
                try {
                    if (fileQueue.get(0) != null) {
                        fileQueue.get(0).run();
                        fileQueue.remove(0);
                    }
                } catch (Exception ignored) {
                }
            }
        }).start();
    }
}
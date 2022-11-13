package us.scarandtay.csproj;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import us.scarandtay.csproj.api.Api;
import us.scarandtay.csproj.config.Configuration;
import us.scarandtay.csproj.utils.ListableItem;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class Main extends Application {
    private static Main instance;
    public Configuration save = new Configuration("save.json");
    public Queue<Runnable> fileQueue = new LinkedList<>();
    public Thread queuedThread;
    public ArrayList<ListableItem> memoryItemsList = new ArrayList<>();


    public static void main(String[] args) {
        launch();
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void start(Stage stage) throws IOException {
        instance = this;
        initConfig();
        initQueue();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    private void initConfig() {
        save.initConfig();
    }

    private void initQueue() {
        queuedThread = new Thread(() -> {
            while (true) {
                try {
                    if (!fileQueue.isEmpty()) {
                        fileQueue.peek().run();
                        fileQueue.poll();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        queuedThread.start();
    }
}
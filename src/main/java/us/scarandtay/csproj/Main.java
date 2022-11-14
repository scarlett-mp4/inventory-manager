package us.scarandtay.csproj;

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
    private double windowPositionX, windowPositionY;
    public Parent root;

    @Override
    public void start(Stage stage) throws IOException {
        instance = this;
        initConfig();
        initQueue();

        this.root = FXMLLoader.load(Main.class.getResource("metroPane.fxml"));
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
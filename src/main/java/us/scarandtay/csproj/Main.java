package us.scarandtay.csproj;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import us.scarandtay.csproj.config.Configuration;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Callable;

public class Main extends Application {
    private static Main instance;
    public Configuration save = new Configuration("save.json");
    public Queue<Callable<String>> fileQueue = new LinkedList<>();
    public Thread queuedThread;

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
                        fileQueue.peek().call();
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
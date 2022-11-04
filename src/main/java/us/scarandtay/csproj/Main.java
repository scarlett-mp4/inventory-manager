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
    Configuration save = new Configuration("save.json");
    Queue<Callable<String>> fileQueue = new LinkedList<>();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        initConfig();

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
        new Thread(() -> {
            while(true) {
                try {
                    if (!fileQueue.isEmpty()) {
                        fileQueue.peek().call();
                        fileQueue.poll();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
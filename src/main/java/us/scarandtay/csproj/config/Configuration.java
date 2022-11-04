package us.scarandtay.csproj.config;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class Configuration {

    private final String configPath;
    private final String resourceFolderPath;
    private final File config;
    private final File resourceFolder;

    public Configuration(String path) {
        this.configPath = "src/main/resources/configuration/" + path;
        this.resourceFolderPath = "src/main/resources/configuration";
        this.resourceFolder = new File(resourceFolderPath);
        this.config = new File(configPath);
    }

    public void initConfig() {
        try {
            if (!resourceFolder.exists())
                resourceFolder.mkdir();
            if (!config.exists())
                config.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

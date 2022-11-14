module us.scarandtay.csproj {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.joda.time;

    opens us.scarandtay.csproj to javafx.fxml;
    exports us.scarandtay.csproj;
    exports us.scarandtay.csproj.controller;
    opens us.scarandtay.csproj.controller to javafx.fxml;
}
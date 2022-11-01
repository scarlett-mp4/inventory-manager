module us.scarandtay.csproj {
    requires javafx.controls;
    requires javafx.fxml;


    opens us.scarandtay.csproj to javafx.fxml;
    exports us.scarandtay.csproj;
}
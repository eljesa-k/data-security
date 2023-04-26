module com.example.datasecurityjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.datasecurityjavafx to javafx.fxml;
    exports com.example.datasecurityjavafx;
}
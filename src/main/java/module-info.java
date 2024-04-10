module com.testjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.testjavafx to javafx.fxml;
    exports com.testjavafx;
}
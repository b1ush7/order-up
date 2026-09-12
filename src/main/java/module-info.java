module com.orderup.orderup {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    exports com.orderup;
    exports com.orderup.controller;
    exports com.orderup.view;
    opens com.orderup.controller to javafx.fxml;
    opens com.orderup.view to javafx.fxml;
}

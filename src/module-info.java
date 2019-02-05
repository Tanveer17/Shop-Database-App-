module ShopApp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.tanveer;
    opens com.tanveer.DialogControllers;
    opens com.tanveer.entities;
    opens com.tanveer.fxml;
}
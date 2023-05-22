module com.splitpay.splitpay {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.dotenv;


    opens com.splitpay.splitpay to javafx.fxml;
    exports com.splitpay.splitpay;

    opens com.splitpay.splitpay.entities to javafx.fxml;
    exports com.splitpay.splitpay.entities;
    exports com.splitpay.splitpay.events;
    opens com.splitpay.splitpay.events to javafx.fxml;
}
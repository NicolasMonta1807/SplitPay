module com.splitpay.splitpay {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.splitpay.splitpay to javafx.fxml;
    exports com.splitpay.splitpay;

    opens com.splitpay.splitpay.entities to javafx.fxml;
    exports com.splitpay.splitpay.entities;
}
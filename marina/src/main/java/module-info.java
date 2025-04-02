module com.marina {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    opens com.marina to javafx.fxml;
    exports com.marina;
}

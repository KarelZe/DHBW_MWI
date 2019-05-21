module anika {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;

    opens de.dhbw.karlsruhe.controller to javafx.fxml;
    exports de.dhbw.karlsruhe;
}

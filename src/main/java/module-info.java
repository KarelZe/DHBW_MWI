module anika {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens de.dhbw.karlsruhe.controller to javafx.fxml;
    exports de.dhbw.karlsruhe;
}

module anika {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens de.dhbw.karlsruhe to javafx.fxml;
    exports de.dhbw.karlsruhe;
}

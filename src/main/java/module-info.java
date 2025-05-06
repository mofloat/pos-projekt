module at.spengergasse.posprojekt {
    requires javafx.controls;
    requires javafx.fxml;


    opens at.spengergasse.posprojekt to javafx.fxml;
    exports at.spengergasse.posprojekt;
}
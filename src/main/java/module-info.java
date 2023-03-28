module nl.saxion.re.ecrcenergizesysteem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.desktop;

    opens nl.saxion.re.ecrcenergizesysteem to javafx.fxml;
    exports nl.saxion.re.ecrcenergizesysteem;
}
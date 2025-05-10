module no.hassle {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.swing;

    opens io.github.jagodevreede.demo.nohassle to javafx.fxml, javafx.graphics;
}
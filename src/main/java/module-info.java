module com.example.parisroutefinder {
    requires javafx.controls;
    requires javafx.fxml;
    requires jmh.core;
    requires jmh.generator.annprocess;

    opens com.example.parisroutefinder to javafx.fxml,jmh.core,jmh.generator.annprocess;
    exports com.example.parisroutefinder;
    exports com.example.parisroutefinder.jmh_generated to jmh.core;
}
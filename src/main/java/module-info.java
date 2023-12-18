module com.example.taskmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires java.net.http;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    exports com.example.taskmanager.models to com.fasterxml.jackson.databind;
    opens com.example.taskmanager.models to com.fasterxml.jackson.databind;
    opens com.example.taskmanager to javafx.fxml;
    exports com.example.taskmanager;
}
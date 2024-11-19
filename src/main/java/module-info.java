module co.edu.uptc.taller {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.jgrapht.core;

    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires java.logging;

    opens co.edu.uptc.taller.controller to javafx.fxml;
    opens co.edu.uptc.taller.persistence to com.fasterxml.jackson.databind;
    opens co.edu.uptc.taller.model to com.fasterxml.jackson.databind;
    exports co.edu.uptc.taller.view;
}

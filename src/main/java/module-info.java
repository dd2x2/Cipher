module ru.dverkask.monoalphabeticcipher {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    exports ru.dverkask.cipher.javafx;
    opens ru.dverkask.cipher.javafx to javafx.fxml;
    exports ru.dverkask.cipher.javafx.controllers;
    opens ru.dverkask.cipher.javafx.controllers to javafx.fxml;
    exports ru.dverkask.cipher;
    opens ru.dverkask.cipher to javafx.fxml;
}
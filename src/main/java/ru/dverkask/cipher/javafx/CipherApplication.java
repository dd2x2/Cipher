package ru.dverkask.cipher.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.dverkask.cipher.PolySingleAlphabeticCipher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.dverkask.cipher.PolySingleAlphabeticCipher.*;

public class CipherApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CipherApplication.class.getResource("monoalphabeticcipher.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1124, 720);
        stage.setTitle("123");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
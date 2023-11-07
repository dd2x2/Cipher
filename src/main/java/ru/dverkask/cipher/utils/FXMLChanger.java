package ru.dverkask.cipher.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import ru.dverkask.cipher.javafx.CipherApplication;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FXMLChanger {
    public static void loadFxml(String fxmlFile, ChoiceBox<String> cipherType) {
        try {
            FXMLLoader loader = new FXMLLoader(CipherApplication.class.getResource(fxmlFile));
            Scene scene = new Scene(loader.load(), 1124, 720);
            Stage stage = (Stage) cipherType.getScene().getWindow();
            stage.setTitle("123");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(FXMLChanger.class.getResource(fxmlFile));
            ex.printStackTrace();
        }
    }
}

package ru.dverkask.cipher.utils;

import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SelectedFile {
    public static void selectFile(final TextField textField) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File file = fileChooser.showOpenDialog(null);

        String text = file != null ? readFile(file)
                : null;
        if (text != null)
            textField.setText(text);
    }

    public static String readFile(final File selectedFile) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new FileReader(selectedFile))
        ) {
            String line;
            while ((line = br.readLine()) != null)
                sb.append(line).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}

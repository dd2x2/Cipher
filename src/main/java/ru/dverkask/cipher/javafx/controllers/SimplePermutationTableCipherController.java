package ru.dverkask.cipher.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import ru.dverkask.cipher.SimplePermutationNewCipher;
import ru.dverkask.cipher.SimplePermutationTableCipher;
import ru.dverkask.cipher.utils.CipherUIHandler;
import ru.dverkask.cipher.utils.SelectedFile;

public class SimplePermutationTableCipherController {
    @FXML private TextField additionalKey;
    @FXML private TextField rowsCount;
    @FXML private Text permutationTable;
    @FXML private TextArea resultArea;
    @FXML private TextField textKey;
    @FXML private TextField encryptInputField;
    @FXML private TextField decryptInputField;
    @FXML private ChoiceBox<String> cipherType;
    @FXML private ScrollPane scrollPane;
    @FXML private void initialize() {
        cipherType.setValue("SimplePermutationTable");
        permutationTable.setStyle("-fx-font: 28 consolas");
        CipherUIHandler.choiceCipher(cipherType);
        scrollPane.setOnMouseClicked(event -> scrollPane.requestFocus());
        scrollPane.setFocusTraversable(true);
        scrollPane.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                scrollPane.requestFocus();
                mouseEvent.consume();
            }
        });
    }

    public void encryptBtnAction(ActionEvent actionEvent) {
        int[] key = textKey.getText().chars()
                .mapToObj(Character::getNumericValue)
                .mapToInt(i -> i)
                .toArray();
        int rowCount = Integer.parseInt(rowsCount.getText());
        SimplePermutationTableCipher simplePermutationTableCipher =
                new SimplePermutationTableCipher(key,
                        rowCount,
                        SimplePermutationTableCipher.fromStringToAdditionalKey(additionalKey.getText()));

        resultArea.setText(
                simplePermutationTableCipher.encrypt(encryptInputField.getText()).replaceAll("§", "")
        );

        permutationTable.setText(simplePermutationTableCipher.buildAndPrintMultipleTranspositionTables(
                encryptInputField.getText()
        ));
    }
    public void decryptBtnAction(ActionEvent actionEvent) {
        int[] key = textKey.getText().chars()
                .mapToObj(Character::getNumericValue)
                .mapToInt(i -> i)
                .toArray();
        int rowCount = Integer.parseInt(rowsCount.getText());
        SimplePermutationTableCipher simplePermutationTableCipher =
                new SimplePermutationTableCipher(key,
                        rowCount,
                        SimplePermutationTableCipher.fromStringToAdditionalKey(additionalKey.getText()));

        resultArea.setText(
                simplePermutationTableCipher.decrypt(decryptInputField.getText()).replaceAll("§", "")
        );

        permutationTable.setText(simplePermutationTableCipher.buildAndPrintMultipleTranspositionTables(
                simplePermutationTableCipher.decrypt(decryptInputField.getText())
        ));
    }
    public void selectEncryptFile(ActionEvent actionEvent) {
        SelectedFile.selectFile(encryptInputField);
    }
    public void selectDecryptFile(ActionEvent actionEvent) {
        SelectedFile.selectFile(decryptInputField);
    }
}

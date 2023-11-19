package ru.dverkask.cipher.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ru.dverkask.cipher.SimplePermutationNewCipher;
import ru.dverkask.cipher.utils.CipherUIHandler;
import ru.dverkask.cipher.utils.SelectedFile;

public class SimplePermutationNewController {
    @FXML private TextField rowsCount;
    @FXML private Text permutationTable;
    @FXML private TextArea resultArea;
    @FXML private TextField textKey;
    @FXML private TextField encryptInputField;
    @FXML private TextField decryptInputField;
    @FXML private ChoiceBox<String> cipherType;
    @FXML private void initialize() {
        cipherType.setValue("SimplePermutation");
        permutationTable.setStyle("-fx-font: 28 consolas");
        CipherUIHandler.choiceCipher(cipherType);
    }

    public void encryptBtnAction(ActionEvent actionEvent) {
        int[] key = textKey.getText().chars()
                .mapToObj(Character::getNumericValue)
                .mapToInt(i -> i)
                .toArray();
        int rowCount = Integer.parseInt(rowsCount.getText());
        SimplePermutationNewCipher simplePermutationNewCipher =
                new SimplePermutationNewCipher(key, rowCount);

        resultArea.setText(
                simplePermutationNewCipher.encrypt(encryptInputField.getText()).replaceAll("§", "")
        );

        permutationTable.setText(SimplePermutationNewCipher.printTranspositionTables(
                simplePermutationNewCipher.getTranspositionTables(encryptInputField.getText()), key));
    }
    public void decryptBtnAction(ActionEvent actionEvent) {
        int[] key = textKey.getText().chars()
                .mapToObj(Character::getNumericValue)
                .mapToInt(i -> i)
                .toArray();
        int rowCount = Integer.parseInt(rowsCount.getText());
        SimplePermutationNewCipher simplePermutationNewCipher =
                new SimplePermutationNewCipher(key, rowCount);

        resultArea.setText(
                simplePermutationNewCipher.decrypt(decryptInputField.getText())
        );
    }
    public void selectEncryptFile(ActionEvent actionEvent) {
        SelectedFile.selectFile(encryptInputField);
    }
    public void selectDecryptFile(ActionEvent actionEvent) {
        SelectedFile.selectFile(decryptInputField);
    }
}

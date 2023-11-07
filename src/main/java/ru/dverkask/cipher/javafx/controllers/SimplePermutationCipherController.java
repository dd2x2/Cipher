package ru.dverkask.cipher.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ru.dverkask.cipher.SimplePermutationCipher;
import ru.dverkask.cipher.utils.CipherUIHandler;
import ru.dverkask.cipher.utils.SelectedFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SimplePermutationCipherController {
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
        SimplePermutationCipher simplePermutationCipher =
                new SimplePermutationCipher(key);

        resultArea.setText(
                simplePermutationCipher.encrypt(encryptInputField.getText()).replaceAll("§", "")
        );

        permutationTable.setText(Arrays.toString(key).substring(1, Arrays.toString(key).length()-1).replaceAll(",", "") + "\n" +
                Arrays.stream(simplePermutationCipher.encryptionMatrix(encryptInputField.getText()))
                        .map(row -> String.join(" ", Arrays.toString(row).substring(1, Arrays.toString(key).length()-1).replaceAll("§", "").replaceAll(",", "")))
                        .collect(Collectors.joining("\n")));
    }
    public void decryptBtnAction(ActionEvent actionEvent) {
        SimplePermutationCipher simplePermutationCipher =
                new SimplePermutationCipher(textKey.getText().chars()
                        .mapToObj(Character::getNumericValue)
                        .mapToInt(i -> i)
                        .toArray());

        resultArea.setText(
                simplePermutationCipher.decrypt(decryptInputField.getText())
        );
    }

    public void selectEncryptFile(ActionEvent actionEvent) {
        SelectedFile.selectFile(encryptInputField);
    }
    public void selectDecryptFile(ActionEvent actionEvent) {
        SelectedFile.selectFile(decryptInputField);
    }

}

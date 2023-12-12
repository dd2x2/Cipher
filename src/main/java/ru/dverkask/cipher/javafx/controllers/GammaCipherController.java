package ru.dverkask.cipher.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ru.dverkask.cipher.GammaCipher;
import ru.dverkask.cipher.utils.CipherUIHandler;
import ru.dverkask.cipher.utils.SelectedFile;

public class GammaCipherController {
    @FXML private TextArea resultArea;
    @FXML private TextField textKey;
    @FXML private ChoiceBox<String> choiceKey;
    @FXML private TextField encryptInputField;
    @FXML private TextField decryptInputField;
    @FXML private ChoiceBox<String> cipherType;
    @FXML private Text binaryInfo;
    @FXML private void initialize() {
        initializeGammaCipherData();
        binaryInfo.setText("Таблица Виженера: " + "\n");
        binaryInfo.setStyle("-fx-font: 16 consolas");

        binaryInfo.setText("Двоично11е представление символов: " + "\n" + GammaCipher.getInfo());

        textKey.setDisable(false);

        CipherUIHandler.choiceCipher(cipherType);
    }
    public void encryptBtnAction(ActionEvent actionEvent) {
        GammaCipher gammaCipher = new GammaCipher(textKey.getText());

        resultArea.setText(
                gammaCipher.xor(encryptInputField.getText())
        );
    }
    public void decryptBtnAction(ActionEvent actionEvent) {
        GammaCipher gammaCipher = new GammaCipher(textKey.getText());

        resultArea.setText(
                gammaCipher.xor(decryptInputField.getText())
        );
    }

    public void selectEncryptFile(ActionEvent actionEvent) {
        SelectedFile.selectFile(encryptInputField);
    }
    public void selectDecryptFile(ActionEvent actionEvent) {
        SelectedFile.selectFile(decryptInputField);
    }
    public void initializeGammaCipherData() {
        choiceKey.setValue("Ключ");
        cipherType.setValue("Gamma");
    }
}

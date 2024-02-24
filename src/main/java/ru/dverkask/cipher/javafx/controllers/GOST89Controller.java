package ru.dverkask.cipher.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ru.dverkask.cipher.course.GOST89;
import ru.dverkask.cipher.course.utils.BitUtil;
import ru.dverkask.cipher.course.utils.GOST89Info;
import ru.dverkask.cipher.utils.CipherUIHandler;
import ru.dverkask.cipher.utils.SelectedFile;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class GOST89Controller {
    @FXML private       TextArea          resultArea;
    @FXML private TextField         textKey;
    @FXML private ChoiceBox<String> choiceKey;
    @FXML private TextField         encryptInputField;
    @FXML private TextField         decryptInputField;
    @FXML private ChoiceBox<String> cipherType;
    @FXML private Text              detailInformation;
    @FXML private void initialize() {
        initializeVigenereCipherData();
        detailInformation.setText("Детальная информация: " + "\n");
        detailInformation.setStyle("-fx-font: 16 consolas");
        textKey.setDisable(false);

        CipherUIHandler.choiceCipher(cipherType);
    }

    public void encryptBtnAction(ActionEvent actionEvent) {
        detailInformation.setText("");
        GOST89Info.clearInfo();
        String keyStr = textKey.getText();
        GOST89Info.append("ШИФРОВАНИЕ 32-З: K0, K1, K2, K3, K4, K5, K6, K7, K0, K1, K2, K3, K4, K5, K6, K7, K0, K1, K2, K3, K4, K5, K6, K7, K7, K6, K5, K4, K3, K2, K1, K0." + "\n");
        GOST89Info.append("Исходный ключ в текстовом виде: " + keyStr + "\n");

        byte[] key = keyStr.getBytes();

        GOST89Info.append("Исходный ключ в байтовом виде (от -127 до 127): " + Arrays.toString(key) + "\n");
        GOST89Info.append("Исходный ключ в байтовом виде (от 0 до 255): " + BitUtil.Print.printBytes(key) + "\n");

        byte[] normalizedKey = new byte[32];
        System.arraycopy(key, 0, normalizedKey, 0, Math.min(key.length, 32));
        GOST89Info.append("Исходный ключ, приведённый к нужному виду: " + Arrays.toString(normalizedKey) + "\n");

        GOST89 gost89 = new GOST89(normalizedKey);

        byte[] plaintext = encryptInputField.getText().getBytes(StandardCharsets.UTF_8);
        GOST89Info.append("Исходный текст: " + new String(plaintext, StandardCharsets.UTF_8) + "\n");
        GOST89Info.append("Исходный текст в байтовом виде (от -127 до 127): " + Arrays.toString(plaintext) + "\n");
        GOST89Info.append("Исходный текст в байтовом виде (от 0 до 255): " + BitUtil.Print.printBytes(plaintext) + "\n");

        byte[] paddedPlaintext = BitUtil.ByteArrays.padWithZeros(plaintext, 8);
        GOST89Info.append("Исходный текст, приведённый к нужному виду: " + Arrays.toString(paddedPlaintext) + "\n");

        GOST89Info.append("S-блок: \n" + BitUtil.Print.printSBox(gost89.getS()));

        GOST89Info.append("S-блок в двоичном виде: \n" + BitUtil.Print.printSBoxInBinary(gost89.getS()));

        byte[] encrypt = gost89.encrypt(paddedPlaintext);

        GOST89Info.append("Результат шифрования в байтовом виде (от -127 до 127): " + Arrays.toString(encrypt) + "\n");
        GOST89Info.append("Результат шифрования в байтовом виде (от 0 до 255): " + BitUtil.Print.printBytes(encrypt) + "\n");

        String encryptedText = new String(encrypt, StandardCharsets.UTF_8).replace("\0", "");
        GOST89Info.append("Результат шифрования в строковом виде: " + encryptedText + "\n");

        String base64encoded = Base64.getEncoder().encodeToString(encrypt);
        GOST89Info.append("Результат шифрования в Base64: " + base64encoded + "\n");
        resultArea.setText(base64encoded);

        detailInformation.setText("Детальная информация: " + "\n" + GOST89Info.getInfo());
    }
    public void decryptBtnAction(ActionEvent actionEvent) {
        detailInformation.setText("");
        GOST89Info.clearInfo();
        String keyStr = textKey.getText();
        GOST89Info.append("ДЕШИФРОВАНИЕ 32-Р: K0, K1, K2, K3, K4, K5, K6, K7, K7, K6, K5, K4, K3, K2, K1, K0, K7, K6, K5, K4, K3, K2, K1, K0, K7, K6, K5, K4, K3, K2, K1, K0." + "\n");
        GOST89Info.append("Исходный ключ в текстовом виде: " + keyStr + "\n");

        byte[] key = keyStr.getBytes();

        GOST89Info.append("Исходный ключ в байтовом виде (от -127 до 127): " + Arrays.toString(key) + "\n");
        GOST89Info.append("Исходный ключ в байтовом виде (от 0 до 255): " + BitUtil.Print.printBytes(key) + "\n");

        byte[] normalizedKey = new byte[32];
        System.arraycopy(key, 0, normalizedKey, 0, Math.min(key.length, 32));
        GOST89Info.append("Исходный ключ, приведённый к нужному виду: " + Arrays.toString(normalizedKey) + "\n");

        GOST89 gost89 = new GOST89(normalizedKey);

        byte[] plaintext = Base64.getDecoder().decode(decryptInputField.getText());
        GOST89Info.append("Исходный текст: " + new String(plaintext, StandardCharsets.UTF_8) + "\n");
        GOST89Info.append("Исходный текст в байтовом виде (от -127 до 127): " + Arrays.toString(plaintext) + "\n");
        GOST89Info.append("Исходный текст в байтовом виде (от 0 до 255): " + BitUtil.Print.printBytes(plaintext) + "\n");

        byte[] paddedPlaintext = BitUtil.ByteArrays.padWithZeros(plaintext, 8);
        GOST89Info.append("Исходный текст, приведённый к нужному виду: " + Arrays.toString(paddedPlaintext) + "\n");

        GOST89Info.append("S-блок: \n" + BitUtil.Print.printSBox(gost89.getS()));

        GOST89Info.append("S-блок в двоичном виде: \n" + BitUtil.Print.printSBoxInBinary(gost89.getS()));

        byte[] decrypt = gost89.decrypt(paddedPlaintext);

        GOST89Info.append("Результат дешифрования в байтовом виде (от -127 до 127): " + Arrays.toString(decrypt) + "\n");
        GOST89Info.append("Результат дешифрования в байтовом виде (от 0 до 255): " + BitUtil.Print.printBytes(decrypt) + "\n");

        String decryptedText = new String(decrypt, StandardCharsets.UTF_8).replace("\0", "");
        GOST89Info.append("Результат дешифрования в строковом виде: " + decryptedText + "\n");

        resultArea.setText(decryptedText);

        detailInformation.setText("Детальная информация: " + "\n" + GOST89Info.getInfo());
    }

    public void selectEncryptFile(ActionEvent actionEvent) {
        SelectedFile.selectFile(encryptInputField);
    }
    public void selectDecryptFile(ActionEvent actionEvent) {
        SelectedFile.selectFile(decryptInputField);
    }
    public void initializeVigenereCipherData() {
        choiceKey.setValue("Свой ключ");
        cipherType.setValue("GOST89");
    }
}

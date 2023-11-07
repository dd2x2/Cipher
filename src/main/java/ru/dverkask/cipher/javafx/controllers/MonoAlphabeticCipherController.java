package ru.dverkask.cipher.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.dverkask.cipher.AbstractCipher;
import ru.dverkask.cipher.CipherEnum;
import ru.dverkask.cipher.MonoAlphabeticCipher;
import ru.dverkask.cipher.utils.CipherUIHandler;
import ru.dverkask.cipher.utils.SelectedFile;

import java.util.List;

public class MonoAlphabeticCipherController {
    private List<Character> alphabetCharacterList = CipherEnum.RU_ALPHABET.getValueCharacterList();
    private List<Character> keyCharacterList = CipherEnum.RU_KEY.getValueCharacterList();
    @FXML private TextArea resultArea;
    @FXML private TextField textAlphabet;
    @FXML private TextField textKey;
    @FXML private ChoiceBox<String> choiceAlphabet;
    @FXML private ChoiceBox<String> choiceKey;
    @FXML private TextField encryptInputField;
    @FXML private TextField decryptInputField;
    @FXML private ChoiceBox<String> cipherType;
    @FXML private void initialize() {
        initializeMonoAlphabeticCipherData();
        textAlphabet.setDisable(false);
        CipherUIHandler.choiceCipher(cipherType);
    }

    public void setAlphabetCharacterLists(ChoiceBox<String> choiceBox) {
        if (CipherEnum.getAlphabetMap().get(choiceBox.getValue()) != null) {
            alphabetCharacterList = CipherEnum.getAlphabetMap()
                    .get(choiceBox.getValue())
                    .getValueCharacterList();
        } else {
            if (textAlphabet.getText() != null) {
                alphabetCharacterList = textAlphabet.getText().chars()
                        .mapToObj(n -> (char) n)
                        .toList();
            }
        }
    }

    public void setKeyCharacterLists(ChoiceBox<String> choiceBox) {
        if (CipherEnum.getKeyMap().get(choiceBox.getValue()) != null) {
            keyCharacterList = CipherEnum.getKeyMap()
                    .get(choiceBox.getValue())
                    .getValueCharacterList();
        } else {
            if (textKey.getText() != null) {
                keyCharacterList = textKey.getText().chars()
                        .mapToObj(n -> (char) n)
                        .toList();
            }
        }
    }
    public void encryptBtnAction(ActionEvent actionEvent) {
        setAlphabetCharacterLists(choiceAlphabet);
        setKeyCharacterLists(choiceKey);

        AbstractCipher monoAlphabeticCipher =
                new MonoAlphabeticCipher(alphabetCharacterList, keyCharacterList);

        resultArea.setText(
                monoAlphabeticCipher.encrypt(encryptInputField.getText())
        );
    }
    public void decryptBtnAction(ActionEvent actionEvent) {
        setAlphabetCharacterLists(choiceAlphabet);
        setKeyCharacterLists(choiceKey);

        AbstractCipher monoAlphabeticCipher =
                new MonoAlphabeticCipher(alphabetCharacterList, keyCharacterList);

        resultArea.setText(
                monoAlphabeticCipher.decrypt(decryptInputField.getText())
        );
    }

    public void selectEncryptFile(ActionEvent actionEvent) {
        SelectedFile.selectFile(encryptInputField);
    }
    public void selectDecryptFile(ActionEvent actionEvent) {
        SelectedFile.selectFile(decryptInputField);
    }
    public void initializeMonoAlphabeticCipherData() {
        choiceAlphabet.setValue("ruAlphabet");
        choiceKey.setValue("ruKey");
        cipherType.setValue("Mono");

        CipherUIHandler.setCustomData(choiceAlphabet, textAlphabet, choiceKey, textKey);
    }
}

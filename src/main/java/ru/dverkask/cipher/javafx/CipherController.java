package ru.dverkask.cipher.javafx;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.dverkask.cipher.AbstractCipher;
import ru.dverkask.cipher.CipherEnum;
import ru.dverkask.cipher.MonoAlphabeticCipher;
import ru.dverkask.cipher.utils.SelectedFile;

import java.util.List;

public class CipherController {
    private List<Character> alphabetCharacterList = CipherEnum.RU_ALPHABET.getValueCharacterList();
    private List<Character> keyCharacterList = CipherEnum.RU_KEY.getValueCharacterList();
    public TextArea resultArea;
    public TextField textAlphabet;
    public TextField textKey;
    @FXML
    private ChoiceBox<String> choiceAlphabet;
    @FXML
    private ChoiceBox<String> choiceKey;
    @FXML
    private TextField encryptInputField;
    @FXML
    private TextField decryptInputField;
    @FXML
    private Label ruAlphabetLabel;
    @FXML
    private Label ruKeyLabel;
    @FXML
    private Label enAlphabetLabel;
    @FXML
    private Label enKeyLabel;
    @FXML
    private void initialize() {
        choiceAlphabet.setValue("ruAlphabet");
        choiceKey.setValue("ruKey");

        choiceAlphabet.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    textAlphabet.setDisable(!newValue.equals("Свой алфавит"));
                }
        );

        choiceKey.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    textKey.setDisable(!newValue.equals("Свой ключ"));
                }
        );

        ruAlphabetLabel.setText("Русский алфавит и ключ: \n" + CipherEnum.RU_ALPHABET.getValue() + "\n" + CipherEnum.RU_KEY.getValue());
        enAlphabetLabel.setText("Английский алфавит и ключ: \n" + CipherEnum.EN_ALPHABET.getValue() + "\n" + CipherEnum.EN_KEY.getValue());
//        ruKeyLabel.setText("Русский ключ: \n" + CipherEnum.RU_KEY.getValue());
//        enAlphabetLabel.setText("Английский алфавит: \n" + CipherEnum.EN_ALPHABET.getValue());
//        enKeyLabel.setText("Английский ключ: \n" + CipherEnum.EN_KEY.getValue());
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
}

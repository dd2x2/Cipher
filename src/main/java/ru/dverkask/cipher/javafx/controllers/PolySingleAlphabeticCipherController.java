package ru.dverkask.cipher.javafx.controllers;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.dverkask.cipher.AbstractCipher;
import ru.dverkask.cipher.CipherEnum;
import ru.dverkask.cipher.PolySingleAlphabeticCipher;
import ru.dverkask.cipher.utils.CipherUIHandler;
import ru.dverkask.cipher.utils.SelectedFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PolySingleAlphabeticCipherController {
    private List<List<Character>> keyCharacterList = List.of(
            CipherEnum.RU_KEY.getValueCharacterList(),
            (CipherEnum.RU_KEY.getValue().substring(1) + CipherEnum.RU_KEY.getValue().charAt(0)).chars()
                    .mapToObj(n -> (char) n)
                    .toList());
    private String alphabet;
    @FXML
    private TextField textAlphabet;
    @FXML
    private TextField textKey;
    @FXML
    private ChoiceBox<String> choiceKey;
    @FXML
    private ChoiceBox<String> choiceAlphabet;
    @FXML
    private TextArea resultArea;
    @FXML
    private TextField encryptInputField;
    @FXML
    private TextField decryptInputField;
    @FXML
    private ChoiceBox<String> cipherType;
    @FXML
    private Label languageLabel1;
    @FXML private Label languageLabel2;
    @FXML private Label languageLabel3;
    @FXML
    private ImageView languageImageView1;
    @FXML
    private ImageView languageImageView2;
    @FXML
    private ImageView languageImageView3;
    @FXML
    private ImageView languageImageView4;
    @FXML
    private ImageView languageImageView5;
    @FXML private ImageView languageImageView6;
    @FXML private ImageView languageImageView7;
    @FXML private ImageView languageImageView8;
    @FXML private ImageView languageImageView9;

    @FXML
    private void initialize() {
        initializePolySingleAlphabeticCipherData();
        setCustomData(choiceAlphabet, textAlphabet, choiceKey, textKey);

        CipherUIHandler.choiceCipher(cipherType);
    }

    public void setCustomData(ChoiceBox<String> choiceAlphabet,
                              TextField textAlphabet,
                              ChoiceBox<String> choiceKey,
                              TextField textKey) {
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
    }

    public void setKeyCharacterList(ChoiceBox<String> choiceBox) {
        if (textKey.getText() != null) {
            this.keyCharacterList = new ArrayList<>();
            List<Character> firstList = textKey.getText().chars()
                    .mapToObj(n -> (char) n)
                    .collect(Collectors.toList());

            this.keyCharacterList.add(firstList);

            List<Character> secondList = new ArrayList<>(firstList);
            Collections.rotate(secondList, -1);

            this.keyCharacterList.add(secondList);
        }
        if (choiceBox.getValue().equals("ruKey")) {
            this.keyCharacterList = List.of(
                    CipherEnum.RU_KEY.getValueCharacterList(),
                    (CipherEnum.RU_KEY.getValue().substring(1) + CipherEnum.RU_KEY.getValue().charAt(0)).chars()
                            .mapToObj(n -> (char) n)
                            .toList(),
                    (CipherEnum.RU_KEY.getValue().substring(2) + CipherEnum.RU_KEY.getValue().charAt(0) + CipherEnum.RU_KEY.getValue().charAt(1)).chars()
                            .mapToObj(n -> (char) n)
                            .toList()
            );
        } else if (choiceBox.getValue().equals("enKey")) {
            this.keyCharacterList = List.of(
                    CipherEnum.EN_KEY.getValueCharacterList(),
                    (CipherEnum.EN_KEY.getValue().substring(1) + CipherEnum.EN_KEY.getValue().charAt(0)).chars()
                            .mapToObj(n -> (char) n)
                            .toList(),
                    (CipherEnum.RU_KEY.getValue().substring(2) + CipherEnum.RU_KEY.getValue().charAt(0) + CipherEnum.RU_KEY.getValue().charAt(1)).chars()
                            .mapToObj(n -> (char) n)
                            .toList()
            );
        }
    }

    public void setAlphabet(ChoiceBox<String> choiceBox) {
        if (choiceAlphabet.getValue().equals("Свой алфавит")) {
            alphabet = textAlphabet.getText();
        } else {
            alphabet = choiceBox.getValue().equals("ruAlphabet") ? CipherEnum.RU_ALPHABET.getValue()
                    : CipherEnum.EN_ALPHABET.getValue();
        }
    }

    public void selectEncryptFile(ActionEvent actionEvent) {
        SelectedFile.selectFile(encryptInputField);
    }

    public void selectDecryptFile(ActionEvent actionEvent) {
        SelectedFile.selectFile(decryptInputField);
    }

    public void encryptBtnAction(ActionEvent actionEvent) {
        setAlphabet(choiceAlphabet);
        setKeyCharacterList(choiceKey);
        AbstractCipher polySingleAlphabeticCipher =
                new PolySingleAlphabeticCipher(alphabet, keyCharacterList);

        resultArea.setText(
                polySingleAlphabeticCipher.encrypt(encryptInputField.getText())
        );
    }

    public void decryptBtnAction(ActionEvent actionEvent) {
        setAlphabet(choiceAlphabet);
        setKeyCharacterList(choiceKey);
        AbstractCipher polySingleAlphabeticCipher =
                new PolySingleAlphabeticCipher(alphabet, keyCharacterList);

        resultArea.setText(
                polySingleAlphabeticCipher.decrypt(decryptInputField.getText())
        );
    }

    public void initializePolySingleAlphabeticCipherData() {
        choiceAlphabet.setValue("ruAlphabet");
        choiceKey.setValue("ruKey");
        cipherType.setValue("PolySingle");

        setRuImages();

        choiceAlphabet.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateLanguageAndImage());
        choiceKey.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateLanguageAndImage());
    }

    private void updateLanguageAndImage() {
        if (choiceAlphabet.getValue().equals("ruAlphabet") && choiceKey.getValue().equals("ruKey")) {
            setRuImages();
        } else if (choiceAlphabet.getValue().equals("enAlphabet") && choiceKey.getValue().equals("enKey")) {
            setEnImages();
        }
    }

    private void setRuImages() {
        languageLabel1.setText("Русский алфавит и ключ (1):");
        languageImageView1.setImage(new Image("/images/rualphabet1.jpg"));
        languageImageView1.setFitWidth(637);
        languageImageView1.setFitHeight(62);

        languageImageView2.setImage(new Image("/images/rualphabet2.jpg"));
        languageImageView2.setFitWidth(580);
        languageImageView2.setFitHeight(59);

        languageImageView3.setImage(new Image("/images/rualphabet3.jpg"));
        languageImageView3.setFitWidth(637);
        languageImageView3.setFitHeight(53);

        languageLabel2.setText("Русский алфавит и ключ (2):");
        languageImageView4.setImage(new Image("/images/rualphabet4.jpg"));
        languageImageView4.setFitWidth(637);
        languageImageView4.setFitHeight(62);

        languageImageView5.setImage(new Image("/images/rualphabet5.jpg"));
        languageImageView5.setFitWidth(580);
        languageImageView5.setFitHeight(59);

        languageImageView6.setImage(new Image("/images/rualphabet6.jpg"));
        languageImageView6.setFitWidth(637);
        languageImageView6.setFitHeight(53);

        languageLabel3.setText("Русский алфавит и ключ (3):");
        languageImageView7.setImage(new Image("/images/rualphabet7.jpg"));
        languageImageView7.setFitWidth(637);
        languageImageView7.setFitHeight(62);

        languageImageView8.setImage(new Image("/images/rualphabet8.jpg"));
        languageImageView8.setFitWidth(580);
        languageImageView8.setFitHeight(59);

        languageImageView9.setImage(new Image("/images/rualphabet9.jpg"));
        languageImageView9.setFitWidth(637);
        languageImageView9.setFitHeight(53);
    }

    private void setEnImages() {
        languageLabel1.setText("Английский алфавит и ключ (1):");
        languageImageView1.setImage(new Image("/images/enalphabet1.jpg"));
        languageImageView1.setFitWidth(480);
        languageImageView1.setFitHeight(54);

        languageImageView2.setImage(new Image("/images/enalphabet2.jpg"));
        languageImageView2.setFitWidth(555);
        languageImageView2.setFitHeight(65);

        languageImageView3.setImage(new Image("/images/enalphabet3.jpg"));
        languageImageView3.setFitWidth(654);
        languageImageView3.setFitHeight(53);

        languageLabel2.setText("Английский алфавит и ключ (2):");
        languageImageView4.setImage(new Image("/images/enalphabet4.jpg"));
        languageImageView4.setFitWidth(480);
        languageImageView4.setFitHeight(54);

        languageImageView5.setImage(new Image("/images/enalphabet5.jpg"));
        languageImageView5.setFitWidth(555);
        languageImageView5.setFitHeight(65);

        languageImageView6.setImage(new Image("/images/enalphabet6.jpg"));
        languageImageView6.setFitWidth(654);
        languageImageView6.setFitHeight(53);

        languageLabel3.setText("Английский алфавит и ключ (3):");
        languageImageView7.setImage(new Image("/images/enalphabet7.jpg"));
        languageImageView7.setFitWidth(480);
        languageImageView7.setFitHeight(54);

        languageImageView8.setImage(new Image("/images/enalphabet8.jpg"));
        languageImageView8.setFitWidth(555);
        languageImageView8.setFitHeight(65);

        languageImageView9.setImage(new Image("/images/enalphabet9.jpg"));
        languageImageView9.setFitWidth(654);
        languageImageView9.setFitHeight(53);
    }
}

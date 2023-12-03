package ru.dverkask.cipher.utils;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.Collections;
import java.util.List;

public class CipherUIHandler {
    public static void choiceCipher(ChoiceBox<String> cipherType) {
        cipherType.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                            String fxmlFile = switch (newValue) {
                                case "Mono" -> "monoalphabeticcipher.fxml";
                                case "PolySingle" -> "polysinglealphabeticcipher.fxml";
                                case "PolyMulti" -> "polymultialphabeticcipher.fxml";
                                case "Vigenere" -> "vigenerecipher.fxml";
                                case "SimplePermutation" -> "simplepermutationnewcipher.fxml";
                                case "SimplePermutationTable" -> "simplepermutationtablecipher.fxml";
                                default -> throw new IllegalStateException("Неизвестное значение: " + newValue);
                            };
                            FXMLChanger.loadFxml(fxmlFile, cipherType);
                        }
                );
    }

    public static void setCustomData(ChoiceBox<String> choiceAlphabet,
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

    public static String shuffleAlphabet(List<Character> alphabet) {
        StringBuilder stringBuilder = new StringBuilder();
        Collections.shuffle(alphabet);

        for (char c : alphabet) {
            stringBuilder.append(c);
        }

        return stringBuilder.toString();
    }
}

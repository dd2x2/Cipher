package ru.dverkask.cipher;

import java.util.Arrays;
import java.util.List;

import java.util.List;

public class PolyMultiAlphabeticCipher extends AbstractCipher {
    private String alphabet;
    private List<String[]> circuits;
    private String key;

    public PolyMultiAlphabeticCipher(String alphabet,
                                     List<String[]> circuits,
                                     int key) {
        this.alphabet = alphabet;
        this.circuits = circuits;
        this.key = String.valueOf(key);
    }

    @Override
    public String encrypt(String plainText) {
        StringBuilder newLine = new StringBuilder();
        int j = 0;
        int circuitIdx = 0;
        List<Integer> keys = Arrays.asList(
                Integer.parseInt(Character.toString(key.charAt(0))),
                Integer.parseInt(Character.toString(key.charAt(1))),
                Integer.parseInt(Character.toString(key.charAt(2)))
        );
        int count = 0;

        for (int i = 0; i < plainText.length(); i++) {
            int place = alphabet.indexOf(plainText.charAt(i));

            newLine.append(circuits.get(circuitIdx)[j % 3].charAt(place));

            j++;
            count++;
            if (j == 3) {
                j = 0;
                keys.set(circuitIdx, keys.get(circuitIdx) - 1);

                if (keys.get(circuitIdx) == 0) {
                    circuitIdx++;
                    if (circuitIdx == circuits.size()) {
                        circuitIdx = 0;
                        keys = Arrays.asList(
                                Integer.parseInt(Character.toString(key.charAt(0))),
                                Integer.parseInt(Character.toString(key.charAt(1))),
                                Integer.parseInt(Character.toString(key.charAt(2)))
                        );
                    }
                }
            }
        }
        return newLine.toString();
    }

    @Override
    public String decrypt(String cipherText) {
        StringBuilder newLine = new StringBuilder();
        int j = 0;
        int circuitIdx = 0;
        List<Integer> keys = Arrays.asList(
                Integer.parseInt(Character.toString(key.charAt(0))),
                Integer.parseInt(Character.toString(key.charAt(1))),
                Integer.parseInt(Character.toString(key.charAt(2)))
        );
        int place = 0;

        for (int i = 0; i < cipherText.length(); i++) {
            place = circuits.get(circuitIdx)[j % 3].indexOf(cipherText.charAt(i));
            newLine.append(alphabet.charAt(place));

            j++;
            if (j == 3) {
                j = 0;
                keys.set(circuitIdx, keys.get(circuitIdx) - 1);

                if (keys.get(circuitIdx) == 0) {
                    circuitIdx++;
                    if (circuitIdx == circuits.size()) {
                        circuitIdx = 0;
                        keys = Arrays.asList(
                                Integer.parseInt(Character.toString(key.charAt(0))),
                                Integer.parseInt(Character.toString(key.charAt(1))),
                                Integer.parseInt(Character.toString(key.charAt(2)))
                        );
                    }
                }
            }
        }

        return newLine.toString();
    }
}

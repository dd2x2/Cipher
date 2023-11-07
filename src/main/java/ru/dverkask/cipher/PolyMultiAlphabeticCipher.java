package ru.dverkask.cipher;

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
        int c1 = Integer.parseInt(Character.toString(key.charAt(0)));
        int c2 = Integer.parseInt(Character.toString(key.charAt(1)));
        int c3 = Integer.parseInt(Character.toString(key.charAt(2)));
        int j = 0;
        int count = 0;
        for (int i = 0; i < plainText.length(); i++) {
            int place = alphabet.indexOf(plainText.charAt(i));
            if (c1 > 0) {
                if (j == 0) {
                    newLine.append(circuits.get(0)[0].charAt(place));
                    j++;
                } else if (j == 1) {
                    newLine.append(circuits.get(0)[1].charAt(place));
                    j++;
                } else if (j == 2) {
                    newLine.append(circuits.get(0)[2].charAt(place));
                    j = 0;
                    c1--;
                }
            } else if (c2 > 0) {
                if (j == 0) {
                    newLine.append(circuits.get(1)[0].charAt(place));
                    j++;
                } else if (j == 1) {
                    newLine.append(circuits.get(1)[1].charAt(place));
                    j++;
                } else if (j == 2) {
                    newLine.append(circuits.get(1)[2].charAt(place));
                    j = 0;
                    c2--;
                }
            } else if (c3 > 0) {
                if (j == 0) {
                    newLine.append(circuits.get(2)[0].charAt(place));
                    j++;
                } else if (j == 1) {
                    newLine.append(circuits.get(2)[1].charAt(place));
                    j++;
                } else if (j == 2) {
                    newLine.append(circuits.get(2)[2].charAt(place));
                    j = 0;
                    c3--;
                }
            }
            if (c1 == 0 && c2 == 0 && c3 == 0) {
                c1 = Integer.parseInt(Character.toString(key.charAt(0)));
                c2 = Integer.parseInt(Character.toString(key.charAt(1)));
                c3 = Integer.parseInt(Character.toString(key.charAt(2)));
            }
        }
        return newLine.toString();
    }

    @Override
    public String decrypt(String cipherText) {
        StringBuilder newLine = new StringBuilder();
        int c1 = Integer.parseInt(Character.toString(key.charAt(0)));
        int c2 = Integer.parseInt(Character.toString(key.charAt(1)));
        int c3 = Integer.parseInt(Character.toString(key.charAt(2)));
        int j = 0;
        int place = 0;
        for (int i = 0; i < cipherText.length(); i++) {
            if (c1 > 0) {
                if (j == 0) {
                    place = circuits.get(0)[0].indexOf(cipherText.charAt(i));
                    j++;
                } else if (j == 1) {
                    place = circuits.get(0)[1].indexOf(cipherText.charAt(i));
                    j++;
                } else if (j == 2) {
                    place = circuits.get(0)[2].indexOf(cipherText.charAt(i));
                    j = 0;
                    c1--;
                }
            } else if (c2 > 0) {
                if (j == 0) {
                    place = circuits.get(1)[0].indexOf(cipherText.charAt(i));
                    j++;
                } else if (j == 1) {
                    place = circuits.get(1)[1].indexOf(cipherText.charAt(i));
                    j++;
                } else if (j == 2) {
                    place = circuits.get(1)[2].indexOf(cipherText.charAt(i));
                    j = 0;
                    c2--;
                }
            } else if (c3 > 0) {
                if (j == 0) {
                    place = circuits.get(2)[0].indexOf(cipherText.charAt(i));
                    j++;
                } else if (j == 1) {
                    place = circuits.get(2)[1].indexOf(cipherText.charAt(i));
                    j++;
                } else if (j == 2) {
                    place = circuits.get(2)[2].indexOf(cipherText.charAt(i));
                    j = 0;
                    c3--;
                }
            }
            if (c1 == 0 && c2 == 0 && c3 == 0) {
                c1 = Integer.parseInt(Character.toString(key.charAt(0)));
                c2 = Integer.parseInt(Character.toString(key.charAt(1)));
                c3 = Integer.parseInt(Character.toString(key.charAt(2)));
            }

            newLine.append(alphabet.charAt(place));
        }

        return newLine.toString();
    }
}

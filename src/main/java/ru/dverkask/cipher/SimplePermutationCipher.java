package ru.dverkask.cipher;

import java.util.ArrayList;
import java.util.List;

public class SimplePermutationCipher extends AbstractCipher{
    private int[] key;
    public SimplePermutationCipher(int[] key) {
        this.key = key;
    }

    @Override
    public String encrypt(String text) {
        StringBuilder textBuilder = new StringBuilder(text);
        while (textBuilder.length() % key.length != 0) {
            textBuilder.append("§");
        }
        text = textBuilder.toString();

        char[][] transpositionTable = new char[key.length][text.length()/key.length];

        for (int row = 0; row < text.length()/key.length; row++) {
            for (int column = 0; column < key.length; column++) {
                transpositionTable[column][row] = text.charAt(row * key.length + column);
            }
        }

        StringBuilder encryptedText = new StringBuilder();

        for (int keyNumber = 1; keyNumber <= key.length; keyNumber++) {
            int column = findIndex(key, keyNumber);
            for (int row = 0; row < text.length()/key.length; row++) {
                encryptedText.append(transpositionTable[column][row]);
            }
        }

        return encryptedText.toString();
    }

    @Override
    public String decrypt(String text) {
        String fakeText = encrypt(text);
        text = insertSpecialSymbols(text, findSpecialSymbolIndices(fakeText));

        char[][] transpositionTable = new char[text.length() / key.length][key.length];

        int index = 0;
        for (int keyNumber = 1; keyNumber <= key.length; keyNumber++) {
            int column = findIndex(key, keyNumber);
            for (int row = 0; row < text.length() / key.length; row++) {
                transpositionTable[row][column] = text.charAt(index);
                index++;
            }
        }

        StringBuilder decryptedText = new StringBuilder();
        for (int row = 0; row < text.length()/key.length; row++) {
            for (int column = 0; column < key.length; column++) {
                decryptedText.append(transpositionTable[row][column]);
            }
        }

        return decryptedText.toString().replace("§", "");
    }

    private static List<Integer> findSpecialSymbolIndices(String text) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '§') {
                indices.add(i);
            }
        }
        return indices;
    }

    private static String insertSpecialSymbols(String text, List<Integer> indices) {
        StringBuilder sb = new StringBuilder(text);
        for(int index : indices) {
            sb.insert(index, '§');
        }
        return sb.toString();
    }

    public static int findIndex(int[] array, int number) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == number) {
                return i;
            }
        }
        return -1;
    }

    public String[][] encryptionMatrix(String plaintext) {
        StringBuilder plaintextBuilder = new StringBuilder(plaintext);
        while (plaintextBuilder.length() % key.length != 0) {
            plaintextBuilder.append("§");
        }
        plaintext = plaintextBuilder.toString();

        int rows = plaintext.length() / key.length;

        String[][] table = new String[rows][key.length];

        int counter = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < key.length; j++) {
                table[i][j] = String.valueOf(plaintext.charAt(counter++));
            }
        }

        return table;
    }
}

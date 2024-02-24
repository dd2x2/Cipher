package ru.dverkask.cipher;

import java.util.ArrayList;
import java.util.List;

public class SimplePermutationNewCipher extends AbstractCipher {
    private int[] key;
    private int rowCount;
    public SimplePermutationNewCipher(int[] key, int rowCount) {
        this.key = key;
        this.rowCount = rowCount;
    }
    @Override
    public String encrypt(String text) {
        StringBuilder textBuilder = new StringBuilder(text);
        while (textBuilder.length() % (key.length * rowCount) != 0) {
                textBuilder.append("§");
        }

        text = textBuilder.toString();
        StringBuilder encryptedText = new StringBuilder();
        int totalTables = text.length() / (key.length * rowCount);

        for (int table = 0; table < totalTables; table++) {
            char[][] transpositionTable = new char[key.length][rowCount];
            for (int row = 0; row < rowCount; row++) {
                for (int column = 0; column < key.length; column++) {
                    transpositionTable[column][row] = text.charAt(table * key.length * rowCount + row * key.length + column);
                }
            }
            for (int keyNumber = 1; keyNumber <= key.length; keyNumber++) {
                int column = findIndex(key, keyNumber);
                for (int row = 0; row < rowCount; row++) {
                    encryptedText.append(transpositionTable[column][row]);
                }
            }
        }

        return encryptedText.toString();
    }

    @Override
    public String decrypt(String text) {
        String fakeText = encrypt(text);
        text = insertSpecialSymbols(text, findSpecialSymbolIndices(fakeText));
        StringBuilder decryptedText = new StringBuilder();

        int totalTables = text.length() / (key.length * rowCount);
        for (int table = 0; table < totalTables; table++) {
            char[][] transpositionTable = new char[key.length][rowCount];
            int index = table * key.length * rowCount;
            for (int keyNumber = 1; keyNumber <= key.length; keyNumber++) {
                int column = findIndex(key, keyNumber);
                for (int row = 0; row < rowCount; row++) {
                    transpositionTable[column][row] = text.charAt(index);
                    index++;
                }
            }
            for (int row = 0; row < rowCount; row++) {
                for (int column = 0; column < key.length; column++) {
                    decryptedText.append(transpositionTable[column][row]);
                }
            }
        }

        return decryptedText.toString().replace("§", "");
    }

    private static List<Integer> findSpecialSymbolIndices(String text) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '§') {
                indexes.add(i);
            }
        }
        return indexes;
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

    public char[][][] getTranspositionTables(String text) {
        StringBuilder textBuilder = new StringBuilder(text);
        while (textBuilder.length() % (key.length * rowCount) != 0) {
            textBuilder.append("§");
        }

        text = textBuilder.toString();
        int totalTables = text.length() / (key.length * rowCount);
        char[][][] transpositionTables = new char[totalTables][key.length][rowCount];

        for (int table = 0; table < totalTables; table++) {
            for (int row = 0; row < rowCount; row++) {
                for (int column = 0; column < key.length; column++) {
                    transpositionTables[table][column][row] = text.charAt(table * key.length * rowCount + row * key.length + column);
                }
            }
        }
        return transpositionTables;
    }

    public static String printTranspositionTables(char[][][] tables, int[] key) {
        StringBuilder result = new StringBuilder();
        for (char[][] table : tables) {
            for (int number : key) {
                result.append(number).append(" ");
            }
            result.append("\n");
            for (int row = 0; row < table[0].length; row++) {
                for (char[] column : table) {
                    char symbol = column[row];
                    if(symbol == '§') {
                        result.append("§").append(" ");
                    } else {
                        result.append(symbol).append(" ");
                    }
                }
                result.append("\n");
            }
            result.append("\n");
        }
        return result.toString();
    }
}
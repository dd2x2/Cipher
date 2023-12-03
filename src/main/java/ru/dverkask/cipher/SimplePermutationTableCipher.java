package ru.dverkask.cipher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimplePermutationTableCipher extends AbstractCipher {
    private int[] key;
    private int rowCount;
    private int[][] additionalKey;
    public SimplePermutationTableCipher(int[] key, int rowCount, int[][] additionalKey) {
        this.key = key;
        this.rowCount = rowCount;
        this.additionalKey = additionalKey;
    }

    @Override
    public String encrypt(String text) {
        StringBuilder textBuilder = new StringBuilder(text);
        int excludedCells = rowCount * key.length - additionalKey.length;

        while (textBuilder.length() % excludedCells != 0) {
            textBuilder.append("§");
        }

        text = textBuilder.toString();
        StringBuilder encryptedText = new StringBuilder();

        int totalTables = (text.length() + excludedCells - 1) / excludedCells;

        int textIndex = 0;
        for (int table = 0; table < totalTables; table++) {
            char[][] transpositionTable = new char[key.length][rowCount];
            for (int row = 0; row < rowCount; row++) {
                for (int column = 0; column < key.length; column++) {
                    if (!ArrayContains(additionalKey, new int[]{column + 1, row + 1})) {
                        if (textIndex < text.length()) {
                            transpositionTable[column][row] = text.charAt(textIndex++);
                        }
                    }
                }
            }
            for (int keyNumber = 1; keyNumber <= key.length; keyNumber++) {
                int column = findIndex(key, keyNumber);
                for (int row = 0; row < rowCount; row++) {
                    if (!ArrayContains(additionalKey, new int[]{column + 1, row + 1}) && transpositionTable[column][row] != '\0') {
                        encryptedText.append(transpositionTable[column][row]);
                    }
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

        int excludedCells = rowCount * key.length - additionalKey.length;
        int totalTables = (int) Math.ceil((double)text.length() / excludedCells);

        int textIndex = 0;

        for (int table = 0; table < totalTables; table++) {
            char[][] transpositionTable = new char[key.length][rowCount];

            for (int keyNumber = 1; keyNumber <= key.length; keyNumber++) {
                int column = findIndex(key, keyNumber);
                for (int row = 0; row < rowCount; row++) {
                    if (!ArrayContains(additionalKey, new int[]{column + 1, row + 1})) {
                        if (textIndex < text.length()) {
                            transpositionTable[column][row] = text.charAt(textIndex++);
                        }
                    }
                }
            }

            for (int row = 0; row < rowCount; row++) {
                for (int column = 0; column < key.length; column++) {
                    if (!ArrayContains(additionalKey, new int[]{column + 1, row + 1}) && transpositionTable[column][row] != '\0') {
                        decryptedText.append(transpositionTable[column][row]);
                    }
                }
            }
        }
        return decryptedText.toString();
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

    private static boolean ArrayContains(int[][] array, int[] key) {
        for (int[] element : array) {
            if(Arrays.equals(element, key)) {
                return true;
            }
        }
        return false;
    }

    public List<char[][]> buildMultipleTranspositionTables(String text) {
        List<char[][]> tables = new ArrayList<>();
        int excludedCells = rowCount * key.length - additionalKey.length;
        int totalTables = (text.length() + excludedCells - 1) / excludedCells;
        int textIndex = 0;
        for (int table = 0; table < totalTables; table++) {
            char[][] transpositionTable = new char[key.length][rowCount];
            for (int row = 0; row < rowCount; row++) {
                for (int column = 0; column < key.length; column++) {
                    if (!ArrayContains(additionalKey, new int[]{column + 1, row + 1})) {
                        if (textIndex < text.length()) {
                            transpositionTable[column][row] = text.charAt(textIndex++);
                        } else {
                            transpositionTable[column][row] = '¶';
                        }
                    } else {
                        transpositionTable[column][row] = '§';
                    }
                }
            }
            tables.add(transpositionTable);
        }
        return tables;
    }

    public String buildAndPrintMultipleTranspositionTables(String text) {
        StringBuilder sb = new StringBuilder();
        List<char[][]> tables = buildMultipleTranspositionTables(text);
        for (char[][] table : tables) {
            for (int i = 0; i < key.length; i++) {
                sb.append(key[i]);
                if (i != key.length - 1) {
                    sb.append(" ");
                }
            }
            sb.append("\n");
            for (int row = 0; row < rowCount; row++) {
                for (int column = 0; column < key.length; column++) {
                    if (table[column][row] != '¶') {
                        sb.append(table[column][row]).append(" ");
                    } else {
                        sb.append("  ");
                    }
                }
                sb.append("\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static int[][] fromStringToAdditionalKey(String additionalKey) {
        int[][] resultArray = new int[additionalKey.length() / 2][2];

        for (int i = 0; i < additionalKey.length(); i += 2) {
            resultArray[i / 2][0] = Character.getNumericValue(additionalKey.charAt(i));
            resultArray[i / 2][1] = Character.getNumericValue(additionalKey.charAt(i + 1));
        }

        return resultArray;
    }
}


package ru.dverkask.cipher;

public class SimplePermutationNewCipher extends AbstractCipher {
    private int[] key;

    public SimplePermutationNewCipher(int[] key) {
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
        return null;
    }

    public static int findIndex(int[] array, int number) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == number) {
                return i;
            }
        }
        return -1;
    }
}

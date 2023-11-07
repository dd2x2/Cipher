package ru.dverkask.cipher;

public class VigenereCipher extends AbstractCipher{
    private final String key;
    private final String alphabet;
    private final StringBuilder log = new StringBuilder();

    public VigenereCipher(String alphabet, String key) {
        this.alphabet = alphabet;
        this.key = key;
    }

    @Override
    public String encrypt(String text) {
        StringBuilder res = new StringBuilder();
        int keywordIndex = 0;
        String spacedAlphabet = String.join(" ", alphabet.split(""));
        log.append(spacedAlphabet).append('\n');
        for (char c : text.toCharArray()) {
            int alphabetIndex = alphabet.indexOf(c);
            if (alphabetIndex != -1) {
                String rotatedAlphabet = alphabet.substring(alphabetIndex) + alphabet.substring(0, alphabetIndex);
                String spacedRotatedAlphabet = String.join(" ", rotatedAlphabet.split(""));
                log.append(spacedRotatedAlphabet).append('\n');
                char keywordChar = key.charAt(keywordIndex);
                keywordIndex = (keywordIndex + 1) % key.length();
                int shift = alphabet.indexOf(keywordChar);
                res.append(rotatedAlphabet.charAt(shift));
            }
        }
        return res.toString();
    }

    @Override
    public String decrypt(String text) {
        StringBuilder res = new StringBuilder();
        int keywordIndex = 0;
        for (char c : text.toCharArray()) {
            int alphabetIndex = alphabet.indexOf(c);
            if (alphabetIndex != -1) {
                int keywordCharIndex = alphabet.indexOf(key.charAt(keywordIndex));
                int position = (alphabetIndex - keywordCharIndex + alphabet.length()) % alphabet.length();
                res.append(alphabet.charAt(position));
                keywordIndex = (keywordIndex + 1) % key.length();
            }
        }
        return res.toString();
    }

    public String getLog() {
        return log.toString();
    }
}

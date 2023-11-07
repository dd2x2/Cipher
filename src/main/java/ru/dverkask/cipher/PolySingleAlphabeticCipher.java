package ru.dverkask.cipher;

import java.util.*;

public class PolySingleAlphabeticCipher extends AbstractCipher{
    private final List<Map<Character, Character>> encryptionMaps = new ArrayList<>();
    private final List<Map<Character, Character>> decryptionMaps = new ArrayList<>();

    public PolySingleAlphabeticCipher(String alphabet,
                                      List<List<Character>> keys) {
        for (List<Character> key : keys) {
            Map<Character, Character> encryptionMap = new HashMap<>();
            Map<Character, Character> decryptionMap = new HashMap<>();
            int length = Math.min(alphabet.length(), key.size());
            for (int i = 0; i < length; i++) {
                char originalChar = alphabet.charAt(i);
                char encryptedChar = key.get(i);
                encryptionMap.put(originalChar, encryptedChar);
                decryptionMap.put(encryptedChar, originalChar);
            }
            encryptionMaps.add(encryptionMap);
            decryptionMaps.add(decryptionMap);
        }
    }

    @Override
    public String encrypt(String text) {
        return convert(text, encryptionMaps);
    }
    @Override
    public String decrypt(String text) {
        return convert(text, decryptionMaps);
    }
    private String convert(String text,
                           List<Map<Character, Character>> maps) {
        StringBuilder encodedText = new StringBuilder();
        int keyIndex = 0;
        for (char ch : text.toCharArray()) {
            Map<Character, Character> map = maps.get(keyIndex);
            encodedText.append(map.getOrDefault(ch, ch));
            keyIndex = (keyIndex + 1) % maps.size();
        }
        return encodedText.toString();
    }
}

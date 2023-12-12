package ru.dverkask.cipher;

import java.util.HashMap;
import java.util.Map;

public class GammaCipher {
    private final String gamma;
    private static final String alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()`~-=+[]{}<,>.?/ ";
    private static final Map<Character, String> binaryMap = new HashMap<>();
    public GammaCipher(String gamma) {
        this.gamma = gamma;
    }

    public String xor(String text) {
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            key.append(gamma.charAt(i % gamma.length()));
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char textChar = text.charAt(i);
            char keyChar = key.charAt(i);

            String binaryTextChar = binaryMap.get(textChar);
            String binaryKeyChar = binaryMap.get(keyChar);

            int number1 = Integer.parseInt(binaryTextChar, 2);
            int number2 = Integer.parseInt(binaryKeyChar, 2);
            
            int xorResult = number1 ^ number2;

            char xorChar = alphabet.charAt(xorResult);
            result.append(xorChar);
        }

        return result.toString();
    }

    public static String getInfo() {
        StringBuilder info = new StringBuilder();

        for (int i = 0; i < alphabet.length(); i++) {
            binaryMap.put(alphabet.charAt(i), String.format("%07d", Integer.parseInt(Integer.toBinaryString(i))));
        }

        for (char ch : alphabet.toCharArray()) {
            info.append("'").append(ch).append("' - ").append(binaryMap.get(ch)).append("\n");
        }

        return info.toString();
    }
}


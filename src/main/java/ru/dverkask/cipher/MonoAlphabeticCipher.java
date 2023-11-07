package ru.dverkask.cipher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MonoAlphabeticCipher extends AbstractCipher {
    private final Map<Character, Character> encryptMap = new HashMap<>();
    private final Map<Character, Character> decryptMap = new HashMap<>();

    public MonoAlphabeticCipher(List<Character> alphabet, List<Character> key) {
        if(alphabet.size() != key.size()) {
            throw new IllegalArgumentException("Алфавит и ключ должны иметь одинаковую длину");
        }

        for (int i = 0; i < alphabet.size(); i++) {
            encryptMap.put(alphabet.get(i), key.get(i));
            decryptMap.put(key.get(i), alphabet.get(i));
        }
    }

    @Override
    public String encrypt(String text) {
        return text.chars()
                .mapToObj(c -> encryptMap.getOrDefault((char) c, (char) c))
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    @Override
    public String decrypt(String text) {
        return text.chars()
                .mapToObj(c -> decryptMap.getOrDefault((char) c, (char) c))
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}

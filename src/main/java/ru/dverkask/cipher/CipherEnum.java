package ru.dverkask.cipher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum CipherEnum {
    RU_ALPHABET("абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ1234567890!@#$%^&*()`~-=+[]{}|;:'\"<,>.?/ "),
    RU_KEY("клмнопрстуфхцчшщъыьэюяабвгдеёжзийКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯАБВГДЕЁЖЗИЙ!@#$%^&*()1234567890/ .>,<\"':;|}{][+=-~`?"),
    RU_SHUFFLED_ALPHABET("1О| цМ?фКЫФЮьтЯН=п{А[э#Дъ5;Б-кмбГ\"!в'РЭЗ&дВнЧ3+ажШзЕш2/$6Ё4)Т(у8ю.гХ~ро@ЪПи]яёСщ%йЦх9лчЙ:сеЬИЩ0>*ыУ,}Ж<Л7^`"),
    EN_ALPHABET("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()`~-=+[]{}|;:'\"<,>.?/ "),
    EN_KEY("bcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()1234567890/ .>,<\"':;|}{][+=-~`?a");
    private final String value;
    private static final Map<String, CipherEnum> keyMap = new HashMap<>();
    private static final Map<String, CipherEnum> alphabetMap = new HashMap<>();

    static {
        keyMap.put("ruKey", CipherEnum.RU_KEY);
        keyMap.put("enKey", CipherEnum.EN_KEY);

        alphabetMap.put("ruAlphabet", CipherEnum.RU_ALPHABET);
        alphabetMap.put("enAlphabet", CipherEnum.EN_ALPHABET);
    }
    CipherEnum(String s) {
        this.value = s;
    }

    public List<Character> getValueCharacterList() {
        return value.chars()
                .mapToObj(n -> (char) n)
                .toList();
    }

    public static Map<String, CipherEnum> getAlphabetMap() {
        return alphabetMap;
    }

    public static Map<String, CipherEnum> getKeyMap() {
        return keyMap;
    }

    public String getValue() {
        return value;
    }
}

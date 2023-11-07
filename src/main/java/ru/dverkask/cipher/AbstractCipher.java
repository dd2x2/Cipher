package ru.dverkask.cipher;

public abstract class AbstractCipher {
    public abstract String encrypt(String text);
    public abstract String decrypt(String text);
}

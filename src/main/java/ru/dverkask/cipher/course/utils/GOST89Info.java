package ru.dverkask.cipher.course.utils;

import ru.dverkask.cipher.course.GOST89;

public class GOST89Info {
    private static StringBuilder info = new StringBuilder();

    public static StringBuilder getInfo() {
        return info;
    }

    public static void append(String str) {
        if (GOST89.getBlocks() <= 1) info.append(str);
    }

    public static void clearInfo() {
        info = new StringBuilder();
    }
}

package ru.dverkask.cipher.course;

import ru.dverkask.cipher.course.utils.BitUtil;
import ru.dverkask.cipher.course.utils.GOST89Info;

import java.util.Arrays;

public class GOST89 {
    private final int[] e_key = new int[32];
    private byte[] key;
    private static int blocks = 0;
    private final byte[][] S = new byte[][]{
            {0xF, 0xC, 0x2, 0xA, 0x6, 0x4, 0x5, 0x0, 0x7, 0x9, 0xE, 0xD, 0x1, 0xB, 0x8, 0x3},
            {0xB, 0x6, 0x3, 0x4, 0xC, 0xF, 0xE, 0x2, 0x7, 0xD, 0x8, 0x0, 0x5, 0xA, 0x9, 0x1},
            {0x1, 0xC, 0xB, 0x0, 0xF, 0xE, 0x6, 0x5, 0xA, 0xD, 0x4, 0x8, 0x9, 0x3, 0x7, 0x2},
            {0x1, 0x5, 0xE, 0xC, 0xA, 0x7, 0x0, 0xD, 0x6, 0x2, 0xB, 0x4, 0x9, 0x3, 0xF, 0x8},
            {0x0, 0xC, 0x8, 0x9, 0xD, 0x2, 0xA, 0xB, 0x7, 0x3, 0x6, 0x5, 0x4, 0xE, 0xF, 0x1},
            {0x8, 0x0, 0xF, 0x3, 0x2, 0x5, 0xE, 0xB, 0x1, 0xA, 0x4, 0x7, 0xC, 0x9, 0xD, 0x6},
            {0x3, 0x0, 0x6, 0xF, 0x1, 0xE, 0x9, 0x2, 0xD, 0x8, 0xC, 0x4, 0xB, 0xA, 0x5, 0x7},
            {0x1, 0xA, 0x6, 0x8, 0xF, 0xB, 0x0, 0x4, 0xC, 0x3, 0x5, 0x9, 0x7, 0xD, 0x2, 0xE},
    };

    public GOST89(byte[] key) {
        setKey(key);
    }

    public byte[][] getS() {
        return S;
    }

    public static int getBlocks() {
        return blocks;
    }

    public void setKey(byte[] key) {
        if (key == null || key.length != 32) throw new RuntimeException();
        this.key = key;
        int[] key_32_t = BitUtil.ByteArrays.byteArrayToIntArray(key);
        System.arraycopy(key_32_t, 0, e_key, 0, 8);
        System.arraycopy(key_32_t, 0, e_key, 8, 8);
        System.arraycopy(key_32_t, 0, e_key, 16, 8);
        BitUtil.ByteArrays.reverseArray(key_32_t);
        System.arraycopy(key_32_t, 0, e_key, 24, 8);
        for (int i = 7; i >= 0; i--) {
            String keyPartBin = Integer.toBinaryString(key_32_t[i]);
            keyPartBin = BitUtil.Print.padBinaryString(keyPartBin, 32);
            GOST89Info.append("Часть ключа k" + (8-i) + " в двоичном виде: " + keyPartBin + "\n");
        }
    }

    private int F(int A, int Ki) {
        GOST89Info.append("Блок N1: " + String.format("%32s", Integer.toBinaryString(A)).replace(' ', '0') + "\n");
        GOST89Info.append("Подключ: " + String.format("%32s", Integer.toBinaryString(Ki)).replace(' ', '0') + "\n");
        int A_Ki = A + Ki;
        GOST89Info.append("Блок после сложения с ключом: " + Integer.toBinaryString(A_Ki) + "\n");
        byte[] A_4 = BitUtil.Fission.splitBy4bits(BitUtil.ByteArrays.intToByteArray(A_Ki));
        for (int i = 7, j = 1; i >= 0; i--) {
            if (j <= 7) {
                int index = A_4[i] & 0xFF;
                if (index < 16) {
                    byte sBoxValue = S[j++][index];
                    A_4[i] = sBoxValue;
                }
            }
        }
        A_Ki = BitUtil.ByteArrays.byteArrayToInt(BitUtil.Fission.concatBy4bit(A_4));
        String paddedBinaryA = String.format("%32s", Integer.toBinaryString(A_Ki)).replace(' ', '0');
        GOST89Info.append("4-битные части блока в двоичном виде: ");
        for(int i=0; i<paddedBinaryA.length(); i+=4){
            GOST89Info.append(paddedBinaryA.substring(i, i+4) + " ");
        }

        GOST89Info.append("\nКонечный блок после конкатенации: " + paddedBinaryA + "\n");

        A_Ki = BitUtil.Rotation.rotateL(A_Ki, 11);
        paddedBinaryA = String.format("%32s", Integer.toBinaryString(A_Ki)).replace(' ', '0');

        GOST89Info.append("Конечный блок после циклического сдвига на 11 бит влево: " + paddedBinaryA + "\n");
        return A_Ki;
    }

    public byte[] encrypt(byte[] input) {
        int[] encrypted = BitUtil.ByteArrays.byteArrayToIntArray(input);
        for (int k = 0; k < encrypted.length; k+=2) {
            int[] chunck = new int[2]; ++blocks;
            System.arraycopy(encrypted,k,chunck,0,2);
            int B = chunck[1];
            int A = chunck[0];
            for (int i = 0; i < 32; i++) {
                GOST89Info.append("Раунд " + (i + 1) + "\n");
                String binaryA = Integer.toBinaryString(A);
                binaryA = String.format("%32s", binaryA).replace(' ', '0');
                GOST89Info.append("Текущий блок: " + binaryA + "\n");
                byte[] fourBitParts = BitUtil.Fission.splitBy4bits(BitUtil.ByteArrays.intToByteArray(A));
                GOST89Info.append("4-битные части блока: " + Arrays.toString(fourBitParts) + "\n");
                int temp = B;
                B = A ^ F(B, e_key[i]);
                GOST89Info.append("Результат XOR первого и второго блока: " + Integer.toBinaryString(B) + "\n");
                A = temp;
            }
            System.arraycopy(new int[]{B,A}, 0, encrypted, k,2);
        }
        blocks = 0;
        return BitUtil.ByteArrays.intArrayToByteArray(encrypted);
    }

    public byte[] decrypt(byte[] input) {
        int[] decrypted = BitUtil.ByteArrays.byteArrayToIntArray(input);
        for (int k = 0; k < decrypted.length; k += 2) {
            int[] chunck = new int[2]; ++blocks;
            System.arraycopy(decrypted, k, chunck, 0, 2);
            int B = chunck[1];
            int A = chunck[0];
            for (int i = 31; i >= 0; i--) {
                GOST89Info.append("Раунд " + (32 - i) + "\n");
                String binaryA = Integer.toBinaryString(A);
                binaryA = String.format("%32s", binaryA).replace(' ', '0');
                GOST89Info.append("Текущий блок: " + binaryA + "\n");
                byte[] fourBitParts = BitUtil.Fission.splitBy4bits(BitUtil.ByteArrays.intToByteArray(A));
                GOST89Info.append("4-битные части блока: " + Arrays.toString(fourBitParts) + "\n");
                int temp = B;
                B = A ^ F(B, e_key[i]);
                GOST89Info.append("Результат XOR первого и второго блока: " + Integer.toBinaryString(B) + "\n");
                A = temp;
            }
            System.arraycopy(new int[]{B, A}, 0, decrypted, k, 2);
        }
        blocks = 0;
        return BitUtil.ByteArrays.intArrayToByteArray(decrypted);
    }
}

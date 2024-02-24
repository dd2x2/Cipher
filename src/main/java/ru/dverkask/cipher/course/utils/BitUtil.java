package ru.dverkask.cipher.course.utils;

public final class BitUtil {
    private static final int INT_BITSIZE   = 32;
    public static class Rotation {
        public static int rotateL(int i, int bits) {
            return ((i << bits) | (i >>> (INT_BITSIZE - bits)));
        }
    }

    public static class ByteArrays {
        public static byte[] padWithZeros(byte[] input, int blockSize) {
            int inputLength = input.length;
            int remainder   = inputLength % blockSize;

            if (remainder == 0) {
                return input;
            }

            int    paddedLength = inputLength + (blockSize - remainder);
            byte[] paddedInput  = new byte[paddedLength];
            System.arraycopy(input, 0, paddedInput, 0, inputLength);

            return paddedInput;
        }

        public static void reverseArray(int[] input) {
            int tmp;
            for (int i = 0; i < input.length >> 1; ++i) {
                tmp = input[i];
                input[i] = input[input.length - i - 1];
                input[input.length - i - 1] = tmp;
            }

        }

        public static int byteArrayToInt(byte[] b) {
            int i = b[0] & 0xff;
            i = (i << 8) ^ (int) b[1] & 0xff;
            i = (i << 8) ^ (int) b[2] & 0xff;
            i = (i << 8) ^ (int) b[3] & 0xff;

            return i;
        }

        public static byte[] intToByteArray(int i) {
            return new byte[]{
                    (byte) (i >> 24),
                    (byte) (i >> 16),
                    (byte) (i >> 8),
                    (byte) i
            };
        }


        public static int[] byteArrayToIntArray(byte[] b) {
            int[] i_32 = new int[b.length >> 2];
            for (int i = 0, j = 0; i < b.length; i += 4, j++) {
                byte[] chunk = new byte[4];
                System.arraycopy(b, i, chunk, 0, chunk.length);
                i_32[j] = byteArrayToInt(chunk);

            }
            return i_32;
        }

        public static byte[] intArrayToByteArray(int[] i_32) {
            byte[] b         = new byte[i_32.length << 2];
            int    padding_t = 0;
            for (int i = 0; i < i_32.length; i++) {
                byte[] chunk = intToByteArray(i_32[i]);
                System.arraycopy(chunk, 0, b, i + padding_t, chunk.length);
                padding_t += chunk.length - 1;

            }
            return b;
        }
    }

    public static class Print {
        public static String printBytes(byte[] bytes) {
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : bytes) {
                int i = b & 0xFF;
                stringBuilder.append(i).append(" ");
            }
            return stringBuilder.toString();
        }

        public static String printInitialN1N2(byte[] input) {
            StringBuilder stringBuilder = new StringBuilder();
            int[] encrypted = BitUtil.ByteArrays.byteArrayToIntArray(input);
            for (int k = 0; k < encrypted.length; k+=2) {
                int[] chunk = new int[2];
                System.arraycopy(encrypted, k, chunk, 0, 2);
                String N1Binary = BitUtil.Print.padBinaryString(Integer.toBinaryString(chunk[0]), 32);
                String N2Binary = BitUtil.Print.padBinaryString(Integer.toBinaryString(chunk[1]), 32);
                stringBuilder.append("\n" + "N1: ").append(N1Binary).append("\n").append("N2: ").append(N2Binary).append("\n");
            }

            return stringBuilder.toString();
        }

        public static String padBinaryString(String bin, int length) {
            StringBuilder binBuilder = new StringBuilder(bin);
            while (binBuilder.length() < length) {
                binBuilder.insert(0, "0");
            }
            bin = binBuilder.toString();
            return bin;
        }

        public static String printSBox(byte[][] S) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < S.length; i++) {
                stringBuilder.append("S").append(i).append(": [");
                for (int j = 0; j < S[i].length; j++) {
                    stringBuilder.append(S[i][j]);
                    if (j < S[i].length - 1) {
                        stringBuilder.append(", ");
                    }
                }
                stringBuilder.append("]" + "\n");
            }

            return stringBuilder.toString();
        }

        public static String printSBoxInBinary(byte[][] S) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < S.length; i++) {
                stringBuilder.append("S").append(i).append(": [");
                for (int j = 0; j < S[i].length; j++) {
                    StringBuilder binaryString = new StringBuilder(Integer.toBinaryString(S[i][j] & 0xFF));
                    while (binaryString.length() < 4) {
                        binaryString.insert(0, "0");
                    }
                    stringBuilder.append(binaryString);
                    if (j < S[i].length - 1) {
                        stringBuilder.append(", ");
                    }
                }
                stringBuilder.append("]\n");
            }
            return stringBuilder.toString();
        }
    }

    public static class Fission {
        public static byte[] splitBy4bits(byte[] input) {
            byte[] input_4bit = new byte[input.length << 1];
            for (int i = 0, j = 0; i < input.length; i++, j++) {
                input_4bit[j] = (byte) ((input[i] >> 4) & 0b1111);
                input_4bit[j + 1] = (byte) ((input[i] & 0b1111));
                j++;
            }
            return input_4bit;
        }

        public static byte[] concatBy4bit(byte[] input_4bit) {
            byte[] input = new byte[input_4bit.length >> 1];
            for (int i = 0, j = 0; i < input_4bit.length; i++, j++) {
                input[j] = (byte) ((input_4bit[i] << 4) | input_4bit[i + 1]);
                i++;
            }
            return input;
        }
    }
}

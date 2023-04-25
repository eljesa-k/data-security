package AES_GCM;

import java.util.Arrays;

public class AES {
    private final KeyGenerator keyGenerator;
    private final GaloisField field;
    private boolean[] P;
    private boolean[] CipherText;
    private final boolean[][][] s = {
            {{false, true, true, false}, {true, false, true, true}, {false, false, false, false}, {false, true, false, false}},
            {{false, true, true, true}, {true, true, true, false}, {false, false, true, false}, {true, true, true, true}},
            {{true, false, false, true}, {true, false, false, false}, {true, false, true, false}, {true, true, false, false}},
            {{false, false, true, true}, {false, false, false, true}, {false, true, false, true}, {true, true, false, true}}
    };
    private final boolean[][][] invSBOX = new boolean[][][]{
            {{false, false, true, false}, {true, true, false, true}, {false, true, true, false}, {true, true, false, false}},
            {{false, false, true, true}, {true, true, true, false}, {false, false, false, false}, {false, true, false, false}},
            {{true, false, false, true}, {true, false, false, false}, {true, false, true, false}, {false, false, false, true}},
            {{true, false, true, true}, {true, true, true, true}, {false, true, false, true}, {false, true, true, true}}
    };
    private final boolean[] S_AES_POLYNOMIAL = {true, false, false, true, true};
    public AES(KeyGenerator k){
        this.keyGenerator = k;
        this.field = new GaloisField(S_AES_POLYNOMIAL, 4);
    }

    public String encrypt(String plainText) {
        this.P = new boolean[16];

        int i;
        int k;
        for(i = 0; i < 4; ++i) {
            String converted = Utilities.toBinary(plainText.charAt(i));
            k = 0;
            char[] var5 = converted.toCharArray();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                char bit = var5[var7];
                this.P[4 * i + k] = bit == '1';
                ++k;
            }
        }

        boolean[] var10;
        int var11;
        boolean bit;
        for(i = 0; i <= 3; ++i) {
            this.nextRoundEncryption(i);
            System.out.println("Round " + i);
            System.out.print("Key = ");
            var10 = this.keyGenerator.getKey(i);
            k = var10.length;

            for(var11 = 0; var11 < k; ++var11) {
                bit = var10[var11];
                System.out.print((char)(bit ? '1' : '0'));
            }

            System.out.print("\nCipher text = ");
            var10 = this.P;
            k = var10.length;

            for(var11 = 0; var11 < k; ++var11) {
                bit = var10[var11];
                System.out.print((char)(bit ? '1' : '0'));
            }

            System.out.println("\n");
        }

        StringBuilder res = new StringBuilder();
        var10 = this.P;
        k = var10.length;

        for(var11 = 0; var11 < k; ++var11) {
            bit = var10[var11];
            res.append((char)(bit ? '1' : '0'));
        }

        return res.toString();
    }

    private void nextRoundEncryption(int round) {
        if (round != 0) {
            boolean[][] B = new boolean[4][4];
            System.arraycopy(this.P, 0, B[0], 0, 4);
            System.arraycopy(this.P, 4, B[1], 0, 4);
            System.arraycopy(this.P, 8, B[2], 0, 4);
            System.arraycopy(this.P, 12, B[3], 0, 4);

            for(int i = 0; i < 4; ++i) {
                B[i] = this.sBox(B[i]);
            }

            boolean[] temp = B[1];
            B[1] = B[3];
            B[3] = temp;
            if (round != 3) {
                B = this.mixColumns(B);
            }

            System.arraycopy(B[0], 0, this.P, 0, 4);
            System.arraycopy(B[1], 0, this.P, 4, 4);
            System.arraycopy(B[2], 0, this.P, 8, 4);
            System.arraycopy(B[3], 0, this.P, 12, 4);
        }

        this.P = Utilities.XOR(this.P, this.keyGenerator.getKey(round));
    }

    private boolean[] sBox(boolean[] input) {
        int row = 2 * (input[0] ? 1 : 0) + (input[1] ? 1 : 0);
        int col = 2 * (input[2] ? 1 : 0) + (input[3] ? 1 : 0);
        return Arrays.copyOf(this.s[row][col], 4);
    }

    private boolean[][] mixColumns(boolean[][] B) {
        boolean[] hex2 = new boolean[]{false, false, true, false};
        boolean[][] D = new boolean[4][4];
        D[0] = Utilities.XOR(B[0], B[1]);
        D[2] = Utilities.XOR(B[2], B[3]);
        D[1] = Utilities.XOR(B[0], this.field.multiply(B[1], hex2));
        D[3] = Utilities.XOR(B[2], this.field.multiply(B[3], hex2));
        return D;
    }

    private boolean[][] invMixColumns(boolean[][] B) {
        boolean[] E = new boolean[]{true, true, true, false};
        boolean[] F = new boolean[]{true, true, true, true};
        boolean[][] D = new boolean[4][4];
        D[0] = Utilities.XOR(this.field.multiply(B[0], F), this.field.multiply(B[1], E));
        D[2] = Utilities.XOR(this.field.multiply(B[2], F), this.field.multiply(B[3], E));
        D[1] = Utilities.XOR(this.field.multiply(B[0], E), this.field.multiply(B[1], E));
        D[3] = Utilities.XOR(this.field.multiply(B[2], E), this.field.multiply(B[3], E));
        return D;
    }

    public String decrypt(String cipherText) {
        this.CipherText = new boolean[16];

        int i;
        for(i = 0; i < 16; ++i) {
            this.CipherText[i] = cipherText.charAt(i) == '1';
        }

        boolean[] var3;
        int var4;
        int var5;
        boolean bit;
        for(i = 3; i >= 0; --i) {
            System.out.println("Round " + (3 - i));
            System.out.print("Key = ");
            var3 = this.keyGenerator.getKey(i);
            var4 = var3.length;

            for(var5 = 0; var5 < var4; ++var5) {
                bit = var3[var5];
                System.out.print((char)(bit ? '1' : '0'));
            }

            System.out.print("\nCipher text = ");
            var3 = this.CipherText;
            var4 = var3.length;

            for(var5 = 0; var5 < var4; ++var5) {
                bit = var3[var5];
                System.out.print((char)(bit ? '1' : '0'));
            }

            System.out.println("\n");
            this.nextRoundDecryption(i);
        }

        StringBuilder res = new StringBuilder();
        var3 = this.CipherText;
        var4 = var3.length;

        for(var5 = 0; var5 < var4; ++var5) {
            bit = var3[var5];
            res.append((char)(bit ? '1' : '0'));
        }

        return res.toString();
    }

    private boolean[] invSBox(boolean[] input) {
        int row = 2 * (input[0] ? 1 : 0) + (input[1] ? 1 : 0);
        int col = 2 * (input[2] ? 1 : 0) + (input[3] ? 1 : 0);
        return Arrays.copyOf(this.invSBOX[row][col], 4);
    }

    private boolean[][] invShiftRows(boolean[][] input) {
        boolean[] temp = input[3];
        input[3] = input[1];
        input[1] = temp;
        return input;
    }

    private void nextRoundDecryption(int round) {
        this.CipherText = Utilities.XOR(this.CipherText, this.keyGenerator.getKey(round));
        if (round != 0) {
            boolean[][] B = new boolean[4][4];
            System.arraycopy(this.CipherText, 0, B[0], 0, 4);
            System.arraycopy(this.CipherText, 4, B[1], 0, 4);
            System.arraycopy(this.CipherText, 8, B[2], 0, 4);
            System.arraycopy(this.CipherText, 12, B[3], 0, 4);
            if (round != 3) {
                B = this.invMixColumns(B);
            }

            B = this.invShiftRows(B);

            for(int j = 0; j < 4; ++j) {
                B[j] = this.invSBox(B[j]);
            }

            System.arraycopy(B[0], 0, this.CipherText, 0, 4);
            System.arraycopy(B[1], 0, this.CipherText, 4, 4);
            System.arraycopy(B[2], 0, this.CipherText, 8, 4);
            System.arraycopy(B[3], 0, this.CipherText, 12, 4);
        }

    }
}

package AES_GCM;

import java.util.Arrays;

public class AES {
    private KeyGenerator keyGenerator;
    private GaloisField field;
    private boolean[] P;
    private final boolean[][][] s = {
            {{false, true, true, false}, {true, false, true, true}, {false, false, false, false}, {false, true, false, false}},
            {{false, true, true, true}, {true, true, true, false}, {false, false, true, false}, {true, true, true, true}},
            {{true, false, false, true}, {true, false, false, false}, {true, false, true, false}, {true, true, false, false}},
            {{false, false, true, true}, {false, false, false, true}, {false, true, false, true}, {true, true, false, true}}
    };
    private final boolean[] S_AES_POLYNOMIAL = {true, false, false, true, true};
    public AES(KeyGenerator k){
        this.keyGenerator = k;
        this.field = new GaloisField(S_AES_POLYNOMIAL, 4);
    }

    public String encrypt(String plainText){
        this.P = new boolean[16];
        for(int i = 0; i < 4; i++){
            String converted = Utilities.toBinary(plainText.charAt(i));
            int k = 0;
            for(char bit : converted.toCharArray()){
                P[4 * i + k] = bit == '1';
                k++;
            }
        }

        for(int i = 0; i <= 3; i++){
            nextRoundEncryption(i);
            System.out.println("Round " + i);

            System.out.print("Key = ");
            for(boolean bit : keyGenerator.getKey(i)){
                System.out.print(bit ? '1' : '0');
            }
            System.out.print("\nCipher text = ");
            for(boolean bit : P){
                System.out.print(bit ? '1' : '0');
            }
            System.out.println("\n");
        }

        StringBuilder res = new StringBuilder();
        for(boolean bit : P){
            res.append(bit ? '1' : '0');
        }
        return res.toString();
    }

    /**
     * Performs the specified round of the S-AES
     * @param round The required round to be performed
     */
    private void nextRoundEncryption(int round){
        if(round != 0){
            boolean[][] B = new boolean[4][4];
            System.arraycopy(this.P, 0, B[0], 0, 4);
            System.arraycopy(this.P, 4, B[1], 0, 4);
            System.arraycopy(this.P, 8, B[2], 0, 4);
            System.arraycopy(this.P, 12, B[3], 0, 4);

            // Substitute boxes
            for(int i = 0; i < 4; i++){
                B[i] = this.sBox(B[i]);
            }

            // Shift rows
            boolean[] temp = B[1];
            B[1] = B[3];
            B[3] = temp;


            // Mix columns
            if(round != 3){
                B = mixColumns(B);
            }

            System.arraycopy(B[0], 0, this.P, 0, 4);
            System.arraycopy(B[1], 0, this.P, 4, 4);
            System.arraycopy(B[2], 0, this.P, 8, 4);
            System.arraycopy(B[3], 0, this.P, 12, 4);
        }
        P = Utilities.XOR(P, keyGenerator.getKey(round));
    }

    /**
     * Performs Substitute Box function on given input
     * @param input an array of boolean of length 4
     * @return Calculated output of S-AES, boolean array of length 4
     */
    private boolean[] sBox(boolean[] input) {
        int row = 2 * (input[0] ? 1 : 0) + (input[1] ? 1 : 0);
        int col = 2 * (input[2] ? 1 : 0) + (input[3] ? 1 : 0);
        return Arrays.copyOf(s[row][col], 4);
    }

    private boolean[][] mixColumns(boolean[][] B){
        final boolean[] hex2 = {false, false, true, false};
        boolean[][] D = new boolean[4][4];

        D[0] = Utilities.XOR(B[0], B[1]);
        D[2] = Utilities.XOR(B[2], B[3]);

        D[1] = Utilities.XOR(B[0], field.multiply(B[1], hex2));
        D[3] = Utilities.XOR(B[2], field.multiply(B[3], hex2));

        return D;
    }

    private boolean[][] invMixColumns(boolean[][] B){
        final boolean[] E = {true, true, true, false};
        final boolean[] F = {true, true, true, true};

        // TODO: implement invMixColumns here
        // above E and F are hex values expressed on boolean
        boolean[][] D = new boolean[4][4];
        D[0] = Utilities.XOR(field.multiply(B[0], F), field.multiply(B[1],E));
        D[2] = Utilities.XOR(field.multiply(B[2], F), field.multiply(B[3],E));
        D[1] = Utilities.XOR(field.multiply(B[0], E), field.multiply(B[1],E));
        D[3] = Utilities.XOR(field.multiply(B[2], E), field.multiply(B[3],E));

        return D;
    }

}

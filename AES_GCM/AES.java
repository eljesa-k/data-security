package AES_GCM;

import java.util.Arrays;

public class AES {
    private KeyGenerator keyGenerator;
    private boolean[] P;

    private final boolean[][][] s = {
            {{false, true, true, false}, {true, false, true, true}, {false, false, false, false}, {false, true, false, false}},
            {{false, true, true, true}, {true, true, true, false}, {false, false, true, false}, {true, true, true, true}},
            {{true, false, false, true}, {true, false, false, false}, {true, false, true, false}, {true, true, false, false}},
            {{false, false, true, true}, {false, false, false, true}, {false, true, false, true}, {true, true, false, true}}
    };

    private final boolean[] S_AES_POLYNOMIAL = { true, false, false, true, true};
    public AES(KeyGenerator k){
        this.keyGenerator = k;
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
                boolean[][] D = new boolean[4][4];

                D[0] = Utilities.XOR(B[0], B[1]);
                D[2] = Utilities.XOR(B[2], B[3]);

                D[1] = mixColumnsSecondRow(B[0], B[1]);
                D[3] = mixColumnsSecondRow(B[2], B[3]);

                System.arraycopy(D, 0, B, 0, 4);
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
    private boolean[] sBox(boolean[] input){
        int row = 2*(input[0] ? 1 : 0) + (input[1] ? 1 : 0);
        int col = 2*(input[2] ? 1 : 0) + (input[3] ? 1 : 0);
        return Arrays.copyOf(s[row][col], 4);
    }

    private boolean[] mixColumnsSecondRow(boolean[] C0, boolean[] C1){
        boolean[] t1 = new boolean[5];
        boolean[] t2 = new boolean[5];
        boolean[] r = new boolean[5];

        // adding a 0 bit to left for padding
        System.arraycopy(C0, 0, t2, 1, 4);

        // shifting for one position to the left - equivalent of multiplying by 2
        System.arraycopy(C1, 0, t1, 0, 4);

        // adding the two together - addition on G(2^m) is same as XOR
        System.arraycopy(Utilities.XOR(t1, t2), 0, r, 0, 5);

        // if there is overflow we "subtract" the S-AES polynomial - which is equivalent to adding/XOR on G(2^m)
        if(r[0])
            r = Utilities.XOR(r, this.S_AES_POLYNOMIAL);

        // remove the overflowing 0 bit
        boolean[] res = new boolean[4];
        System.arraycopy(r, 1, res, 0, 4);

        return res;
    }
}

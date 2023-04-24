package AES_GCM;

import java.util.Arrays;

public class AES {
    private KeyGenerator keyGenerator;
    private boolean[] P;
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

        this.P = Utilities.XOR(this.P, keyGenerator.getKey(0));

        nextRound(1);
        nextRound(2);
        nextRound(3);

        // TODO: remove next lines
        for(boolean bit : P){
                System.out.print(bit ? '1' : '0');
            }
        return null;
    }

    /**
     * Performs the specified round of the S-AES
     * @param round The required round to be performed
     */
    private void nextRound(int round){
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
            // TODO: implement mix columns
        }

        System.arraycopy(B[0], 0, this.P, 0, 4);
        System.arraycopy(B[1], 0, this.P, 4, 4);
        System.arraycopy(B[2], 0, this.P, 8, 4);
        System.arraycopy(B[3], 0, this.P, 12, 4);
    }
    /**
     * Performs Substitute Box function on given input
     * @param input an array of boolean of length 4
     * @return Calculated output of S-AES, boolean array of length 4
     */
    private boolean[] sBox(boolean[] input){
        boolean[][][] s = {
                {{false, true, true, false}, {true, false, true, true}, {false, false, false, false}, {false, true, false, false}},
                {{false, true, true, true}, {true, true, true, false}, {false, false, true, false}, {true, true, true, true}},
                {{true, false, false, true}, {true, false, false, false}, {true, false, true, false}, {true, true, false, false}},
                {{false, false, true, true}, {false, false, false, true}, {false, true, false, true}, {true, true, false, true}}
        };

        int row = 2*(input[0] ? 1 : 0) + (input[1] ? 1 : 0);
        int col = 2*(input[2] ? 1 : 0) + (input[3] ? 1 : 0);

        return Arrays.copyOf(s[row][col], 4);
    }


}

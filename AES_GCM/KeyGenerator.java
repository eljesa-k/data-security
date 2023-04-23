package AES_GCM;

import java.util.Arrays;

public class KeyGenerator {
    private boolean[][] keys;
    private final boolean[][] RC = {
            {false, false, false, true},
            {false, false, true, false},
            {false, true, false, false}
    };

    /**
     * Creates sub-keys for Simplified AES algorithm
     * @param key initial 16-bit key in hexadecimal
     * @throws Exception if initial key length is not 4, or key is not in hexadecimal
     */

    // TODO: put back the exception
    public KeyGenerator(String key) {
//        if(key.length() != 4)
//            throw new Exception("Invalid initial key! Key length must be 4.");

        this.keys = new boolean[4][16];

        // Convert and fill in the original key K0
        for(int i = 0; i < 4; i++){
//            if(key.charAt(i) < '0' || key.charAt(i) > 'F') throw new Exception("Invalid initial key! Key must be hexadecimal");
            String converted = Utilities.toBinary(key.charAt(i));
            int k = 0;
            for(char bit : converted.toCharArray()){
                keys[0][4 * i + k] = bit == '1';
                k++;
            }
        }

        // Generate the other 3 keys
        this.generateNextKey(1);
        this.generateNextKey(2);
        this.generateNextKey(3);
    }


    /**
     * Returns the specified key
     * @param index The index of the required key. An integer between 0 and 3
     * @return The required key as a boolean array of length 16
     */
    public boolean[] getKey(int index){
        if(index >= 0 && index < 4) {
            boolean[] temp = new boolean[16];
            System.arraycopy(this.keys[index], 0, temp, 0, 16);
            return temp;
        }
        return null;
    }

    /**
     * Function g of Simplified AES
     * @param input the array of bits (length 4)
     * @param rc_index Index i of RC for the specific round
     * @return Output of the function (boolean array of length 4)
     */
    private boolean[] g(boolean[] input, int rc_index){
        boolean[] result = sBox(new boolean[] {input[1], input[2], input[3], input[0]});
        return Utilities.XOR(result, this.RC[rc_index]);
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

    /**
     * Creates a sub-key for S-AES
     * @param currentKey the index of the key which should be generated
     */
    private void generateNextKey(int currentKey){
        boolean[] k0 = new boolean[4], k1 = new boolean[4], k2 = new boolean[4], k3 = new boolean[4];
        System.arraycopy(this.keys[currentKey - 1], 0, k0, 0, 4);
        System.arraycopy(this.keys[currentKey - 1], 4, k1, 0, 4);
        System.arraycopy(this.keys[currentKey - 1], 8, k2, 0, 4);
        System.arraycopy(this.keys[currentKey - 1], 12, k3, 0, 4);

        k0 = Utilities.XOR(k0, this.g(k3, currentKey -1));
        k1 = Utilities.XOR(k0, k1);
        k2 = Utilities.XOR(k1, k2);
        k3 = Utilities.XOR(k2, k3);

        System.arraycopy(k0, 0, this.keys[currentKey], 0, 4);
        System.arraycopy(k1, 0, this.keys[currentKey], 4, 4);
        System.arraycopy(k2, 0, this.keys[currentKey], 8, 4);
        System.arraycopy(k3, 0, this.keys[currentKey], 12, 4);
    }

}
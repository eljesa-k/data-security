package AES_GCM;

public class Utilities {

    /**
     * Converts a character from base 16 to binary
     * @param num The character to convert
     * @return String The converted 4-bit number
     */
    public static String toBinary(char num){
        switch (num){
            case('A'):
                return "1010";
            case('B'):
                return "1011";
            case('C'):
                return "1100";
            case('D'):
                return "1101";
            case('E'):
                return "1110";
            case('F'):
                return "1111";
            default:{
                return convertFromDecimal(num - '0', 2, 4);
            }
        }
    }
    /**
     * Converts numbers from base 10 to any specified base and length
     * @param number The decimal number to be converted
     * @param base Base
     * @param length Desired length of the result string
     * @return String
     */
    public static String convertFromDecimal(int number, int base, int length){
        StringBuilder res = new StringBuilder();
        for(int i = 1; i <= length; i++){
            res.insert(0, number % base);
            number /= base;
        }

        return res.toString();
    }

    /**
     * Performs logic XOR operation over each of the elements of two given arrays
     * @param A first array of boolean
     * @param B second array of boolean
     * @return An array of boolean with same length as the two given arrays
     */
    public static boolean[] XOR(boolean[] A, boolean[] B){
        boolean[] res = new boolean[A.length];

        for(int i = 0; i < A.length; i++){

            res[i] = A[i] ^ B[i];
        }
        return res;
    }
}

package AES_GCM;

import java.util.Random;

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
    public static String getMessageFromBinary(String binary){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 13;  i+=4){
            stringBuilder.append(singleLetter(binary.substring(i, i+4)));
        }
        return stringBuilder.toString();
    }
    private static String singleLetter(String fourBit) {
        switch (fourBit) {
            case "0000":
                return "0";
            case "0001":
                return "1";
            case "0010":
                return "2";
            case "0011":
                return "3";
            case "0100":
                return "4";
            case "0101":
                return "5";
            case "0110":
                return "6";
            case "0111":
                return "7";
            case "1000":
                return "8";
            case "1001":
                return "9";
            case "1010":
                return "A";
            case "1011":
                return "B";
            case "1100":
                return "C";
            case "1101":
                return "D";
            case "1110":
                return "E";
            case "1111":
                return "F";
            default:
                return null;
        }
    }
    public static String generateIV(int numberOfBits){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < numberOfBits; i++){
            Random randI = new Random();
            int randomInt = randI.nextInt(2);
            stringBuilder.append(randomInt);
        }
        return stringBuilder.toString();
    }
    public static String increaseBinaryByOne(String binaryNumber){
        int decimalNumber = Integer.parseInt(binaryNumber, 2);
        decimalNumber++;
        String binaryString = Integer.toBinaryString(decimalNumber);
        return binaryString;
    }
}

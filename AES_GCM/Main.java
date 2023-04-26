package AES_GCM;

public class Main {
    public static void main(String[] args) {
        KeyGenerator keyGenerator = new KeyGenerator("CAF1");
        AES c = new AES(keyGenerator);
        //String plain_text = "AE24";
//        String plain_text = "10101110001001000";
//        System.out.println("Plain Text:" + plain_text);
//        String cipher_text = c.encrypt(plain_text);
//        System.out.println("\nResult = " + cipher_text);
//        System.out.println("--------------------------------");
//        String decipher_text = c.decrypt(cipher_text);
//        String result = Utilities.getMessageFromBinary(decipher_text);
//        System.out.println("\nResult = " + result);

        GCM g = new GCM(c, "0000000000000000");
        String [] test = g.encryptBlocksGCM("10101110001001000101011100010010011010111000100101010101110001001011");
        System.out.println("ciphered-aes-gcm: ");
        for (int i = 0;  i < test.length; i++){
            System.out.print(test[i]);
        }
        System.out.println();
        System.out.println("----------------------------------------------------");
        String [] testdecryption = g.decryptBlocksGCM("0011100100100100011011101001110100101011000110101010000011111001");//result i enkriptimit
        System.out.println("deciphered-aes-gcm: ");
        for (int i = 0;  i < testdecryption.length; i++){
            System.out.print(testdecryption[i]);
        }
    }
}

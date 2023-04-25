package AES_GCM;

public class Main {
    public static void main(String[] args) {
        KeyGenerator keyGenerator = new KeyGenerator("CAF1");
        AES c = new AES(keyGenerator);
        String plain_text = "AE24";
        System.out.println("Plain Text:" + plain_text);
        String cipher_text = c.encrypt(plain_text);
        System.out.println("\nResult = " + cipher_text);
        System.out.println("--------------------------------");
        String decipher_text = c.decrypt(cipher_text);
        String result = Utilities.getMessageFromBinary(decipher_text);
        System.out.println("\nResult = " + result);
    }
}

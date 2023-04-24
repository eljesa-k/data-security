package AES_GCM;

public class Main {
    public static void main(String[] args) {
        KeyGenerator keyGenerator = new KeyGenerator("CAF1");
        AES c = new AES(keyGenerator);

//        String plain_text = new FileReader().getFileContent();
        String plain_text = "AE24";
        String cipher_text = c.encrypt(plain_text);
        System.out.println("\nResult = " + cipher_text);
    }
}

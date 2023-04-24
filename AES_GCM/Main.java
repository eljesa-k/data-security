package AES_GCM;

public class Main {
    public static void main(String[] args) {

        KeyGenerator keyGenerator = new KeyGenerator("CAF1");
        AES c = new AES(keyGenerator);
        GCM g = new GCM(c, 0);

//        String plain_text = new FileReader().getFileContent();
//        String plain_text = "AE24";
//        c.encrypt(plain_text);


    }
}

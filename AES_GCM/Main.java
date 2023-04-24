package AES_GCM;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        KeyGenerator keyGenerator = new KeyGenerator("CAF1");
        AES c = new AES(keyGenerator);

        String plaintext = new FileReader().getFileContent();
        c.encrypt(plaintext);

//        // TODO: remove next lines
//        System.out.println("Keys");
//        for(int i = 0; i < 4; i++){
//            for(boolean bit : keyGenerator.getKey(i)){
//                System.out.print(bit ? '1' : '0');
//            }
//            System.out.println();
//        }
    }
}

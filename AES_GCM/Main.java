package AES_GCM;

public class Main {
    public static void main(String[] args) {
        KeyGenerator keyGenerator = new KeyGenerator("CAF1");
        Encryption encryption = new Encryption(keyGenerator);

        encryption.encrypt("AE24");

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

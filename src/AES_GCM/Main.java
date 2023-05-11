package src.AES_GCM;

public class Main {
    private KeyGenerator keyGenerator;
    private AES c;
    private GCM g;

    public Main(){
        keyGenerator = new KeyGenerator("CAF1");
        c = new AES(keyGenerator);
        g = new GCM(c, "0000000000000000");
    }
    public void main(String[] args) {
        //String plain_text = "AE24";
//        String plain_text = "10101110001001000";
//        System.out.println("Plain Text:" + plain_text);
//        String cipher_text = c.encrypt(plain_text);
//        System.out.println("\nResult = " + cipher_text);
//        System.out.println("--------------------------------");
//        String decipher_text = c.decrypt(cipher_text);
//        String result = Utilities.getMessageFromBinary(decipher_text);
//        System.out.println("\nResult = " + result);
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
    public String getText(String plaintext, boolean isEncryption){
        StringBuilder p = new StringBuilder();
        plaintext = StringToBinary.convertStringToBinary(plaintext);
        System.out.println(plaintext);
        System.out.println( "/////////////////////");
        if(isEncryption){
//            String [] test = g.encryptBlocksGCM("10101110001001000101011100010010011010111000100101010101110001001011");
            String [] test = g.encryptBlocksGCM(plaintext);
            System.out.println("ciphered-aes-gcm: ");

            StringBuilder s = new StringBuilder();
            for(String item : test){
                s.append(item);
            }
            for (int i = 0;  i < test.length; i++){
                System.out.print(test[i]);
            }
            return s.toString();
        }else{
            String [] testdecryption = g.decryptBlocksGCM(plaintext);//result i enkriptimit
//            String [] testdecryption = g.decryptBlocksGCM("0011100100100100011011101001110100101011000110101010000011111001");//result i enkriptimit
            System.out.println("deciphered-aes-gcm: ");
            for (int i = 0;  i < testdecryption.length; i++){
                System.out.print(testdecryption[i]);
            }
            StringBuilder s = new StringBuilder();
            for(String item : testdecryption){
                s.append(item);
            }
            return s.toString();
        }
    }
}

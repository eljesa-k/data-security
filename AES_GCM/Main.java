package AES_GCM;

public class Main {
    public static void main(String[] args) {

        KeyGenerator keyGenerator = new KeyGenerator("CAF1");
        AES c = new AES(keyGenerator);
        System.out.println("TEST ME");

//        String plain_text = new FileReader().getFileContent();
        String plain_text = "AE24";
        String cipher_text = c.encrypt(plain_text);
        System.out.println("\nResult = " + cipher_text);

//        final boolean[] S_AES_POLYNOMIAL = { true, false, false, true, true};
//        GaloisField field = new GaloisField(S_AES_POLYNOMIAL, 4);
//
//        boolean[] H1 = {false, false, false, true};
//        boolean[] H5 = {false, true, false, true};
//        boolean[] H9 = {true, false, false, true};
//        boolean[] E = {true, true, true, false};
//        boolean[] F = {true, true, true, true};
//        boolean[] A = {true, false, true, false};
//
//        boolean[] res = field.multiply(F, A);
//        for(boolean bit : res){
//            System.out.print(bit ? '1' : '0');
//        }
//
//        System.out.println();
//        boolean[] res2 = field.multiply(H5, H9);
//        for(boolean bit : res2){
//            System.out.print(bit ? '1' : '0');
//        }
//
//        System.out.println();
//        boolean[] res3 = field.multiply(H5, H1);
//        for(boolean bit : res3){
//            System.out.print(bit ? '1' : '0');
//        }

    }
}

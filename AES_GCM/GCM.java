package AES_GCM;

import java.util.Arrays;

public class GCM {
    private AES AES_Algorithm;
    private String plaintext; //input

    private String ciphertext; //input per decrypt
    private String VI;
    private String counter;
    private String generalCounter; // VI + COUNTER

    private String generalCounterDecryption;
    private String[] BlocksGCM = new String[4]; //supposing qe plaintext i ka 4X16 bit

    private String[] BlocksGCMDecrypted = new String[4]; //supposing qe plaintext i ka 4X16 bit
    private String H;
    private String AAD;

    private String Tag;
    private final String S_AES_POLYNOMIAL = "10011";
    private final boolean[] S_AES_POLYNOMIAL_B = {true, false, false, true, true};
    private GaloisField field;

    public GCM(AES AES_Algorithm, String AAD) {
        this.AES_Algorithm = AES_Algorithm;
//        VI = Utilities.generateIV(10);
        // TODO: remove next line
        VI = "0000000000";
        counter = "0000";
        generalCounter = VI + counter;
        generalCounterDecryption = VI + counter;
        System.out.println("VI: " + VI);

        this.H = AES_Algorithm.encrypt("0000000000000000");
        this.AAD = AAD;
        this.field = new GaloisField(S_AES_POLYNOMIAL_B, 4);
    }
    public String[] encryptBlocksGCM(String plaintext){
        this.plaintext = plaintext;
        //divide plaintext into blocks
        int num_blocks = plaintext.length()/16;
        String[] blocks = new String[num_blocks];
        for (int i = 0; i < num_blocks; i++){
            blocks[i] = plaintext.substring(i, i+16);
        }

        String EkCtr0 = "";

        //encrypt each block from the plaintext
        for (int i = 0; i <= num_blocks; i++){
            //skip on first time because counter starts at 0000
            if (i == 0){
                //encrypt GeneralCounter
                EkCtr0 = AES_Algorithm.encrypt(VI + counter);
                Tag = field.multiply(AAD, H);
            }else{
                counter = Utilities.increaseBinaryByOne(counter);
                //encrypt GeneralCounter
                String encryptedGeneralCounter = AES_Algorithm.encrypt(VI + counter);
                //calculate XOR
                BlocksGCM[i-1] = calculate_CTi(encryptedGeneralCounter, blocks[i]);
                Tag = field.multiply(calculate_CTi(Tag, BlocksGCM[i-1]), H);
            }

        }

        Tag = calculate_CTi(Tag, EkCtr0);
        //return array of Strings that represent the encrypted blocks form the plaintext, array of Ct_i
        return Arrays.copyOf(BlocksGCM, BlocksGCM.length);
    }

    public String[] decryptBlocksGCM(String cipherText){
        this.ciphertext = cipherText;
        //divide ciphertext into blocks
        String[] blocks = new String[4];
        for (int i= 0,j=0; i < 13 && j<4; i+=4, j++){
            blocks[i] = cipherText.substring(i, i+4);
        }

        //encrypt each block from the ciphertext
        int length = blocks.length;
        for (int i = 0; i < length; i++){
            //skip on first time because counter starts at 0000
            if (i !=0 ){
                generalCounterDecryption = Utilities.increaseBinaryByOne(generalCounterDecryption);
            }
            //encrypt GeneralCounter
            String encryptedGeneralCounterDecryption = AES_Algorithm.encrypt(generalCounterDecryption);
            //calculate XOR
            BlocksGCMDecrypted[i] = calculate_CTi(encryptedGeneralCounterDecryption, blocks[i]);
        }
        return BlocksGCMDecrypted;
    }


    //need to test - unsure per qato == '1' a bon mir
    private String calculate_CTi(String encryptedGeneralCounter, String block){
        char[] A_char = encryptedGeneralCounter.toCharArray();
        char[] B_char = block.toCharArray();
        boolean[] A = new boolean[A_char.length];
        boolean[] B = new boolean[B_char.length];
        for (int i = 0; i<A.length; i++){
            if (A_char[i] == '1')
                A[i] = true;

            if (B_char[i]== '1')
                B[i] = true;
        }
        boolean[] result =  Utilities.XOR(A,B);
        return  result.toString();
    }

}
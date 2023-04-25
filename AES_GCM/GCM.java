package AES_GCM;

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

    public GCM(AES AES_Algorithm) {
        this.AES_Algorithm = AES_Algorithm;
        VI = Utilities.generateIV(10);
        counter = "0000";
        generalCounter = VI + counter;
    }
    public String[] encryptBlocksGCM(String plaintext){
        this.plaintext = plaintext;
        //divide plaintext into blocks
        String[] blocks = new String[4];
        for (int i= 0,j=0; i < 13 && j<4; i+=4, j++){
            blocks[i] = plaintext.substring(i, i+4);
        }
        //encrypt each block from the plaintext
        int length = blocks.length;
        for (int i = 0; i < length; i++){
            //skip on first time because counter starts at 0000
            if (i !=0 ){
                generalCounter = Utilities.increaseBinaryByOne(generalCounter);
            }
            //encrypt GeneralCounter
            String encryptedGeneralCounter = AES_Algorithm.encrypt(generalCounter);
            //calculate XOR
            BlocksGCM[i] = calculate_CTi(encryptedGeneralCounter, blocks[i]);
        }
        //return array of Strings that represent the encrypted blocks form the plaintext, array of Ct_i
        return BlocksGCM;
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
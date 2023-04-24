package AES_GCM;

public class GCM {
    private final AES enc;
    private boolean[][] counters;
    private boolean[] H;

    public GCM(AES e, int initial_counter){
        this.enc = e;
        this.H = enc.encrypt("0000");
    }

    public String encrypt(String plainText){
        // TODO : counters
        // TODO : y[i]
        // TODO : g functions
        // TODO : tag

        return null;
    }


}

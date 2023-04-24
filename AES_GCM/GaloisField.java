package AES_GCM;

public class GaloisField {
    private final boolean[] polynomial;
    public final int length;
    public GaloisField(boolean[] polynomial, int length){
        this.length = length;
        this.polynomial = polynomial;
    }

    /**
     * Multipies two numbers on Galois Field 2^m
     * @param a first number
     * @param b second number
     * @return boolean[] result
     */
    public boolean[] multiply(boolean[] a, boolean[] b){
        boolean[] result = new boolean[2 * length - 1];
        boolean[] temp;

        // multiply by shifting
        for(int i = 0; i < length; i++){
            if(b[i]){
                temp = new boolean[2*length];
                System.arraycopy(a, 0, temp, i, length);
                result = Utilities.XOR(result, temp);
            }
        }

        result = normalize(result);

        temp = new boolean[length];
        System.arraycopy(result, length -1, temp, 0, length);
        return temp;
    }

    private boolean[] normalize(boolean[] result){
        for(int i = 0; i < length-1; i++){
            if(result[i]){
                boolean[] temp = new boolean[2 * length - 1];
                System.arraycopy(this.polynomial, 0, temp, i, length + 1);
                result = Utilities.XOR(result, temp);
            }
        }
        return result;
    }
}

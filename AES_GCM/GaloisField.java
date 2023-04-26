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
    public String multiply(String a1, String b1){
        boolean[] a = new boolean[a1.length()];
        boolean[] b = new boolean[b1.length()];
        for(int i = 0; i < a1.length(); i++) {
            a[i] = a1.charAt(i) == '1';
        }
        for(int i = 0; i < b1.length(); i++) {
            b[i] = b1.charAt(i) == '1';
        }

        boolean[] temp = multiply(a, b);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < a1.length();  i+=4){
            stringBuilder.append(temp[i] ? '1' : '0');
        }
        return stringBuilder.toString();
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

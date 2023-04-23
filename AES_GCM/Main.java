package AES_GCM;

public class Main {
    public static void main(String[] args) {
        KeyGenerator keyGenerator = new KeyGenerator("CFA1");

        // TODO: remove next lines
        for(int i = 0; i < 4; i++){
            for(boolean bit : keyGenerator.getKey(i)){
                System.out.print(bit ? '1' : '0');
            }
            System.out.println();
        }
    }
}

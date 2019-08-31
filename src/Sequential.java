/**
 * @author Soham Dongargaonkar [sd4324] on 31/08/19
 */
public class Sequential {
    private int low;
    private int high;
    private Prime.Iterator iterator;

    Sequential(){
        low = 2;
        high = 2;   //Default low and high values to lowest valid prime
        iterator = new Prime.Iterator();
    }

    public static void main(String[] args) {
        Sequential sequential = new Sequential();
        sequential.parseArguments(args);


    }

    /**
     * Parses arguments and sets high and low values.
     *
     * @param args arguments from STDIN
     *
     * @throws IllegalStateException if low > high, ie. args[0] > args[1].
     */
    private void parseArguments(String[] args) {
        low = Integer.parseInt(args[0]);
        high = Integer.parseInt(args[1]);
        if (low > high) {
            throw new IllegalStateException("low cannot exceed high, check " +
                    "arguments");
        }
    }


}

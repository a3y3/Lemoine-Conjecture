/**
 * @author Soham Dongargaonkar [sd4324] on 31/08/19
 */
public class Sequential {
    private int low;
    private int high;

    // if enabled using -v, print p and q for all numbers in range
    private boolean verboseOutputs;

    private Sequential() {
        low = 2;
        high = 2;   //Default low and high values to lowest valid prime
        verboseOutputs = false; // Default behaviour prints only highest prime
    }

    public static void main(String[] args) {
        Sequential sequential = new Sequential();
        sequential.parseArguments(args);
        sequential.scanOddNumbers();
    }

    /**
     * Parses arguments and sets high and low values.
     *
     * @param args arguments from STDIN
     * @throws IllegalStateException if low > high, ie. args[0] > args[1].
     */
    private void parseArguments(String[] args) {
        low = Integer.parseInt(args[0]);
        high = Integer.parseInt(args[1]);
        if (low > high) {
            throw new IllegalStateException("low cannot exceed high, check " +
                    "arguments");
        }
        for (int i = 2; i < args.length; i++){
            if (args[i].equals("-v")){
                verboseOutputs = true;
                break;      //For performance, MUST remove if adding more flags.
            }
        }
    }

    /**
     * Contains a for loop that iterators over odd numbers, and calls @code{getPrimes
     * (i)} for each @code{i} in the loop.
     * Depending on the value of @code{verboseOutputs}, the value "i=p+2q" is printed.
     * If @code{verboseOutputs} is false (default), only the highest @code{i} is printed.
     */
    private void scanOddNumbers() {
        low = low % 2 == 0 ? low + 1 : low;
        high = high % 2 == 0 ? high - 1 : high;  //Make sure low and high are odd
        int maxP = -1;
        int finalQ = -1;
        int finalNumber = - 1;
        for (int i = low; i <= high; i += 2) {
            int[] pAndQ = getPrimes(i);
            if (maxP < pAndQ[0]){
                maxP = pAndQ[0];
                finalQ = pAndQ[1];
                finalNumber = i;
            }
            if (verboseOutputs){
                System.out.println(i + " = " + pAndQ[0] + " + 2*" + pAndQ[1]);
            }
        }
        System.out.println(finalNumber + " = " + maxP + " + 2*" + finalQ);
    }

    /**
     * Given a number, finds two primes @code{p} and @code{q} such that number = p + 2q.
     *
     * @param number the number for which thw primes are supposed to be found. @code{
     *               number} must be odd.
     *
     * @return an array containing p in index 0 and q in index 1.
     */
    private int[] getPrimes(int number) {
        Prime.Iterator pIterator = new Prime.Iterator();
        Prime.Iterator qIterator = new Prime.Iterator();
        int p = pIterator.next();
        int q = 2;        // q is allowed to be even, so start with 2

        for (; p + (2 * q) != number; p = pIterator.next()) {
            while ((p + (2 * q)) < number) {
                q = qIterator.next();
            }
            if (p + (2 * q) == number){
                break;
            }
            qIterator.restart();
            q = 2;
        }
        return new int[]{p, q};
    }
}

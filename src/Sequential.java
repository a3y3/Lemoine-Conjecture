/**
 * @author Soham Dongargaonkar [sd4324] on 31/08/19
 */
public class Sequential {
    private int low;
    private int high;

    Sequential() {
        low = 2;
        high = 2;   //Default low and high values to lowest valid prime
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
    }

    private void scanOddNumbers() {
        low = low % 2 == 0 ? low + 1 : low;
        high = high % 2 == 0 ? high - 1 : high;  //Make sure low and high are odd
        for (int i = low; i <= high; i += 2) {
            int[] pAndQ = getPrimes(i);
        }
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
        System.out.println(number + " = " + p + " + 2*" + q);
        return new int[]{p, q};
    }
}

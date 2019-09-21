/**
 * Lemoine's Conjecture states that each odd number n > 5 can be represented
 * as n = p + 2q, where p and q are prime numbers.
 * <p>
 * The class accepts a lower and an upper bound and prints a single number n
 * in that range that has the highest p value.
 * <p>
 * You can also run the program with the -v flag to see all the numbers,
 * instead of only the number with the largest p value.
 * <p>
 * Run the program with -h for help and examples.
 *
 * @author Soham Dongargaonkar [sd4324] on 31/08/19
 */
public class LemoineConjecture {
    int low;
    int high;
    boolean verboseOutputs;

    private static final String SEQ_MODE = "SEQUENTIAL";
    private static final String PARALLEL_MODE = "PARALLEL";

    /**
     * Initializes both low and high with 2. These are the lowest valid primes.
     * <p>
     * The suppressed warning is to prevent IntelliJ from complaining about adding a
     * private access modifier to this constructor.
     */
    @SuppressWarnings("WeakerAccess")
    LemoineConjecture() {
        low = 2;
        high = 2;
        verboseOutputs = false; // Default behaviour prints only highest prime
    }

    /**
     * Parses arguments, and runs sequential and parallel versions of the program.
     *
     * @param args arguments from STDIN. See {@code java LemoineConjecture -h} for help
     *             and usage.
     */
    public static void main(String[] args) {
        LemoineConjecture conjecture = new LemoineConjecture();
        new ArgumentParser(conjecture).parseArguments(args);

        System.out.println("*** STARTING SERIAL EXECUTION ***");
        conjecture.runProgram(SEQ_MODE);
        System.out.println();
        System.out.println("*** STARTING PARALLEL EXECUTION ***");
        conjecture.runProgram(PARALLEL_MODE);
    }


    /**
     * Runs either the sequential or the parallel version depending on the {@code mode}
     * passed.
     *
     * @param mode either sequential or parallel. The appropriate version of the
     *             program is called depending upon this value.
     */
    private void runProgram(String mode) {
        long startTime = System.nanoTime();
        if (mode.equals(SEQ_MODE)) {
            scanOddNumbersSequential();
        } else {
            scanOddNumbersParallel();
        }
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Time taken for execution:" + elapsedTime / 1000000 + " ms");
    }

    /**
     * Contains a for loop that iterators over odd numbers, and calls {@code getPrimes
     * (i)} for each {@code i} in the loop.
     * Depending on the value of {@code verboseOutputs}, the value "i=p+2q" is printed.
     * If {@code verboseOutputs} is false (default), only the highest {@code i} is
     * printed.
     */
    private void scanOddNumbersSequential() {
        low = low % 2 == 0 ? low + 1 : low;
        high = high % 2 == 0 ? high - 1 : high;  //Make sure low and high are odd
        int maxP = -1;
        int finalQ = -1;
        int finalNumber = -1;
        for (int i = low; i <= high; i += 2) {
            int[] pAndQ = getPrimes(i);
            if (maxP <= pAndQ[0]) {
                maxP = pAndQ[0];
                finalQ = pAndQ[1];
                finalNumber = i;
            }
            if (verboseOutputs) {
                System.out.println(i + " = " + pAndQ[0] + " + 2*" + pAndQ[1]);
            }
        }
        System.out.println(finalNumber + " = " + maxP + " + 2*" + finalQ);
    }

    /**
     * Contains a for loop that iterators over odd numbers, and calls {@code getPrimes
     * (i)} for each {@code i} in the loop.
     * <p>
     * The method is different from {@code scanOffNumbersSequential()} in that this
     * method contains a comment for parallelizing the scanning. If the program is
     * compiled with omp4j (and not javac), the method will run parallelly.
     * <p>
     * Depending on the value of {@code verboseOutputs}, the value "i=p+2q" is printed.
     * If {@code verboseOutputs} is false (default), only the highest {@code i} is
     * printed.
     */
    private void scanOddNumbersParallel() {
        low = low % 2 == 0 ? low + 1 : low;
        high = high % 2 == 0 ? high - 1 : high;  //Make sure low and high are odd
        int[] maxP = new int[]{-1};
        int[] finalQ = new int[]{-1};
        int[] finalNumber = new int[]{-1};

        // omp parallel for
        for (int i = low; i <= high; i += 2) {
            int[] pAndQ = getPrimes(i);
            if (maxP[0] <= pAndQ[0]) {
                maxP[0] = pAndQ[0];
                finalQ[0] = pAndQ[1];
                finalNumber[0] = i;
            }
            if (verboseOutputs) {
                System.out.println(i + " = " + pAndQ[0] + " + 2*" + pAndQ[1] + "\tt_ID: "
                        + OMP4J_THREAD_NUM);
            }

        }
        System.out.println(finalNumber[0] + " = " + maxP[0] + " + 2*" + finalQ[0]);
    }

    /**
     * Given a number, finds two primes {@code p} and {@code q} such that number = p + 2q.
     *
     * @param number the number for which thw primes are supposed to be found. {@code
     *               number} must be odd.
     * @return an array containing p in index 0 and q in index 1.
     */
    private int[] getPrimes(int number) {
        Prime.Iterator pIterator = new Prime.Iterator();
        int p = pIterator.next();
        int q = 2;        // q is allowed to be even, so start with 2

        for (; p <= number; p = pIterator.next()) {
            if ((number - p) % 2 == 0) {
                int tempQ = (number - p) / 2;
                if (Prime.isPrime(tempQ)) {
                    q = tempQ;
                    break;
                }
            }
        }
        return new int[]{p, q};
    }
}
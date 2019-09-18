import java.util.ArrayList;

/**
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
     *
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
    private void runProgram(String mode){
        long startTime = System.nanoTime();
        if (mode.equals(SEQ_MODE)) {
            scanOddNumbersSequential();
        }
        else{
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
        int finalNumber = - 1;
        for (int i = low; i <= high; i += 2) {
            int[] pAndQ = getPrimes(i);
            if (maxP <= pAndQ[0]){
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
     * Contains a for loop that iterators over odd numbers, and calls {@code getPrimes
     * (i)} for each {@code i} in the loop.
     * Depending on the value of {@code verboseOutputs}, the value "i=p+2q" is printed.
     * If {@code verboseOutputs} is false (default), only the highest {@code i} is
     * printed.
     */
    private void scanOddNumbersParallel() {
        low = low % 2 == 0 ? low + 1 : low;
        high = high % 2 == 0 ? high - 1 : high;  //Make sure low and high are odd
        int maxP = -1;
        int finalQ = -1;
        int finalNumber = - 1;

        // omp parallel for global(maxP, finalQ, finalNumber)
        for (int i = low; i <= high; i += 2) {
            int[] pAndQ = getPrimes(i);
            if (maxP <= pAndQ[0]){
                maxP = pAndQ[0];
                finalQ = pAndQ[1];
                finalNumber = i;
            }
            if (verboseOutputs){
                System.out.println(i + " = " + pAndQ[0] + " + 2*" + pAndQ[1] + "ID: " + OMP4J_THREAD_NUM);
                System.out.println(i + " final = " + maxP + " + 2*" + finalQ + "ID: " + OMP4J_THREAD_NUM);
            }

        }
        System.out.println(finalNumber + " = " + maxP + " + 2*" + finalQ);
    }

    /**
     * Given a number, finds two primes {@code p} and {@code q} such that number = p + 2q.
     *
     * @param number the number for which thw primes are supposed to be found. {@code
     *               number} must be odd.
     *
     * @return an array containing p in index 0 and q in index 1.
     */
    private int[] getPrimes(int number) {
        System.out.println("Getting primes for " + number);
        Prime.Iterator pIterator = new Prime.Iterator();
        Prime.Iterator qIterator = new Prime.Iterator();
        int p = pIterator.next();
        int q = 2;        // q is allowed to be even, so start with 2

        for (; p + (2 * q) <= number; p = pIterator.next()) {
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
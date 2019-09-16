/**
 * @author Soham Dongargaonkar [sd4324] on 31/08/19
 */
public class LemoineConjecture {
    private int low;
    private int high;

    // if enabled using -v, print p and q for all numbers in range
    private boolean verboseOutputs;

    /**
     * initialize both low and high with 2. These are the lowest valid primes.
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
        conjecture.parseArguments(args);
        conjecture.runSequential();
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
            else if(args[i].equals(("-h"))){
                displayHelp();
            }
        }
    }

    /**
     * Displays usages and quits the JVM. Be cautious; using this method will quit this
     * program, so use only if the arguments supplied are incorrect or if the user
     * wishes to see how to use the program by specifying the -h flag.
     */
    private void displayHelp(){
        System.out.println("USAGE");
        System.out.println("java LemoineConjecture [low] [high] | " +  "[OPTIONAL_FLAG " +
                "1] [OPTIONAL_FLAG 2]" );
        System.out.println();
        System.out.println();
        System.out.println("EXPLANATION");
        System.out.println("Lemoine's Conjecture states that for any odd number n, a " +
                "combination of primes p and q exists such that n = p + 2q.");
        System.out.println("This program finds p and q for all odd numbers between the " +
                "range low and high.");
        System.out.println("The default behaviour of the program will print ONE number " +
                "for which the p value is the highest. See verbose flag below for the " +
                "other possible behaviour.");
        System.out.println();
        System.out.println("low: must be an integer. This number specifies the starting" +
                " range for running the conjecture.");
        System.out.println("high: must be an integer. This number specifies the ending " +
                "range for running the conjecture.");
        System.out.println();
        System.out.println();
        System.out.println("OPTIONAL FLAGS");
        System.out.println("Can be in any order, but must be of the following");
        System.out.println("-v: verbose outputs. Output p and q values for ALL the " +
                "numbers in the range, instead of outputting only one value.");
        System.out.println("-h: help. Displays this document.");
        System.exit(0);
    }
    /**
     * Calls {@code scanOdd}
     */
    private void runSequential(){
        scanOddNumbers();
    }
    /**
     * Contains a for loop that iterators over odd numbers, and calls {@code getPrimes
     * (i)} for each {@code i} in the loop.
     * Depending on the value of {@code verboseOutputs}, the value "i=p+2q" is printed.
     * If {@code verboseOutputs} is false (default), only the highest {@code i} is
     * printed.
     */
    private void scanOddNumbers() {
        low = low % 2 == 0 ? low + 1 : low;
        high = high % 2 == 0 ? high - 1 : high;  //Make sure low and high are odd
        int maxP = -1;
        int finalQ = -1;
        int finalNumber = - 1;
        int printCounter = 0;
        for (int i = low; i <= high; i += 2) {
            int[] pAndQ = getPrimes(i);
            if (maxP < pAndQ[0]){
                maxP = pAndQ[0];
                finalQ = pAndQ[1];
                finalNumber = i;
            }
            printCounter ++;
            if (printCounter % 10 == 0) {
                System.out.println("scanning " + i);
            }
            if (verboseOutputs){
                System.out.println(i + " = " + pAndQ[0] + " + 2*" + pAndQ[1]);
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

/**
 * Parses arguments passed in String args[] and modifies the passed LemoineConjecture's
 * instance variables accordingly.
 *
 * @author Soham Dongargaonkar [sd4324] on 16/09/19
 */
class ArgumentParser {
    private LemoineConjecture conjecture;

    ArgumentParser(LemoineConjecture conjecture) {
        this.conjecture = conjecture;
    }

    /**
     * Parses arguments and sets high and low values.
     *
     * @param args arguments from STDIN
     */
    void parseArguments(String[] args) {
        try {
            conjecture.low = Integer.parseInt(args[0]);
            conjecture.high = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Arguments one and two are incorrect. See below for " +
                    "details. Exiting ...");
            displayHelp();
        }
        if (conjecture.low > conjecture.high) {
            System.err.println("low cannot be greater than high. See below for details");
            displayHelp();
        }
        for (int i = 2; i < args.length; i++) {
            if (args[i].equals("-v")) {
                conjecture.verboseOutputs = true;
                break;      //For performance, MUST remove if adding more flags.
            } else if (args[i].equals(("-h"))) {
                displayHelp();
            }
        }
    }

    /**
     * Displays usages and quits the JVM. Be cautious; using this method will quit this
     * program, so use only if the arguments supplied are incorrect or if the user
     * wishes to see how to use the program by specifying the -h flag.
     */
    private void displayHelp() {
        System.out.println("USAGE");
        System.out.println("java LemoineConjecture [low] [high] | " + "[OPTIONAL_FLAG " +
                "1] [OPTIONAL_FLAG 2]");
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
        System.out.println();
        System.out.println();
        System.out.println("EXAMPLE");
        System.out.println("1.\tjava LemoineConjecture 100 1000");
        System.out.println("2.\tjava LemoineConjecture 10000 50000 -v");
        System.exit(0);
    }
}

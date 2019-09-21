# Lemoine-Conjecture
A parallel program to verify Lemoine's Conjecture in a given range of numbers.

### The conjecture
Lemoine's Conjecture states that every odd number n > 5 can be represented as n = p + 2q, where p and q are prime numbers.

The program accepts a lower and an upper bound and prints a single number n in that range that has the highest p value.
You can also run the program with the verbose flag (**-v**) to see all the numbers, instead of only the number with the largest p value.

### Important
The program uses the [Open MP for Java](http://www.omp4j.org/) library and must be compiled with the [omp4j jar](http://www.omp4j.org/download), not javac:

`java -jar omp4j.jar *.java`

### Example Outputs
```
java LemoineConjecture 1000000 1999999
*** STARTING SERIAL EXECUTION ***
1746551 = 1237 + 2*872657
Time taken for execution:9348 ms

*** STARTING PARALLEL EXECUTION ***
1746551 = 1237 + 2*872657
Time taken for execution:5321 ms
```
```
java LemoineConjecture 1000 1010 -v

*** STARTING SERIAL EXECUTION ***
1001 = 3 + 2*499
1003 = 5 + 2*499
1005 = 7 + 2*499
1007 = 73 + 2*467
1009 = 3 + 2*503
1007 = 73 + 2*467
Time taken for execution:64 ms

*** STARTING PARALLEL EXECUTION ***
1005 = 7 + 2*499	t_ID: 1
1009 = 3 + 2*503	t_ID: 1
1007 = 73 + 2*467	t_ID: 2
1003 = 5 + 2*499	t_ID: 0
1001 = 3 + 2*499	t_ID: 3
1007 = 73 + 2*467
Time taken for execution:50 ms

```
Run the program with the help flag (**-h**) for more help and examples.

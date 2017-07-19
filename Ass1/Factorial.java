/**
* @author adiel cahana <adielcahana@gmail.com>
* @version 1.0
* @since 2016-03-02 */
public class Factorial {
    /**
    * find the factorial of a given number iteratively.
    * <p>
    * @param num - to be calculated.
    * @return factorial - the factorial of num. */
    public static long factorialIter(long num) {
        long factorial = 1;
        for (long i = 1; i <= num; i++) {
            factorial *= i;
        }
        return factorial;
    }
    /**
    * find the factorial of a given number recursively.
    * <p>
    * @param num - to be calculated.
    * @return factorial - the factorial of num. */
    public static long factorialRecursive(long num) {
        if (num == 1 || num == 0) {
            return 1;
        }
        return num * factorialRecursive(num - 1);
    }
    /**
    * main function of Factorial.
    * <p>
    * prints the factorial of a given number
    * <p>
    * @param args - the number to be factorialize. */
    public static void main(String[] args) {
        int num = Integer.parseInt(args[0]);
        System.out.println("recursive: " + factorialRecursive(num));
        System.out.println("iterative: " + factorialIter(num));
    }
}
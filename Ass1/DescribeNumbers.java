/**
* @author adiel cahana <adielcahana@gmail.com>
* @version 1.0
* @since 2016-03-02 */
public class DescribeNumbers {
    /**
    * cast a string type to int type.
    * <p>
    * gets a string array and returns its elements as int array
    * <p>
    * @param numbers - the string of numbers.
    * @return intNums - array of int. */
    public static int[] stringsToInts(String[] numbers) {
        int size = numbers.length;
        int[] intNums = new int[size];
        int i = 0;
        while (i < size) {
            intNums[i] = Integer.parseInt(numbers[i]);
            i++;
        }
        return intNums;
    }
    /**
    * finds the minimum number of a series of numbers.
    * <p>
    * @param numbers - array of numbers.
    * @return min - the minimal number of the array. */
    public static int min(int[] numbers) {
        int min = numbers[0];
        int size = numbers.length;
        int i = 1;
        while (i < size) {
            if (min > numbers[i]) {
                min = numbers[i];
            }
            i++;
        }
        return min;
    }
    /**
    * finds the maximum number of a series of numbers.
    * <p>
    * @param numbers - array of numbers.
    * @return max - the maximal number of the array. */
    public static int max(int[] numbers) {
        int max = numbers[0];
        int size = numbers.length;
        int i = 1;
        while (i < size) {
            if (max < numbers[i]) {
                max = numbers[i];
            }
            i++;
        }
        return max;
    }
    /**
    * finds the average of a series of numbers.
    * <p>
    * @param numbers - array of numbers.
    * @return avg - the average of the given array. */
    public static float avg(int[] numbers) {

        float avg = 0;
        int size = numbers.length;
        int i = 0;
        while (i < size) {
            avg += numbers[i];
            i++;
        }
        return avg / size;
    }
    /**
    * main method of DescribeNumbers.
    * <p>
    * prints the minimum, maximum and average of a given set of numbers
    * <p>
    * @param args - string array of numbers. */
    public static void main(String[] args) {
        int[] num = stringsToInts(args);  //cast the args to integer numbers
        System.out.println("min: " + min(num));
        System.out.println("max: " + max(num));
        System.out.println("avg: " + avg(num));
    }
}

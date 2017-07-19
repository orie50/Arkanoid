import java.util.Map;
import java.util.TreeMap;

/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-05-05 */
public class ExpressionsTest {

    /**
     * run test for the program.
     * <p>
     * @throws Exception if evaluation / assignment failed.
     * @param args - no args expected*/
    public static void main(String[] args) throws Exception {
        Expression e = new Plus(new Mult(2, "x"), new Plus(new Sin(new Mult(4, "y")), new Pow("e", "x")));
        System.out.println(e);
        Map<String, Double> assignment = new TreeMap<String, Double>();
        assignment.put("x",  2.0);
        assignment.put("y",  0.25);
        assignment.put("e",  2.71);
        System.out.println(e.evaluate(assignment));
        e = e.differentiate("x");
        System.out.println(e);
        System.out.println(e.evaluate(assignment));
        System.out.println(e.simplify());
        System.out.println(e);
    }
}
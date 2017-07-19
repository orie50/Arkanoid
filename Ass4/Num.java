import java.util.List;
import java.util.Map;
/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-04-18 */
public class Num implements Expression {

    private double value;

    /** Num constructor.
     * Set the Num a value.
     * <p>
     * @param value - the value of the Num */
    public Num(double value) {
        this.value = value;
    }

    /** Evaluate the Num.
     * Set a value to the Num.
     * <p>
     * @param assignment - the value that should assigned to the Num (doesn't use).
     * @throws Exception if the try doesn't succeed catch the Exception.
     * @return the evaluate */
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double evaluate = 0;
        try {
            evaluate = this.evaluate();
        } catch (Exception e) {
            System.out.println("evaluate doesn't succeed");
        }
        return evaluate;
    }

    /** Evaluate the Num.
     * Give the Num his value.
     * <p>
     * @throws Exception if the try doesn't succeed catch the Exception.
     * @return the value of the Num */
    public double evaluate() throws Exception {
        double evaluate = 0;
        try {
            evaluate = this.value;
        } catch (Exception e) {
            System.out.println("evaluate failed");
        }
        return evaluate;
    }

    /** @return the Num string. */
    public String toString() {
        // Cancel the minus if Num == -0.0.
        if (Double.toString(value).equals("-0.0")) {
            return "0.0";
        }
        return Double.toString(value);
    }

     /** @return null because Num have no Variables. */
    public List<String> getVariables() {
        return null;
    }

    /**
     * assigns a given expression to a var.
     * <p>
     * @param var - a string of the var to assign the expression to
     * @param expression - a string of the var to assign the expression to
     * @return the same Num (because it's not a var it can't be assigned.*/
    public Expression assign(String var, Expression expression) {
        return this;
    }

    @Override
     /** @return the Num differentiate == (0). */
    public Expression differentiate(String var) {
        return new Num(0);
    }

    /** Return the Num simplify.
     * <p>
     * @return the Num. */
    public Expression simplify() {
        return this;
    }
}
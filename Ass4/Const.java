import java.util.List;
import java.util.Map;

/**
* @author Ori Engelberg <turht50@gmail.com>
* @version 1.0
* @since 2016-04-22 */
public class Const implements Expression {

    private Num value;
    private String name;

    /** Const constructor.
     * Check if the const is e or PI and set the value and the name of the const.
     * <p>
     * @param constant - the sign of the constant (e\PI).*/
    public Const(String constant) {
    // If the const = "e".
    if (constant.equals("e")) {
        this.value = new Num(Math.E);
        this.name = constant;
        } else if (constant.equals("Pi")) {
         // If the const = "PI".
            this.value = new Num(Math.PI);
            this.name = constant;
            } else {
             // If it's not a const.
                throw new RuntimeException("Error wrong argument" + constant);
                }
    }

    /** Evaluate the const.
     * Throw new Exception because const have a constant value.
     * <p>
     * @param assignment - the value that should assigned to the const.
     * @throws Exception that const can't be assigned to value.
     * @return new Exception */
    public double evaluate(Map<String, Double> assignment) throws Exception {
        throw new Exception("cannot assgin to Num");
        }

    /** Evaluate the const.
     * Give the const his value (for "e" = math.e, for "PI" = math.PI).
     * <p>
     * @throws Exception if the try doesn't succeed catch the Exception.
     * @return the value of the const */
    public double evaluate() throws Exception {
        double evaluate = 0;
        try {
            evaluate = this.value.evaluate();
            } catch (Exception e) {
                System.out.println("evaluate failed");
        }
        return evaluate;
        }

    @Override
    /** Return a list of the variables.
     * <p>
     * @return null (because const it's not a variable. */
    public List<String> getVariables() {
        return null;
        }

    /** Return the const string.
     * <p>
     * @return the name of the const ("e" for e, "PI" for PI). */
    public String toString() {
        return this.name;
        }

    @Override
    /** Return the const assign.
     * <p>
     * @return the const. */
    public Expression assign(String var, Expression expression) {
        return this;
        }

    @Override
    /** Return the const differentiate.
     * <p>
     * @return Num (0) because the const isn't a Var. */
    public Expression differentiate(String var) {
        return new Num(0);
        }

    /** Return the const simplify.
     * <p>
     * @return the const. */
    public Expression simplify() {
        return this;
        }
}
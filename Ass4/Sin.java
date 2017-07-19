/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-04-21 */
public class Sin extends UnaryExpression implements Expression {

    /** Sin constructor.
     * Give Sin an inhered argument and the operator "Sin".
     * <p>
     * @param sinus - the argument of the expression.*/
    public Sin(Object sinus) {
        super(sinus);
        this.setOperator("Sin");
    }

    @Override
    /** Evaluate the Sin.
     * Set the arguments the value of sinus(argument).
     * <p>
     * @throws Exception if the evaluation failed.
     * @return sinus of the argument */
    public double evaluate() throws Exception {
        double sin = 0;
        try {
            sin = Math.sin(Math.toRadians(getArg().evaluate()));
        } catch (Exception e) {
            System.out.println("Sin evaluation failed :" + e);
            throw e;
        }
        return sin;
    }

    @Override
    /**
     * assigns a given expression to a var.
     * <p>
     * @param var - a string of the var to assign the expression to
     * @param expression - a string of the var to assign the expression to
     * @return a new Sin expression with the new assigned vars.*/
    public Expression assign(String var, Expression expression) {
        return new Sin(getArg().assign(var, expression));
    }

    @Override
    /** differentiate of Sin.
     * <p>
     * @param var - the var to differentiate according it.
     * @return the differentiate of sin(u) => (u' * cos(u)). */
    public Expression differentiate(String var) {
        return new Mult(getArg().differentiate(var), new Cos(getArg()));
    }

    /** Simplification of Sin.
     * If the argument is't var evaluate the expression else check trigonometric identities.
     * <p>
     * @return the simplify expression. */
    public Expression simplify() {
        //if there is no vars, evaluate
        if (getArg().getVariables() == null) {
            try {
                double evaluate = this.evaluate();
                Expression exp = new Num(evaluate);
                return exp;
            } catch (Exception e) {
                System.out.println("Sin evaluation failed :" + e);
            }
        }
        //simplify the arguments
        Expression arg = getArg().simplify();
        // advanced simplification
        // sin(90 + x), sin(x + 90) = cos(x) // trigonometric identities.
        if (this.toString().equals(new Sin(new Plus(arg.getVariables().get(0), 90)).toString())
                || this.toString().equals(new Sin(new Plus(90, arg.getVariables().get(0))).toString())) {
            return new Cos(arg.getVariables().get(0));
        }
        return this;
    }
}
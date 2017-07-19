/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-04-21 */
public class Neg extends UnaryExpression implements Expression {

    /** Neg constructor.
     * Give Neg an inhered argument and the operator "-".
     * <p>
     * @param negation - the argument of the expression.*/
    public Neg(Object negation) {
        super(negation);
        this.setOperator("-");
    }

    @Override
    /** Evaluate the Neg.
     * Set the arguments the value of minus(argument).
     * <p>
     * @throws Exception if the evaluation failed.
     * @return minus of the argument */
    public double evaluate() throws Exception {
        double neg = 0;
        try {
            //System.out.println(getArg().evaluate());
            neg = -(getArg().evaluate());
        } catch (Exception e) {
            System.out.println("neg evaluation failed :" + e);
            throw e;
        }
        return neg;
    }

    @Override
    /**
     * return a string representation of the expression.
     * <p>
     * @return String - printable representation*/
    public String toString() {
        if (getArg() instanceof Var || getArg() instanceof Var) {
            return "(" + this.getOperator() + getArg().toString() + ")";
        }
        return super.toString();
    }

    @Override
    /**
     * assigns a given expression to a var.
     * <p>
     * @param var - a string of the var to assign the expression to
     * @param expression - a string of the var to assign the expression to
     * @return a new Neg expression with the new assigned vars.*/
    public Expression assign(String var, Expression expression) {
        return new Neg(getArg().assign(var, expression));
    }

    @Override
    /** differentiate of Neg.
     * <p>
     * @param var - the var to differentiate according it.
     * @return the differentiate of -(u) => -(u'). */
    public Expression differentiate(String var) {
        return new Neg(getArg().differentiate(var));
    }

    /** Simplification of Neg.
     * If the argument is't var evaluate the expression else check for minus reduction.
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
                System.out.println("neg evaluation failed :" + e);
            }
        }
        // advanced simplification
        // --(expression) = expression, ---(expression) = -(expression).
        Expression e = this.getArg().simplify();
        if (e instanceof Neg) {
            return ((Neg) e).getArg();
        }
        return new Neg(e);
    }
}
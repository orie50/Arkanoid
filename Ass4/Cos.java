/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-04-21 */
public class Cos extends UnaryExpression implements Expression {

    /**
     * Cos constructor.
     * Give Cos an inhered argument and the operator "Cos".
     * <p>
     * @param cosinus - the argument of the expression.*/
    public Cos(Object cosinus) {
        super(cosinus);
        this.setOperator("Cos");
    }

    @Override
    /**
     * Evaluate the Cos.
     * Set the arguments the value of cosinus(argument).
     * <p>
     * @throws Exception if the evaluation failed.
     * @return cosinus of the argument */
    public double evaluate() throws Exception {
        double cos = 0;
        try {
            cos = Math.cos(Math.toRadians(getArg().evaluate()));
        } catch (Exception e) {
            System.out.println("Cos evaluation failed :" + e);
            throw e;
        }
        return cos;
    }

    @Override
    /**
     * assigns a given expression to a var.
     * <p>
     * @param var - a string of the var to assign the expression to
     * @param expression - a string of the var to assign the expression to
     * @return a new Cos expression with the new assigned vars.*/
    public Expression assign(String var, Expression expression) {
        return new Cos(getArg().assign(var, expression));
    }

    @Override
    /**
     * differentiate of Cos.
     * <p>
     * @param var - the var to differentiate according it.
     * @return the differentiate of cos(u) => (u' * -sin(u)). */
    public Expression differentiate(String var) {
        return new Mult(getArg().differentiate(var), new Neg(new Sin(getArg())));
    }

    /**
     * Simplification of Cos.
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
                System.out.println("Cos evaluation failed :" + e);
            }
        }
        //simplify the arguments
        Expression arg = getArg().simplify();
        // advanced simplification
        // cos(90 + x), cos(x + 90) = -sin(x) // trigonometric identities.
        if (this.toString().equals(new Cos(new Plus(arg.getVariables().get(0), 90)).toString())
                || this.toString().equals(new Cos(new Plus(90, arg.getVariables().get(0))).toString())) {
            return new Neg(new Sin(arg.getVariables().get(0)));
        }
        return this;
    }
}

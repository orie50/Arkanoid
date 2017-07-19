
/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-05-02 */
public class Plus extends BinaryExpression implements Expression {
    /**
     * Plus constructor.
     * <p>
     * @param argA - can be Expression, Double, or a String representing a Var.
     * @param argB - can be Expression, Double, or a String representing a Var.*/
    public Plus(Object argA, Object argB) {
        super(argA, argB);
        this.setOperator(" + ");
    }

    @Override
    /**
     * sums the to arguments.
     * if there is Vars in one of the arguments, exception is thrown.
     * <p>
     * @return sum - the numeric sum of the arguments.*/
    public double evaluate() throws Exception {
        double sum = 0;
        try {
            // try to evaluate both of the arguments and sum them
            sum = this.getArgA().evaluate() + this.getArgB().evaluate();
        } catch (Exception e) {
            // there are Vars in the arguments
            System.out.println("Plus evaluation failed :" + e);
            throw e;
        }
        return sum;
    }

    @Override
    /**
     * assigns a given expression to a var.
     * <p>
     * @param var - a string of the var to assign the expression to
     * @param expression - a string of the var to assign the expression to
     * @return Plus - a new Plus expression with the new assigned vars.*/
    public Expression assign(String var, Expression expression) {
        return new Plus(this.getArgA().assign(var, expression), this.getArgB().assign(var, expression));
    }

    @Override
    /**
     * differentiate the expression with respect to a given var.
     * <p>
     * @param var - the var to differentiate
     * @return Plus - the differentiated expression.*/
    public Expression differentiate(String var) {
        return new Plus(this.getArgA().differentiate(var), this.getArgB().differentiate(var));
    }

    @Override
    /**
     * simplify the representation of the given expression with respect to a given var.
     * <p>
     * @return Expression - the simplified expression.*/
    public Expression simplify() {
        //if there is no vars, evaluate
        if (this.getArgA().getVariables() == null && this.getArgB().getVariables() == null) {
            try {
                double evaluate = this.evaluate();
                Expression exp = new Num(evaluate);
                return exp;
            } catch (Exception e) {
                System.out.println("Plus evaluation failed :" + e);
            }
        }
        //simplify the arguments
        Expression argA = this.getArgA().simplify();
        Expression argB = this.getArgB().simplify();
        //if one of the expression is of the form x+0 return x
        if (argA.toString().equals("0.0")) {
            return argB;
        }
        if (argB.toString().equals("0.0")) {
            return argA;
        }
        return new Plus(argA, argB);
    }
}

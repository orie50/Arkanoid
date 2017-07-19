/**
* @author Adiel cahana <adiel.cahana@gmail.com>
* @version 1.0
* @since 2016-05-02 */
public class Minus extends BinaryExpression implements Expression {
    /**
     * Minus constructor.
     * <p>
     * @param minuend - can be Expression, Double, or a String representing a Var.
     * @param subtrahend - can be Expression, Double, or a String representing a Var.*/
    public Minus(Object minuend, Object subtrahend) {
        super(minuend, subtrahend);
        this.setOperator(" - ");
    }

    @Override
    /**
     * evaluate the difference argB from argA
     * <p>
     * if there is Vars in one of the arguments, exception is thrown.
     * @return difference - the numeric difference of the arguments.*/
    public double evaluate() throws Exception {
        double difference = 0;
        try {
            // try to evaluate both of the arguments and sum them
            difference = this.getArgA().evaluate() - this.getArgB().evaluate();
        } catch (Exception e) {
            // there are Vars in the arguments
            System.out.println("Minus evaluation failed :" + e);
            throw e;
        }
        return difference;
    }

    @Override
    /**
     * assigns a given expression to a var.
     * <p>
     * @param var - a string of the var to assign the expression to
     * @param expression - a string of the var to assign the expression to
     * @return Minus - a new Minus expression with the new assigned vars.*/
    public Expression assign(String var, Expression expression) {
        return new Minus(this.getArgA().assign(var, expression), this.getArgB().assign(var, expression));
    }

    @Override
    /**
     * differentiate the expression with respect to a given var.
     * <p>
     * @param var - the var to differentiate
     * @return Minus - the differentiated expression.*/
    public Expression differentiate(String var) {
        return new Minus(this.getArgA().differentiate(var), this.getArgB().differentiate(var));
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
                System.out.println("Minus evaluation failed :" + e);
            }
        }
        //simplify the arguments
        Expression argA = this.getArgA().simplify();
        Expression argB = this.getArgB().simplify();
        // both of of the arguments are the same
        if (argB.toString().equals(argA.toString())) {
            return new Num(0);
        }
        // argA is 0, return -argBz
        if (argA.toString().equals("0.0")) {
            return new Neg(argB);
        }
        // argB is 0, return argA
        if (argB.toString().equals("0.0")) {
            return argA;
        }
        return new Minus(argA, argB);
    }
}

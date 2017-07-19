/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-05-02 */
public class Div extends BinaryExpression implements Expression {
    /**
     * Div constructor.
     * <p>
     * @param numerator - can be Expression, Double, or a String representing a Var.
     * @param denominator - can be Expression, Double, or a String representing a Var.*/
    public Div(Object numerator, Object denominator) {
        super(numerator, denominator);
        this.setOperator(" / ");
    }

    @Override
    /**
     * evaluate quotient of the numerator and the denominator
     * <p>
     * if there is Vars in one of the arguments, exception is thrown.
     * @return quotient - the numeric quotient of the arguments.*/
    public double evaluate() throws Exception {
        double quotient = 0;
        try {
            //if there is no vars, evaluate
            quotient = this.getArgA().evaluate() / this.getArgB().evaluate();
        } catch (Exception e) {
            // there are Vars in the arguments
            System.out.println("Div evaluation failed :" + e);
            throw e;
        }
        return quotient;
    }

    @Override
    /**
     * assigns a given expression to a var.
     * <p>
     * @param var - a string of the var to assign the expression to
     * @param expression - a string of the var to assign the expression to
     * @return Div - a new Div expression with the new assigned vars.*/
    public Expression assign(String var, Expression expression) {
        return new Div(this.getArgA().assign(var, expression), this.getArgB().assign(var, expression));
    }

    @Override
    /**
     * differentiate the expression with respect to a given var.
     * <p>
     * @param var - the var to differentiate
     * @return Expression - the differentiated expression.*/
    public Expression differentiate(String var) {
        //the Div is "const" (does not contain the var)
        if (this.getArgA() instanceof Num && this.getArgB() instanceof Num) {
            return new Num(0);
        }
        //differentiate according to the differentiation quotient rule
        return new Div(
                new Minus(new Mult(this.getArgA().differentiate(var), this.getArgB()),
                        new Mult(this.getArgA(), this.getArgB().differentiate(var))),
                new Pow(this.getArgB(), new Num(2)));
    }

    @Override
    public Expression simplify() {
        //if there is no vars, evaluate
        if (this.getArgA().getVariables() == null && this.getArgB().getVariables() == null) {
            try {
                double evaluate = this.evaluate();
                Expression exp = new Num(evaluate);
                return exp;
            } catch (Exception e) {
                System.out.println("Div evaluation failed :" + e);
            }
        }
        //simplify the arguments
        Expression argA = this.getArgA().simplify();
        Expression argB = this.getArgB().simplify();
        // the numerator is 0
        if (argA.toString().equals("0.0")) {
            return new Num(0);
        }
        // both of the arguments are equal
        if (argA.toString().equals(argB.toString())) {
            return new Num(1);
        }
        // the denominator is 1
        if (argB.toString().equals("1.0")) {
            return argA;
        }
        return new Div(argA, argB);
    }
}

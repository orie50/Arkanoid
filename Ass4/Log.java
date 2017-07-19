/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-05-02 */
public class Log extends BinaryExpression implements Expression {
    /**
     * Log constructor.
     * <p>
     * @param base - can be Expression, Double, or a String representing a Var.
     * @param antilogarithm - can be Expression, Double, or a String representing a Var.*/
    public Log(Object base, Object antilogarithm) {
        super(base, antilogarithm);
        this.setOperator(", ");
    }

    @Override
    public double evaluate() throws Exception {
        /**
         * evaluate argA to the power of argB
         * <p>
         * if there is Vars in one of the arguments, exception is thrown.
         * @return logarithm - the value of (log(value of argA) / log(value of argB)).*/
        double logarithm = 0;
        try {
            logarithm = Math.log(this.getArgB().evaluate()) / Math.log(this.getArgA().evaluate());
            //System.out.println(logarithm);
        } catch (Exception e) {
            System.out.println("log evaluation failed :" + e);
            throw e;
        }
        return logarithm;
    }

    @Override
    /**
     * assigns a given expression to a var.
     * <p>
     * @param var - a string of the var to assign the expression to
     * @param expression - a string of the var to assign the expression to
     * @return Log - a new Log expression with the new assigned vars.*/
    public Expression assign(String var, Expression expression) {
        return new Log(this.getArgA().assign(var, expression), this.getArgB().assign(var, expression));
    }

    @Override
    /**
     * return a string representation of the expression
     * <p>
     * @return String - printable representation*/
    public String toString() {
        return "Log" + super.toString();
    }

    @Override
    /**
     * differentiate the expression with respect to a given var.
     * <p>
     * @param var - the var to differentiate
     * @return Expression - the differentiated expression.*/
    public Expression differentiate(String var) {
        boolean varInBase = this.getArgA().toString().contains(var);
        boolean varInAntilogarithm = this.getArgB().toString().contains(var);
        //the Log is "const" (does not contain the var)
        if (!varInBase && !varInAntilogarithm) {
            return new Num(0);
        }
        //change the log base and differentiate
        Expression log = new Div(new Log(new Num(2), this.getArgB()), new Log(new Num(2), this.getArgA()));
        return log.differentiate(var);
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
                System.out.println("log evaluation failed :" + e);
            }
        }
        //simplify the arguments
        Expression argA = this.getArgA().simplify();
        Expression argB = this.getArgB().simplify();
        // both of the arguments are equal
        if (argA.toString().equals(argB.toString())) {
            return new Num(1);
        }
        return new Log(argA, argB);
    }
}

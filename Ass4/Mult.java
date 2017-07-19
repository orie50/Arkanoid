/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-05-02 */
public class Mult extends BinaryExpression implements Expression {
    /**
     * Mult constructor.
     * <p>
     * @param multiplier - can be Expression, Double, or a String representing a Var.
     * @param multiplicand - can be Expression, Double, or a String representing a Var.*/
    public Mult(Object multiplier, Object multiplicand) {
        super(multiplier, multiplicand);
        this.setOperator(" * ");
    }

    @Override
    /**
     * evaluate the product of argB and argA
     * <p>
     * if there is Vars in one of the arguments, exception is thrown.
     * @return product - the numeric product of the arguments.*/
    public double evaluate() throws Exception {
        double product = 0;
        try {
            //if there is no vars, evaluate
            product = this.getArgA().evaluate() * this.getArgB().evaluate();
        } catch (Exception e) {
            // there are Vars in the arguments
            System.out.println("Mult evaluation failed :" + e);
            throw e;
        }
        return product;
    }

    @Override
    /**
     * assigns a given expression to a var.
     * <p>
     * @param var - a string of the var to assign the expression to
     * @param expression - a string of the var to assign the expression to
     * @return Mult - a new Mult expression with the new assigned vars.*/
    public Expression assign(String var, Expression expression) {
        return new Mult(this.getArgA().assign(var, expression), this.getArgB().assign(var, expression));
    }

    @Override
    /**
     * differentiate the expression with respect to a given var.
     * <p>
     * @param var - the var to differentiate
     * @return Plus - the differentiated expression.*/
    public Expression differentiate(String var) {
        // both args are numbers, return 0
        if (this.getArgA() instanceof Num && this.getArgB() instanceof Num) {
            return new Num(0);
        }
        //     // one arg is a Num
        //        if (this.getArgA() instanceof Num && !(this.getArgB() instanceof Num)) {
        //            return new Mult(this.getArgA(), this.getArgB().differentiate(var));
        //        }
        // both args aren't Nums
        return new Plus(new Mult(this.getArgA().differentiate(var), this.getArgB()),
                new Mult(this.getArgA(), this.getArgB().differentiate(var)));
    }

    @Override
    /**
     * simplify the representation of the given expression with respect to a given var.
     * <p>
     * @return Expression - the simplified expression.*/
    public Expression simplify() {
        //if there is no vars, evaluate
        if (getArgA().getVariables() == null && getArgB().getVariables() == null) {
            try {
                double evaluate = this.evaluate();
                Expression exp = new Num(evaluate);
                return exp;
            } catch (Exception e) {
                System.out.println("Mult evaluation failed :" + e);
            }
        }
        //simplify the arguments
        Expression argA = this.getArgA().simplify();
        Expression argB = this.getArgB().simplify();
        // one of the args is 0
        if (argA.toString().equals("0.0") || argB.toString().equals("0.0")) {
            return new Num(0);
        }
        // one of the args is 1
        if (argA.toString().equals("1.0")) {
            return argB;
        }
        if (argB.toString().equals("1.0")) {
            return argA;
        }
        return new Mult(argA, argB);
    }
}
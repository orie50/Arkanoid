/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-05-02 */
public class Pow extends BinaryExpression implements Expression {
    /**
     * Pow constructor.
     * <p>
     * @param base - can be Expression, Double, or a String representing a Var.
     * @param exponent - can be Expression, Double, or a String representing a Var.*/
    public Pow(Object base, Object exponent) {
        super(base, exponent);
        this.setOperator("^");
    }

    @Override
    /**
     * evaluate argA to the power of argB
     * <p>
     * if there is Vars in one of the arguments, exception is thrown.
     * @return Power - the value of (agrA)^(argB).*/
    public double evaluate() throws Exception {
        double power = 0;
        try {
            //if there is no vars, evaluate
            power = Math.pow(this.getArgA().evaluate(), this.getArgB().evaluate());
        } catch (Exception e) {
            // there are Vars in the arguments
            System.out.println("Pow evaluation failed :" + e);
            throw e;
        }
        return power;
    }


    @Override
    /**
     * assigns a given expression to a var.
     * <p>
     * @param var - a string of the var to assign the expression to
     * @param expression - a string of the var to assign the expression to
     * @return Pow - a new Pow expression with the new assigned vars.*/
    public Expression assign(String var, Expression expression) {
        return new Pow(this.getArgA().assign(var, expression), this.getArgB().assign(var, expression));
    }

    @Override
    /**
     * differentiate the expression with respect to a given var.
     * <p>
     * @param var - the var to differentiate
     * @return the differentiated expression.*/
    public Expression differentiate(String var) {
        boolean varInBase = this.getArgA().toString().contains(var);
        boolean varInExponent = this.getArgB().toString().contains(var);
        //both of the args needs to be differentiate
        if (varInBase && varInExponent) {
            Expression expression = new Log(new Const("e"), this.getArgA());
            expression = new Mult(this.getArgB(), expression);
            expression = new Pow(new Const("e"), expression);
            return expression.differentiate(var);
        }
        //Base needs to be differentiate
        if (varInBase) {
            return new Mult(new Div(new Mult(this.getArgB(), this), this.getArgA()),
                    this.getArgA().differentiate(var));
            //exponent needs to be differentiate
        } else if (varInExponent) {
            Expression expression = new Log(new Const("e"), this.getArgA());
            expression = new Mult(new Mult(this, expression), this.getArgB().differentiate(var));
            return expression;
        }
        //the pow is "const" (does not contain the var)
        return new Num(0);
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
                System.out.println("Pow evaluation failed :" + e);
            }
        }
        //simplify the arguments
        Expression argA = this.getArgA().simplify();
        Expression argB = this.getArgB().simplify();
        //the base is 0
        if (argA.toString().equals("0.0")) {
            return new Num(0);
        }
        //the exponent is 1
        if (argB.toString().equals("1.0")) {
            return argA;
        }
        //the exponent is 0
        if (argB.toString().equals("0.0")) {
            return new Num(0);
        }
        //the exponent is Pow
        if (argA instanceof Pow) {
            Expression exp = new Pow(((Pow) argA).getArgA(),
                    new Mult(argB, ((Pow) argA).getArgB()));
            return exp.simplify();
        }
        return new Pow(argA, argB);
    }
}

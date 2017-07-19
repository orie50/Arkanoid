import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-04-24 */
public abstract class UnaryExpression extends BaseExpression {
    private Expression arg;

    /**
     * BinaryExpression constructor.
     * <p>
     * verify the instance of the objects given as parameter and.
     * construct the expression accordingly.
     * @param arg - can be Expression, Double, or a String representing a Var. */
    public UnaryExpression(Object arg) {
        if (arg instanceof String) {
            this.arg = new Var((String) arg);
        } else if (arg instanceof Double) {
            this.arg = new Num((double) arg);
        } else if (arg instanceof Integer) {
            this.arg = new Num((int) arg);
        } else if (arg instanceof Expression) {
            this.arg = (Expression) arg;
        } else {
            throw new InvalidParameterException();
        }
        this.setOperator(getOperator());
    }

    /**
     * arg getter.
     * <p>
     * @return arg*/
    protected Expression getArg() {
        return arg;
    }

    /**
     * arg setter.
     * <p>
     * @param argument - Expression*/
    protected void setArg(Expression argument) {
        this.arg = argument;
    }

    /**
     * Set all the variables in the expression in a list.
     * <p>
     * @return a string list of variables*/
    public List<String> getVariables() {
        List<String> variables = new ArrayList<String>();
        List<String> tempVars = arg.getVariables();
        //add the vars to the list.
        if (tempVars != null) {
            variables.addAll(tempVars);
        }
        //assgin null if the list is empty.
        if (variables.isEmpty()) {
            return null;
        }
        return variables;
    }

    /**
     * return a string representation of the expression.
     * <p>
     * @return the representation string*/
    public String toString() {
        // If the arg is BinaryExpression print without "()".
        if (arg instanceof BinaryExpression) {
            return this.getOperator() + getArg().toString();
        }
        // Else print with "()"
        return this.getOperator() + "(" + arg.toString() + ")";
    }
}
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Adiel cahana <adiel.cahana@gmail.com>
 * @version 1.0
 * @since 2016-05-02 */
public abstract class BinaryExpression extends BaseExpression {
    private Expression argA;
    private Expression argB;
    /**
     * BinaryExpression constructor.
     * <p>
     * verify the instance of the objects given as parameter and
     * construct the expression accordingly
     * @param argA - can be Expression, Double, or a String representing a Var.
     * @param argB - can be Expression, Double, or a String representing a Var.*/
    public BinaryExpression(Object argA, Object argB) {
        // correctly assign argA expression according to its type
        if (argA instanceof String) {
            this.argA = new Var((String) argA);
        } else if (argA instanceof Double) {
            this.argA = new Num((double) argA);
        } else if (argA instanceof Integer) {
            this.argA = new Num((int) argA);
        } else if (argA instanceof Expression) {
            this.argA = (Expression) argA;
        } else {
            throw new InvalidParameterException();
        }
        // correctly assign argB expression according to its type
        if (argB instanceof String) {
            this.argB = new Var((String) argB);
        } else if (argB instanceof Double) {
            this.argB = new Num((double) argB);
        } else if (argB instanceof Integer) {
            this.argB = new Num((int) argB);
        } else if (argB instanceof Expression) {
            this.argB = (Expression) argB;
        } else {
            throw new InvalidParameterException();
        }
        this.setOperator(getOperator());
    }

    /**
     * argA getter.
     * <p>
     * @return argA*/
    protected Expression getArgA() {
        return argA;
    }

    /**
     * argA setter.
     * <p>
     * @param argumentA - Expression*/
    protected void setArgA(Expression argumentA) {
        this.argA = argumentA;
    }
    /**
     * argB getter.
     * <p>
     * @return argB*/
    protected Expression getArgB() {
        return argB;
    }
    /**
     * argB setter.
     * <p>
     * @param argumentB - Expression*/
    protected void setArgB(Expression argumentB) {
        this.argB = argumentB;
    }
    /**
     * Set all the variables in the expression in a list.
     * <p>
     * @return a string list of variables*/
    public List<String> getVariables() {
        List<String> variables = new ArrayList<String>();
        List<String> tempVars = argA.getVariables();
        //add argA vars to the list
        if (tempVars != null) {
            variables.addAll(tempVars);
        }
        tempVars = argB.getVariables();
        //add argB vars to the list
        if (tempVars != null) {
            //delete duplicates
            variables.removeAll(tempVars);
            variables.addAll(tempVars);
        }
        //assgin null if the list is empty
        if (variables.isEmpty()) {
            return null;
        }
        return variables;
    }

    /**
     * return a string representation of the expression.
     * <p>
     * @return String - printable representation*/
    public String toString() {
        return "(" + argA.toString() + this.getOperator() + argB.toString() + ")";
    }
}

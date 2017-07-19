import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * @author Ori Engelberg <turht50@gmail.com>
 * @version 1.0
 * @since 2016-04-27 */
public abstract class BaseExpression {

    private String operator;

    /**
     * operator setter.
     * <p>
     * @param operatorExpression - the operator of the expression. */
    protected void setOperator(String operatorExpression) {
        this.operator = operatorExpression;
    }

    /** operator getter.
     * <p>
     * @return operator*/
    protected String getOperator() {
        return operator;
    }

    /**
     * Evaluate the expression and return the result.
     * <p>
     * the evaluation is using the variable values provided in the assignment
     * If the expression contains a variable which is not in the assignment, an exception is thrown
     * @param assignment - Map of variables and values
     * @throws Exception if the evaluation failed.
     * @return result*/
    public double evaluate(Map<String, Double> assignment) throws Exception {
        // create a set of the map entries
        Set<Entry<String, Double>> values = assignment.entrySet();
        Iterator<Entry<String, Double>> i = values.iterator();
        double expression = 0;
        try {
            // go over each entry and try to assign it to the expression
            Entry<String, Double> value = i.next();
            Expression newExpression = this.assign(value.getKey(), new Num(value.getValue()));
            while (i.hasNext()) {
                value = i.next();
                newExpression = newExpression.assign(value.getKey(), new Num(value.getValue()));
            }
            // try to evaluate the final expression
            expression = newExpression.evaluate();
        } catch (Exception e) {
            System.out.println("evaluate failed");
        }
        return expression;
    }

    /**
     * Evaluate the expression.
     * Calculate the value of expression.
     * <p>
     * @throws Exception if the evaluation failed.
     * @return value of the expression */
    public abstract double evaluate() throws Exception;

    /**
     * assigns a given expression to a var.
     * <p>
     * @param var - a string of the var to assign the expression to
     * @param expression - a string of the var to assign the expression to
     * @return a new expression with the new assigned vars.*/
    public abstract Expression assign(String var, Expression expression);
}

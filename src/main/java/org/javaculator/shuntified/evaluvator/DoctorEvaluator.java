package org.javaculator.shuntified.evaluvator;

import org.javaculator.shuntified.cache.RollbackCache;
import org.javaculator.shuntified.models.Token;
import org.javaculator.shuntified.models.operator.impl.AssignOp;
import org.javaculator.shuntified.models.operator.impl.BinaryOp;
import org.javaculator.shuntified.models.operator.impl.UnaryOp;
import org.javaculator.shuntified.models.literal.LiteralToken;
import org.javaculator.shuntified.models.variable.VariableToken;

import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.BinaryOperator;

public class DoctorEvaluator {
    private final RollbackCache cache = new RollbackCache();

    public void evaluate(List<Token> equation)  {
        // If RPN empty, return no result
        if (equation.isEmpty()) {
           return;
        }

        // Create stack to hold intermediary values
        Stack<Double> values = new Stack<>();

        // For each RPN token
        for (Token token : equation) {
            if (token instanceof BinaryOp binaryOp) { // Binary ops
                evaluateBinaryOperator(binaryOp, values);
            } else if (token instanceof UnaryOp unaryOp) { // Unary ops
                evaluateUnaryOperator(unaryOp, values);
            } else if (token instanceof LiteralToken constantTkn) { // Constant
                evaluateConstantToken(constantTkn, values);
            } else if (token instanceof VariableToken variableTkn) { // Variable
                evaluateVariableToken(variableTkn, values);
            } else if (token instanceof AssignOp assignOp) {
                evaluateAssignOperator(assignOp, values);
            } else {
                throw new RuntimeException("Equasion could not be calculated: ");
            }
        }

       System.out.println(cache.get());
    }

    private void evaluateAssignOperator(AssignOp assignOp, Stack<Double> values) {
        if (values.isEmpty()) {
            throw new RuntimeException("Invalid assignment operation, empty rhs portion");
        }

        String variable = assignOp.getTargetVariable();

        Double result = switch (assignOp.getSign()) {
            case "=" -> cache.putAndGetCurrent(variable, values.pop());
            case "+=" -> cache.putAndGetCurrent(variable, cache.get(variable) + values.pop());
            case "-=" -> cache.putAndGetCurrent(variable, cache.get(variable) - values.pop());
            case "*=" -> cache.putAndGetCurrent(variable, cache.get(variable) * values.pop());
            case "/=" -> cache.putAndGetCurrent(variable, cache.get(variable) / values.pop());
            case "%=" -> cache.putAndGetCurrent(variable, cache.get(variable) % values.pop());
            default -> throw new RuntimeException("un ; assignment operation");
        };

        values.push(result);
    }

    /**
     * Evaluates a binary operator token.
     * @param token         The current token being evaluated.
     * @param values        The current stack of intermediary values.
     */
    protected void evaluateBinaryOperator(BinaryOp token, Stack<Double> values) {
        Double result = switch (token.getSign()) {
            case "+" -> handleBinaryCalculation(values, "+", Double::sum);
            case "-" -> handleBinaryCalculation(values, "-", (o1, o2) -> o1 - o2);
            case "*" -> handleBinaryCalculation(values, "*", (o1, o2) -> o1 * o2);
            case "/" -> handleBinaryCalculation(values, "/", (o1, o2) -> o1 / o2);
            case "%" -> handleBinaryCalculation(values, "%", (o1, o2) -> o1 % o2);
            default -> throw new RuntimeException("unhandled operation: " + token.getSign());
        };

        // Push outputs to stack
        values.push(result);
    }

    public double handleBinaryCalculation(Stack<Double> values, String sign, BinaryOperator<Double> operation) {
        if (values.size() < 2) {
            throw new RuntimeException("Not enough operands for operation: " + sign);
        }

        Double op2 = values.pop();
        Double op1 = values.pop();

        return operation.apply(op1, op2);
    }

    /**
     * Evaluates a unary operator token.
     * @param token         The current token being evaluated.
     * @param values        The current stack of intermediary values.
     */
    protected void evaluateUnaryOperator(UnaryOp token,
                                         Stack<Double> values) {
        // Check operands exist
        if (values.isEmpty()) {
            throw new RuntimeException("Missing operands for " + token.toString());
        }

        Double result = switch (token.getSign()) {
            case "−" -> handleNegate(values);
            default -> handlePrePost(token.getSign());
        };

        values.push(result);
        // Push outputs to stack
//        values.push(token.getOperation().getAction().applyAsDouble(values.pop()));
    }

    private Double handlePrePost(String sign) {
        String operation = sign.substring(0, sign.indexOf('('));
        String identifier = sign.substring(sign.indexOf('(') + 1, sign.indexOf(')'));

        return switch (operation) {
            case "preinc" -> cache.increment(identifier, false);
            case "predec" -> cache.decrement(identifier, false);
            case "postinc" -> cache.increment(identifier, true);
            case "postdec" -> cache.decrement(identifier, true);
            default -> throw new RuntimeException("Unhandled unary operation: " + sign);
        };
    }

    private Double handleNegate(Stack<Double> values) {
        if (values.isEmpty()) {
            throw new RuntimeException("Missing values for negations");
        }

        return - values.pop();
    }

    /**
     * Evaluates a constant token.
     * @param token         The current token being evaluated.
     * @param values        The current stack of intermediary values.
     */
    protected void evaluateConstantToken(LiteralToken token, Stack<Double> values) {
        // Push immediately to output
        values.push(token.getValue());
    }

    /**
     * Evaluates a constant token.
     * @param token         The current token being evaluated.
     * @param values        The current stack of intermediary values.
     */
    protected void evaluateVariableToken(VariableToken token, Stack<Double> values) {
        // Get actual value and push to output
        values.push(cache.get(token.getSign()));
    }

    /**
     * Gets the map of variable names to values.
     * @return              The map of variable names to values.
     */
    public Map<String, Double> getVariables() {
        return cache.get();
    }
}

package org.javaculator.rules;

import org.parboiled.trees.ImmutableBinaryTreeNode;

public class CalculationNode extends ImmutableBinaryTreeNode<CalculationNode> {
    private double value;
    private Character operator;

    public CalculationNode(double value) {
        super(null, null);
        this.value = value;
    }

    public CalculationNode(Character operator, CalculationNode left, CalculationNode right) {
        super(left, right);
        this.operator = operator;
    }

    public double getValue() {
        if (operator == null) {
            return value;
        }

        double left = left().getValue();
        double right = right().getValue();

        return switch (operator) {
            case '+' -> left + right;
            case '-' -> left - right;
            case '*' -> left * right;
            case '/' -> left / right;
            case '^' -> Math.pow(left, right);
            default -> throw new IllegalStateException();
        };
    }

    @Override
    public String toString() {
        return (operator == null ? "Value " + value : "Operator '" + operator + '\'') + " | " + getValue();
    }
}

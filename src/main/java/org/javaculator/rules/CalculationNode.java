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
        if (operator == null) return value;
        return switch (operator) {
            case '+' -> left().getValue() + right().getValue();
            case '-' -> left().getValue() - right().getValue();
            case '*' -> left().getValue() * right().getValue();
            case '/' -> left().getValue() / right().getValue();
            case '^' -> Math.pow(left().getValue(), right().getValue());
            case 'R' -> Math.sqrt(left().getValue());
            default -> throw new IllegalStateException();
        };
    }

    @Override
    public String toString() {
        return (operator == null ? "Value " + value : "Operator '" + operator + '\'') + " | " + getValue();
    }
}

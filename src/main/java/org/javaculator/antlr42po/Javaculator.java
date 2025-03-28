package org.javaculator.antlr42po;

import org.javaculator.antlr4.Calc2BaseVisitor;
import org.javaculator.antlr4.Calc2Parser;
import org.javaculator.antlr42po.handlers.primary.PrimaryExprHandlers;

import java.util.HashMap;
import java.util.Map;

public class Javaculator extends Calc2BaseVisitor<Integer> {
    private final Map<String, Integer> vars = new HashMap<>();

    public void setVar(String name, Integer value) {
        vars.put(name, value);
    }

    public Map<String, Integer> getVars() {
        return vars;
    }
    public void clearVars() {vars.clear();}

    @Override
    public Integer visitAssignment(Calc2Parser.AssignmentContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Integer visitExpression(Calc2Parser.ExpressionContext ctx) {

        if (ctx.ID() != null) {
            String id = ctx.ID().getText();
            String op = ctx.getChild(1).getText();

            int right = visit(ctx.expression());
            if (op.equals("=")) {
                vars.put(id, right);
                return right;
            } else {

                int leftValue = vars.getOrDefault(id, 0);
                int newValue = switch (op) {
                    case "+=" -> leftValue + right;
                    case "-=" -> leftValue - right;
                    case "*=" -> leftValue * right;
                    case "/=" -> leftValue / right;
                    case "%=" -> leftValue % right;
                    default -> throw new RuntimeException("Unknown operator: " + op);
                };
                vars.put(id, newValue);
                return newValue;
            }
        } else {
            return visit(ctx.additiveExpr());
        }
    }

    @Override
    public Integer visitAdditiveExpr(Calc2Parser.AdditiveExprContext ctx) {
        int result = visit(ctx.multiplicativeExpr(0));

        for (int i = 1; i < ctx.multiplicativeExpr().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText(); // Operator is at odd positions.
            int right = visit(ctx.multiplicativeExpr(i));
            if (op.equals("+")) {
                result += right;
            } else if (op.equals("-")) {
                result -= right;
            }
        }
        return result;
    }

    // multiplicativeExpr: unaryExpr (('*'|'/'|'%') unaryExpr)* ;
    @Override
    public Integer visitMultiplicativeExpr(Calc2Parser.MultiplicativeExprContext ctx) {
        int result = visit(ctx.unaryExpr(0));
        for (int i = 1; i < ctx.unaryExpr().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            int right = visit(ctx.unaryExpr(i));
            switch (op) {
                case "*":
                    result *= right;
                    break;
                case "/":
                    result /= right;
                    break;
                case "%":
                    result %= right;
                    break;
                default:
                    throw new RuntimeException("Unknown operator: " + op);
            }
        }
        return result;
    }

    // unaryExpr: ('+'|'-') unaryExpr | primaryExpr ;
    @Override
    public Integer visitUnaryExpr(Calc2Parser.UnaryExprContext ctx) {
        if (ctx.getChildCount() == 2) {
            // The first child is the unary operator.
            String op = ctx.getChild(0).getText();

            return switch (op) {
                case "-" -> -visit(ctx.unaryExpr());
                case "+" -> visit(ctx.unaryExpr());
                case "--" -> vars.merge(ctx.ID().getText(), -1, Integer::sum);
                case "++" -> vars.merge(ctx.ID().getText(), 1, Integer::sum);
                default ->  throw new RuntimeException("Unknown operator: " + op);
            };
        } else {
            return visit(ctx.primaryExpr());
        }
    }

    // primaryExpr: INT | ID | '(' expression ')' ;
    @Override
    public Integer visitPrimaryExpr(Calc2Parser.PrimaryExprContext ctx) {
        return PrimaryExprHandlers.handle(ctx, vars, this::visit);
    }
}

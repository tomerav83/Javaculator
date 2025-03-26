package org.javaculator.antlr4;


import org.javaculator.antlr4.CalculationException.Type;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CalculationInterpreter extends CalcBaseVisitor<Integer> {
    private final Map<String, Integer> vars = new HashMap<>();

    public void setVar(String name, int value) {
        vars.put(name, value);
    }

    public Map<String, Integer> getVars() {
        return vars;
    }

    @Override
    public Integer visitProg(CalcParser.ProgContext ctx) {
        String varName = ctx.lhs().ID().getText();
        int rhs = visit(ctx.expression());
        String op = ctx.op.getText();

        int current = vars.getOrDefault(varName, 0);
        int result = switch (op) {
            case "=" -> rhs;
            case "+=" -> current + rhs;
            case "-=" -> current - rhs;
            case "*=" -> current * rhs;
            case "/=" -> current / rhs;
            case "%=" -> current % rhs;
            default -> throw new RuntimeException("Unknown assignment: " + op);
        };

        vars.put(varName, result);
        return result;
    }

    @Override
    public Integer visitAddSubExpr(CalcParser.AddSubExprContext ctx) {
        int left = visit(ctx.expression(0));
        int right = visit(ctx.expression(1));
        return ctx.op.getText().equals("+") ? left + right : left - right;
    }

    @Override
    public Integer visitMulDivModExpr(CalcParser.MulDivModExprContext ctx) {
        int left = visit(ctx.expression(0));
        int right = visit(ctx.expression(1));
        return switch (ctx.op.getText()) {
            case "*" -> left * right;
            case "/" -> left / right;
            case "%" -> left % right;
            default -> throw new RuntimeException("Unknown op: " + ctx.op.getText());
        };
    }

    @Override
    public Integer visitPreIncrementExpr(CalcParser.PreIncrementExprContext ctx) {
        String id = ctx.ID().getText();
        int val = vars.getOrDefault(id, 0) + 1;
        vars.put(id, val);
        return val;
    }

    @Override
    public Integer visitPreDecrementExpr(CalcParser.PreDecrementExprContext ctx) {
        String id = ctx.ID().getText();
        int val = vars.getOrDefault(id, 0) - 1;
        vars.put(id, val);
        return val;
    }

    @Override
    public Integer visitPostIncrementExpr(CalcParser.PostIncrementExprContext ctx) {
        String id = ctx.ID().getText();
        int val = vars.getOrDefault(id, 0);
        vars.put(id, val + 1);
        return val;
    }

    @Override
    public Integer visitPostDecrementExpr(CalcParser.PostDecrementExprContext ctx) {
        String id = ctx.ID().getText();
        int val = vars.getOrDefault(id, 0);
        vars.put(id, val - 1);
        return val;
    }

    @Override
    public Integer visitIntLiteral(CalcParser.IntLiteralContext ctx) {
        return Integer.parseInt(ctx.INT().getText());
    }

    @Override
    public Integer visitIdentifier(CalcParser.IdentifierContext ctx) {
        return vars.getOrDefault(ctx.ID().getText(), 0);
    }

    @Override
    public Integer visitParenExpr(CalcParser.ParenExprContext ctx) {
        return visit(ctx.expression());
    }
}

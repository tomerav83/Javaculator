package org.javaculator.antlr4;

import org.javaculator.antlr4.op.lr.impl.AddSubOp;
import org.javaculator.antlr4.op.lr.impl.MulDivModOp;
import org.javaculator.antlr4.op.unary.impl.PostDecOp;
import org.javaculator.antlr4.op.unary.impl.PostIncOp;
import org.javaculator.antlr4.op.unary.impl.PreDecOp;
import org.javaculator.antlr4.op.unary.impl.PreIncOp;

import java.util.HashMap;
import java.util.Map;

public class CalculationInterpreter extends CalcBaseVisitor<Integer> {
    private final Map<String, Integer> vars = new HashMap<>();

    public void setVar(String name, Integer value) {
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
        return AddSubOp.handle(ctx, this::visit);
    }

    @Override
    public Integer visitMulDivModExpr(CalcParser.MulDivModExprContext ctx) {
        return MulDivModOp.handle(ctx, this::visit);
    }

    @Override
    public Integer visitPreIncrementExpr(CalcParser.PreIncrementExprContext ctx) {
      return PreIncOp.handle(ctx, vars);
    }

    @Override
    public Integer visitPreDecrementExpr(CalcParser.PreDecrementExprContext ctx) {
        return PreDecOp.handle(ctx, vars);
    }

    @Override
    public Integer visitPostIncrementExpr(CalcParser.PostIncrementExprContext ctx) {
        return PostIncOp.handle(ctx, vars);
    }

    @Override
    public Integer visitPostDecrementExpr(CalcParser.PostDecrementExprContext ctx) {
        return PostDecOp.handle(ctx, vars);
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

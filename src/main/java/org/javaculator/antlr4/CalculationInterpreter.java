package org.javaculator.antlr4;

import org.javaculator.antlr4.op.assignment.AssignExprOp;
import org.javaculator.antlr4.op.assignment.AugmentedAssignExprOp;
import org.javaculator.antlr4.op.lr.AddSubOp;
import org.javaculator.antlr4.op.lr.MulDivModOp;
import org.javaculator.antlr4.op.signed.SignedIdentifierExprOp;
import org.javaculator.antlr4.op.signed.SignedNumberExprOp;
import org.javaculator.antlr4.op.unary.*;
import org.javaculator.antlr4.utils.ParserCtxUtils;

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
    public Integer visitAssignExpr(CalcParser.AssignExprContext ctx) {
        return AssignExprOp.handle(ctx, vars, this::visit);
    }

    @Override
    public Integer visitAugmentedAssignExpr(CalcParser.AugmentedAssignExprContext ctx) {
        return AugmentedAssignExprOp.handle(ctx, vars, this::visit);
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
    public Integer visitSignedNumberExpr(CalcParser.SignedNumberExprContext ctx) {
        return SignedNumberExprOp.handle(ctx);
    }

    @Override
    public Integer visitSignedIdentifierExpr(CalcParser.SignedIdentifierExprContext ctx) {
        return SignedIdentifierExprOp.handle(ctx, vars);
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
        return ParserCtxUtils.getLiteral(ctx);
    }

    @Override
    public Integer visitIdentifier(CalcParser.IdentifierContext ctx) {
        return IdentifierOP.handle(ctx, vars);
    }

    @Override
    public Integer visitParenExpr(CalcParser.ParenExprContext ctx) {
        return visit(ctx.expression());
    }
}

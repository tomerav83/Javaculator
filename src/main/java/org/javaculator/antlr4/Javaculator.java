package org.javaculator.antlr4;

import org.javaculator.antlr4.handlers.*;

import java.util.HashMap;
import java.util.Map;

public class Javaculator extends CalcBaseVisitor<Integer> {
    private final Map<String, Integer> vars = new HashMap<>();

    public Map<String, Integer> getVars() {
        return vars;
    }
    public void clearVars() {vars.clear();}

    // assignment

    @Override
    public Integer visitAssignExpr(CalcParser.AssignExprContext ctx) {
        return AssignExprHandler.INSTANCE.handle(ctx, vars, this::visit).orElse(0);
    }

    @Override
    public Integer visitRootAddExpr(CalcParser.RootAddExprContext ctx) {
        return RootAddExprHandler.INSTANCE.handle(ctx, this::visit).orElse(0);
    }

    // mult

    @Override
    public Integer visitRootMultExpr(CalcParser.RootMultExprContext ctx) {
        return RootMultExprHandler.INSTANCE.handle(ctx, this::visit).orElse(0);
    }

    @Override
    public Integer visitRootUnaryExpr(CalcParser.RootUnaryExprContext ctx) {
        return RootUnaryExprHandler.INSTANCE.handle(ctx, this::visit).orElse(0);
    }

    @Override
    public Integer visitSignedUnaryExpr(CalcParser.SignedUnaryExprContext ctx) {
        return SignedUnaryExprHandler.INSTANCE.handle(ctx, this::visit).orElse(null);
    }

    @Override
    public Integer visitPreUnaryExpr(CalcParser.PreUnaryExprContext ctx) {
        return PreUnaryExprHandler.INSTANCE.handle(ctx, vars).orElse(0);
    }

    @Override
    public Integer visitRootPrimaryExpr(CalcParser.RootPrimaryExprContext ctx) {
        return RootPrimaryExprHandler.INSTANCE.handle(ctx)
                .map(this::visit)
                .orElse(0);
    }

    @Override
    public Integer visitLiteralExpr(CalcParser.LiteralExprContext ctx) {
        return LiteralExprHandler.INSTANCE.handle(ctx).orElse(0);
    }

    @Override
    public Integer visitPostUnaryExpr(CalcParser.PostUnaryExprContext ctx) {
        return PostUnaryExprHandler.INSTANCE.handle(ctx, vars).orElse(0);
    }

    @Override
    public Integer visitParenExpr(CalcParser.ParenExprContext ctx) {
        return ParenExprHandler.INSTANCE.handle(ctx)
                .map(this::visit)
                .orElse(0);
    }
}

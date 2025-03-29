package org.javaculator.antlr4;

import org.javaculator.antlr4.handlers.*;
import org.javaculator.antlr4.snapshot.Snapshot;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Javaculator extends CalcBaseVisitor<BigDecimal> {
    private final Snapshot snapshot = new Snapshot();

    public Snapshot getSnapshot() {
        return snapshot;
    }
    public void clearVars() {snapshot.clear();}

    // assignment

    @Override
    public BigDecimal visitAssignExpr(CalcParser.AssignExprContext ctx) {
        return AssignExprHandler.INSTANCE.handle(ctx, snapshot, this::visit).orElse(null);
    }

    @Override
    public BigDecimal visitRootAddExpr(CalcParser.RootAddExprContext ctx) {
        return RootAddExprHandler.INSTANCE.handle(ctx, this::visit).orElse(null);
    }

    // mult

    @Override
    public BigDecimal visitRootMultExpr(CalcParser.RootMultExprContext ctx) {
        return RootMultExprHandler.INSTANCE.handle(ctx, this::visit).orElse(null);
    }

    @Override
    public BigDecimal visitRootUnaryExpr(CalcParser.RootUnaryExprContext ctx) {
        return RootUnaryExprHandler.INSTANCE.handle(ctx, this::visit).orElse(null);
    }

    @Override
    public BigDecimal visitSignedUnaryExpr(CalcParser.SignedUnaryExprContext ctx) {
        return SignedUnaryExprHandler.INSTANCE.handle(ctx, this::visit).orElse(null);
    }

    @Override
    public BigDecimal visitPreUnaryExpr(CalcParser.PreUnaryExprContext ctx) {
        return PreUnaryExprHandler.INSTANCE.handle(ctx, snapshot).orElse(null);
    }

    @Override
    public BigDecimal visitPostUnaryExpr(CalcParser.PostUnaryExprContext ctx) {
        return PostUnaryExprHandler.INSTANCE.handle(ctx, snapshot).orElse(null);
    }

    @Override
    public BigDecimal visitRootPrimaryExpr(CalcParser.RootPrimaryExprContext ctx) {
        return RootPrimaryExprHandler.INSTANCE.handle(ctx)
                .map(this::visit)
                .orElse(null);
    }

    @Override
    public BigDecimal visitIdentifierExpr(CalcParser.IdentifierExprContext ctx) {
        return IdentifierExprHandler.INSTANCE.handle(ctx, snapshot).orElse(null);
    }

    @Override
    public BigDecimal visitLiteralExpr(CalcParser.LiteralExprContext ctx) {
        return LiteralExprHandler.INSTANCE.handle(ctx).orElse(null);
    }

    @Override
    public BigDecimal visitParenExpr(CalcParser.ParenExprContext ctx) {
        return ParenExprHandler.INSTANCE.handle(ctx)
                .map(this::visit)
                .orElse(null);
    }

    @Override
    public BigDecimal visitNullExpr(CalcParser.NullExprContext ctx) {
        return null;
    }
}

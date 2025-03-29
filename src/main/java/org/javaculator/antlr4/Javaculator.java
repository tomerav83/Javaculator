package org.javaculator.antlr4;

import org.javaculator.antlr4.handlers.*;
import org.javaculator.antlr4.handlers.additive.AdditiveExprHandler;
import org.javaculator.antlr4.handlers.assignment.AssignExprHandler;
import org.javaculator.antlr4.handlers.assignment.SingletonExprHandler;
import org.javaculator.antlr4.handlers.literals.FloatingPointHandler;
import org.javaculator.antlr4.handlers.literals.IdentifierHandler;
import org.javaculator.antlr4.handlers.literals.IntegerHandler;
import org.javaculator.antlr4.handlers.literals.LiteralExprHandler;
import org.javaculator.antlr4.handlers.unary.PostIncDecHandler;
import org.javaculator.antlr4.handlers.unary.PreIncDecHandler;
import org.javaculator.antlr4.handlers.multiplicative.MultiplicativeExprHandler;
import org.javaculator.antlr4.handlers.unary.SignedUnaryExprHandler;
import org.javaculator.antlr4.snapshot.Snapshot;

import java.math.BigDecimal;

public class Javaculator extends CalcBaseVisitor<BigDecimal> {
    private final Snapshot snapshot = new Snapshot();

    public Snapshot getSnapshot() {
        return snapshot;
    }
    public void clearVars() {snapshot.clear();}

    @Override
    public BigDecimal visitAssignExpr(CalcParser.AssignExprContext ctx) {
        return AssignExprHandler.INSTANCE.handle(ctx, snapshot, this::visit).orElse(null);
    }

    @Override
    public BigDecimal visitSingletonExpr(CalcParser.SingletonExprContext ctx) {
        return SingletonExprHandler.INSTANCE.handle(ctx, this::visit).orElse(null);
    }

    @Override
    public BigDecimal visitAddSubExpr(CalcParser.AddSubExprContext ctx) {
        return AdditiveExprHandler.INSTANCE.handle(ctx, this::visit).orElse(null);
    }

    @Override
    public BigDecimal visitMulDivModExpr(CalcParser.MulDivModExprContext ctx) {
        return MultiplicativeExprHandler.INSTANCE.handle(ctx, this::visit).orElse(null);
    }

    @Override
    public BigDecimal visitSignedExpr(CalcParser.SignedExprContext ctx) {
        return SignedUnaryExprHandler.INSTANCE.handle(ctx, this::visit).orElse(null);
    }

    @Override
    public BigDecimal visitPreIncDecExpr(CalcParser.PreIncDecExprContext ctx) {
        return PreIncDecHandler.INSTANCE.handle(ctx, snapshot).orElse(null);
    }

    @Override
    public BigDecimal visitPostIncDecExpr(CalcParser.PostIncDecExprContext ctx) {
        return PostIncDecHandler.INSTANCE.handle(ctx, snapshot).orElse(null);
    }


    @Override
    public BigDecimal visitLiteralsExpr(CalcParser.LiteralsExprContext ctx) {
        return LiteralExprHandler.INSTANCE.handle(ctx, this::visit).orElse(null);
    }

    @Override
    public BigDecimal visitIdentifier(CalcParser.IdentifierContext ctx) {
        return IdentifierHandler.INSTANCE.handle(ctx, snapshot).orElse(null);
    }

    @Override
    public BigDecimal visitFloatingPoint(CalcParser.FloatingPointContext ctx) {
        return FloatingPointHandler.INSTANCE.handle(ctx).orElse(null);
    }

    @Override
    public BigDecimal visitInteger(CalcParser.IntegerContext ctx) {
        return IntegerHandler.INSTANCE.handle(ctx).orElse(null);
    }

    @Override
    public BigDecimal visitParenExpr(CalcParser.ParenExprContext ctx) {
        return ParenExprHandler.INSTANCE.handle(ctx, this::visit).orElse(null);
    }

    @Override
    public BigDecimal visitNull(CalcParser.NullContext ctx) {
        return null;
    }
}

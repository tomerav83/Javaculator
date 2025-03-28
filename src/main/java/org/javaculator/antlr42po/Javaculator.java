package org.javaculator.antlr42po;

import org.javaculator.antlr4.Calc2BaseVisitor;
import org.javaculator.antlr4.Calc2Parser;
import org.javaculator.antlr42po.handlers.additive.AdditiveExprHandlers;
import org.javaculator.antlr42po.handlers.assignment.AssignmentExprHandlers;
import org.javaculator.antlr42po.handlers.assignment.impl.AssignmentExprHandler;
import org.javaculator.antlr42po.handlers.multiplicative.MultiplicativeExprHandlers;
import org.javaculator.antlr42po.handlers.primary.PrimaryExprHandlers;
import org.javaculator.antlr42po.handlers.unary.UnaryExprHandlers;

import java.util.HashMap;
import java.util.Map;

public class Javaculator extends Calc2BaseVisitor<Integer> {
    private final Map<String, Integer> vars = new HashMap<>();

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
       return AssignmentExprHandlers.handle(ctx, vars, this::visitExpression, this::visitAdditiveExpr);
    }

    @Override
    public Integer visitAdditiveExpr(Calc2Parser.AdditiveExprContext ctx) {
        return AdditiveExprHandlers.handle(ctx, this::visitMultiplicativeExpr);
    }

    @Override
    public Integer visitMultiplicativeExpr(Calc2Parser.MultiplicativeExprContext ctx) {
        return MultiplicativeExprHandlers.handle(ctx, this::visitUnaryExpr);
    }

    @Override
    public Integer visitUnaryExpr(Calc2Parser.UnaryExprContext ctx) {
        return UnaryExprHandlers.handle(ctx, vars, this::visitUnaryExpr, this::visitPrimaryExpr);
    }

    @Override
    public Integer visitPrimaryExpr(Calc2Parser.PrimaryExprContext ctx) {
        return PrimaryExprHandlers.handle(ctx, vars, this::visit);
    }
}

package org.javaculator.shuntified.lexer.stages.impl;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.javaculator.shuntified.lexer.stages.TokenizationStage;
import org.javaculator.shuntified.models.Token;
import org.javaculator.shuntified.models.operator.Operator;
import org.javaculator.shuntified.models.operator.impl.AssignOp;
import org.javaculator.shuntified.models.operator.impl.BinaryOp;
import org.javaculator.shuntified.models.operator.impl.UnaryOp;
import org.javaculator.shuntified.pattern.Patterns;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OperatorStage implements TokenizationStage {
    private final List<Operator> operators = List.of(
            UnaryOp.create("post_inc", Patterns.POST_INCREMENT, Token.Association.LEFT, 1),
            UnaryOp.create("post_dec", Patterns.POST_DECREMENT, Token.Association.LEFT, 1),
            UnaryOp.create("pre_inc", Patterns.PRE_INCREMENT, Token.Association.RIGHT, 2),
            UnaryOp.create("pre_dec", Patterns.PRE_DECREMENT, Token.Association.RIGHT, 2),
            UnaryOp.create("−", Patterns.NEGATION, Token.Association.RIGHT, 2),
            AssignOp.create("=", Patterns.ASSIGN, Token.Association.RIGHT, 5),
            AssignOp.create("+=", Patterns.ADD_ASSIGN, Token.Association.RIGHT, 5),
            AssignOp.create("-=", Patterns.SUB_ASSIGN, Token.Association.RIGHT, 5),
            AssignOp.create("*=", Patterns.MULTIPLY_ASSIGN, Token.Association.RIGHT, 5),
            AssignOp.create("/=", Patterns.DIV_ASSIGN, Token.Association.RIGHT, 5),
            AssignOp.create("%=", Patterns.MOD_ASSIGN, Token.Association.RIGHT, 5),
            BinaryOp.create("+", Patterns.ADD, Token.Association.LEFT, 4),
            BinaryOp.create("-", Patterns.SUB, Token.Association.LEFT, 4),
            BinaryOp.create("*", Patterns.MULTIPLY, Token.Association.LEFT, 3),
            BinaryOp.create("/", Patterns.DIV, Token.Association.LEFT, 3),
            BinaryOp.create("%", Patterns.MOD, Token.Association.LEFT, 3)
    );

    public static OperatorStage create() {
        return new OperatorStage();
    }

    @Override
    public Token match(String input, int position) {
        for (Operator operator : operators) {
            if (operator.isMatching(input, position)) {
                return operator;
            }
        }

        return null;
    }
}

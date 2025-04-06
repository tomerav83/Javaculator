package org.javaculator.shuntified.lexer.stages.impl;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.javaculator.shuntified.lexer.stages.TokenizationStage;
import org.javaculator.shuntified.models.Token;
import org.javaculator.shuntified.models.Token.Association;
import org.javaculator.shuntified.models.operator.Operator;
import org.javaculator.shuntified.models.operator.impl.AssignOp;
import org.javaculator.shuntified.models.operator.impl.BinaryOp;
import org.javaculator.shuntified.models.operator.impl.UnaryOp;
import org.javaculator.shuntified.pattern.Patterns;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OperatorStage implements TokenizationStage {
    private final List<Operator> operators = List.of(
            UnaryOp.create("post_inc", Association.LEFT, 1, Patterns.POST_INCREMENT),
            UnaryOp.create("post_dec", Association.LEFT, 1, Patterns.POST_DECREMENT),
            UnaryOp.create("pre_inc", Association.RIGHT, 2, Patterns.PRE_INCREMENT),
            UnaryOp.create("pre_dec", Association.RIGHT, 2, Patterns.PRE_DECREMENT),
            AssignOp.create("="),
            AssignOp.create("+="),
            AssignOp.create("-="),
            AssignOp.create("*="),
            AssignOp.create("/="),
            AssignOp.create("%="),
            BinaryOp.create("+", Association.LEFT, 4),
            BinaryOp.create("-", Association.LEFT, 4),
            BinaryOp.create("*", Association.LEFT, 3),
            BinaryOp.create("/", Association.LEFT, 3),
            BinaryOp.create("%", Association.LEFT, 3)
    );

    public static OperatorStage create() {
        return new OperatorStage();
    }

    @Override
    public Token match(String input, int position) {
        for (Operator operator : operators) {
            if (operator instanceof UnaryOp unaryOp && unaryOp.matchByPattern(input, position)) {
                return unaryOp;
            } else if (input.startsWith(operator.getSign(), position)) {
                return operator;
            }
        }

        return null;
    }
}

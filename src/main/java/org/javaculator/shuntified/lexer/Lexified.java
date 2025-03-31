package org.javaculator.shuntified.lexer;

import org.javaculator.shuntified.models.op.Op;
import org.javaculator.shuntified.models.op.Operators;
import org.javaculator.shuntified.models.tkn.Brackets;
import org.javaculator.shuntified.models.tkn.Tkn;
import org.javaculator.shuntified.models.tkn.impl.BracketTkn;
import org.javaculator.shuntified.models.tkn.impl.ConstantTkn;
import org.javaculator.shuntified.models.tkn.impl.VariableTkn;
import org.javaculator.shuntified.utils.InputPreprocessor;
import org.javaculator.shuntified.pattern.collectors.impl.NumbersCollector;
import org.javaculator.shuntified.pattern.collectors.impl.VariableCollector;

import java.util.ArrayList;
import java.util.List;

public class Lexified {
    private final String input;
    private final List<String> vars;
    private final List<String> nums;

    public Lexified(String input) {
        this.input = InputPreprocessor.preprocess(input);
        this.vars = VariableCollector.INSTANCE.collect(this.input);
        this.nums = NumbersCollector.INSTANCE.collect(this.input);
    }

    public List<Tkn> tokenize() {
        int position = 0;
        List<Tkn> tokens = new ArrayList<>();

        while (position < input.length()) {
            Tkn token = readToken(position);
            tokens.add(token);
            position += token.getOriginalRep().length();
        }

        return tokens;
    }

    private Tkn readToken(int start) {
        // Detect operators
        for (Op operator: Operators.get()) {
            if (input.startsWith(operator.getSymbol(), start)) {
                return operator.getToken();
            }
        }
        // Detect variables
        for (String variable : vars) {
            if (input.startsWith(variable, start)) {
                return new VariableTkn(variable);
            }
        }

        // Detect brackets
        for (BracketTkn bracket : Brackets.get()) {
            if (input.startsWith(bracket.getOriginalRep(), start)) {
                return bracket;
            }
        }
        // Detect numbers
        for (String num : nums) {
            if (input.startsWith(num, start)) {
                return new ConstantTkn(num);
            }
        }

        // No match found
        throw new RuntimeException("couldn't match any token for input=%s at=%s".formatted(input, start));
    }

}

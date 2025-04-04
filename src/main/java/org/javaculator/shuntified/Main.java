package org.javaculator.shuntified;

import org.javaculator.shuntified.evaluvator.DoctorEvaluator;
import org.javaculator.shuntified.lexer.StagingLexer;
import org.javaculator.shuntified.lexer.exception.UnhandledTokenException;
import org.javaculator.shuntified.models.Token;
import org.javaculator.shuntified.parser.Parserfire;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DoctorEvaluator evaluator = new DoctorEvaluator();
        String input = "i $ 12 -12";
        String input2 = "k = 3.14";
        String input3 = "_a = -(5 - --k)";
        String input4 = "k += (i -= 1)";
//        String input2 = "k = 12 / -24";
//        String input3 = "z = _a* 5/ (2 + k) + 12e2 + 12e-2 + 0xFF + 12L + 12.0F";

        calculate(input, evaluator);
        calculate(input2, evaluator);
        calculate(input3, evaluator);
        calculate(input4, evaluator);
    }

    private static void calculate(String input, DoctorEvaluator evaluator) {
        try {
            List<Token> tokens = new StagingLexer(input).tokenize();
            List<Token> rpn = Parserfire.parse(tokens);
            evaluator.evaluate(rpn);
        } catch (UnhandledTokenException e) {
            e.printStackTrace();
        }
    }
}

package org.javaculator.shuntified;

import org.javaculator.shuntified.evaluvator.DoctorEvaluator;
import org.javaculator.shuntified.lexer.Lexified;
import org.javaculator.shuntified.models2.Token;
import org.javaculator.shuntified.parser.Parserfire;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DoctorEvaluator evaluator = new DoctorEvaluator();
        String input = "i = 12";
        String input2 = "k = 3.14";
        String input3 = "_a = -(5 - --k)";
//        String input2 = "k = 12 / -24";
//        String input3 = "z = _a* 5/ (2 + k) + 12e2 + 12e-2 + 0xFF + 12L + 12.0F";

        calculate(input, evaluator);
        calculate(input2, evaluator);
        calculate(input3, evaluator);
    }

    private static void calculate(String input, DoctorEvaluator evaluator) {
        List<Token> tokens = new Lexified(input).tokenize();
        List<Token> rpn = Parserfire.parse(tokens);
        evaluator.evaluate(rpn);
    }
}

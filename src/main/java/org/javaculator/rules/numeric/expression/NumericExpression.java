package org.javaculator.rules.numeric.expression;

import org.javaculator.rules.CalculationNode;
import org.javaculator.rules.Parsers;
import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.annotations.SuppressSubnodes;
import org.parboiled.support.Var;

@BuildParseTree
public class NumericExpression extends BaseParser<CalculationNode> {
    public static final NumericExpression INSTANCE = new NumericExpression();

    public Rule expression() {
        return operator(term(), FirstOf("+ ", "- "));
    }

    public Rule term() {
        return operator(factor(), FirstOf("* ", "/ "));
    }

    public Rule factor() {
        return operator(FirstOf(Parsers.decimalNumber(), parens()), toRule("^ "));
    }

    public Rule operator(Rule subRule, Rule operatorRule) {
        Var<Character> op = new Var<>();

        return Sequence(
                subRule,
                ZeroOrMore(
                        operatorRule, op.set(matchedChar()),
                        subRule,
                        push(new CalculationNode(op.get(), pop(1), pop()))
                )
        );
    }

    public Rule parens() {
        return Sequence("( ", expression(), ") ");
    }
}

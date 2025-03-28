package org.javaculator;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.javaculator.antlr4.*;
import org.javaculator.antlr42po.CalculationProcessor;
import org.javaculator.antlr42po.Javaculator;

import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        CalculationProcessor.calculate("a = 1");
        CalculationProcessor.calculate("c = 2");
        CalculationProcessor.calculate("d = 12");
        System.out.println(CalculationProcessor.calculate("x *= a *= c *=  (d *= 2)"));
    }
}
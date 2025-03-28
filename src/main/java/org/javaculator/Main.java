package org.javaculator;

import org.javaculator.antlr4.CalculationProcessor;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        CalculationProcessor.calculate("a = 1");
        CalculationProcessor.calculate("c = 2");
        CalculationProcessor.calculate("d = 12");
        System.out.println(CalculationProcessor.calculate("x += a /= c *=  (d *= 2) - 1"));
    }
}
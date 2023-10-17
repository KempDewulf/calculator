package calculator.util;

import java.util.Map;
import java.util.Set;

public enum Operator {
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE,
    SQUARED;
    private static final Set<String> operatorStrings = Set.of("+", "-", "x", "\u00F7","\u00B2");
    public static boolean isOperator(String string) {
        System.out.println(string);
        System.out.println(operatorStrings);
        return operatorStrings.contains(string);
    }
}

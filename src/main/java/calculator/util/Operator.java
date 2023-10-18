package calculator.util;

import java.util.Map;
import java.util.Set;

public enum Operator {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("\u00F7"),
    SQUARED("\u00B2");

    private final String operatorSymbol;

    Operator(String operatorSymbol) {
        this.operatorSymbol = operatorSymbol;
    }

    public String getSymbol() {
        return this.operatorSymbol;
    }

    public Operator getOperator(String string) {
        for (Operator o : Operator.values()) {
            if (getSymbol().equals(string)) return o;
        }
        throw new IllegalArgumentException("Operator doesn't exist");
    }

    public boolean isOperator(String string) {
        try {
            getOperator(string);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


}

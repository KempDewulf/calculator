package calculator.services;

import calculator.domain.Calculator;
import calculator.util.Operator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CalculatorService {
    private final List<String> numbers = new ArrayList<>();
    private final List<Operator> operators = new ArrayList<>();
    private boolean showingResult = false;
    private Operator operator;

    public void setOperator(Operator operator) {
        if (numbers.size() <= operators.size()) {
            throw new IllegalStateException("can't have multiple operators after each other");
        }
        operators.add(operator);
        showingResult = false;
    }

    public void deleteOperators() {
        operators.clear();
    }

    public void addNumber(String input) {
        if (showingResult) {
            numbers.clear();
            showingResult = false;
        }
        if (getLastInput().equals("\u00B2")) {
            throw new IllegalStateException("can't enter number after squared operator");
        } else if (input.equals(".") && getLastNumber().contains(".")) {
            throw new IllegalStateException("can't have multiple decimal dots in one number");
        }
        numbers.add(input);
    }

    public void deleteNumbers() {
        numbers.clear();
    }

    public String getLastNumber() {
        int lastIndex = numbers.size() - 1;
        return lastIndex >= 0 ? numbers.get(lastIndex) : "";
    }

    public Operator getLastOperator() {
        int lastIndex = operators.size() - 1;
        return operators.get(lastIndex);
    }

    public String getLastInput() {
        if (operators.size() > numbers.size()) {
            return getLastOperator().getSymbol();
        } else {
            return getLastNumber();
        }
    }

    public String getFullInput() {
        StringBuilder string = new StringBuilder();

        for (int i = 0; i < numbers.size(); i++) {
            string.append(numbers.get(i));
            if (i < operators.size()) {
                string.append(operators.get(i).getSymbol());
            }
        }

        return string.toString();
    }

    public void deleteLastInput() {
        if (operator.isOperator(getLastInput())) {
            int lastIndex = operators.size() - 1;
            operators.remove(lastIndex);
        } else {
            int lastIndex = numbers.size() - 1;
            numbers.remove(lastIndex);
        }
    }

    public void deleteAllInput() {
        deleteNumbers();
        deleteOperators();
    }

    public void calculateInput() {
        while (!operators.isEmpty()) {
            calculateNext();
        }
    }

    public void calculateNext() {
        Operator op = operators.get(0);
        float num1 = Float.parseFloat(numbers.get(0));
        float num2 = op.equals(Operator.SQUARED) ? num1 : Float.parseFloat(numbers.get(1));

        float result = calculate(op, num1, num2);

        operators.remove(0);
        numbers.remove(0);
        numbers.set(0, Float.toString(result));
    }

    public float calculate(Operator op, float num1, float num2) {
        float result = 0;
        switch (op) {
            case ADD -> result = Calculator.add(num1, num2);
            case SUBTRACT -> result = Calculator.subtract(num1, num2);
            case SQUARED, MULTIPLY -> result = Calculator.multiply(num1, num2);
            case DIVIDE -> result = Calculator.divide(num1, num2);
        }
        return result;
    }
}

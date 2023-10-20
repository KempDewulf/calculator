package calculator.services;

import calculator.domain.Calculator;
import calculator.util.Operator;

import java.util.ArrayList;
import java.util.List;

public class CalculatorService {
    private final List<String> numbers = new ArrayList<>();
    private final List<Operator> operators = new ArrayList<>();
    private boolean showingResult = false;
    private Operator operator;

    public void setOperator(Operator operator) {
        if (numbers.size() <= operators.size()) {
            operators.set(operators.size() - 1, operator);
        } else {
            operators.add(operator);
        }
        showingResult = false;
        System.out.println(operators);
    }

    public void deleteOperators() {
        operators.clear();
    }

    public void addNumber(String input) {
        boolean isLastInputIsSquared = getLastInput().equals("\u00B2");
        boolean isAddingMultipleDecimalPoints = input.equals(".") && getLastNumber().contains(".");
        boolean isInputAfterOperator = numbers.size() <= operators.size();

        if (showingResult) {
            clearInput();
        }
        if (isLastInputIsSquared) {
            throw new IllegalStateException("can't enter number after squared operator");
        } else if (isAddingMultipleDecimalPoints) {
            throw new IllegalStateException("can't have multiple decimal dots in one number");
        }
        if (isInputAfterOperator) {
            numbers.add(input);
        } else {
            numbers.set(operators.size(), getLastNumber() + input);
        }
    }

    public void clearInput() {
        numbers.clear();
        showingResult = false;
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
        if (numbers.size() == operators.size() && getLastOperator() != Operator.SQUARED) {
            throw new IllegalStateException("Last input can't be operator");
        }
        calculateExponentiations();
        calculateOperations(Operator.MULTIPLY, Operator.DIVIDE);
        calculateOperations(Operator.ADD, Operator.SUBTRACT);
    }

    public void calculateExponentiations() {
        while (operators.contains(Operator.SQUARED)) {
            int index = operators.indexOf(Operator.SQUARED);
            float num1 = Float.parseFloat(numbers.get(index));
            float result = calculate(Operator.SQUARED, num1, num1);

            numbers.set(index, Float.toString(result));
            operators.remove(index);
        }
    }

    public void calculateOperations(Operator op1, Operator op2) {
        while (operators.contains(op1) || operators.contains(op2)) {
            System.out.println("ok");
            int firstOpIndex = operators.indexOf(op1);
            int secondOpIndex = operators.indexOf(op2);

            //get the lowest positive index
            int index = (firstOpIndex >= 0 && secondOpIndex >= 0) ? Math.min(firstOpIndex, secondOpIndex) : (firstOpIndex >= 0) ? firstOpIndex : secondOpIndex;

            float num1 = Float.parseFloat(numbers.get(index));
            float num2 = Float.parseFloat(numbers.get(index + 1));
            Operator op = operators.get(index);

            float result = calculate(op, num1, num2);

            operators.remove(index);
            numbers.remove(index);
            numbers.set(index, Float.toString(result));
        }
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

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
        boolean isLastInputOperator = numbers.size() <= operators.size();

        if (isLastInputOperator) {
            operators.set(operators.size() - 1, operator);
        } else {
            operators.add(operator);
        }
        showingResult = false;
    }

    public void removeOperators() {
        operators.clear();
    }

    public void addNumber(String input) {
        if (showingResult) {
            removeNumbers();
            showingResult = false;
        }

        boolean isLastInputIsSquared = getLastInput().equals("\u00B2");
        boolean isAddingMultipleDecimalPoints = input.equals(".") && getLastNumber().contains(".");
        boolean isInputAfterOperator = numbers.size() <= operators.size();

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

    public void removeNumbers() {
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
        removeNumbers();
        removeOperators();
    }

    public void calculateInput() {
        if (numbers.size() == operators.size() && getLastOperator() != Operator.SQUARED) {
            throw new IllegalStateException("Last input can't be operator");
        }
        calculateExponentiations();
        calculateOperations(Operator.MULTIPLY, Operator.DIVIDE);
        calculateOperations(Operator.ADD, Operator.SUBTRACT);

        showingResult = true;
    }

    public void calculateExponentiations() {
        while (operators.contains(Operator.SQUARED)) {
            int index = operators.indexOf(Operator.SQUARED);
            float num1 = Float.parseFloat(numbers.get(index));
            float result = calculate(Operator.SQUARED, num1, num1);

            operators.remove(index);

            if (result == Math.floor(result)) {
                numbers.set(index, String.valueOf((int) result));
            } else {
                numbers.set(index, Float.toString(result));
            }
        }
    }

    public void calculateOperations(Operator op1, Operator op2) {
        while (operators.contains(op1) || operators.contains(op2)) {
            int firstOpIndex = operators.indexOf(op1);
            int secondOpIndex = operators.indexOf(op2);

            int index = getLowestPositiveIndex(firstOpIndex, secondOpIndex);

            float num1 = Float.parseFloat(numbers.get(index));
            float num2 = Float.parseFloat(numbers.get(index + 1));
            Operator op = operators.get(index);

            float result = calculate(op, num1, num2);

            operators.remove(index);
            numbers.remove(index);

            if (result == Math.floor(result)) {
                numbers.set(index, String.valueOf((int) result));
            } else {
                numbers.set(index, Float.toString(result));
            }
        }
    }

    public int getLowestPositiveIndex(int index1, int index2) {
        if (index1 >= 0 && index2 >= 0) {
            return Math.min(index1, index2);
        } else if (index1 >= 0) {
            return index1;
        } else {
            return index2;
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

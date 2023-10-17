package calculator.services;

import calculator.domain.Calculator;
import calculator.util.Operator;

import java.util.regex.Pattern;

public class CalculatorService {
    private String input = "";
    private Operator operator;
    private String operatorString;
    private boolean showingResult = false;

    public void setOperator(Operator operator, String operatorString) {
        if (this.operator != null || input.equals("")) {
            throw new IllegalStateException("already an operator set");
        }
        this.operator = operator;

        if (operator == Operator.SQUARED) {
            this.operatorString = "\u00B2";
        } else {
            this.operatorString = operatorString;
        }
        showingResult = false;
        addInput(this.operatorString);
    }

    public Operator getOperator() {
        return this.operator;
    }

    public String getOperatorString() {
        return this.operatorString;
    }

    public void deleteOperator() {
        this.operator = null;
    }

    public void addInput(String input) {
        if (showingResult) {
            this.input = "";
            showingResult = false;
        } else if (operator == Operator.SQUARED && !input.equals("\u00B2")) {
            throw new IllegalStateException("can't enter number after squared operator");
        }
        if (input.equals(".") && this.input.contains(input)){
            throw new IllegalStateException("can't add multiple decimal dots");
        }
        this.input += input;
    }

    public String getInput() {
        return this.input;
    }

    public void deleteLastInput() {
        int lastCharIndex = input.length() - 1;
        String lastChar = input.substring(lastCharIndex);

        if (Operator.isOperator(lastChar)) {
            this.operator = null;
            this.operatorString = null;
        }

        this.input = this.input.substring(0, lastCharIndex);
    }

    public void deleteAllInput() {
        this.input = "";
    }

    public void getResult() {
        String[] numbers = input.split(Pattern.quote(operatorString));
        float num1 = Float.parseFloat(numbers[0]);
        float num2 = 0;
        if (numbers.length > 1) {
             num2 = Float.parseFloat(numbers[1]);
        }
        float result = 0;

        switch(operator) {
            case ADD ->
                result = Calculator.add(num1, num2);
            case SUBTRACT ->
                result = Calculator.subtract(num1, num2);
            case MULTIPLY ->
                result = Calculator.multiply(num1, num2);
            case DIVIDE -> {
                if (num2 == 0) {
                    this.input = "Wie Deelt Door 0 Is Een Snul";
                    return;
                }
                result = Calculator.divide(num1, num2);
            }
            case SQUARED ->
                result = Calculator.square(num1);
        }

        this.input =  Float.toString(result);
        this.showingResult = true;
        this.operator = null;
    }
}

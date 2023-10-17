package calculator.services;

public class CalculatorService {
    private String input;

    public void addInput(String input) {
        this.input += input;
    }

    public String getInput() {
        return this.input;
    }

    public String add(String num1, String num2) {
        float float1 = Float.parseFloat(num1);
        float float2 = Float.parseFloat(num2);

        return Float.toString(float1 + float2);
    }
    public String subtract(String num1, String num2) {
        float float1 = Float.parseFloat(num1);
        float float2 = Float.parseFloat(num2);

        return Float.toString(float1 - float2);
    }
    public String multiply(String num1, String num2) {
        float float1 = Float.parseFloat(num1);
        float float2 = Float.parseFloat(num2);

        return Float.toString(float1 * float2);
    }
    public String divide(String num1, String num2) {
        float float1 = Float.parseFloat(num1);
        float float2 = Float.parseFloat(num2);

        return Float.toString(float1 / float2);
    }
}

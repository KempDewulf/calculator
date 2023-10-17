package calculator.services;

public class CalculatorService {
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

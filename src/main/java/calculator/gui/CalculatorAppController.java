package calculator.gui;

import java.net.URL;
import java.util.ResourceBundle;

import calculator.services.CalculatorService;
import calculator.util.Operator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CalculatorAppController {
    private final CalculatorService service = new CalculatorService();
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button ADD;
    @FXML
    private Button DIVIDE;
    @FXML
    private Button MULTIPLY;
    @FXML
    private Button SQUARED;
    @FXML
    private Button SUBTRACT;
    @FXML
    private Label outputLbl;

    @FXML
    void onDecimal(ActionEvent event) {

    }

    @FXML
    void onDeleteAll(ActionEvent event) {
        service.deleteAllInput();

        showInput();
    }

    @FXML
    void onDeleteLast(ActionEvent event) {
        service.deleteLastInput();

        showInput();
    }

    @FXML
    void onEnter(ActionEvent event) {
        service.calculateInput();

        showInput();
    }

    @FXML
    void onOperatorClick(ActionEvent event) {
        String operator = ((Button) event.getSource()).getId();

        service.setOperator(Operator.valueOf(operator));

        showInput();
    }

    @FXML
    void onNumClick(ActionEvent event) {
        String number = ((Button) event.getSource()).getText();

        service.addNumber(number);
        showInput();
    }

    private void showInput() {
        outputLbl.setText(service.getFullInput());
    }

    @FXML
    void initialize() {
        assert outputLbl != null : "fx:id=\"outputLbl\" was not injected: check your FXML file 'CalculatorApp.fxml'.";

    }


}

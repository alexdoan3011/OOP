import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Controller {
	// Formatters for currency and percentages
	private static final NumberFormat currency = NumberFormat.getCurrencyInstance();
	private static final NumberFormat percent = NumberFormat.getPercentInstance();
	// 15% default
	private BigDecimal tipPercentage = new BigDecimal(0.15);

	// GUI controls defined in FXML and used by the controller's code
	@FXML
	private TextField amountTextField, tipTextField, totalTextField, numOfWorkers;
	@FXML
	private Label tipPercentageLabel;
	@FXML
	private Slider tipSlider;

	// calculates and displays the tip and total amounts
	@FXML
	private void calculateButtonPressed(ActionEvent event) {
		try {
			BigDecimal amount = new BigDecimal(amountTextField.getText());
			BigDecimal tip = amount.multiply(tipPercentage);
			BigDecimal total = amount.add(tip);

			tipTextField.setText(currency.format(tip));
			totalTextField.setText(currency.format(total));
		} catch (NumberFormatException ex) {
			amountTextField.setText("Enter amount");
			amountTextField.selectAll();
			amountTextField.requestFocus();
		}
	}

	@FXML
	private void sliderListener(MouseEvent e){
        DecimalFormat dF = new DecimalFormat("0.#");
		tipPercentageLabel.setText(dF.format(tipSlider.getValue()) + "%");
	}

	public void amountTextField() {

	}

	// Called by FXMLLoader to initialize the controller
	public void initialize() {
		// 0-4 rounds down, 5-9 rounds up
		currency.setRoundingMode(RoundingMode.HALF_UP);
//		tipSlider.valueProperty().addListener(new ChangeListener(){
//
//			@Override
//			public void changed(ObservableValue arg0, Object arg1, Object arg2) {
//				// TODO Auto-generated method stub
//				System.out.println(tipSlider.getValue());
//			}
//		});

	}



}


/*
 * Name: Van Nam Doan
 * Student ID: 040943291
 * Course & Section: CST8132 312
 * Assignment: Lab 8
 * Date: April 14th, 2019
 */

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import java.awt.Window.Type;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Font;

/**
 * this class contains the frame of the application
 *
 * @author Van Nam Doan
 * @version 1.3
 * @since 1.8
 */
public class TipCalculatorFrame {
	private static final DecimalFormat DF = new DecimalFormat("$0.00");
	JFrame frame;
	private int tipPercentage = 15; // current tip percentage
	private JTextField amountTextField;
	private JLabel tipPercentageLabel;
	private JSlider tipPercentageSlider; // slider that varies from 0 to 30
	private JTextField tipTextField;
	private JLabel tipLabel;
	private JLabel totalLabel;
	private JButton calculateButton;
	private JTextField totalTextField;
	private JLabel errorMessage;

	/**
	 * Create the application.
	 */
	protected TipCalculatorFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// frame
		frame = new JFrame();
		frame.setTitle("Tip Calculator");
		frame.setType(Type.UTILITY);
		frame.setBounds(100, 100, 359, 305);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 10, 30, 200, 10, 0 };
		gridBagLayout.rowHeights = new int[] { 30, 20, 30, 10, 30, 10, 30, 20, 30, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);
		// amount label
		JLabel amountLabel = new JLabel("Amount");
		GridBagConstraints gbc_amountLabel = new GridBagConstraints();
		gbc_amountLabel.anchor = GridBagConstraints.EAST;
		gbc_amountLabel.insets = new Insets(0, 0, 5, 5);
		gbc_amountLabel.gridx = 1;
		gbc_amountLabel.gridy = 0;
		frame.getContentPane().add(amountLabel, gbc_amountLabel);
		// amount input field
		amountTextField = new JTextField();
		GridBagConstraints gbc_amountTextField = new GridBagConstraints();
		gbc_amountTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_amountTextField.insets = new Insets(0, 0, 5, 5);
		gbc_amountTextField.gridx = 2;
		gbc_amountTextField.gridy = 0;
		frame.getContentPane().add(amountTextField, gbc_amountTextField);
		amountTextField.setColumns(10);
		// live update
		amountTextField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				calculateResults();
			}
		});

		errorMessage = new JLabel("");
		errorMessage.setFont(new Font("Tahoma", Font.ITALIC, 11));
		errorMessage.setForeground(Color.RED);
		GridBagConstraints gbc_errorMessage = new GridBagConstraints();
		gbc_errorMessage.anchor = GridBagConstraints.NORTHWEST;
		gbc_errorMessage.insets = new Insets(0, 0, 5, 5);
		gbc_errorMessage.gridx = 2;
		gbc_errorMessage.gridy = 1;
		frame.getContentPane().add(errorMessage, gbc_errorMessage);
		// dynamic tip percentage field
		tipPercentageLabel = new JLabel("15%");
		GridBagConstraints gbc_tipPercentageLabel = new GridBagConstraints();
		gbc_tipPercentageLabel.anchor = GridBagConstraints.EAST;
		gbc_tipPercentageLabel.insets = new Insets(0, 0, 5, 5);
		gbc_tipPercentageLabel.gridx = 1;
		gbc_tipPercentageLabel.gridy = 2;
		frame.getContentPane().add(tipPercentageLabel, gbc_tipPercentageLabel);
		// tip slider
		tipPercentageSlider = new JSlider();
		tipPercentageSlider.setValue(15);
		tipPercentageSlider.setMajorTickSpacing(5);
		tipPercentageSlider.setMinorTickSpacing(1);
		tipPercentageSlider.setMaximum(30);
		GridBagConstraints gbc_tipPercentageSlider = new GridBagConstraints();
		gbc_tipPercentageSlider.fill = GridBagConstraints.HORIZONTAL;
		gbc_tipPercentageSlider.insets = new Insets(0, 0, 5, 5);
		gbc_tipPercentageSlider.gridx = 2;
		gbc_tipPercentageSlider.gridy = 2;
		frame.getContentPane().add(tipPercentageSlider, gbc_tipPercentageSlider);
		// tip slider changing dynamic tip percentage field
		tipPercentageSlider.addChangeListener(e -> {
			try {
				calculateResults();// live update
				setTipPercentage(tipPercentage = tipPercentageSlider.getValue());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		// tip label
		tipLabel = new JLabel("Tip");
		GridBagConstraints gbc_tipLabel = new GridBagConstraints();
		gbc_tipLabel.anchor = GridBagConstraints.EAST;
		gbc_tipLabel.insets = new Insets(0, 0, 5, 5);
		gbc_tipLabel.gridx = 1;
		gbc_tipLabel.gridy = 4;
		frame.getContentPane().add(tipLabel, gbc_tipLabel);
		// tip result non-editable text field
		tipTextField = new JTextField();
		tipTextField.setEditable(false);
		GridBagConstraints gbc_tipTextField = new GridBagConstraints();
		gbc_tipTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_tipTextField.insets = new Insets(0, 0, 5, 5);
		gbc_tipTextField.gridx = 2;
		gbc_tipTextField.gridy = 4;
		frame.getContentPane().add(tipTextField, gbc_tipTextField);
		tipTextField.setColumns(10);
		// total label
		totalLabel = new JLabel("Total");
		GridBagConstraints gbc_totalLabel = new GridBagConstraints();
		gbc_totalLabel.anchor = GridBagConstraints.EAST;
		gbc_totalLabel.insets = new Insets(0, 0, 5, 5);
		gbc_totalLabel.gridx = 1;
		gbc_totalLabel.gridy = 6;
		frame.getContentPane().add(totalLabel, gbc_totalLabel);
		// total result non-editable text field
		totalTextField = new JTextField();
		totalTextField.setEditable(false);
		GridBagConstraints gbc_totalTextField = new GridBagConstraints();
		gbc_totalTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_totalTextField.insets = new Insets(0, 0, 5, 5);
		gbc_totalTextField.gridx = 2;
		gbc_totalTextField.gridy = 6;
		frame.getContentPane().add(totalTextField, gbc_totalTextField);
		totalTextField.setColumns(10);
		// calculate button
		calculateButton = new JButton("Calculate");
		GridBagConstraints gbc_calculateButton = new GridBagConstraints();
		gbc_calculateButton.insets = new Insets(0, 0, 0, 5);
		gbc_calculateButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_calculateButton.gridx = 2;
		gbc_calculateButton.gridy = 8;
		frame.getContentPane().add(calculateButton, gbc_calculateButton);
		// frame construction ends
		calculateButton.addActionListener(e -> calculateResults());// setting action to button click
		frame.getRootPane().setDefaultButton(calculateButton);// pressing the enter key generate the same result as
																// clicking button
	}

	/**
	 * used in change listener for slider.
	 *
	 * @param value what the tip percentage label will show, followed by a "%".
	 */
	private void setTipPercentage(int value) {
		tipPercentageLabel.setText(value + "%");
	}

	/**
	 * calculate the results and show them, or show error messages.
	 */
	private void calculateResults() {
		try {
			errorMessage.setText("");
			double amount = Double.parseDouble(amountTextField.getText());// taking value out of user input
			if (amount >= 0) {
				tipTextField.setText(DF.format(amount * tipPercentage / 100));// calculate and display tip result
				totalTextField.setText(DF.format(amount + amount * tipPercentage / 100));// calculate and display total
																							// result
			} else {
				errorMessage.setText("<html><font color='red'><i>Amount cannot be negative</i></font></html>");
			}
		} catch (NumberFormatException numberFormatException) {
			errorMessage.setText("<html><font color='red'><i>Improper amount input</i></font></html>");// error message
																										// label when
																										// user enter
																										// invalid
																										// number format
			JOptionPane.showMessageDialog(frame, "Unknown error");
		}
	}
}


/* Name: Amanda Mullen
 * Student ID: 040774267
 * Course & Section: CST8132 313
 * Assignment: Lab 8
 * Date: April 7th, 2019
 */
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class TipCalculatorFrame extends JFrame {
	/**
	 * Label for 
	 */
	JLabel amountLabel;
	JLabel tipPercentLabel;
	JLabel tipLabel;
	JLabel totalLabel;
	JTextField amountTextField;
	JSlider tipPercentageSlider; 
	JTextField tipTextField; 
	JTextField totalTextField;
	JButton calculateButton; 
	int tipPercentage= 15;
	Double totalValue = null; 
	Double amount; 
	static final DecimalFormat DF = new DecimalFormat("$0.00");  
	
	/**
	 * TipCalculatorFrame constructor method 
	 */
	public TipCalculatorFrame(){
		super("Tip Calculator");
		setSize(300,200);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setLayout(new GridBagLayout()); 
		GridBagConstraints c = new GridBagConstraints(); 
		
		calculateButton= new JButton("Calculate");
		 c.fill = GridBagConstraints.HORIZONTAL;
		 c.ipady = 0;
		 c.weightx = 0.5;
		 c.weighty = 0.8; 
		 c.anchor = GridBagConstraints.PAGE_END;
		 c.gridx = 2;
		 c.gridy = 6;
		 calculateButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				calculateResults();
			}
		 
		 });
		 add(calculateButton, c);
	
		
		amountLabel = new JLabel("Amount");
		 c.fill = GridBagConstraints.HORIZONTAL;
		 c.anchor = GridBagConstraints.FIRST_LINE_START;
		 c.gridx = 1;
		 c.gridy =1; 
		 add(amountLabel, c);
		 
		tipPercentLabel = new JLabel(tipPercentage + "%"); 
		 c.fill = GridBagConstraints.HORIZONTAL;
		 c.anchor = GridBagConstraints.LINE_START;
		 c.gridx = 1;
		 c.gridy =2; 
		 add(tipPercentLabel, c);
		
		 tipLabel = new JLabel("Tip");
		 c.fill = GridBagConstraints.HORIZONTAL;
		 c.anchor = GridBagConstraints.LINE_END;
		 c.gridx = 1;
		 c.gridy =3; 
		 add(tipLabel, c);
		 
		 totalLabel = new JLabel("Total");
		 c.fill = GridBagConstraints.HORIZONTAL;
		 c.anchor = GridBagConstraints.LINE_START;
		 c.gridx = 1;
		 c.gridy =4; 
		 add(totalLabel, c);
		 
		 amountTextField = new JTextField();
		 c.fill = GridBagConstraints.HORIZONTAL;
		 c.anchor = GridBagConstraints.CENTER;
		 c.gridx = 2;
		 c.gridy =1; 
		 amountTextField.setEditable(true);
		 TextHandler tHandler = new TextHandler(); 
		 amountTextField.addActionListener(tHandler);
		 add(amountTextField, c);
		 
		 tipPercentageSlider = new JSlider(0, 30); 
		 c.fill = GridBagConstraints.HORIZONTAL;
		 c.anchor = GridBagConstraints.CENTER;
		 c.gridx = 2;
		 c.gridy =2; 
		 tipPercentageSlider.setMajorTickSpacing(5);
		 tipPercentageSlider.setPaintTicks(true);
		 SliderHandler sHandler = new SliderHandler(); 
		 tipPercentageSlider.addChangeListener(sHandler); 
		 add(tipPercentageSlider, c);
		 
		 
		 tipTextField = new JTextField();
		 c.fill = GridBagConstraints.HORIZONTAL;
		 c.anchor = GridBagConstraints.CENTER;
		 c.gridx = 2;
		 c.gridy =3;
		tipTextField.setEditable(false);
		add(tipTextField, c);
		 
		 totalTextField = new JTextField("");
		 c.fill = GridBagConstraints.HORIZONTAL;
		 c.anchor = GridBagConstraints.CENTER;
		 c.gridx = 2;
		 c.gridy =4; 
		 totalTextField.setEditable(false);
		 add(totalTextField, c);
		 

	
	}//end of constructor 
	
	//Changelistener for the slider goes here 
	/**
	 * Method to set the tip percentage based on the slider settings 
	 * @param value The desired percentage of the total to be used as the tip.  
	 */
	public void setTipPercentage(int value) {
		
			tipPercentage = value; 
			tipPercentLabel.setText(value + "%");		
		
	}//end of setpercentage 
	
	/**
	 * Calculates the total based on the amount and the desired percentage 
	 */
	public void calculateResults() {
		boolean validAmount = true;
		try {
			amount = Double.parseDouble(amountTextField.getText());
		} catch (Exception e) {
			validAmount = false;
			tipTextField.setText("Invalid amount");
		}
		if (amount <0) {
			validAmount = false;
			tipTextField.setText("Negative amount not allowed");
		}
		if (validAmount){
			double tipAmount = amount * tipPercentage / 100;
			double total = amount + tipAmount;
			tipTextField.setText(DF.format(tipAmount));
			totalTextField.setText(DF.format(total));
			System.out.print("well at least the button works");
		} else {
			totalTextField.setText("");
		}
	}//end of results 
	
//private class ButtonHandler implements ActionListener {	
//	
//	public void actionPerformed(ActionEvent action) {
//		calculateResults(); 
//	}//end of action performed 
//}//end of button 

private class SliderHandler implements ChangeListener{
	
	@Override
	public void stateChanged(ChangeEvent event) {
		// TODO Auto-generated method stub
		int value = tipPercentageSlider.getValue(); 
		System.out.println(value);
		setTipPercentage(value);
	}//end of method	 
	
}//end of slider

private class TextHandler implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String input = amountTextField.getText(); 
		amount = Double.parseDouble(input); 
	}
	
}

}//end of class 


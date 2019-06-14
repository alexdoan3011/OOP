
/* Name: Amanda Mullen
 * Student ID: 040774267
 * Course & Section: CST8132 313
 * Assignment: Lab 8
 * Date: April 7th, 2019
 */

import java.awt.EventQueue;

public class TipCalculator {

	/**
	 * Launches the Program 
	 * @param args Command Line Arguments 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EventQueue.invokeLater(new Runnable() { 
			
			public void run() {
				try {
					TipCalculatorFrame frame = new TipCalculatorFrame();
					frame.setVisible(true);
				}//end of try 
				
				catch (Exception e) {
					e.printStackTrace();
				}//end of catch 
				
			}//end of run 
			
		});//end of EventQueue 

	}//end of main 

}//end of class 

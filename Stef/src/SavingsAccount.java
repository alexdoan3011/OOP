
/* * Name: Stefanos Sioulas
 * Student ID: 040728628 
 * Course & Section: CST8132 312  
 * Assignment: Lab 4 * Date: Feb 6, 2019 */

import java.text.DecimalFormat;
import java.util.Random;

/**
 * this class initializes savings accounts and implements interest rate
 * 
 * @author Stefanos Sioulas
 * @version 1.0
 */
public class SavingsAccount extends BankAccount {

	private float interestRate;

	/**
	 * creates savings account and generates random interest rate
	 */
	public SavingsAccount() {
		super();

		Random rd = new Random();

		// int interestLow = 2 / 100;
		// int interestHigh = 10 / 100;
		// interestRate = interestLow + rd.nextInt(interestHigh);
		interestRate = rd.nextFloat();
	}

	/**
	 * formats and stores interest rate
	 * 
	 * @return string with account num, interest rate and balance
	 */
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.000");
		String s = "Saving account:\t\t#" + super.toString() + "\t  Interest rate: " + df.format(interestRate);
		return s;

	}

	/**
	 * calculates saving account with interest rate
	 */
	public void calculateAndUpdateBalance() {
		// TODO Auto-generated method stub
		double monthlyInterest = interestRate / 12;
		balance = balance + balance * monthlyInterest;
	}

}

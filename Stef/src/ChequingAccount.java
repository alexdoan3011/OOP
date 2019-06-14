
/* * Name: Stefanos Sioulas
 * Student ID: 040728628 
 * Course & Section: CST8132 312  
 * Assignment: Lab 4 * Date: Feb 6, 2019 */

import java.text.DecimalFormat;
import java.util.Random;

/**
 * this class initializes chequing accounts and implements fees
 * 
 * @author Stefanos Sioulas
 * @version 1.0
 */

public class ChequingAccount extends BankAccount {

	private double fee;
	Random rd = new Random();

	/**
	 * creates chequing account and generates fees
	 */
	public ChequingAccount() {
		super();
		// generate random fee between 1 and 5
		int feeLow = 1;
		int feeHigh = 5;
		fee = feeLow + rd.nextInt(feeHigh);

	}

	/**
	 * formats and stores fee
	 * 
	 * @return string with account num, balance and fees
	 */
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.000");
		String s = "Chequing account:\t#" + super.toString() + "\t  Fee: " + df.format(fee);
		return s;

	}

	/**
	 * subtracts fee from chequing account
	 */
	public void calculateAndUpdateBalance() {
		// TODO Auto-generated method stub
		balance -= fee;
	}
}

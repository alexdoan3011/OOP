
/* * Name: Stefanos Sioulas
 * Student ID: 040728628 
 * Course & Section: CST8132 312  
 * Assignment: Lab 4 * Date: Feb 6, 2019 */

import java.text.DecimalFormat;
import java.util.Random;

/**
 * this class stores the balance, account numbers and other info for the
 * chequing and savings accounts
 * 
 * @author Stefanos Sioulas
 * @version 1.0
 */

public abstract class BankAccount {

	/**
	 * stores account balances
	 */
	protected double balance;
	/**
	 * stores account numbers to identify
	 */
	protected int accountNo;
	/**
	 * stores the number of accounts
	 */
	protected int numAccounts;

	/**
	 * generates random account numbers and balance
	 */
	public BankAccount() {
		Random rd = new Random();
		numAccounts += 1;

		while (this.accountNo == 0) { // give random accountNo, make sure it is not 0
			this.accountNo = rd.nextInt();
		}

		// generate random balance between 20 and 100
		int balanceLow = 20;
		int balanceHigh = 100;
		balance = balanceLow + rd.nextInt(balanceHigh);

	}

	/**
	 * returns account balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * formats and stores balance and account number
	 */
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00");// balance format
		String s = "Account #: " + accountNo + "\nBalance: " + df.format(balance);// output for accounts
		return s;

	}

	/**
	 * 
	 */
	public abstract void calculateAndUpdateBalance();

}

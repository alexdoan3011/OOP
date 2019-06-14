
/*
* Name: Van Nam Doan
* Student ID: 040943291
* Course & Section: CST8132 312
* Assignment: Lab 3
* Date: Jan 21, 2019
*/
import java.util.Random;

public class Account {
	private long accountNum;
	private Client client;
	private double balance;

	Account(Client client, double balance) {
		this.client = client;
		this.balance = balance;
		Random Random = new Random();
		while (this.accountNum == 0)// randomly generating account number, not letting value = 0
			this.accountNum = Random.nextLong();
		if (this.accountNum < 0)// if account number is <0, make it >0
			this.accountNum = -1 * this.accountNum;
	}

	public Client getClient() {
		return client;
	}

	public double getBalance() {
		return balance;
	}

	public long getAccountNum() {
		return accountNum;
	}

	public String getName() {
		return client.getName();
	}

	public void deposit(double amt) {
		balance += amt;
	}

	public boolean withdraw(double amt) {// deduct money from account
		if (amt <= balance) {
			balance -= amt;
			return true;
		}
		return false;
	}
}

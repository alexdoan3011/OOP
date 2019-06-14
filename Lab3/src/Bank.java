
/*
* Name: Van Nam Doan
* Student ID: 040943291
* Course & Section: CST8132 312
* Assignment: Lab 3
* Date: Jan 21, 2019
*/
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Scanner;

public class Bank {
	private Scanner input = new Scanner(System.in);
	private Account[] accounts;
	private String bankName;

	Bank() {
		input = new Scanner(System.in);
		System.out.print("Bank name: ");
		bankName = input.next();
		System.out.print("Number of accounts: ");
		int temp = 0;
		boolean valid = false;
		String str = input.next();
		while (!valid) {
			valid = true;
			//first check
			for (char c : str.toCharArray()) {
				if (!Character.isDigit(c)) {
					System.out.print("Enter valid number: ");
					str = input.next();
					valid = false;
					break;
				}
			}
			//if first check doesn't pass, repeat the loop, ignore the rest
			if (!valid)
				continue;
			//if first check pass, cast the string into temp
			temp = Integer.parseInt(str);
			//second check
			if (temp <= 0) {
				System.out.print("Enter valid number: ");
				str = input.next();
				valid = false;
			}
		}
		accounts = new Account[temp];// initializing array accounts to the user entered capacity
		for (int i = 0; i < accounts.length; i++) {// loop provides input for every account
			System.out.print("Account#" + (i + 1) + ":\n\tInput first name: ");
			String firstName = input.next();
			System.out.print("\tInput last name: ");
			String lastName = input.next();
			System.out.print("\tInput phone number: ");
			long phoneNum;
			if (input.hasNextLong())// filtering user input
				phoneNum = input.nextLong();
			else {
				while (!input.hasNextLong()) {// filtering user input and display error
					System.out.print("\tEnter a valid phone number: ");
					input.next();// clearing input
				}
				phoneNum = input.nextLong();
			}
			String email = "";
			boolean at = false;
			boolean dot = false;
			do {
				if (email.isEmpty())
					System.out.print("\tInput email address: ");
				else
					System.out.print("\tInput valid email address: ");
				email = input.next();
				at = false;
				dot = false;
				int pos = 0;
				for (int j = 1; j < email.length(); j++) {// checking if email entered with @ in front of .
					if (email.charAt(j) == '@') {// check for @
						at = true;
						pos = j;
						break;
					}
				}
				for (int j = (pos + 2); j < email.length(); j++) {// check for . from position of @ plus 2
					if (at && email.charAt(j) == '.') {
						dot = true;
						pos = j;
						break;
					}
				}
				if (email.length() == (pos + 1)) {// check if string is empty after the dot
					dot = false;
				}
			} while (!(at && dot));
			Client client = new Client(firstName, lastName, phoneNum, email);// creating a new client object
			System.out.print("\tInput initial balance: ");
			double balance = input.nextDouble();
			accounts[i] = new Account(client, balance);// initializing an account
		}
	}

	public void printAccount() {
		DecimalFormat df = new DecimalFormat();// for formating balance
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator('-');
		DecimalFormat df2 = new DecimalFormat("000,000,000,000,000,000,000", symbols);// for formating account number
		System.out.println("Bank of " + bankName);
		for (int i = 0; i < accounts.length; i++) {// loop displays all accounts
			System.out.print("Account# " + df2.format(accounts[i].getAccountNum()) + " info:");
			System.out.print(" Client: " + accounts[i].getClient().getName());
			System.out.print("; Phone#: " + accounts[i].getClient().getPhoneNum());
			System.out.print("; Email: " + accounts[i].getClient().getEmail());
			System.out.println("; Balance: " + df.format(accounts[i].getBalance()));
		}
	}

	public static void main(String arg[]) {
		DecimalFormat df = new DecimalFormat();// for formating balance
		Bank bank = new Bank();
		boolean exit = false;// loop exit controller
		while (!exit) {
			System.out.println("Select option:\n\tp: view accounts\n\tw: withdraw\n\td: deposit\n\tq: exit");// display
																												// choices
			char choice = bank.input.next().charAt(0);
			switch (Character.toLowerCase(choice)) {
			case 'p': {
				bank.printAccount();
				break;
			}
			case 'w': {
				int index = 0;
				if (bank.accounts.length > 1) {
					System.out.print("Enter account index [1-" + (bank.accounts.length) + "]: ");// adding 1 to index to
																									// be more natural
					index = bank.input.nextInt() - 1;
					while (index >= bank.accounts.length) {// index range controller
						System.out.print("Input valid index [1-" + (bank.accounts.length) + "]: ");
						index = bank.input.nextInt() - 1;
					}
				}
				System.out.print("Enter amount to withdraw: ");
				double withdraw = bank.input.nextDouble();
				if (!bank.accounts[index].withdraw(withdraw))// amount range controller & money deduction
					System.out
							.println("Insufficient Funds! Balance is $" + df.format(bank.accounts[index].getBalance()));
				break;
			}
			case 'd': {
				int index = 0;
				if (bank.accounts.length > 1) {
					System.out.print("Enter account index [1-" + (bank.accounts.length) + "]: ");// adding 1 to index to
																									// be more natural
					index = bank.input.nextInt() - 1;
					while (index >= bank.accounts.length) {// index range controller
						System.out.print("Input valid index [1-" + (bank.accounts.length) + "]: ");
						index = bank.input.nextInt() - 1;
					}
				}
				System.out.print("Enter amount to deposit: ");
				double deposit = bank.input.nextDouble();
				bank.accounts[index].deposit(deposit);// put money into account
				break;
			}
			case 'q':
				exit = true;// exit controller
				System.out.println("Exiting...");
				break;
			}
		}
	}
}

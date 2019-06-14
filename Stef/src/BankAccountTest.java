
/* * Name: Stefanos Sioulas
 * Student ID: 040728628
 * Course & Section: CST8132 312
 * Assignment: Lab 4 * Date: Feb 6, 2019 */

/**
 * this class declares, instantiates and initializes 5 accounts for testing purposes
 *
 * @author Stefanos Sioulas
 * @version 1.0
 */
public class BankAccountTest {

    BankAccount[] accounts;

    /**
     * creates and initializes 5 bank accounts
     */
    public BankAccountTest() {
        accounts = new BankAccount[5];
        accounts[0] = new SavingsAccount();
        accounts[1] = new SavingsAccount();
        accounts[2] = new ChequingAccount();
        accounts[3] = new ChequingAccount();
        accounts[4] = new ChequingAccount();

    }

    /**
     * performs monthly balance update for each account
     */
    public void monthlyProcess(BankAccount[] accounts) {
        for (BankAccount element : accounts) {
            element.calculateAndUpdateBalance();
        }
    }

    /**
     * displays the balance of each account
     */
    public void displayBalance(BankAccount[] accounts) {
        for (int i = 0; i < accounts.length; i++)
            System.out.println(accounts[i].toString());
    }

    /**
     * creates an instance of the class and calls monthlyProcess and display methods 12 times
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        BankAccountTest test = new BankAccountTest();
        for (int i = 0; i < 12; i++) {
            System.out.println("Month " + (i+1));
            test.monthlyProcess(test.accounts);
            test.displayBalance(test.accounts);
        }
    }

}

/*
 * Name: Van Nam Doan
 * Student ID: 040943291
 * Course & Section: CST8132 312
 * Assignment: Lab 4
 * Date: Feb 3, 2019
 */

import java.util.Random;

/**
 * The purpose of this class is to create 2 saving accounts and 3 chequing accounts, and to perform update and print
 *
 * @author Van Nam Doan
 * @version 1.1
 * @since JDK 1.8
 */
public class BankAccountTest {
    /**
     * an array of all created bank account
     */
    private BankAccount[] acc;

    public BankAccountTest() {
        Random random = new Random();
        /**
         * number of saving accounts
         */
        int sv = 2;
        /**
         * number of chequing accounts
         */
        int ch = 3;
        acc = new BankAccount[sv + ch];// allocating memory for both saving and chequing accounts

        for (int i = 0; i < sv; i++) {// filling the first sv members of the array with saving accounts
            acc[i] = new SavingsAccount(random.nextDouble() * 0.08 + 0.02, 20 + random.nextDouble() * 80);// creating saving account with randomized interest rate, 0.02-0.1 and randomized balance, 20-100
        }
        for (int i = sv; i < (sv + ch); i++) {// filling the rest with chequing accounts
            acc[i] = new ChequingAccount(random.nextDouble() * 4 + 1, 20 + random.nextDouble() * 80);// creating chequing account with randomized fee, 1-5 and randomized balance, 20-100
        }
    }

    /**
     * Initializes a new bank account test object, display all accounts, update all accounts' balance for each of the 12 months
     *
     * @param args UNUSED - command line arguments
     */
    public static void main(String[] args) {
        BankAccountTest bank = new BankAccountTest();
        bank.display(bank.acc);// display all accounts
        for (int i = 0; i < 12; i++) {
            System.out.print("Month " + (i + 1) + ", found " + BankAccount.numAccounts + " account");// display month and number of accounts
            if (BankAccount.numAccounts > 1)
                System.out.print("s");// print "s" for plural
            System.out.println(": ");
            bank.monthlyProcess(bank.acc);// update balance for each month on all accounts
            bank.display(bank.acc);// display all accounts for every month
        }
    }

    /**
     * this method updates all account bank balance
     *
     * @param acc an array of bank accounts
     */
    public void monthlyProcess(BankAccount[] acc) {
        for (int i = 0; i < acc.length; i++)
            acc[i].calculateAndUpdateBalance();
    }

    /**
     * this method displays info of all bank accounts
     *
     * @param acc an array of bank accounts
     */
    public void display(BankAccount[] acc) {
        for (int i = 0; i < acc.length; i++)
            System.out.println(acc[i].toString());
    }
}

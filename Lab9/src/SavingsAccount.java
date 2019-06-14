/*
 * Name: Van Nam Doan
 * Student ID: 040943291
 * Course & Section: CST8132 312
 * Assignment: Lab 9
 * Date: Apr 9, 2019
 */

import java.text.DecimalFormat;

/**
 * The purpose of this class is to initialize saving accounts and to print or update said accounts
 *
 * @author Van Nam Doan
 * @version 1.2
 * @since 1.8
 */
public class SavingsAccount extends BankAccount {
    /**
     * annual interest rate (0,1)
     */
    private double interestRate;
    /**
     * minimum balance for saving account (>=0)
     */
    private double minimumBalance;

    /**
     * create saving account
     */
    SavingsAccount(long accNumber, Person accHolder, double balance, double minimumBalance, double interestRate) {
        this.accNumber = accNumber;
        this.accHolder = accHolder;
        this.balance = balance;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
    }

    /**
     * default constructor
     */
    SavingsAccount() {
    }

    /**
     * this method updates saving account balance with interest added
     *
     * @return operation log
     */

    @Override
    public String monthlyAccountUpdate() {
        DecimalFormat dfLogBalance = new DecimalFormat("0.0#");// for formatting balance for log
        if (balance > minimumBalance) {
            double temp = balance;// store the balance before update
            double interestMonth = interestRate / 12;// calculate interest rate per month from annual interest rate
            balance = balance + balance * interestMonth;// add monthly interest into balance
            return "Monthly update successful for account " + accNumber + ". Balance changed: " + dfLogBalance.format(temp) + " -> " + dfLogBalance.format(balance);// operation log message
        } else {
            return "Monthly update failed for account " + accNumber + ". Insufficient balance";
        }
    }

    /**
     * this method is for printing a saving account
     *
     * @return concatenated string with account number, balance and interest rate
     */
    @Override
    public String toString() {
        DecimalFormat dfPrintBalanceInterestRate = new DecimalFormat("0.0#");// for formatting minimum balance and interest rate
        return super.toString() + "  Minimum Balance: " + dfPrintBalanceInterestRate.format(minimumBalance) + "  Interest Rate: " + dfPrintBalanceInterestRate.format(interestRate);
    }

    /**
     * file output formatting specific to saving accounts
     *
     * @return saving account output format
     */
    @Override
    public String toOutput() {
        DecimalFormat dfOutputBalanceInterestRate = new DecimalFormat("0.0#");// for formatting minimum balance
        return super.toOutput() + " " + dfOutputBalanceInterestRate.format(interestRate) + " " + dfOutputBalanceInterestRate.format(minimumBalance);
    }

    /**
     * import minimum balance and interest rate
     *
     * @return true if account added successfully
     */
    public boolean addBankAccount() {
        if (super.addBankAccount()) {
            if (Bank.getTemp().size() != 0 && !Bank.isImportDisabled()) {
                if (InputFilter.validBalance(Bank.getTemp().get(Bank.getCounter()))) {
                    interestRate = Double.parseDouble(Bank.getTemp().get(Bank.getCounter()));
                    Bank.setCounter(Bank.getCounter() + 1);
                } else {
                    Bank.getImportLog().add("Import error detected. Import disabled");// import file might be corrupted
                    Bank.disableImport();// disabling import
                    return false;
                }
            }
            if (Bank.getTemp().size() != 0 && !Bank.isImportDisabled()) {
                if (InputFilter.validBalance(Bank.getTemp().get(Bank.getCounter()))) {
                    minimumBalance = Double.parseDouble(Bank.getTemp().get(Bank.getCounter()));
                    Bank.setCounter(Bank.getCounter() + 1);
                } else {
                    Bank.getImportLog().add("Import error detected. Import disabled");// import file might be corrupted
                    Bank.disableImport();// disabling import
                    return false;
                }
            }
            Bank.getImportLog().add("Minimum balance: ");
            Bank.getImportLog().add("[" + minimumBalance + "]");
            Bank.getImportLog().add("Interest rate: ");
            Bank.getImportLog().add("[" + interestRate + "]");
            return true;
        } else {
            return false;
        }
    }
}

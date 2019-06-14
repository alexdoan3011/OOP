/*
 * Name: Van Nam Doan
 * Student ID: 040943291
 * Course & Section: CST8132 312
 * Assignment: Lab 9
 * Date: Apr 9, 2019
 */

import java.text.DecimalFormat;

/**
 * The purpose of this class is to initialize chequing accounts and to print or update said accounts
 *
 * @author Van Nam Doan
 * @version 1.2
 * @since 1.8
 */
public class ChequingAccount extends BankAccount {
    /**
     * fee deducted every month (>=0)
     */
    private double fee;

    /**
     * create chequing account
     */
    ChequingAccount(long accNumber, Person accHolder, double balance, double fee) {
        this.accNumber = accNumber;
        this.accHolder = accHolder;
        this.balance = balance;
        this.fee = fee;
    }

    /**
     * default constructor
     */
    ChequingAccount() {
    }

    /**
     * this method deducts fee from a chequing account
     *
     * @return operation log
     */
    @Override
    public String monthlyAccountUpdate() {
        DecimalFormat dF = new DecimalFormat("0.0#");// for formatting balance for log
        if (balance >= fee) {
            double temp = balance;// store the balance before update
            balance -= fee;
            return "Monthly update successful for account " + accNumber + ". Balance changed: " + dF.format(temp) + " -> " + dF.format(balance);// operation log message
        } else {
            return "Monthly update failed for account " + accNumber + ". Insufficient balance";
        }
    }// deduct balance by fee every month

    /**
     * this method is for printing a chequing account
     *
     * @return concatenated string with account number, balance and fee
     */
    @Override
    public String toString() {
        DecimalFormat dfPrintFee = new DecimalFormat("0.0#");// for formatting fee
        return super.toString() + "  Fee: " + dfPrintFee.format(fee);
    }

    /**
     * file output formatting specific to chequing accounts
     *
     * @return chequing account output format
     */
    @Override
    public String toOutput() {
        DecimalFormat dfOutputFee = new DecimalFormat("0.0#");// for formatting minimum balance
        return super.toOutput() + " " + dfOutputFee.format(fee);
    }

    /**
     * import monthly fee
     *
     * @return true if account added successfully
     */
    public boolean addBankAccount() {
        if (super.addBankAccount()) {
            if (Bank.getTemp().size() != 0 && !Bank.isImportDisabled()) {
                Bank.getImportLog().add("Monthly fee: ");
                if (Bank.getTemp().size() != 0 && !Bank.isImportDisabled()) {
                    if (InputFilter.validBalance(Bank.getTemp().get(Bank.getCounter()))) {
                        fee = Double.parseDouble(Bank.getTemp().get(Bank.getCounter()));
                        Bank.getImportLog().add("[" + fee + "]");
                        Bank.setCounter(Bank.getCounter() + 1);
                    } else {
                        Bank.getImportLog().add("Import error detected. Import disabled");// import file might be corrupted
                        Bank.disableImport();// disabling import
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }
}

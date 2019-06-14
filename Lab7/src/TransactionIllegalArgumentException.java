public class TransactionIllegalArgumentException extends IllegalArgumentException {
    /**
     * account number of the account operation is being performed on
     */
    private long accNum;
    /**
     * "w" for withdrawing operation, "d" for depositing operation
     */
    private String operation;
    /**
     * amount for withdrawing or depositing
     */
    private double amt;
    /**
     * balance of the account operation is being performed on
     */
    private double balance;

    /**
     * exception object containing all variables needed to output error message
     *
     * @param accNum    account number of the account operation is being performed on
     * @param operation "w" for withdrawing operation, "d" for depositing operation
     * @param amt       amount for withdrawing or depositing
     * @param balance   balance of the account operation is being performed on
     */
    TransactionIllegalArgumentException(long accNum, String operation, double amt, double balance) {
        this.accNum = accNum;
        this.operation = operation;
        this.amt = amt;
        this.balance = balance;
    }

    /**
     * perform calculation and return error message
     *
     * @return error message
     */
    public String toString() {
        String message = "";
        if (amt < 0) {// negative amount is illegal, create error message
            message = "Update operation error for account " + accNum + ": Illegal amount: " + amt;
        } else {
            if (operation.equals("w")) {// if operation is withdrawing
                if (amt > balance)// amount bigger than balance is illegal, create error message
                    message = "Update operation error for account " + accNum + ": Balance insufficient. Amount requested: " + amt + ". Current balance: " + balance;
            }
        }
        return message;
    }
}

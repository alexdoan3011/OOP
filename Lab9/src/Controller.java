/*
 * Name: Van Nam Doan
 * Student ID: 040943291
 * Course & Section: CST8132 312
 * Assignment: Lab 9
 * Date: Apr 9, 2019
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * this class controls every function of the UI, tabs, buttons, drop down lists, text fields, list views, text area
 *
 * @author Van Nam Doan
 * @version 1.2
 * @since 1.8
 */
public class Controller {
    /**
     * choice of update: -1 if not selected, 0 if withdraw, 1 if deposit, 2 if monthly update. Relevant in Update account tab
     */
    @FXML
    private static int updateChoice = -1;
    /**
     * FXCollections for drop down menu in Update account tab
     */
    private final ObservableList<String> operationList = FXCollections.observableArrayList("Withdraw", "Deposit", "Monthly Update");
    /**
     * error message labels:
     * errAccNum: error message label for account number text field in Add account tab
     * errPhoneNum: error message label for phone number text field in Add account tab
     * errEmail: error message label for email text field in Add account tab
     * errOpeningBalance: error message label for opening balance text field in Add account tab
     * errFirstName: error message label for first name text field in Add account tab
     * errLastName: error message label for last name text field in Add account tab
     * errMinBalance: error message label for minimum balance text field in Add account tab
     * errInterestRate: error message label for interest rate text field in Add account tab
     * errAmount: error message label for amount text field in Update account tab
     * errFindAccNum: error message label for account number text field in Update account tab
     * errFindAccNumDisplay: error message label for account number text field in Display account tab
     */
    @FXML
    private javafx.scene.control.Label errAccNum, errPhoneNum, errEmail, errOpeningBalance, errFirstName, errLastName, errMinBalance, errInterestRate, errAmount, errFindAccNum, errFindAccNumDisplay;
    /**
     * minimumBalanceLabel: minimum balance label in Add account tab
     * interestRateLabel: interest rate label in Add account tab
     */
    @FXML
    private javafx.scene.control.Label minimumBalanceLabel, interestRateLabel;
    /**
     * text fields:
     * accNum: account number text field in Add account tab
     * firstName: first name text field in Add account tab
     * lastName: last name text field in Add account tab
     * phoneNum: phone number text field in Add account tab
     * emailAddress: email text field in Add account tab
     * openingBalance: opening balance text field in Add account tab
     * minBalance: minimum balance text field in Add account tab
     * interestRate: interest rate text field in Add account tab
     * findAccNum: account number text field in Update account tab
     * amount: amount text field in Update account tab
     * findAccNumDisplay: account number text field in Display account tab
     */
    @FXML
    private javafx.scene.control.TextField accNum, firstName, lastName, phoneNum, emailAddress, openingBalance, minBalance, interestRate, findAccNum, amount, findAccNumDisplay;
    /**
     * tabs:
     * addAccountTab: Add account tab
     * updateAccountTab: Update account tab
     * displayAccountTab: Display account tab
     * printAllAccTab: Print all accounts tab
     * importTab: Import tab
     */
    @FXML
    private javafx.scene.control.Tab addAccountTab, updateAccountTab, displayAccountTab, printAllAccTab, importTab;
    /**
     * account info text area in Display account tab
     */
    @FXML
    private TextArea accInfo;
    /**
     * "Choose operation" selector in Update account tab
     */
    @FXML
    private ComboBox<String> operationChoice;
    /**
     * anchor panes in Update account tab
     * wAndD: anchor pane for withdraw & deposit
     * mU: anchor pane for monthly update
     */
    @FXML
    private AnchorPane wAndD, mU;
    /**
     * Log printing list views:
     * monthlyUpdateLog: monthly update log list view in Update account tab
     * importLog: import log list view in Import tab
     */
    @FXML
    private ListView<String> monthlyUpdateLog, importLog;
    /**
     * print all accounts list view in Print all accounts tab
     */
    @FXML
    private ListView<String> printAllAcc;
    /**
     * Buttons in the import tab
     */
    @FXML
    private Button importOneEntry, importAllData, credit;
    /**
     * true when Saving account radio button in Add account tab is selected
     */
    private boolean addingSavAcc = true;
    /**
     * true when all fields in Account tab is populated with correct values
     */
    private boolean validAccInput = true;

    /**
     * for displaying info-type pop-up window
     *
     * @param info info to display
     */
    private static void popUpInfo(String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);// display info alert for action
        alert.setTitle("System info");
        alert.setHeaderText(null);
        alert.setContentText(info);
        alert.showAndWait();
    }

    /**
     * for displaying error-type pop-up window
     *
     * @param error error to display
     */
    static void popUpError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);// display info alert for action
        alert.setTitle("System info");
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }

    /**
     * credit button that shows the author info for the program
     */
    @FXML
    void credit() {
        Stage stage = (Stage) credit.getScene().getWindow();// get a handle to the stage
        confirmation(stage);
    }

    void confirmation(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);// Create an alert window
        alert.setTitle("System info");
        alert.setHeaderText(null);
        alert.setContentText("Name: Van Nam Doan\n" +
                "Student ID: 040943291\n" +
                "Course & Section: CST8132 312\n" +
                "Assignment: Lab 9\n" +
                "Date: Apr 5, 2019\n\n" +
                "Exit application?");
        new ButtonType("", ButtonBar.ButtonData.YES);
        new ButtonType("", ButtonBar.ButtonData.NO);
        alert.showAndWait().ifPresent(type -> {
            if (type == ButtonType.OK) {
                stage.close();// kill it
            } else {
                alert.close();// close alert window
            }
        });
    }

    /**
     * live update error checking: key listener for account number text field in Add account tab
     * check error for every key entered
     * doesn't check when the key is tab to avoid checking when the user doesn't enter anything and skip to the next field
     *
     * @param event key event
     */
    @FXML
    protected void accNumListener(KeyEvent event) {
        if (event.getCode() != KeyCode.TAB) {
            accNumCheck();
        }
    }

    /**
     * check account number text field in Add account tab, display error message when invalid
     */
    @FXML
    private void accNumCheck() {
        if (InputFilter.validAccNum(accNum.getText())) {// when valid account number
            errAccNum.setVisible(false);// hide error message
            if (accNum.getText().length() > 8) {// when account number has more than 8 digits
                errAccNum.setVisible(true);
                validAccInput = false;
                errAccNum.setText("More than 8 digits");// display customized error message for this invalidity
            } else if (Bank.findAccount(Long.parseLong(accNum.getText()), 0) != -1) {// when account number is found in database
                errAccNum.setVisible(true);
                validAccInput = false;
                errAccNum.setText("Account number already exists");
            }
        } else {
            errAccNum.setVisible(true);
            validAccInput = false;
            errAccNum.setText("Invalid account number");
        }
    }

    /**
     * live update error checking: key listener for first name text field in Add account tab
     * check error for every key entered
     * doesn't check when the key is tab to avoid checking when the user doesn't enter anything and skip to the next field
     *
     * @param event key event
     */
    @FXML
    protected void firstNameListener(KeyEvent event) {
        if (event.getCode() != KeyCode.TAB) {
            firstNameCheck();
        }
    }

    /**
     * check first name text field in Add account tab, display error message when invalid
     */
    @FXML
    private void firstNameCheck() {
        if (firstName.getText().length() > 0) {
            errFirstName.setVisible(false);
        } else {
            errFirstName.setVisible(true);// if there is no text inside first name text field
            validAccInput = false;
            errFirstName.setText("Please enter first name");
        }
    }

    /**
     * live update error checking: key listener for last name text field in Add account tab
     * check error for every key entered
     * doesn't check when the key is tab to avoid checking when the user doesn't enter anything and skip to the next field
     *
     * @param event key event
     */
    @FXML
    protected void lastNameListener(KeyEvent event) {
        if (event.getCode() != KeyCode.TAB) {
            lastNameCheck();
        }
    }

    /**
     * check last name text field in Add account tab, display error message when invalid
     */
    @FXML
    private void lastNameCheck() {
        if (lastName.getText().length() > 0) {
            errLastName.setVisible(false);
        } else {
            errLastName.setVisible(true);// if there is no text inside last name text field
            validAccInput = false;
            errLastName.setText("Please enter last name");
        }
    }

    /**
     * live update error checking: key listener for phone number text field in Add account tab
     * check error for every key entered
     * doesn't check when the key is tab to avoid checking when the user doesn't enter anything and skip to the next field
     *
     * @param event key event
     */
    @FXML
    protected void phoneNumListener(KeyEvent event) {
        if (event.getCode() != KeyCode.TAB) {
            phoneNumCheck();
        }
    }

    /**
     * check phone number text field in Add account tab, display error message when invalid
     */
    @FXML
    private void phoneNumCheck() {
        if (InputFilter.validPhoneNum(phoneNum.getText())) {// if phone number text field only has numbers
            errPhoneNum.setVisible(false);
        } else {
            errPhoneNum.setVisible(true);
            validAccInput = false;
            errPhoneNum.setText("Enter numbers only");
        }
    }

    /**
     * live update error checking: key listener for email address text field in Add account tab
     * check error for every key entered
     * doesn't check when the key is tab to avoid checking when the user doesn't enter anything and skip to the next field
     *
     * @param event key event
     */
    @FXML
    protected void emailAddressListener(KeyEvent event) {
        if (event.getCode() != KeyCode.TAB) {
            emailAddressCheck();
        }
    }

    /**
     * check email address text field in Add account tab, display error message when invalid
     */
    @FXML
    private void emailAddressCheck() {
        if (InputFilter.validEmailAddress(emailAddress.getText())) {// if email text field has text in the form of x@x.x, x has at least 1 character
            errEmail.setVisible(false);
        } else {
            errEmail.setVisible(true);
            validAccInput = false;
            errEmail.setText("Invalid email address");
        }
    }

    /**
     * live update error checking: key listener for opening balance text field in Add account tab
     * check error for every key entered
     * doesn't check when the key is tab to avoid checking when the user doesn't enter anything and skip to the next field
     *
     * @param event key event
     */
    @FXML
    protected void openingBalanceListener(KeyEvent event) {
        if (event.getCode() != KeyCode.TAB) {
            if (InputFilter.validBalance(openingBalance.getText())) {// if opening balance text field has a positive double
                errOpeningBalance.setVisible(false);
            } else {
                errOpeningBalance.setVisible(true);
                validAccInput = false;
                errOpeningBalance.setText("Invalid opening balance");
            }
        }
    }

    /**
     * check opening balance text field in Add account tab, display error message when invalid
     */
    @FXML
    private void openingBalanceCheck() {
        if (InputFilter.validBalance(openingBalance.getText())) {// if opening balance text field has a positive double
            errOpeningBalance.setVisible(false);
        } else {
            errOpeningBalance.setVisible(true);
            validAccInput = false;
            errOpeningBalance.setText("Invalid opening balance");
        }
    }

    /**
     * live update error checking: key listener for minimum balance text field in Add account tab
     * check error for every key entered
     * doesn't check when the key is tab to avoid checking when the user doesn't enter anything and skip to the next field
     *
     * @param event key event
     */
    @FXML
    protected void minimumBalanceListener(KeyEvent event) {
        if (event.getCode() != KeyCode.TAB) {
            minimumBalanceCheck();
        }
    }

    /**
     * check minimum balance text field in Add account tab, display error message when invalid
     */
    @FXML
    private void minimumBalanceCheck() {
        if (InputFilter.validBalance(minBalance.getText())) {// if minimum balance text field has a positive double
            errMinBalance.setVisible(false);
        } else {
            errMinBalance.setVisible(true);
            validAccInput = false;
            if (addingSavAcc) {
                errMinBalance.setText("Invalid minimum balance");// error message for saving account adding
            } else {
                errMinBalance.setText("Invalid fee");// error message for chequing account adding
            }
        }
    }

    /**
     * live update error checking: key listener for interest rate text field in Add account tab
     * check error for every key entered
     * doesn't check when the key is tab to avoid checking when the user doesn't enter anything and skip to the next field
     *
     * @param event key event
     */
    @FXML
    protected void interestRateListener(KeyEvent event) {
        if (event.getCode() != KeyCode.TAB) {
            interestRateCheck();
        }
    }

    /**
     * check interest rate text field in Add account tab, display error message when invalid
     */
    @FXML
    private void interestRateCheck() {
        double interestRateTemp;
        if (interestRate.isVisible()) {// if interest rate text field is active
            if (InputFilter.validBalance(interestRate.getText())) {
                errInterestRate.setVisible(false);
                interestRateTemp = Double.parseDouble(interestRate.getText());
                if (!(interestRateTemp <= 1 && interestRateTemp >= 0)) {
                    errInterestRate.setVisible(true);
                    validAccInput = false;
                    errInterestRate.setText("Interest rate has to be in (0,1)");
                }
            } else {
                errInterestRate.setVisible(true);
                validAccInput = false;
                errInterestRate.setText("Invalid interest rate");
            }

        } else {
            errInterestRate.setVisible(false);
        }
    }

    /**
     * key listener for the entire window
     * let user press enter instead of clicking a tab button
     * save accounts' info into import file in Print all accounts tab
     *
     * @param event key event
     */
    @FXML
    protected void keyListener(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {// when user presses enter in selected tabs
            if (addAccountTab.isSelected()) {// when in Add account tab
                addAccountButtonClicked();// add new account
            } else if (updateAccountTab.isSelected()) {// when in Update account tab
                if (updateChoice == 0 || updateChoice == 1) {// when withdraw or deposit is selected from the drop down selector
                    updateOKButtonClicked();// withdraw or deposit
                } else if (updateChoice == 2) {// when monthly update is selected from the drop down selector
                    monthlyUpdateOKButtonClicked();// perform monthly update
                }
            } else if (displayAccountTab.isSelected()) {// when in Display account tab
                displayAccountOKButtonClicked();// display account
            }
        }
        if (printAllAccTab.isSelected()) {// when in Print all account tab
            if (event.getCode() == KeyCode.I) {// when key "i" is pressed
                Bank.printAccountDetails(true);// print account detail to import file (bankData.txt)
                popUpInfo("Data written to bankData.txt");// display info alert for action
            }
        }
    }

    /**
     * actions when user selects saving account option in Add account tab
     * transforms label and text field prompt
     * enable and show interest rate label and text field
     * recheck minimum balance and interest rate
     */
    @FXML
    protected void savAccSelected() {
        minimumBalanceLabel.setText("Minimum balance:");// transform label
        minBalance.setPromptText("Enter minimum balance");// and text field prompt
        interestRateLabel.setVisible(true);
        interestRate.setVisible(true);
        interestRate.setDisable(false);
        addingSavAcc = true;
        if (minBalance.getText().length() != 0) {// perform check if minimum balance text field is not empty
            minimumBalanceCheck();
        } else {
            errMinBalance.setVisible(false);// if the field is empty, remove error message
        }
        if (interestRate.getText().length() != 0) {// perform check if interest rate text field is not empty
            interestRateCheck();
        } else {
            errInterestRate.setVisible(false);// if the field is empty, remove error message
        }
    }

    /**
     * actions when user selects chequing account option in Add account tab
     * transforms label and text field prompt
     * disable and hide interest rate label and text field
     * hide interest rate error
     * recheck monthly fee
     */
    @FXML
    protected void cheAccSelected() {
        minimumBalanceLabel.setText("Monthly Fee:");// transform label
        minBalance.setPromptText("Enter monthly fee");// and text field prompt
        interestRateLabel.setVisible(false);
        interestRate.setVisible(false);
        interestRate.setDisable(true);
        errInterestRate.setVisible(false);
        addingSavAcc = false;
        if (minBalance.getText().length() != 0) {// perform check if monthly fee text field is not empty
            minimumBalanceCheck();
        } else {
            errMinBalance.setVisible(false);// if the field is empty, remove error message
        }
    }

    /**
     * actions when user press Add account button in Add account tab, or press enter in Add account tab
     * performs check on all fields
     */
    @FXML
    private void addAccountButtonClicked() {
        int numAccBefore = Bank.getAccounts().size();// record number of accounts before adding
        validAccInput = true;
        /* perform check on all fields */
        accNumCheck();
        firstNameCheck();
        lastNameCheck();
        phoneNumCheck();
        emailAddressCheck();
        openingBalanceCheck();
        minimumBalanceCheck();
        interestRateCheck();
        /* temporary local variables for storing user input */
        long accNumTemp;
        String firstNameTemp;
        String lastNameTemp;
        String phoneNumTemp;
        String emailAddressTemp;
        double openingBalanceTemp;
        /* temporary placeholder bank account */
        BankAccount bankAccountTemp;
        if (validAccInput) {// if all checks are green
            /* format user's string inputs and store in temporary local vars */
            accNumTemp = Long.parseLong(accNum.getText());
            firstNameTemp = firstName.getText();
            lastNameTemp = lastName.getText();
            phoneNumTemp = phoneNum.getText();
            emailAddressTemp = emailAddress.getText();
            openingBalanceTemp = Double.parseDouble(openingBalance.getText());
            Person personTemp = new Person(firstNameTemp, lastNameTemp, phoneNumTemp, emailAddressTemp);// create a temporary client object
            if (addingSavAcc) {// if this is an Saving account adding operation
                /* take user's inputs for fields related to Saving account */
                double minBalanceTemp;
                double interestRateTemp;
                minBalanceTemp = Double.parseDouble(minBalance.getText());
                interestRateTemp = Double.parseDouble(interestRate.getText());
                bankAccountTemp = new SavingsAccount(accNumTemp, personTemp, openingBalanceTemp, minBalanceTemp, interestRateTemp);// allocate placeholder bank account
                Bank.getAccounts().add(bankAccountTemp);// add place holder to database
            } else {
                /* take user's inputs for field related to Chequing account */
                double monthlyFeeTemp;
                monthlyFeeTemp = Double.parseDouble(minBalance.getText());
                bankAccountTemp = new ChequingAccount(accNumTemp, personTemp, openingBalanceTemp, monthlyFeeTemp);// allocate placeholder bank account
                Bank.getAccounts().add(bankAccountTemp);// add place holder to database
            }
        }
        if (Bank.getAccounts().size() > numAccBefore) {// if database is increased after adding process, display info alert
            popUpInfo("Account added");
        }
    }

    /**
     * actions for then the OK button is clicked when in Update account tab, withdraw or deposit selected in drop down menu
     * perform check for account number and update account;
     * locate account and perform update on account
     * display update successful info alert
     */
    @FXML
    private void updateOKButtonClicked() {
        DecimalFormat dfMessageBalance = new DecimalFormat("0.0#");// for formatting balance for message
        // perform check on all fields
        findAccNumCheck();
        amountCheck();
        boolean validOperation = !errFindAccNum.isVisible() && !errAmount.isVisible();// valid when no error message is visible
        double balanceTemp;// local temp var for balance before update
        int index = 0;
        double amountTemp = 0;
        if (validOperation) {
            index = Bank.findAccount(Long.parseLong(findAccNum.getText()), 0);
            amountTemp = Double.parseDouble(amount.getText());// local temp var for update amount
        }
        if (validOperation) {
            balanceTemp = Bank.getAccounts().get(index).balance;// storing balance before update
            String choice = operationChoice.getValue();// get string from drop down menu
            if (choice.equalsIgnoreCase("withdraw")) {// if operation is withdraw
                if (amountTemp <= balanceTemp) {// if amount is smaller or equal to account balance
                    Bank.getAccounts().get(index).withdraw(amountTemp);// perform withdraw
                    /* display withdraw successful info alert */
                    popUpInfo("Withdraw successful. Account balance has changed:\n" + dfMessageBalance.format(balanceTemp) + " -> " + dfMessageBalance.format(Bank.getAccounts().get(index).balance));
                } else {
                    /* display insufficient balance error alert */
                    popUpError("Insufficient balance\nAmount requested: " + dfMessageBalance.format(Double.parseDouble(amount.getText())) + "\nBalance: " + dfMessageBalance.format(Bank.getAccounts().get(index).balance));
                }
            } else if (choice.equalsIgnoreCase("deposit")) {// if operation is deposit
                Bank.getAccounts().get(index).deposit(amountTemp);// perform deposit
                /* display deposit successful info alert */
                popUpInfo("Deposit successful. Account balance has changed:\n" + dfMessageBalance.format(balanceTemp) + " -> " + dfMessageBalance.format(Bank.getAccounts().get(index).balance));
            }
        }
    }

    /**
     * live update error checking: key listener for account number text field in Update account tab
     * check error for every key entered
     * doesn't check when the key is tab to avoid checking when the user doesn't enter anything and skip to the next field
     *
     * @param event key event
     */
    @FXML
    protected void findAccNumListener(KeyEvent event) {
        if (event.getCode() != KeyCode.TAB) {
            findAccNumCheck();
        }
    }

    /**
     * check account number text field in Update account tab, display error message when invalid
     */
    @FXML
    private void findAccNumCheck() {
        if (InputFilter.validAccNum(findAccNum.getText())) {// if account number is malformed
            errFindAccNum.setVisible(false);
            if (Bank.findAccount(Long.parseLong(findAccNum.getText()), 0) == -1) {// if account is not found
                errFindAccNum.setVisible(true);
                errFindAccNum.setText("Cannot find account");
            }
        } else {
            errFindAccNum.setVisible(true);
            errFindAccNum.setText("Invalid account number");
        }
    }

    /**
     * live update error checking: key listener for account number text field in Display account tab
     * check error for every key entered
     * doesn't check when the key is tab to avoid checking when the user doesn't enter anything and skip to the next field
     *
     * @param event key event
     */
    @FXML
    protected void findAccNumDisplayListener(KeyEvent event) {
        if (event.getCode() != KeyCode.TAB) {
            findAccNumDisplayCheck();
        }
    }

    /**
     * check account number text field in Display account tab, display error message when invalid
     */
    @FXML
    private void findAccNumDisplayCheck() {
        if (InputFilter.validAccNum(findAccNumDisplay.getText())) {
            errFindAccNumDisplay.setVisible(false);
            if (Bank.findAccount(Long.parseLong(findAccNumDisplay.getText()), 0) == -1) {
                errFindAccNumDisplay.setVisible(true);
                errFindAccNumDisplay.setText("Cannot find account");
            }
        } else {
            errFindAccNumDisplay.setVisible(true);
            errFindAccNumDisplay.setText("Invalid account number");
        }
    }

    /**
     * live update error checking: key listener for amount text field in Display account tab
     * check error for every key entered
     * doesn't check when the key is tab to avoid checking when the user doesn't enter anything and skip to the next field
     *
     * @param event key event
     */
    @FXML
    protected void amountListener(KeyEvent event) {
        if (event.getCode() != KeyCode.TAB) {
            amountCheck();
        }
    }

    /**
     * check amount text field in Display account tab, display error message when invalid
     */
    @FXML
    private void amountCheck() {
        if (InputFilter.validBalance(amount.getText())) {
            errAmount.setVisible(false);
        } else {
            errAmount.setVisible(true);
            errAmount.setText("Illegal amount");
        }
    }

    /**
     * actions performed when user selects an update mode in Update account tab
     * when withdraw or deposit modes are selected: hide anchor pane contains monthly update contents and set it to back of the stack pane
     * when monthly update mode is selected: hide anchor pane contains withdraw/deposit contents and set it to back of the stack pane
     */
    @FXML
    protected void operationSelected() {
        String choice = operationChoice.getValue();
        switch (choice.toLowerCase().charAt(0)) {
            case 'w':// withdraw mode
                updateChoice = 0;
                mU.toBack();// set anchor pane contains monthly update contents to back
                wAndD.setVisible(true);
                mU.setVisible(false);
                break;
            case 'd':
                updateChoice = 1;
                mU.toBack();
                wAndD.setVisible(true);
                mU.setVisible(false);
                break;
            default:
                updateChoice = 2;
                wAndD.toBack();// set anchor pane contains withdraw/deposit contents to back
                mU.setVisible(true);
                wAndD.setVisible(false);
                break;
        }
    }

    /**
     * actions performed when OK button in Display account tab is clicked
     * perform check on account number field
     * display corresponding account info
     */
    @FXML
    private void displayAccountOKButtonClicked() {
        int index;
        /* perform check on account number field */
        findAccNumDisplayCheck();
        if (!errFindAccNumDisplay.isVisible()) {
            index = Bank.findAccount(Long.parseLong(findAccNumDisplay.getText()), 0);
            accInfo.setText(Bank.getAccounts().get(index).toString());
        }
    }

    /**
     * actions performed when clicking OK button which appears when monthly update mode in Update account tab is selected
     * perform monthly update on all accounts
     * display monthly update log
     */
    @FXML
    private void monthlyUpdateOKButtonClicked() {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(Bank.monthlyUpdate());
        monthlyUpdateLog.setItems(items);
    }

    /**
     * actions performed when import one entry button in Import tab is clicked
     * import one line of data
     * display log
     * if there is no more data to display, or import error, hide import buttons, expand log field
     */
    @FXML
    protected void importOneEntry() {
        Bank.importData(false);// import one data line
        /* display log */
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(Bank.getImportLog());
        importLog.setItems(items);
        /* if there is no more data to display, or import error, hide import buttons, expand log field */
        if (Bank.isImportDisabled()) {
            AnchorPane.setTopAnchor(importLog, 10.0);
            importOneEntry.setDisable(true);
            importOneEntry.setVisible(false);
            importAllData.setDisable(true);
            importAllData.setVisible(false);
        }
    }

    /**
     * actions performed when import one entry button in Import tab is clicked
     * import one line of data
     * display log
     * hide import buttons, expand log field
     */
    @FXML
    protected void importAllData() {
        Bank.importData(true);// import all data
        /* display log */
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(Bank.getImportLog());
        importLog.setItems(items);
        /* hide import buttons, expand log field */
        AnchorPane.setTopAnchor(importLog, 10.0);
        importOneEntry.setDisable(true);
        importOneEntry.setVisible(false);
        importAllData.setDisable(true);
        importAllData.setVisible(false);
    }

    /**
     * this method runs at the start of the application
     */
    @FXML
    private void initialize() {
        operationChoice.setValue("");// set mode in the Update account tab to blank
        operationChoice.setItems(operationList);// populate the choices for the combo box in the Update account tab
        /*set the anchor panes in Update account tab invisible*/
        wAndD.setVisible(false);
        mU.setVisible(false);
        /* set all error labels to invisible */
        errAccNum.setVisible(false);
        errFirstName.setVisible(false);
        errLastName.setVisible(false);
        errPhoneNum.setVisible(false);
        errEmail.setVisible(false);
        errOpeningBalance.setVisible(false);
        errMinBalance.setVisible(false);
        errInterestRate.setVisible(false);
        errFindAccNum.setVisible(false);
        errAmount.setVisible(false);
        errFindAccNumDisplay.setVisible(false);
        accInfo.setWrapText(true);// enable text wrapping for account info text area in Display account tab
        /* action listener: when Update account tab is selected */
        updateAccountTab.setOnSelectionChanged(e -> {
            if (updateAccountTab.isSelected()) {
                if (findAccNum.getText().length() != 0) {// if the fields are not blank
                    findAccNumCheck();// check them
                }
                if (amount.getText().length() != 0) {
                    amountCheck();
                }
            }
        });
        /* action listener: when Print all account tab is selected */
        printAllAccTab.setOnSelectionChanged(e -> {
            /* print all accounts */
            if (printAllAccTab.isSelected()) {
                ArrayList<String> allAccInfo = new ArrayList<>();
                if (Bank.getAccounts().size() == 0) {// if database is empty
                    allAccInfo.add("No account to display");
                } else {
                    for (BankAccount account : Bank.getAccounts()) {// add all account info into allAccInfo String arrayList
                        allAccInfo.add(account.toString());
                    }
                    allAccInfo.add("Data written to bankoutput.txt");
                    allAccInfo.add("Press i to also write to import file");
                }
                /* display String arrayList */
                ObservableList<String> items = FXCollections.observableArrayList();
                items.addAll(allAccInfo);
                printAllAcc.setItems(items);
                Bank.printAccountDetails(false);// write to output file
            }
        });
        /* check if import is disabled when switching to import tab */
        importTab.setOnSelectionChanged(e -> {
            if (importTab.isSelected()) {
                /* hide buttons and expand log list view */
                if (Bank.isImportDisabled()) {
                    AnchorPane.setTopAnchor(importLog, 10.0);
                    importOneEntry.setDisable(true);
                    importOneEntry.setVisible(false);
                    importAllData.setDisable(true);
                    importAllData.setVisible(false);
                }
            }
        });
    }
}


/*
 * Name: Van Nam Doan
 * Student ID: 040943291
 * Course & Section: CST8132 312
 * Assignment: Lab 7
 * Date: Mar 19, 2019
 */

import java.util.Scanner;

/**
 * the purpose of this class is to display the main menu
 *
 * @author Van Nam Doan
 * @version 1.1
 * @since 1.8
 */
public class Assign1 {
    /**
     * Scanner object
     */
    private static Scanner input;
    /**
     * object of class Bank
     */
    private static Bank bank;

    /**
     * create new bank object and call for main menu
     *
     * @param args command line for main method
     */
    public static void main(String... args) {
        bank = new Bank();
        while (mainMenu()) ;
    }

    /**
     * contains main menu: its logic and output
     */
    private static boolean mainMenu() {
        boolean hasImport = false;// enable option r for switch statements
        if (!Bank.importDisabled) {
            Bank.openInputFile();// read import file every time main menu runs
            Bank.readRecords();
        }
        if (!Bank.importDisabled) {
            if (Bank.temp.size() > 0) {// if there is data to import
                if (!(Bank.counter < Bank.temp.size())) {// check if data has already been imported
                    Bank.temp.clear();// remove all import data for this run
                }
            }
            if (Bank.temp.size() > 0) {// if there is still data to import after first check
                System.out.println("IMPORT DATA FOUND");
                System.out.println("r: Import one data entry");
                System.out.println("R: Import all data");
                hasImport = true;
            }
        }
        System.out.println("a: Add new account");
        System.out.println("u: Update an account");
        System.out.println("d: Display an account");
        System.out.println("p: Print all accounts");
        System.out.println("m: Run monthly update");
        System.out.println("q: Quit");
        System.out.println("Enter your option (Enter \"\\m\" anytime to return to this menu): ");// added message to let user know the added capability
        input = new Scanner(System.in);
        String choice = input.nextLine();
        if (choice.equals("\\m")) {
            System.out.println("Already in main menu");
            return true;
        }
        if (hasImport) {
            switch (choice) {
                case "r":
                    System.out.println("_____________________________________________________________________\n\n");
                    System.out.println("Importing one data entry\n");
                    bank.addAccount();// import account
                    System.out.println("\nImport successful\n");
                    System.out.println("_____________________________________________________________________\n");
                    return true;
                case "R":
                    System.out.println("_____________________________________________________________________\n\n");
                    System.out.println("Importing all data\n\n");
                    while (Bank.counter < Bank.temp.size()) {
                        bank.addAccount();// import account
                    }
                    System.out.println("\nImport successful\n\n");
                    System.out.println("_____________________________________________________________________\n");
                    return true;
                default:
                    Bank.temp.clear();// remove all import data for this run if user chooses not to import
            }
        }
        switch (choice.toLowerCase()) {
            case "a":
                if (!bank.addAccount()) {// add new account
                    System.err.println("Account not added");
                }
                break;
            case "u":
                String messageUpdate = bank.updateAccount();
                if (!messageUpdate.isEmpty())// prevent println when message is empty
                    System.out.println(messageUpdate);
                break;
            case "d":
                String messageDisplay = bank.displayAccount();// prompt user to input account number and display corresponding account details
                if (!messageDisplay.isEmpty())// prevent println when message is empty
                    System.out.println(messageDisplay);
                break;
            case "p":
                bank.printAccountDetails();// print details of all accounts with header
                break;
            case "m":
                bank.monthlyUpdate();// update all account monthly
                break;
            case "q":
                // exit main menu loop
                System.out.println("Exiting... ");// message to let user know program has ended
                return false;
            default:
                System.out.println("Invalid input. Please re-enter your option: ");
                return true;
        }
        return true;
    }

    /**
     * input filter that doesn't allow user to input anything with space character except for names and denying import option. Prompt user to re-input
     * also let user returns to main menu from anywhere with \m
     *
     * @param reEnter to display customized error message
     * @return filtered input with no space
     */
    static String spaceCheck(String reEnter) {
        String userInput = "";
        if (userInput.isEmpty()) {
            input = new Scanner(System.in);
            boolean withoutSpace = false;
            if (!reEnter.equals("first name") && !reEnter.equals("last name") && !reEnter.equals("")) {//when input is not name-related, run space-filter
                while (!withoutSpace) {
                    userInput = input.nextLine();
                    withoutSpace = true;
                    for (char character : userInput.toCharArray()) {
                        if (character == ' ') {
                            System.out.println("Space character is not allowed. Please re-enter " + reEnter + ": ");
                            withoutSpace = false;
                            break;
                        }
                    }
                }
            } else {
                userInput = input.nextLine();
            }
            if (userInput.equals("\\m")) {// when input is \m, return to main menu
                System.out.println("Returning to main menu");
            }
        }
        return userInput;
    }
}

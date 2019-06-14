/*
 * Name: Van Nam Doan
 * Student ID: 040943291
 * Course & Section: CST8132 312
 * Assignment: Lab 5
 * Date: Feb 26, 2019
 */

import java.util.Scanner;

/**
 * the purpose of this class is to display the main menu
 *
 * @author Van Nam Doan
 * @version 1.0
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
        mainMenu();
    }

    /**
     * contains main menu: its logic and output
     */
    public static void mainMenu() {
        boolean quit = false;// loop exit controller
        while (!quit) {
            System.out.println("a: Add new account");
            System.out.println("u: Update an account");
            System.out.println("d: Display an account");
            System.out.println("p: Print all accounts");
            System.out.println("m: Run monthly update");
            System.out.println("q: Quit");
            System.out.println("Enter your option (Enter \"\\m\" anytime to return to this menu): ");// added message to let user know the added capability
            String choice = Assign1.spaceCheck("option");
            if (choice.equals("\\m")) {
                System.out.println("Already in main menu");
                mainMenu();
                return;
            }
            switch (choice) {
                case "a":
                    bank.addAccount();// add new account
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
                    quit = true;// exit main menu loop
                    System.out.println("Exiting... ");// message to let user know program has ended
                    break;
                default:
                    System.out.println("Invalid input. Please re-enter your option: ");
                    break;
            }
        }
    }

    /**
     * input filter that doesn't allow user to input anything with space character except for names
     * also let user returns to main menu from anywhere with \m
     *
     * @param reEnter to display customized error message
     * @return filtered input with no space
     */
    public static String spaceCheck(String reEnter) {
        input = new Scanner(System.in);
        String userInput = "";
        boolean withoutSpace = false;
        if (!reEnter.equals("first name") && !reEnter.equals("last name")) {//when input is not name-related, run space-filter
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
        return userInput;
    }
}

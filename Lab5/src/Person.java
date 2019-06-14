/*
 * Name: Van Nam Doan
 * Student ID: 040943291
 * Course & Section: CST8132 312
 * Assignment: Lab 5
 * Date: Feb 26, 2019
 */

/**
 * The purpose of this class is to store and give info related to account holder
 *
 * @author Van Nam Doan
 * @version 1.0
 * @since 1.8
 */
public class Person {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;

    /**
     * assign a Person object with its parameters
     *
     * @param firstName    account holder's first name
     * @param lastName     account holder's last name
     * @param phoneNumber  account holder's phone number
     * @param emailAddress account holder's email address
     */
    Person(String firstName, String lastName, String phoneNumber, String emailAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    /**
     * get account holder's first name
     *
     * @return account holder's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * get account holder's last name
     *
     * @return account holder's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * get account holder's phone number
     *
     * @return account holder's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * get account holder's email address
     *
     * @return account holder's email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }
}

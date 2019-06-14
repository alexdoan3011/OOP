/*
 * Name: Van Nam Doan
 * Student ID: 040943291
 * Course & Section: CST8132 312
 * Assignment: Lab 9
 * Date: Apr 9, 2019
 */

/**
 * this class contains error checking helper methods, for filtering user input
 *
 * @author Van Nam Doan
 * @version 1.2
 * @since 1.8
 */
class InputFilter {

    /**
     * check if String entered is a valid account number
     *
     * @param accNum String to filter
     * @return true if valid
     */
    static boolean validAccNum(String accNum) {
        long accNumTest;
        try {
            accNumTest = Long.parseLong(accNum);
        } catch (Exception e) {
            return false;
        }
        return (accNumTest > 0); // does not accept account number smaller than or equal zero
    }

    /**
     * check if String entered is a valid phone number
     *
     * @param phoneNum String to filter
     * @return true if valid
     */
    static boolean validPhoneNum(String phoneNum) {
        if (phoneNum.length() == 0) {
            return false;
        }
        for (char phoneNumChar : phoneNum.toCharArray()) {// phone number needs to be all digits
            if (!Character.isDigit(phoneNumChar)) {
                return false;
            }
        }
        return true;
    }

    /**
     * check if the String entered can be parsed into a double bigger or equal to zero
     *
     * @param balance String to filter
     * @return true if valid
     */
    static boolean validBalance(String balance) {
        for (char i : balance.toCharArray()) {// doesn't approve character 'd' and character 'f'
            if (!Character.isDigit(i) && i != '.') {
                return false;
            }
        }
        double balanceTest;
        try {
            balanceTest = Double.parseDouble(balance);
        } catch (Exception e) {
            return false;
        }
        return !(balanceTest < 0);
    }

    /**
     * check if the String entered is a valid email address in the form of x@x.x, with x not empty
     *
     * @param emailAddress String to filter
     * @return true if valid
     */
    static boolean validEmailAddress(String emailAddress) {
        boolean at = false;
        boolean dot = false;
        int pos = 0;
        for (int j = 1; j < emailAddress.length(); j++) {// checking if email entered with @ in front of .
            if (emailAddress.charAt(j) == '@') {// check for @
                at = true;
                pos = j;
                break;
            }
        }
        for (int j = (pos + 2); j < emailAddress.length() - 1; j++) {// check for . from position of @ plus 2 to before the last character
            if (at && emailAddress.charAt(j) == '.') {
                dot = true;
                break;
            }
        }
        return (at && dot);
    }
}

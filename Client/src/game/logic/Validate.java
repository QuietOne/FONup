/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.logic;

/**
 *
 * @author tihomir
 */
public class Validate {
        /**
     * Validate of email.
     *
     * @param email email that is going to be validated
     * @return true if it from fon.bg.ac.rs, false otherwise
     */
    public boolean email(String email) {
        return email.endsWith("@fon.bg.ac.rs");
    }

    /**
     * Validate of first name.
     *
     * @param firstName
     * @return false if firstName is empty or some of characters are not letters
     */
    public boolean firstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            return false;
        }
        if ((int) firstName.charAt(0) < 65 || (int) firstName.charAt(0) > 90) {
            return false;
        }
        for (int i = 1; i < firstName.length(); i++) {
            if ((int) firstName.charAt(i) < 97 || (int) firstName.charAt(i) > 122) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validate of last name.
     *
     * @param lastName
     * @return false if lastName is empty or some of characters are not letters
     */
    public boolean lastName(String lastName) {
        return firstName(lastName);
    }

    /**
     * Validate of password
     *
     * @param password
     * @return false if password contains less than 8 characters
     */
    public boolean password(String password) {
        if (password == null) {
            return false;
        }
        return password.length() >= 8;
    }

    /**
     * Validate if requirements for changing password are met. <br>
     * <ol>
     * <li>Previous password must be equal to one that is in client.</li>
     * <li>New password must be at least 8 characters long.</li>
     * <li>New passwords must be equal.</li>
     * </ol>
     *
     * @param oldPassword the previous password
     * @param newPassword1 the first new password
     * @param newPassword2 the second new password
     * @return true if requirements are met
     */
    public boolean changePassword(String oldPassword, String newPassword1, String newPassword2) throws Exception {
        if (!Controller.getInstance().getActiveClient().getPassword().equals(oldPassword)) {
            throw new Exception("Sifra se ne poklapa sa prethodnom");
        }
        if (!newPassword1.equals(newPassword2)) {
            throw new Exception("Sifre nisu iste");
        }
        return password(newPassword2);
    }
}

package server.logic;

import database.DataBaseUtils;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Validate of client for input processing.
 *
 * @author Tihomir Radosavljević
 * @version 1.0
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
     * Validate of log in input.
     *
     * @param username that is in log in form
     * @param password that is in log in form
     * @return true if there is client with this username and password
     */
    public boolean logIn(String username, String password) {
        GameDBLogic bLogic = new GameDBLogic();
        try {
            return bLogic.getClient(username, password) != null;
        } catch (Exception ex) {
            Logger.getLogger(Validate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
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
     * Validate of username.
     *
     * @param username
     * @return false if username is already taken
     */
    public boolean username(String username) {
        GameDBLogic gdbl = new GameDBLogic();
        boolean taken = false;
        try {
            taken = !gdbl.isUsernameTaken(username);
        } catch (Exception ex) {
            Logger.getLogger(Validate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return taken;
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
        /*if (!Controller.getInstance().getActiveClient().getPassword().equals(oldPassword)) {
         throw new Exception("Sifra se ne poklapa sa prethodnom");
         }*/
        if (!newPassword1.equals(newPassword2)) {
            throw new Exception("Sifre nisu iste");
        }
        return password(newPassword2);
    }

    /**
     * Validate name of the database management system.
     *
     * @param dbms
     * @return true if name is not null and not taken, false otherwise
     */
    public boolean dataBaseManagementSystem(String dbms) {
        return !dbms.equals("")
                && !DataBaseUtils.getInstance().getDBManagementSystems().contains(dbms);
    }
}

package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class for client representation.
 *
 * @author Tihomir Radosavljević
 * @version 1.0
 */
public class Client implements Serializable, GenericDomainObject {

    /**
     * Id of client in base.
     */
    private int id;
    /**
     * First name of client.
     */
    private String firstName;
    /**
     * Last name of client.
     */
    private String lastName;
    /**
     * Username of client.
     */
    private String username;
    /**
     * Password of client.
     */
    private String password;
    /**
     * Email address of client.
     */
    private String email;
    /**
     * Indicator if this client has been forbidden to use this application.
     */
    private boolean forbidden;
    /**
     * Indicator if this client has administrator's privileges.
     */
    private boolean administrator;

    public Client() {
        forbidden = false;
        administrator = false;
    }

    public Client(int id) {
        this.id = id;
        forbidden = false;
        administrator = false;
    }

    /**
     * For checking login only.
     *
     * @param username
     * @param password
     */
    public Client(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * For registering purposes.
     *
     * @param firstName
     * @param lastName
     * @param username
     * @param password
     * @param email
     */
    public Client(String firstName, String lastName, String username, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        forbidden = false;
        administrator = false;
    }

    /**
     * Get the value of administrator
     *
     * @return the value of administrator
     */
    public boolean isAdministrator() {
        return administrator;
    }

    /**
     * Set the value of administrator
     *
     * @param administrator new value of administrator
     */
    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    /**
     * Get the value of forbidden
     *
     * @return the value of forbidden
     */
    public boolean isForbidden() {
        return forbidden;
    }

    /**
     * Set the value of forbidden
     *
     * @param forbidden new value of forbidden
     */
    public void setForbidden(boolean forbidden) {
        this.forbidden = forbidden;
    }

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the value of email
     *
     * @return the value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the value of email
     *
     * @param email new value of email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the value of password
     *
     * @return the value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the value of password
     *
     * @param password new value of password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the value of username
     *
     * @return the value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the value of username
     *
     * @param username new value of username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the value of lastName
     *
     * @return the value of lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the value of lastName
     *
     * @param lastName new value of lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the value of firstName
     *
     * @return the value of firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the value of firstName
     *
     * @param firstName new value of firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.username);
        hash = 89 * hash + Objects.hashCode(this.email);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Client other = (Client) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public String getTableName() {
        return "client";
    }

    @Override
    public List<GenericDomainObject> getAll(ResultSet rs) throws Exception {
        List<GenericDomainObject> clients = new ArrayList<>();
        while (rs.next()) {
            Client client = new Client();
            client.setId(rs.getInt("idClient"));
            client.setFirstName(rs.getString("name"));
            client.setLastName(rs.getString("coname"));
            client.setUsername(rs.getString("username"));
            client.setPassword(rs.getString("password"));
            client.setEmail(rs.getString("email"));
            client.setForbidden(rs.getBoolean("forbiden"));
            client.setAdministrator(rs.getBoolean("admin"));
            clients.add(client);
        }
        return clients;
    }

    @Override
    public String getInsertValues() {
        return id + ",'" + firstName + "','" + lastName + "','" + username + "','" + password + "','" + email + "'," + forbidden + "," + administrator;
    }
}

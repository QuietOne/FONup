package server.logic;

import domain.Client;
import server.system.operation.client.AddClient;

/**
 * Class for registering client.
 *
 * @author Tihomir Radosavljević
 * @version 1.0
 */
public class RegisterClient {

    /**
     * Preparation for registering client.
     *
     * @param client that is supposed to be added into data base
     * @throws Exception why preparation for register failed
     */
    public void prepareRegister(Client client) throws Exception {
        prepareRegister(client.getFirstName(), client.getLastName(),
                client.getUsername(), client.getPassword(), client.getEmail());
    }

    /**
     * Preparation for registering client.
     *
     * @param firstName first name of client that is supposed to be added into
     * data base
     * @param lastName last name of client that is supposed to be added into
     * data base
     * @param username client's username that is supposed to be added into data
     * base
     * @param password client's password that is supposed to be added into data
     * base
     * @param email client's email that is supposed to be added into data base
     * @throws Exception why preparation for register failed
     */
    public void prepareRegister(String firstName, String lastName,
            String username, String password, String email) throws Exception {
        Validate validate = new Validate();
        if (!validate.firstName(firstName)) {
            throw new Exception("Ime nije dobro unešeno");
        }
        if (!validate.lastName(lastName)) {
            throw new Exception("Prezime nije dobro unešeno");
        }
        if (!validate.password(password)) {
            throw new Exception("Korisnička lozinka nije dobro unešena");
        }
        if (!validate.username(username)) {
            throw new Exception("Korisničko ime nije dobro unešeno");
        }
        if (!validate.email(email)) {
            throw new Exception("Email nije dobro unešen");
        }
    }

    /**
     * Add Client to data base.
     *
     * @param client
     * @throws Exception why preparation for register failed
     */
    public void register(Client client) throws Exception {
        new AddClient().execute(client);
    }
}

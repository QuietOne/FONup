package domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Class for representation of results made by client.
 *
 * @author Jelena Tabaš
 * @author Tihomir Radosavljević
 * @version 2.0
 */
public class Result implements Serializable {

    /**
     * Client made this result.
     */
    private Client client;

    private Test test;
    /**
     * Date when this result has been made.
     */
    private Date date;
    /**
     * Value of result.
     */
    private int value;

    public Result() {
        value = 0;
    }

    public Result(Client client) {
        this.client = client;
        value = 0;
    }

    public Result(Client client, Test test, Date date, int value) {
        this.client = client;
        this.test = test;
        this.date = date;
        this.value = value;
    }

    /**
     * Get the value of value
     *
     * @return the value of value
     */
    public int getValue() {
        return value;
    }

    /**
     * Set the value of value
     *
     * @param value new value of value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Get the value of date
     *
     * @return the value of date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Set the value of date
     *
     * @param date new value of date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Get the value of client
     *
     * @return the value of client
     */
    public Client getClient() {
        return client;
    }

    /**
     * Set the value of client
     *
     * @param client new value of client
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Get the value of test
     *
     * @return the value of test
     */
    public Test getTest() {
        return test;
    }

    /**
     * Set the value of test
     *
     * @param test new value of test
     */
    public void setTest(Test test) {
        this.test = test;
    }

    /**
     * Add value to the current result value.
     *
     * @param value that needs to be added
     */
    public void addValue(int value) {
        this.value += value;
    }

    @Override
    public String toString() {
        return value+"";
    }

}

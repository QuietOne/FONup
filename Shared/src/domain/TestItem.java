package domain;

import java.io.Serializable;

/**
 * Class for representation of item on test.
 *
 * @author Jelena Tabaš
 * @author Tihomir Radosavljević
 * @version 2.0
 */
public class TestItem implements Serializable{

    /**
     * Id of test item in base.
     */
    private int id;
    /**
     * Test to which this item belongs.
     */
    private Test test;

    /**
     * Question which this item has.
     */
    private Question question;

    public TestItem() {
    }

    public TestItem(int id) {
        this.id = id;
    }

    public TestItem(int id, Test test) {
        this.id = id;
        this.test = test;
    }

    /**
     * Get the value of question
     *
     * @return the value of question
     */
    public Question getQuestion() {
        return question;
    }

    /**
     * Set the value of question
     *
     * @param question new value of question
     */
    public void setQuestion(Question question) {
        this.question = question;
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
}

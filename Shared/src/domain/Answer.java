package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

/**
 * Class for representation of answer in game.
 *
 * @author Jelena Tabaš
 * @version 1.0
 */
public class Answer implements Serializable, GenericDomainObject{

    /**
     * Id of answer in base.
     */
    private int id;
    /**
     * Question to which this answer reference to.
     */
    private Question question;
    /**
     * Text representation of answer.
     */
    private String text;
    /**
     * Indicator if this answer is correct answer to the question.
     */
    private boolean correct;

    public Answer() {
        correct = false;
    }

    public Answer(int id) {
        this.id = id;
        correct = false;
    }

    public Answer(int id, Question question) {
        this.id = id;
        this.question = question;
        correct = false;
    }

    /**
     * Get the value of correct
     *
     * @return the value of correct
     */
    public boolean isCorrect() {
        return correct;
    }

    /**
     * Set the value of correct
     *
     * @param correct new value of correct
     */
    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    /**
     * Get the value of text
     *
     * @return the value of text
     */
    public String getText() {
        return text;
    }

    /**
     * Set the value of text
     *
     * @param text new value of text
     */
    public void setText(String text) {
        this.text = text;
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

    @Override
    public String getTableName() {
        return "answer";
    }

    @Override
    public List<GenericDomainObject> getAll(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getInsertValues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

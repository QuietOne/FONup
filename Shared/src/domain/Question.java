package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for representation of questions.
 *
 * @author Jelena Tabaš
 * @author Tihomir Radosavljević
 * @author Lazar Ristić
 * @version 2.0
 */
public class Question implements Serializable, GenericDomainObject {

    /**
     * Id of question in base.
     */
    private int id;
    /**
     * Text representation of question.
     */
    private String text;
    /**
     * Indicator if this question has been approved by administrators.
     */
    private boolean approved;
    /**
     * Indicator if this question has been deleted by administrators.
     */
    private boolean deleted;
    /**
     * Category of question.
     */
    private Category category;
    /**
     * List of answers.
     */
    private List<Answer> answersList;

    public Question() {
        approved = false;
        deleted = false;
        answersList = new ArrayList<>();
    }

    public Question(int id) {
        this.id = id;
        approved = false;
        deleted = false;
        answersList = new ArrayList<>();
    }

    /**
     * Get the value of category
     *
     * @return the value of category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Set the value of category
     *
     * @param category new value of category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Get the value of deleted
     *
     * @return the value of deleted
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Set the value of deleted
     *
     * @param deleted new value of deleted
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Get the value of approved
     *
     * @return the value of approved
     */
    public boolean isApproved() {
        return approved;
    }

    /**
     * Set the value of approved
     *
     * @param approved new value of approved
     */
    public void setApproved(boolean approved) {
        this.approved = approved;
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
     * Get the value of answersList
     *
     * @return
     */
    public List<Answer> getAnswersList() {
        return answersList;
    }

    /**
     * Set the value of setAnswersList
     *
     * @param answersList
     */
    public void setAnswersList(List<Answer> answersList) {
        this.answersList = answersList;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.id;
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
        final Question other = (Question) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String getTableName() {
        return "question";
    }

    @Override
    public List<GenericDomainObject> getAll(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getInsertValues() {
        return id + ",'" + text + "'," + approved + "," + deleted + "," + category.getId();
    }
}

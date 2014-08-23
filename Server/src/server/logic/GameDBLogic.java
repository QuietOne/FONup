package server.logic;

import database.DataBaseBroker;
import domain.Answer;
import domain.Category;
import domain.Client;
import domain.Question;
import domain.Result;
import domain.Test;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for all kind of manipulation of data.
 *
 * @author Tihomir Radosavljević
 * @version 0.3
 */
public class GameDBLogic {

    /**
     * Method for changing client data. It changes everything except id.
     *
     * @param client
     * @throws Exception
     */
    public void changeClientData(Client client) throws Exception {
        DataBaseBroker dbb = new DataBaseBroker();
        dbb.loadDriver();
        dbb.establishConnection();
        dbb.overwriteClient(client);
        dbb.commit();
        dbb.closeConnection();
    }

    /**
     * Method for finding category if name is known.
     *
     * @param categoryName name of the category
     * @return the searched category
     * @throws Exception
     */
    public Category findCategory(String categoryName) throws Exception {
        DataBaseBroker dbb = new DataBaseBroker();
        dbb.loadDriver();
        dbb.establishConnection();
        Category category = dbb.findCategory(categoryName);
        dbb.commit();
        dbb.closeConnection();
        return category;
    }

    /**
     * Method for finding question if text is known.
     *
     * @param text name of the question
     * @return the searched question
     * @throws Exception
     */
    public Question findQuestion(String text) throws Exception {
        DataBaseBroker dbb = new DataBaseBroker();
        dbb.loadDriver();
        dbb.establishConnection();
        Question question = dbb.findQuestion(text);
        dbb.commit();
        dbb.closeConnection();
        return question;
    }

    /**
     * Method for getting Client.
     *
     * @param username of the client
     * @param password of the client
     * @return Client if username and password are correct and approved, null
     * otherwise
     * @throws Exception
     */
    public Client getClient(String username, String password) throws Exception {
        DataBaseBroker dbb = new DataBaseBroker();
        dbb.loadDriver();
        dbb.establishConnection();
        Client client = dbb.getClient(username, password);
        if (client.isForbidden()) {
            return null;
        }
        dbb.commit();
        dbb.closeConnection();
        return client;
    }

    /**
     * Method for getting Client.
     *
     * @param username
     * @return
     * @throws Exception
     */
    public Client getClient(String username) throws Exception {
        DataBaseBroker dbb = new DataBaseBroker();
        dbb.loadDriver();
        dbb.establishConnection();
        Client client = dbb.getClient(username);
        dbb.commit();
        dbb.closeConnection();
        return client;
    }

    /**
     * Method for finding out if username already exist in the database.
     *
     * @param username
     * @return true if username exist, false otherwise
     * @throws Exception
     */
    public boolean isUsernameTaken(String username) throws Exception {
        DataBaseBroker dbb = new DataBaseBroker();
        dbb.loadDriver();
        dbb.establishConnection();
        boolean taken = dbb.isUsernameTaken(username);
        dbb.commit();
        dbb.closeConnection();
        return taken;
    }

    /**
     * Method for loading category tree.
     *
     * @return
     */
    public Category loadCategoryTree() {
        Category categoryTree = null;
        try {
            DataBaseBroker dbb = new DataBaseBroker();
            dbb.loadDriver();
            dbb.establishConnection();
            categoryTree = dbb.loadCategoryTree();
            dbb.commit();
            dbb.closeConnection();
        } catch (Exception ex) {
            Logger.getLogger(GameDBLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categoryTree;
    }

    /**
     * Method for getting results for all clients. It only return top 100.
     *
     * @return
     */
    public List<Result> loadResults() {
        List<Result> results = null;
        try {
            DataBaseBroker dbb = new DataBaseBroker();
            dbb.loadDriver();
            dbb.establishConnection();
            results = dbb.loadResults();
            dbb.commit();
            dbb.closeConnection();
        } catch (Exception ex) {
            Logger.getLogger(GameDBLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }

    /**
     * Method for loading results for the specific client.
     *
     * @param client
     * @return
     */
    public List<Result> loadResults(Client client) {
        List<Result> results = null;
        try {
            DataBaseBroker dbb = new DataBaseBroker();
            dbb.loadDriver();
            dbb.establishConnection();
            results = dbb.loadResults(client);
            dbb.commit();
            dbb.closeConnection();
        } catch (Exception ex) {
            Logger.getLogger(GameDBLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }

    /**
     * Method that returns 10 clients that username begins with this string.
     *
     * @param username string with which we match username in the database
     * @return list of Clients
     */
    public List<Client> autocompleteClient(String username) {
        List<Client> clients = null;
        try {
            DataBaseBroker dbb = new DataBaseBroker();
            dbb.loadDriver();
            dbb.establishConnection();
            clients = dbb.autocompleteClient(username);
            dbb.commit();
            dbb.closeConnection();
        } catch (Exception ex) {
            Logger.getLogger(GameDBLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clients;
    }

    /**
     * Method that returns 10 categories that name begins with this string.
     *
     * @param name string with which we match name in the database
     * @return
     */
    public List<Category> autocompleteCategory(String name) {
        List<Category> categories = null;
        try {
            DataBaseBroker dbb = new DataBaseBroker();
            dbb.loadDriver();
            dbb.establishConnection();
            categories = dbb.autocompleteCategory(name);
            dbb.commit();
            dbb.closeConnection();
        } catch (Exception ex) {
            Logger.getLogger(GameDBLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }

    /**
     * Method that returns 10 questions that text begins with this string.
     *
     * @param text
     * @return
     */
    public List<Question> autocompleteQuestion(String text) {
        List<Question> questions = null;
        try {
            DataBaseBroker dbb = new DataBaseBroker();
            dbb.loadDriver();
            dbb.establishConnection();
            questions = dbb.autocompleteQuestion(text);
            dbb.commit();
            dbb.closeConnection();
        } catch (Exception ex) {
            Logger.getLogger(GameDBLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return questions;
    }

    /**
     * Method for flagging the client as forbidden (deleted in our case).
     *
     * @param client
     * @throws Exception
     */
    public void deleteClient(Client client) throws Exception {
        DataBaseBroker dbb = new DataBaseBroker();
        dbb.loadDriver();
        dbb.establishConnection();
        client.setForbidden(true);
        dbb.overwriteClient(client);
        dbb.commit();
        dbb.closeConnection();
    }

    /**
     * Method for flagging the client as not forbidden.
     *
     * @param client
     * @throws Exception
     */
    public void approveClient(Client client) throws Exception {
        DataBaseBroker dbb = new DataBaseBroker();
        dbb.loadDriver();
        dbb.establishConnection();
        client.setForbidden(false);
        dbb.overwriteClient(client);
        dbb.commit();
        dbb.closeConnection();
    }

    /**
     * Method for flagging the category as not approved (deleted in our case).
     *
     * @param category
     * @throws Exception
     */
    public void deleteCategory(Category category) throws Exception {
        DataBaseBroker dbb = new DataBaseBroker();
        dbb.loadDriver();
        dbb.establishConnection();
        category.setApproved(false);
        dbb.overwriteCategory(category);
        dbb.commit();
        dbb.closeConnection();
    }

    /**
     * Method for flagging the category as approved.
     *
     * @param category
     * @throws Exception
     */
    public void approveCategory(Category category) throws Exception {
        DataBaseBroker dbb = new DataBaseBroker();
        dbb.loadDriver();
        dbb.establishConnection();
        category.setApproved(true);
        dbb.overwriteCategory(category);
        dbb.commit();
        dbb.closeConnection();
    }

    /**
     * Change name of category.
     *
     * @param category
     * @param name
     * @throws Exception
     */
    public void changeCategoryName(Category category, String name) throws Exception {
        DataBaseBroker dbb = new DataBaseBroker();
        dbb.loadDriver();
        dbb.establishConnection();
        category.setName(name);
        dbb.overwriteCategory(category);
        dbb.commit();
        dbb.closeConnection();
    }

    /**
     * Method for flagging the question as not approved (deleted in our case).
     *
     * @param question
     * @throws Exception
     */
    public void deleteQuestion(Question question) throws Exception {
        DataBaseBroker dbb = new DataBaseBroker();
        dbb.loadDriver();
        dbb.establishConnection();
        question.setDeleted(true);
        dbb.overwriteQuestion(question);
        dbb.commit();
        dbb.closeConnection();
    }

    /**
     * Method for flagging the question as approved.
     *
     * @param question
     * @throws Exception
     */
    public void approveQuestion(Question question) throws Exception {
        DataBaseBroker dbb = new DataBaseBroker();
        dbb.loadDriver();
        dbb.establishConnection();
        question.setApproved(true);
        dbb.overwriteQuestion(question);
        dbb.commit();
        dbb.closeConnection();
    }

    /**
     * Change text of question.
     *
     * @param question
     * @param text
     * @throws Exception
     */
    public void changeQuestionText(Question question, String text) throws Exception {
        DataBaseBroker dbb = new DataBaseBroker();
        dbb.loadDriver();
        dbb.establishConnection();
        question.setText(text);
        dbb.overwriteQuestion(question);
        dbb.commit();
        dbb.closeConnection();
    }
    
    public void changeQuestionText(Question question) throws Exception {
        DataBaseBroker dbb = new DataBaseBroker();
        dbb.loadDriver();
        dbb.establishConnection();
        dbb.overwriteQuestion(question);
        dbb.commit();
        dbb.closeConnection();
    }

    /**
     * Method for flagging clients as forbidden (deleted in our case).
     *
     * @param clients
     * @throws Exception
     */
    public void deleteClients(List<Client> clients) throws Exception {
        DataBaseBroker dbb = new DataBaseBroker();
        dbb.loadDriver();
        dbb.establishConnection();
        for (Client client : clients) {
            client.setForbidden(true);
        }
        dbb.overwriteClients(clients);
        dbb.commit();
        dbb.closeConnection();
    }

    /**
     * Method for getting answers for the specific question.
     *
     * @param question
     * @return
     * @throws Exception
     */
    public List<Answer> getAnswers(Question question) throws Exception {
        DataBaseBroker dbb = new DataBaseBroker();
        dbb.loadDriver();
        dbb.establishConnection();
        List<Answer> answers = dbb.getAnswers(question);
        dbb.commit();
        dbb.closeConnection();
        return answers;
    }

    /**
     * Method for saving the answers to the database.
     *
     * @param answer
     * @throws Exception
     */
    public void saveAnswer(Answer answer) throws Exception {
        DataBaseBroker dbb = new DataBaseBroker();
        dbb.loadDriver();
        dbb.establishConnection();
        dbb.saveAnswer(answer);
        dbb.commit();
        dbb.closeConnection();
    }

    /**
     * Method for saving the answers to the database.
     *
     * @param answers
     * @throws Exception
     */
    public void saveAnswers(List<Answer> answers) throws Exception {
        DataBaseBroker dbb = new DataBaseBroker();
        dbb.loadDriver();
        dbb.establishConnection();
        dbb.saveAnswers(answers);
        dbb.commit();
        dbb.closeConnection();
    }

    /**
     * Method for saving the test to the database.
     *
     * @param test
     * @throws Exception
     */
    public void saveTest(Test test) throws Exception {
        DataBaseBroker dbb = new DataBaseBroker();
        dbb.loadDriver();
        dbb.establishConnection();
        dbb.saveTest(test);
        dbb.commit();
        dbb.closeConnection();
    }

    /**
     * Method for getting questions of the specific category.
     *
     * @param category
     * @return
     */
    public List<Question> getQuestions(Category category) {
        List<Question> questions = null;
        try {
            DataBaseBroker dbb = new DataBaseBroker();
            dbb.loadDriver();
            dbb.establishConnection();
            questions = dbb.getQuestion(category);
            dbb.commit();
            dbb.closeConnection();
        } catch (Exception ex) {
            Logger.getLogger(GameDBLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return questions;
    }

    /**
     * Method for adding result to the database.
     *
     * @param result
     */
    public void addResult(Result result) {
        try {
            DataBaseBroker dbb = new DataBaseBroker();
            dbb.loadDriver();
            dbb.establishConnection();
            dbb.addResult(result);
            dbb.commit();
            dbb.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Question getQuestion(String text) {
        Question question = null;
        try {
            DataBaseBroker dbb = new DataBaseBroker();
            dbb.loadDriver();
            dbb.establishConnection();
            question = dbb.autocompleteQuestion(text).get(0);
            dbb.commit();
            dbb.closeConnection();
        } catch (Exception ex) {
            Logger.getLogger(GameDBLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return question;
    }
}

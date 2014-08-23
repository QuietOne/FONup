package database;

import domain.Answer;
import domain.Category;
import domain.Client;
import domain.GenericDomainObject;
import domain.Question;
import domain.Result;
import domain.Test;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Class for work with database.
 *
 * @author Tihomir Radosavljević
 * @author Jelena Tabaš
 * @version 1.0
 */
public class DataBaseBroker {

    /**
     * Connection for database.
     */
    private Connection connection;

    /**
     * Loading driver needed for connection with database.
     *
     * @throws ClassNotFoundException driver do not exist
     */
    public void loadDriver() throws ClassNotFoundException {
        try {
            Class.forName(DataBaseUtils.getInstance().getDriver());
        } catch (ClassNotFoundException ex) {
            System.err.println("Driver does not exist.");
            throw ex;
        }
    }

    /**
     * Method for establishing connection with database.
     *
     * @throws Exception URL do not exist
     */
    public void establishConnection() throws Exception {
        try {
            connection = DriverManager.getConnection(DataBaseUtils.getInstance().getURL(),
                    DataBaseUtils.getInstance().getUser(),
                    DataBaseUtils.getInstance().getPassword());
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            System.err.println("URL does not exist.");
            throw new Exception("Connection has not been established.");
        }
    }

    /**
     * Method for closing open connection.
     */
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.err.println("Connection closure failure " + ex.getMessage());
        }
    }

    /**
     * Method for committing new SQL query.
     *
     * @throws SQLException
     */
    public void commit() throws SQLException {
        connection.commit();
    }

    /**
     * Method for rollback new SQL query.
     *
     * @throws SQLException
     */
    public void rollback() throws SQLException {
        connection.rollback();
    }

    /**
     * Method for creating new connection from db.properties file.
     *
     * @throws Exception Driver do not exist
     */
    public void createConnection() throws Exception {
        try {
            Properties properties = new Properties();
            properties.setProperty("user", "");
            properties.setProperty("password", "");
            Driver d = (Driver) Class.forName(DataBaseUtils.getInstance().getDriver()).newInstance();
            d.connect(DataBaseUtils.getInstance().getURL(), properties);
        } catch (Exception ex) {
            System.err.println("Driver does not exist.");
            throw ex;
        }
    }

    public List<GenericDomainObject> getAll(GenericDomainObject domainObject) throws Exception {
        try {
            String sql = "SELECT * FROM " + domainObject.getTableName();
            System.out.println(sql);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            return domainObject.getAll(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Neuspesno ucitavanje objekata!");
        }
    }

    public void insert(GenericDomainObject domainObject) throws Exception {
        try {
            String sql = "INSERT INTO " + domainObject.getTableName() + " VALUES(" + domainObject.getInsertValues() + ")";
            System.out.println(sql);
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Neuspesno cuvanje objekata!");
        }
    }

    public Category findCategory(String catName) throws Exception {
        try {
            String query = "SELECT * FROM category WHERE name=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, catName);
            ResultSet rs = preparedStatement.executeQuery();
            Category category = null;
            if (rs.next()) {
                category = new Category();
                category.setId(rs.getInt("idCat"));
                category.setName(rs.getString("name"));
                category.setApproved(rs.getBoolean("approved"));
                category.setParentCategory(findCategory(rs.getInt("idCate")));
            }
            return category;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception();
        }
    }

    public Question findQuestion(String text) throws Exception {
        try {
            String query = "SELECT * FROM question WHERE text=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, text);
            ResultSet rs = preparedStatement.executeQuery();
            Question question = null;
            if (rs.next()) {
                question = new Question();
                question.setId(rs.getInt("idQuestion"));
                question.setText(rs.getString("text"));
                question.setApproved(rs.getBoolean("approved"));
                question.setDeleted(rs.getBoolean("deleted"));
                question.setCategory(findCategory(rs.getInt("idCat")));
            }
            return question;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception();
        }
    }

    private Category findCategory(int id) throws Exception {
        try {
            String query = "SELECT * FROM category WHERE idCat=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            Category category = null;
            if (rs.next()) {
                category = new Category();
                category.setId(rs.getInt("idCat"));
                category.setName(rs.getString("name"));
                category.setApproved(rs.getBoolean("approved"));
                int id1 = rs.getInt("idCate");
                if (id1 != 0) {
                    category.setParentCategory(findCategory(id1));
                } else {
                    category.setParentCategory(null);
                }

            }
            return category;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception();
        }
    }

    private Client findClient(int id) {
        Client client = null;
        try {
            String query = "SELECT * FROM client WHERE idClient=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                client = new Client();
                client.setId(rs.getInt("idClient"));
                client.setFirstName(rs.getString("name"));
                client.setLastName(rs.getString("coname"));
                client.setUsername(rs.getString("username"));
                client.setPassword(rs.getString("password"));
                client.setEmail(rs.getString("email"));
                client.setForbidden(rs.getBoolean("forbiden"));
                client.setAdministrator(rs.getBoolean("admin"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return client;
    }

    private Test findTest(int id) {
        Test test = null;
        try {
            String query = "SELECT * FROM test WHERE idClient=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                test = new Test();
                test.setId(rs.getInt("idTest"));
                test.setCategory(findCategory(rs.getInt("idCat")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return test;
    }

    public Client getClient(String username, String pass) throws Exception {
        try {
            String query = "SELECT * FROM client WHERE username=? AND password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, pass);
            ResultSet rs = preparedStatement.executeQuery();
            Client client = null;
            if (rs.next()) {
                client = new Client();
                client.setId(rs.getInt("idClient"));
                client.setFirstName(rs.getString("name"));
                client.setLastName(rs.getString("coname"));
                client.setUsername(rs.getString("username"));
                client.setPassword(rs.getString("password"));
                client.setEmail(rs.getString("email"));
                client.setForbidden(rs.getBoolean("forbiden"));
                client.setAdministrator(rs.getBoolean("admin"));
            }
            return client;
        } catch (Exception ex) {
            System.out.println("Client does not exist");
            ex.printStackTrace();
            throw new Exception();
        }
    }

    public boolean isUsernameTaken(String username) throws Exception {
        try {
            String query = "SELECT * FROM client WHERE username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return true;
            }
            return false;
        } catch (Exception ex) {
            System.out.println("Unexpected error occured");
            ex.printStackTrace();
            throw new Exception();
        }
    }

    public List<Client> autocompleteClient(String username) {
        List<Client> clientList = null;
        try {
            clientList = new ArrayList<>();
            String query = "SELECT * FROM client WHERE username LIKE ? LIMIT 10";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username + "%");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                if (rs.getBoolean("forbiden")) {
                    continue;
                }
                Client client = new Client();
                client.setId(rs.getInt("idClient"));
                client.setFirstName(rs.getString("name"));
                client.setLastName(rs.getString("coname"));
                client.setUsername(rs.getString("username"));
                client.setPassword(rs.getString("password"));
                client.setEmail(rs.getString("email"));
                client.setForbidden(rs.getBoolean("forbiden"));
                client.setAdministrator(rs.getBoolean("admin"));
                clientList.add(client);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return clientList;
    }

    public List<Category> autocompleteCategory(String name) {
        List<Category> categoryList = null;
        try {
            categoryList = new ArrayList<>();
            String query = "SELECT * FROM category WHERE name LIKE ? LIMIT 10";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name + "%");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                /*if (rs.getBoolean("approved")) {
                 continue;
                 }*/
                Category category = new Category();
                category.setId(rs.getInt("idCat"));
                category.setName(rs.getString("name"));
                category.setApproved(rs.getBoolean("approved"));
                categoryList.add(category);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return categoryList;
    }

    public List<Question> autocompleteQuestion(String text) {
        List<Question> questionList = null;
        try {
            questionList = new ArrayList<>();
            String query = "SELECT * FROM question WHERE text LIKE ? LIMIT 10";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, text + "%");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                if (rs.getBoolean("approved")) {
                    continue;
                }
                Question question = new Question();
                question.setId(rs.getInt("idQuestion"));
                question.setText(rs.getString("text"));
                question.setApproved(rs.getBoolean("approved"));
                question.setDeleted(rs.getBoolean("deleted"));

                questionList.add(question);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return questionList;

    }

    public void overwriteClient(Client client) {
        try {
            String query = "UPDATE client SET name = ?, coname=?, username=?, password=?, email=?, forbiden=?, admin=? WHERE idClient=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setString(3, client.getUsername());
            preparedStatement.setString(4, client.getPassword());
            preparedStatement.setString(5, client.getEmail());
            preparedStatement.setBoolean(6, client.isForbidden());
            preparedStatement.setBoolean(7, client.isAdministrator());
            preparedStatement.setInt(8, client.getId());

            preparedStatement.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Unexpected error occured");
            ex.printStackTrace();
        }
    }

    public void overwriteCategory(Category category) {
        try {
            String query = "UPDATE category SET name = ?, approved=?, idCate=? WHERE idCat=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, category.getName());
            preparedStatement.setBoolean(2, category.isApproved());
            preparedStatement.setInt(3, category.getParentCategory().getId());
            preparedStatement.setInt(4, category.getId());

            preparedStatement.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Unexpected error occured");
            ex.printStackTrace();
        }
    }

    public void overwriteQuestion(Question question) {
        try {
            String query = "UPDATE question SET text = ?, approved=?, deleted=?, idCat=? WHERE idQuestion=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, question.getText());
            preparedStatement.setBoolean(2, question.isApproved());
            preparedStatement.setBoolean(3, question.isDeleted());
            preparedStatement.setInt(4, question.getCategory().getId());
            preparedStatement.setInt(5, question.getId());

            preparedStatement.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Unexpected error occured");
            ex.printStackTrace();
        }
    }

    public void overwriteClients(List<Client> clients) {
        for (Client client : clients) {
            overwriteClient(client);
        }
    }

    public List<Answer> getAnswers(Question question) throws Exception {
        List<Answer> answerList = null;
        try {
            answerList = new ArrayList<>();
            String query = "SELECT * FROM answer WHERE idQuestion=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, question.getId());
            ResultSet rs = preparedStatement.executeQuery();
            Answer answer = null;
            while (rs.next()) {
                answer = new Answer();
                answer.setId(rs.getInt("idAnswer"));
                answer.setId(rs.getInt("idQuestion"));
                answer.setText(rs.getString("text"));
                answer.setCorrect(rs.getBoolean("correct"));
                answerList.add(answer);
            }

        } catch (Exception ex) {
            System.out.println("Client does not exist");
            ex.printStackTrace();
            throw new Exception();
        }
        return answerList;

    }

    public void saveAnswer(Answer answer) {
        try {
            String query = "INSERT INTO answer VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, answer.getId());
            preparedStatement.setInt(2, answer.getQuestion().getId());
            preparedStatement.setString(3, answer.getText());
            preparedStatement.setBoolean(4, answer.isCorrect());
            preparedStatement.executeUpdate();
            System.out.println("New answer has been added to base");
        } catch (SQLException ex) {
            System.out.println("Answer not added to the base");
        }
    }

    public void saveAnswers(List<Answer> answers) {
        for (Answer answer : answers) {
            saveAnswer(answer);
        }
    }

    public Client getClient(String username) {
        Client client = null;
        try {
            String query = "SELECT * FROM client WHERE username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                client = new Client();
                client.setId(rs.getInt("idClient"));
                client.setFirstName(rs.getString("name"));
                client.setLastName(rs.getString("coname"));
                client.setUsername(rs.getString("username"));
                client.setPassword(rs.getString("password"));
                client.setEmail(rs.getString("email"));
                client.setForbidden(rs.getBoolean("forbiden"));
                client.setAdministrator(rs.getBoolean("admin"));
            }
        } catch (Exception ex) {
            System.out.println("Client does not exist");
            ex.printStackTrace();
        }
        return client;

    }

    public Category loadCategoryTree() {
        Category category = new Category();
        try {
            String query = "SELECT * FROM category WHERE idCate IS NULL";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            List<Category> children = new ArrayList<>();
            while (rs.next()) {
                Category cat = new Category();
                cat.setId(rs.getInt("idCat"));
                cat.setName(rs.getString("name"));
                cat.setApproved(rs.getBoolean("approved"));
                attachChildren(cat);
                children.add(cat);
            }
            category.setSubCategories(children);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return category;
    }

    private void attachChildren(Category category) {
        try {
            String query = "SELECT * FROM category WHERE idCate=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, category.getId());
            ResultSet rs = ps.executeQuery();
            List<Category> children = new ArrayList<>();
            while (rs.next()) {
                Category cat = new Category();
                cat.setId(rs.getInt("idCat"));
                cat.setName(rs.getString("name"));
                cat.setApproved(rs.getBoolean("approved"));
                attachChildren(cat);
                children.add(cat);
            }
            category.setSubCategories(children);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Result> loadResults() {
        List<Result> resultList = null;
        try {
            resultList = new ArrayList<>();
            String query = "SELECT * FROM result ORDER BY value LIMIT 100";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            Result result = null;
            while (rs.next()) {
                result = new Result();
                result.setClient(findClient(rs.getInt("idClient")));
                result.setTest(findTest(rs.getInt("idTest")));
                result.setDate(rs.getDate("date"));
                result.setValue(rs.getInt("value"));
                resultList.add(result);
            }
        } catch (Exception ex) {
            System.out.println("Result does not exist");
            ex.printStackTrace();
        }
        return resultList;
    }

    public List<Result> loadResults(Client client) {
        List<Result> resultList = null;
        try {
            resultList = new ArrayList<>();
            String query = "SELECT * FROM result WHERE idClient=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, client.getId());
            ResultSet rs = preparedStatement.executeQuery();
            Result result = null;
            while (rs.next()) {
                result = new Result();
                result.setClient(findClient(rs.getInt("idClient")));
                result.setTest(findTest(rs.getInt("idTest")));
                result.setDate(rs.getDate("date"));
                result.setValue(rs.getInt("value"));
                resultList.add(result);
            }
        } catch (Exception ex) {
            System.out.println("Result does not exist");
            ex.printStackTrace();
        }
        return resultList;
    }

    /**
     * Method for saving test and all its items.
     *
     * @param test
     */
    public void saveTest(Test test) {
        try {
            String query = "INSERT INTO test VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, test.getId());
            preparedStatement.setInt(2, test.getCategory().getId());
            preparedStatement.executeUpdate();
            System.out.println("New test has been added to base");
        } catch (SQLException ex) {
            System.out.println("Test not added to the base");
        }
    }

    /**
     * Get questions for specific category.
     *
     * @param category
     * @return
     */
    public List<Question> getQuestion(Category category) {
        List<Question> questionList = null;
        try {
            questionList = new ArrayList<>();
            String query = "SELECT * FROM question WHERE idCat=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, category.getId());
            ResultSet rs = preparedStatement.executeQuery();
            Question question = null;
            while (rs.next()) {
                question = new Question();
                question.setId(rs.getInt("idQuestion"));
                question.setText(rs.getString("text"));
                question.setApproved(rs.getBoolean("approved"));
                question.setDeleted(rs.getBoolean("deleted"));
                question.setCategory(findCategory(rs.getInt("idCat")));
                questionList.add(question);
            }
        } catch (Exception ex) {
            System.out.println("Question does not exist");
            ex.printStackTrace();
        }
        return questionList;
    }

    /**
     * Method for saving result into database.
     *
     * @param result
     */
    public void addResult(Result result) {
        try {
            String query = "INSERT INTO result VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, result.getClient().getId());
            preparedStatement.setInt(2, result.getTest().getId());
            preparedStatement.setDate(3, (Date) result.getDate());
            preparedStatement.setInt(4, result.getValue());
            System.out.println("New result has been added to base");
        } catch (SQLException ex) {
            System.out.println("Result not added to the base");
        }

    }
}

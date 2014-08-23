package game.logic;

import domain.Category;
import domain.Client;
import domain.Question;
import domain.Result;
import game.protocol.GameProtocol;
import game.protocol.Status;
import game.protocol.objects.ClientRequest;
import game.protocol.objects.ServerResponse;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for representation of needed information for game.
 *
 * @author Tihomir Radosavljević
 * @author Jelena Tabas
 * @version 1.0
 */
public class Controller {

    private List<Category> approveCategories;
    private List<Client> deleteClients;
    private List<Question> approveQuestions;
    private List<Question> approvedQuestions;

    /**
     * Category three.
     */
    private Category categoryTree;
    /**
     * Currently active player.
     */
    private Client activeClient;
    /**
     * High scores relevant to client.
     */
    private HighScores highScores;

    private Question question;

    private Socket socket;

    private Controller() {
        approveCategories = new ArrayList<>();
        approveQuestions = new ArrayList<>();
        deleteClients = new ArrayList<>();
        approvedQuestions = new ArrayList<>();
    }

    public void loadApprovedQuestions(Category category) {
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setGameProtocol(GameProtocol.GET_QUESTIONS);
        clientRequest.setObject(category);
        try {
            approvedQuestions = (List<Question>) sendAndReceive(clientRequest);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Controller getInstance() {
        return ControllerHolder.INSTANCE;
    }

    public Category getCategoryTree() {
        return categoryTree;
    }

    public Client getActiveClient() {
        return activeClient;
    }

    public void setActiveClient(Client activeClient) {
        this.activeClient = activeClient;
    }

    public HighScores getHighScores() {
        return highScores;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void register(Client client) {
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setObject(client);
            clientRequest.setGameProtocol(GameProtocol.REGISTER);
            sendAndReceive(clientRequest);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Category> getApproveCategories() {
        return approveCategories;
    }

    public List<Client> getDeleteClients() {
        return deleteClients;
    }

    public List<Question> getApproveQuestions() {
        return approveQuestions;
    }

    public List<Question> getApprovedQuestions() {
        return approvedQuestions;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void deleteQuestion(Question question) {
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setGameProtocol(GameProtocol.DELETE_QUESTION);
        clientRequest.setObject(question);
        try {
            sendAndReceive(clientRequest);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static class ControllerHolder {

        private static final Controller INSTANCE = new Controller();
    }

    /**
     * Loading client from data base.
     *
     * @param username of targeted client.
     */
    public void loadClient(String username) {
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setGameProtocol(GameProtocol.LOGIN_CLIENT);
            clientRequest.setObject(username);
            activeClient = (Client) sendAndReceive(clientRequest);
            loadCategoryTree();
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Must be loaded Client before this operation is available.
     */
    public void loadHighScores() {
        try {
            if (activeClient == null) {
                throw new NullPointerException("You didn't load client.");
            }
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setObject(activeClient);
            clientRequest.setGameProtocol(GameProtocol.LOAD_HIGHSCORES);
            highScores = new HighScores((List<Result>) sendAndReceive(clientRequest));
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Loading top 100 highscores.
     *
     * @return
     */
    List<Result> loadTopHighScores() {
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setGameProtocol(GameProtocol.LOAD_TOP_HIGHSCORES);
            return ((List<Result>) sendAndReceive(clientRequest));
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    void addResult(Result result) {
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setGameProtocol(GameProtocol.ADD_RESULT);
            sendAndReceive(clientRequest);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method for loading category tree from data base.
     */
    private void loadCategoryTree() {
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setGameProtocol(GameProtocol.LOAD_CATEGORY_TREE);
            categoryTree = (Category) sendAndReceive(clientRequest);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method for changing client parameter.
     */
    public void changeClientInformation() throws Exception {
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setObject(activeClient);
        clientRequest.setGameProtocol(GameProtocol.CHANGE_CLIENT_INFO);
        sendAndReceive(clientRequest);
    }

    /**
     * Delete category from database.
     *
     * @param categoryName
     */
    public void deleteCategory(String categoryName) {
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setObject(categoryName);
            clientRequest.setGameProtocol(GameProtocol.DELETE_CATEGORY);
            sendAndReceive(clientRequest);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadCategoryTree();
    }

    /**
     * Change name of category.
     *
     * @param currentCategoryName
     * @param newCategoryName
     */
    public void changeCategory(String currentCategoryName, String newCategoryName) {
        try {
            ClientRequest clientRequest = new ClientRequest();
            List<String> list = new ArrayList<>();
            list.add(currentCategoryName);
            list.add(newCategoryName);
            clientRequest.setObject(list);
            clientRequest.setGameProtocol(GameProtocol.CHANGE_CATEGORY);
            sendAndReceive(clientRequest);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadCategoryTree();
    }

    /**
     * Add new category.
     *
     * @param categoryName
     * @param categoryParentName
     */
    public void addCategory(String categoryName, String categoryParentName) {
        try {
            ClientRequest clientRequest = new ClientRequest();
            List<String> list = new ArrayList<>();
            list.add(categoryName);
            list.add(categoryParentName);
            clientRequest.setObject(list);
            clientRequest.setGameProtocol(GameProtocol.ADD_CATEGORY);
            sendAndReceive(clientRequest);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadCategoryTree();
    }

    /**
     * Autocomplete for Client.
     *
     * @param username
     * @return
     */
    public List<Client> autocompleteClient(String username) {
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setObject(username);
            clientRequest.setGameProtocol(GameProtocol.AUTOCOMPLETE_CLIENT);
            return (List<Client>) sendAndReceive(clientRequest);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Delete client from the database.
     *
     * @param client
     */
    public void deleteClient(Client client) throws Exception {
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setObject(client);
        clientRequest.setGameProtocol(GameProtocol.DELETE_CLIENT);
        sendAndReceive(clientRequest);
    }

    /**
     * Delete clients from the database.
     *
     * @param clients
     */
    public void deleteClients(List<Client> clients) throws Exception {
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setObject(clients);
        clientRequest.setGameProtocol(GameProtocol.DELETE_CLIENTS);
        sendAndReceive(clientRequest);
    }

    /**
     * Approve category with this name.
     *
     * @param categoryName
     */
    public void approveCategory(String categoryName) {
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setObject(categoryName);
            clientRequest.setGameProtocol(GameProtocol.APPROVE_CATEGORY);
            sendAndReceive(clientRequest);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadCategoryTree();
    }

    /**
     * Approve client
     *
     * @param client
     */
    public void approveClient(Client client) {
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setObject(client);
            clientRequest.setGameProtocol(GameProtocol.APPROVE_CLIENT);
            sendAndReceive(clientRequest);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Approve question.
     *
     * @param question
     */
    public void approveQuestion(Question question) {
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setObject(question);
            clientRequest.setGameProtocol(GameProtocol.APPROVE_QUESTION);
            sendAndReceive(clientRequest);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Add question.
     *
     * @param question
     */
    public void addQuestion(Question question) {
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setObject(question);
            clientRequest.setGameProtocol(GameProtocol.ADD_QUESTION);
            sendAndReceive(clientRequest);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Change text of question.
     *
     * @param question
     * @param newText
     */
    public void changeQuestion(Question question, String newText) {
        try {
            ClientRequest clientRequest = new ClientRequest();
            List<Object> list = new ArrayList<>();
            list.add(question);
            list.add(newText);
            clientRequest.setObject(list);
            clientRequest.setGameProtocol(GameProtocol.CHANGE_QUESTION);
            sendAndReceive(clientRequest);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changeQuestions() {
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setGameProtocol(GameProtocol.CHANGE_QUESTIONS);
        clientRequest.setObject(approvedQuestions);
        try {
            sendAndReceive(clientRequest);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method that screams are you happy now.
     *
     * @param text
     * @param newText
     */
    public void changeQuestion(String text, String newText) {
        Question question = null;
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setGameProtocol(GameProtocol.GET_QUESTION);
            clientRequest.setObject(text);
            question = (Question) sendAndReceive(clientRequest);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        changeQuestion(question, newText);
    }

    /**
     * Validation of client.
     *
     * @param username
     * @param password
     * @return
     */
    public boolean validateClient(String username, String password) {
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setGameProtocol(GameProtocol.VALIDATION_CLIENT);
            clientRequest.setObject(new Client(username, password));
            return (Boolean) sendAndReceive(clientRequest);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private Object sendAndReceive(ClientRequest clientRequest) throws Exception {
        ServerResponse serverResponse = null;
        try {
            ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
            outStream.writeObject(clientRequest);
            ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
            serverResponse = (ServerResponse) inStream.readObject();
            Status operationStatus = serverResponse.getStatus();
            if (operationStatus == Status.NOT_COMPLETE) {
                throw new Exception(serverResponse.getMessage());
            }
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (serverResponse == null) {
            return null;
        }
        if (serverResponse.getStatus() == Status.EXCEPTION) {
            throw new Exception(serverResponse.getMessage());
        }
        return serverResponse.getObject();
    }

    /**
     * Preparation for registering.
     *
     * @param client
     * @throws java.lang.Exception
     */
    public void prepareRegister(Client client) throws Exception {
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setGameProtocol(GameProtocol.PREPARE_REGISTER);
        clientRequest.setObject(client);
        sendAndReceive(clientRequest);
    }

    /**
     * Returns Category with specific name.
     *
     * @param categoryName
     * @return
     */
    public Category getCategory(String categoryName) {
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setGameProtocol(GameProtocol.GET_CATEGORY);
            clientRequest.setObject(categoryName);
            return (Category) sendAndReceive(clientRequest);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Autocomplete questions.
     *
     * @param text
     * @return
     */
    public List<Question> autocompleteQuestions(String text) {
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setGameProtocol(GameProtocol.AUTOCOMPLETE_QUESTION);
            clientRequest.setObject(text);
            return (List<Question>) sendAndReceive(clientRequest);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Category> autocompleteCategory(String text) {
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setGameProtocol(GameProtocol.AUTOCOMPLETE_CATEGORY);
            clientRequest.setObject(text);
            return (List<Category>) sendAndReceive(clientRequest);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void addApproveCategory(Category category) {
        approveCategories.add(category);
    }

    public void addApproveQuestion(Question question) {
        approveQuestions.add(question);
    }

    public void addDeletedClient(Client client) {
        deleteClients.add(client);
    }

    public void setApprovedQuestions(List<Question> approvedQuestions) {
        this.approvedQuestions = approvedQuestions;
    }

    /**
     * Method that approve categories, questions and delete clients.
     *
     * @param object
     */
    public void executeAdmin(Object object) {
        if (object instanceof Category) {
            for (Category category : approveCategories) {
                approveCategory(category.getName());
            }
            approveCategories.clear();
        }
        if (object instanceof Client) {
            try {
                deleteClients(deleteClients);
            } catch (Exception ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            deleteClients.clear();
        }
        if (object instanceof Question) {
            for (Question question : approveQuestions) {
                approveQuestion(question);
            }
            approveQuestions.clear();
        }
    }
}

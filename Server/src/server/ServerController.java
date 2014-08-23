package server;

import domain.Answer;
import domain.Category;
import domain.Client;
import domain.Question;
import domain.Result;
import domain.Test;
import game.protocol.Status;
import server.logic.Validate;
import game.protocol.objects.ClientRequest;
import game.protocol.objects.ServerResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.logic.GameDBLogic;
import server.logic.RegisterClient;
import server.system.operation.category.AddCategory;
import server.system.operation.question.AddQuestion;

/**
 * Class for calling all system operation.
 *
 * @author Tihomir Radosavljevic
 * @author Jelena Tabas
 * @version 0.5
 */
public class ServerController {

    private ServerController() {
    }

    public static ServerController getInstance() {
        return ServerControllerHolder.INSTANCE;
    }

    /**
     * Autocomplete question
     *
     * @param clientRequest
     * @return
     */
    public Object autocompleteQuestion(ClientRequest clientRequest) {
        GameDBLogic bLogic = new GameDBLogic();
        return bLogic.autocompleteQuestion((String) clientRequest.getObject());
    }

    public Object getQuestion(ClientRequest clientRequest) {
        GameDBLogic bLogic = new GameDBLogic();
        return bLogic.getQuestion((String) clientRequest.getObject());
    }

    public Object autocompleteCategory(ClientRequest clientRequest) {
        GameDBLogic bLogic = new GameDBLogic();
        return bLogic.autocompleteCategory((String) clientRequest.getObject());
    }

    private static class ServerControllerHolder {

        private static final ServerController INSTANCE = new ServerController();
    }

    /**
     * System operation for validation of Client.
     *
     * @param clientRequest
     * @return
     */
    public Object validationClient(ClientRequest clientRequest) {
        Validate v = new Validate();
        Client c = (Client) clientRequest.getObject();
        return v.logIn(c.getUsername(), c.getPassword());
    }

    /**
     * Load client data from requested client
     *
     * @param clientRequest
     * @return
     */
    public Object loadClient(ClientRequest clientRequest) {
        GameDBLogic bLogic = new GameDBLogic();
        try {
            return bLogic.getClient((String) clientRequest.getObject());
        } catch (Exception ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Return highscores for one specific client.
     *
     * @param clientRequest
     * @return
     */
    public Object loadHighScores(ClientRequest clientRequest) {
        Client c = (Client) clientRequest.getObject();
        return new GameDBLogic().loadResults(c);
    }

    /**
     * Return top 100 highscores.
     *
     * @param clientRequest
     * @return
     */
    public Object loadTopHighScores(ClientRequest clientRequest) {
        return new GameDBLogic().loadResults();
    }

    /**
     * Saving result to the database.
     *
     * @param clientRequest
     * @return
     */
    public Object addResult(ClientRequest clientRequest) {
        GameDBLogic bLogic = new GameDBLogic();
        Result result = (Result) clientRequest.getObject();
        bLogic.addResult(result);
        return null;
    }

    /**
     * Is registering possible.
     *
     * @param clientRequest
     * @return
     */
    public ServerResponse prepareRegister(ClientRequest clientRequest) {
        Client client = (Client) clientRequest.getObject();
        RegisterClient register = new RegisterClient();
        ServerResponse serverResponse = new ServerResponse();
        try {
            register.prepareRegister(client);
            serverResponse.setStatus(Status.COMPLETE);
        } catch (Exception ex) {
            serverResponse.setMessage(ex.getMessage());
            serverResponse.setStatus(Status.EXCEPTION);
        }
        return serverResponse;
    }

    /**
     * Registering.
     *
     * @param clientRequest
     * @return
     */
    public Object register(ClientRequest clientRequest) {
        Client client = (Client) clientRequest.getObject();
        RegisterClient rc = new RegisterClient();
        try {
            rc.register(client);
        } catch (Exception ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Loading categoryTree.
     *
     * @param clientRequest
     * @return
     */
    public Object loadCategoryTree(ClientRequest clientRequest) {
        Category categoryTree = null;
        GameDBLogic bLogic = new GameDBLogic();
        try {
            categoryTree = bLogic.loadCategoryTree();
        } catch (Exception ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categoryTree;
    }

    /**
     * Changing information about client.
     *
     * @param clientRequest
     */
    public void changeClientInformation(ClientRequest clientRequest) {
        GameDBLogic bLogic = new GameDBLogic();
        Client client = (Client) clientRequest.getObject();
        try {
            bLogic.changeClientData(client);
        } catch (Exception ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete category.
     *
     * @param clientRequest
     */
    public void deleteCategory(ClientRequest clientRequest) {
        String categoryName = (String) clientRequest.getObject();
        GameDBLogic bLogic = new GameDBLogic();
        try {
            bLogic.deleteCategory(bLogic.findCategory(categoryName));
        } catch (Exception ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete question.
     *
     * @param clientRequest
     */
    public void deleteQuestion(ClientRequest clientRequest) {
        Question question = (Question) clientRequest.getObject();
        GameDBLogic bLogic = new GameDBLogic();
        try {
            bLogic.deleteQuestion(question);
        } catch (Exception ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Change name of category.
     *
     * @param clientRequest
     */
    public void changeCategory(ClientRequest clientRequest) {
        List<String> list = (List<String>) clientRequest.getObject();
        GameDBLogic bLogic = new GameDBLogic();
        try {
            bLogic.changeCategoryName(bLogic.findCategory(list.get(0)), list.get(1));
        } catch (Exception ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Add new category.
     *
     * @param clientRequest
     */
    public void addCategory(ClientRequest clientRequest) {
        List<String> list = (List<String>) clientRequest.getObject();
        GameDBLogic bLogic = new GameDBLogic();
        Category parent = null;
        try {
            parent = bLogic.findCategory(list.get(1));
        } catch (Exception ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Category category = new Category();
        category.setName(list.get(0));
        category.setParentCategory(parent);
        try {
            new AddCategory().execute(category);
        } catch (Exception ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns list of clients that begins with input string.
     *
     * @param clientRequest
     * @return
     */
    public Object autocompleteClient(ClientRequest clientRequest) {
        String username = (String) clientRequest.getObject();
        GameDBLogic bLogic = new GameDBLogic();
        return bLogic.autocompleteClient(username);
    }

    /**
     * Flags client for deleting.
     *
     * @param clientRequest
     */
    public void deleteClient(ClientRequest clientRequest) {
        Client client = (Client) clientRequest.getObject();
        GameDBLogic bLogic = new GameDBLogic();
        try {
            bLogic.deleteClient(client);
        } catch (Exception ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Flags client for deleting.
     *
     * @param clientRequest
     */
    public void deleteClients(ClientRequest clientRequest) {
        List<Client> clients = (List<Client>) clientRequest.getObject();
        GameDBLogic bLogic = new GameDBLogic();
        try {
            bLogic.deleteClients(clients);
        } catch (Exception ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Flags category for approved.
     *
     * @param clientRequest
     */
    public void approveCategory(ClientRequest clientRequest) {
        String categoryName = (String) clientRequest.getObject();
        try {
            GameDBLogic bLogic = new GameDBLogic();
            Category category = bLogic.findCategory(categoryName);
            bLogic.approveCategory(category);
        } catch (Exception ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void approveClient(ClientRequest clientRequest) {
        Client client = (Client) clientRequest.getObject();
        GameDBLogic bLogic = new GameDBLogic();
        try {
            bLogic.approveClient(client);
        } catch (Exception ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void approveQuestion(ClientRequest clientRequest) {
        Question question = (Question) clientRequest.getObject();
        GameDBLogic bLogic = new GameDBLogic();
        try {
            bLogic.approveQuestion(question);
        } catch (Exception ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addQuestion(ClientRequest clientRequest) {
        Question question = (Question) clientRequest.getObject();
        try {
            new AddQuestion().execute(question);
        } catch (Exception ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changeQuestion(ClientRequest clientRequest) {
        List<Object> list = (List<Object>) clientRequest.getObject();
        GameDBLogic bLogic = new GameDBLogic();
        try {
            bLogic.changeQuestionText((Question) list.get(0), (String) list.get(1));
        } catch (Exception ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Object getQuestions(ClientRequest clientRequest) {
        Category category = (Category) clientRequest.getObject();
        GameDBLogic bLogic = new GameDBLogic();
        return bLogic.getQuestions(category);
    }

    public Object getAnswers(ClientRequest clientRequest) {
        Question question = (Question) clientRequest.getObject();
        Random random = new Random();
        GameDBLogic bLogic = new GameDBLogic();
        List<Answer> allAnswers = null;
        try {
            allAnswers = bLogic.getAnswers(question);
        } catch (Exception ex) {
            return null;
        }
        List<Answer> answers = new ArrayList<>();
        //adding 3 wrong answers
        int i = 0;
        while (i < 3) {
            int number = random.nextInt(allAnswers.size());
            if (!allAnswers.get(number).isCorrect()) {
                answers.add(allAnswers.get(number));
                allAnswers.remove(number);
            } else {
                i++;
            }
        }
        //adding correct answer
        for (Answer answer : allAnswers) {
            if (answer.isCorrect()) {
                answers.add(answer);
            }
        }
        //shuffle answers so the correct one won't be last
        Collections.shuffle(answers);
        return answers;
    }

    public void saveTest(ClientRequest clientRequest) {
        Test test = (Test) clientRequest.getObject();
        GameDBLogic bLogic = new GameDBLogic();
        try {
            bLogic.saveTest(test);
        } catch (Exception ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Object getCategory(ClientRequest clientRequest) {
        String categoryName = (String) clientRequest.getObject();
        GameDBLogic bLogic = new GameDBLogic();
        try {
            Category category = bLogic.findCategory(categoryName);
            return category;
        } catch (Exception ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void changeQuestions(ClientRequest clientRequest) {
        List<Question> questions = (List<Question>) clientRequest.getObject();
        GameDBLogic bLogic = new GameDBLogic();
        for (Question question : questions) {
            try {
                bLogic.changeQuestionText(question);
            } catch (Exception ex) {
                Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

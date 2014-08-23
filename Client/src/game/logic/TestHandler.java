package game.logic;

import domain.Answer;
import domain.Category;
import domain.Client;
import domain.Question;
import domain.Result;
import domain.Test;
import domain.TestItem;
import game.protocol.GameProtocol;
import game.protocol.Status;
import game.protocol.objects.ClientRequest;
import game.protocol.objects.ServerResponse;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for handling the test from client's perspective.
 *
 * @author Tihomir Radosavljević
 * @author Lazar Ristić
 * @version 0.1
 */
public class TestHandler {

    private Question activeQuestion;
    private Test test;
    private Result result;
    private List<Question> questions;
    private Socket socket = Controller.getInstance().getSocket();

    /**
     * From which category the test should be.
     *
     * @param category
     */
    public void newTest(Category category) {
        test = new Test();
        test.setCategory(category);
        result = new Result(Controller.getInstance().getActiveClient());
        result.setTest(test);
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setObject(test.getCategory());
        clientRequest.setGameProtocol(GameProtocol.GET_QUESTIONS);
        try {
            questions = (List<Question>) sendAndReceive(clientRequest);
        } catch (Exception ex) {
            Logger.getLogger(TestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Loading new question.
     */
    public void loadNewQuestion() {
        int number = 0;
        while (!questions.isEmpty()) {
            number = new Random().nextInt(questions.size());
            if (!questions.get(number).isDeleted() && questions.get(number).isApproved()) {
                break;
            } else {
                questions.remove(number);
            }
        }
        TestItem testItem = new TestItem();
        test.getListItems().add(testItem);
        testItem.setTest(test);
        if (questions.isEmpty()) {
            Question question = new Question();
        } else {
            testItem.setQuestion(questions.get(number));
            testItem.getQuestion().setAnswersList(generateAnswers(questions.get(number)));
            questions.remove(number);
            activeQuestion = testItem.getQuestion();
        }
    }

    public void answerQuestion(Answer answer) {
        if (answer.isCorrect()) {
            //every correct answer is one point worth
            result.addValue(1);
        }
    }

    public Question getActiveQuestion() {
        return activeQuestion;
    }

    /**
     * Generate 4 answers for one question.
     *
     * @param question
     * @return
     */
    private List<Answer> generateAnswers(Question question) {
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setObject(question);
        clientRequest.setGameProtocol(GameProtocol.GET_ANSWERS);
        try {
            return (List<Answer>) sendAndReceive(clientRequest);
        } catch (Exception ex) {
            Logger.getLogger(TestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Method for returning test that the client do.
     *
     * @param client
     * @return
     */
    public Test letMeSeeTest(Client client) {
        return test;
    }

    /**
     * Return result that of test that client do.
     *
     * @param client
     * @return
     */
    public Result getResultOfTest(Client client) {
        return result;
    }

    /**
     * Method for saving test into the database.
     */
    public void saveTest() {
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setObject(test);
            clientRequest.setGameProtocol(GameProtocol.SAVE_TEST);
            sendAndReceive(clientRequest);
        } catch (Exception ex) {
            Logger.getLogger(TestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
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
}

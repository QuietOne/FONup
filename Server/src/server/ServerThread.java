package server;

import game.protocol.GameProtocol;
import game.protocol.Status;
import game.protocol.objects.ClientRequest;
import game.protocol.objects.ServerResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for threading.
 *
 * @author Tihomir Radosavljevic
 * @author Jelena Tabas
 * @version 1.0
 */
public class ServerThread extends Thread {

    Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            startCommunication();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void startCommunication() throws IOException, ClassNotFoundException {
        while (true) {
            ObjectInputStream inputStream
                    = new ObjectInputStream(socket.getInputStream());
            ClientRequest clientRequest
                    = (ClientRequest) inputStream.readObject();
            processRequest(clientRequest);
        }
    }

    private synchronized void processRequest(ClientRequest clientRequest) throws IOException {
        GameProtocol protocol = clientRequest.getGameProtocol();
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setStatus(Status.NOT_COMPLETE);
        System.out.println(clientRequest.getGameProtocol());
        switch (protocol) {
            case VALIDATION_CLIENT:
                serverResponse.setObject(ServerController.getInstance().validationClient(clientRequest));
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case LOGIN_CLIENT:
                serverResponse.setObject(ServerController.getInstance().loadClient(clientRequest));
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case LOAD_HIGHSCORES:
                serverResponse.setObject(ServerController.getInstance().loadHighScores(clientRequest));
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case LOAD_TOP_HIGHSCORES:
                serverResponse.setObject(ServerController.getInstance().loadTopHighScores(clientRequest));
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case ADD_RESULT:
                serverResponse.setObject(ServerController.getInstance().addResult(clientRequest));
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case PREPARE_REGISTER:
                serverResponse = ServerController.getInstance().prepareRegister(clientRequest);
                break;
            case REGISTER:
                ServerController.getInstance().register(clientRequest);
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case LOAD_CATEGORY_TREE:
                serverResponse.setObject(ServerController.getInstance().loadCategoryTree(clientRequest));
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case CHANGE_CLIENT_INFO:
                ServerController.getInstance().changeClientInformation(clientRequest);
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case DELETE_CATEGORY:
                ServerController.getInstance().deleteCategory(clientRequest);
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case CHANGE_CATEGORY:
                ServerController.getInstance().changeCategory(clientRequest);
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case ADD_CATEGORY:
                ServerController.getInstance().addCategory(clientRequest);
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case AUTOCOMPLETE_CLIENT:
                serverResponse.setObject(ServerController.getInstance().autocompleteClient(clientRequest));
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case DELETE_CLIENT:
                ServerController.getInstance().deleteClient(clientRequest);
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case DELETE_CLIENTS:
                ServerController.getInstance().deleteClients(clientRequest);
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case APPROVE_CATEGORY:
                ServerController.getInstance().approveCategory(clientRequest);
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case APPROVE_CLIENT:
                ServerController.getInstance().approveClient(clientRequest);
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case APPROVE_QUESTION:
                ServerController.getInstance().approveQuestion(clientRequest);
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case ADD_QUESTION:
                ServerController.getInstance().addQuestion(clientRequest);
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case CHANGE_QUESTION:
                ServerController.getInstance().changeQuestion(clientRequest);
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case GET_QUESTIONS:
                serverResponse.setObject(ServerController.getInstance().getQuestions(clientRequest));
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case GET_ANSWERS:
                serverResponse.setObject(ServerController.getInstance().getAnswers(clientRequest));
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case SAVE_TEST:
                ServerController.getInstance().saveTest(clientRequest);
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case GET_CATEGORY:
                serverResponse.setObject(ServerController.getInstance().getCategory(clientRequest));
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case AUTOCOMPLETE_QUESTION:
                serverResponse.setObject(ServerController.getInstance().autocompleteQuestion(clientRequest));
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case GET_QUESTION:
                serverResponse.setObject(ServerController.getInstance().getQuestion(clientRequest));
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case AUTOCOMPLETE_CATEGORY:
                serverResponse.setObject(ServerController.getInstance().autocompleteCategory(clientRequest));
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case CHANGE_QUESTIONS:
                ServerController.getInstance().changeQuestions(clientRequest);
                serverResponse.setStatus(Status.COMPLETE);
                break;
            case DELETE_QUESTION:
                ServerController.getInstance().deleteQuestion(clientRequest);
                serverResponse.setStatus(Status.COMPLETE);
                break;

            default:
                serverResponse.setStatus(Status.PROTOCOL_UNKNOWN);
        }
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(serverResponse);
        System.out.println(serverResponse.getStatus());
    }
}

package game.logic;

import domain.Answer;
import domain.Category;
import domain.Client;
import domain.Result;
import domain.Test;

/**
 * Controller for logic during the game.
 *
 * @author Tihomir Radosavljevic
 * @version 1.0
 */
public class GameController {

    private final TestHandler clientTestHandler;

    private GameController() {
        clientTestHandler = new TestHandler();
    }

    public static GameController getInstance() {
        return GraphicalControllerHolder.INSTANCE;
    }

    private static class GraphicalControllerHolder {

        private static final GameController INSTANCE = new GameController();
    }

    /**
     * Loading new test item.
     */
    public void loadNewQuestion() {
        clientTestHandler.loadNewQuestion();
    }

    /**
     * Check if question is loaded.
     *
     * @return
     */
    public boolean isThereMoreQuestions() {
        return clientTestHandler.getActiveQuestion() != null;
    }

    /**
     * Get text for jButtons from answers.
     *
     * @return 4 strings
     */
    public String[] getAnswersText() {
        String[] texts = new String[4];
        int i = 0;
        for (Answer answer : clientTestHandler.getActiveQuestion().getAnswersList()) {
            texts[i] = answer.getText();
            i++;
        }
        return texts;
    }

    /**
     * Get text for jLabel from Question.
     *
     * @return
     */
    public String getQuestionText() {
        String temp = clientTestHandler.getActiveQuestion().getText();
        StringBuilder builder = new StringBuilder(temp);
        boolean canDo = true;
        final int NEW_LINE = 70;
        final int RANGE = 50;
        for (int i = 1; i < temp.length() / NEW_LINE; i++) {
            int j = 0;
            for (; j < RANGE; j++) {
                if (i * NEW_LINE + j >= temp.length()) {
                    canDo = false;
                }
                if (temp.charAt(i * NEW_LINE + j) == ' ') {
                    break;
                }
            }
            if (canDo) {
                builder.insert(i * NEW_LINE + j + 1, "\n");
            }
        }
        return builder.toString();
    }

    /**
     * Method for answering question.
     *
     * @param text
     */
    public void answerQuestion(String text) {
        for (Answer answer : clientTestHandler.getActiveQuestion().getAnswersList()) {
            if (answer.getText().equals(text)) {
                clientTestHandler.answerQuestion(answer);
                return;
            }
        }
    }

    /**
     * Was answer correct.
     *
     * @param text
     * @return
     */
    public boolean isCorrect(String text) {
        for (Answer answer : clientTestHandler.getActiveQuestion().getAnswersList()) {
            if (answer.getText().equals(text)) {
                return answer.isCorrect();
            }
        }
        return false;
    }

    /**
     * Method for telling server from which category the test should be made.
     *
     * @param category
     */
    public void loadNewTest(Category category) {
        clientTestHandler.newTest(category);
    }

    /**
     * Method for telling server from which category the test should be made if
     * only name of category is known.
     *
     * @param categoryName
     */
    public void loadNewTest(String categoryName) {
        Category category = Controller.getInstance().getCategory(categoryName);
        clientTestHandler.newTest(category);
    }

    /**
     * Saving test that has been done.
     */
    public void saveTest() {
        clientTestHandler.saveTest();
    }
    
    /**
     * Getting result of test.
     * @return 
     */
    public Result getResult(){
        return clientTestHandler.getResultOfTest(Controller.getInstance().getActiveClient());
    }
    
    public Test letMeSeeTest() {
        return clientTestHandler.letMeSeeTest(Controller.getInstance().getActiveClient());
    }
}

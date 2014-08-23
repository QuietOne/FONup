package game.protocol;

/**
 * Enum for all possible operations.
 * @author Tihomir Radosavljevic
 * @author Jelena Tabas
 * @version 0.1
 */
public enum GameProtocol {

    /**
     * Send Client with only username and password.
     */
    VALIDATION_CLIENT,
    /**
     * Send username and Client will be returned.
     */
    LOGIN_CLIENT,
    /**
     * Send client and get list of results.
     */
    LOAD_HIGHSCORES,
    /**
     * Loads top 100 highscores.
     */
    LOAD_TOP_HIGHSCORES,
    /**
     * Send result that needs saving
     */
    ADD_RESULT,
    /**
     * Send client to check if it is good. Good if no exception has been made.
     */
    PREPARE_REGISTER,
    /**
     * Send client and save it to the database.
     */
    REGISTER,
    /**
     * Loads the category tree.
     */
    LOAD_CATEGORY_TREE,
    /**
     * Send client and it will change client data with exact id.
     */
    CHANGE_CLIENT_INFO,
    /**
     * Send category name to delete it.
     */
    DELETE_CATEGORY,
    /**
     * Send strings category and it will change its name.
     */
    CHANGE_CATEGORY,
    /**
     * Send strings category to a category.
     */
    ADD_CATEGORY,
    /**
     * Send string and gets a list of clients.
     */
    AUTOCOMPLETE_CLIENT,
    /**
     * Send client to delete it.
     */
    DELETE_CLIENT,
    /**
     * Send clients to delete them.
     */
    DELETE_CLIENTS,
    /**
     * Send category name.
     */
    APPROVE_CATEGORY,
    /**
     * Send client to approve him.
     */
    APPROVE_CLIENT,
    /**
     * Send question to approve it.
     */
    APPROVE_QUESTION,
    /**
     * Send question to add it.
     */
    ADD_QUESTION,
    /**
     * Send question and string to change it text.
     */
    CHANGE_QUESTION,
    /**
     * Send category and get all questions.
     */
    GET_QUESTIONS,
    /**
     * Send questions and get answers.
     */
    GET_ANSWERS,
    /**
     * Send test to the database.
     */
    SAVE_TEST,
    /**
     * Send string and get Category with that name.
     */
    GET_CATEGORY,
    /**
     * Send string and receive questions.
     */
    AUTOCOMPLETE_QUESTION,
    /**
     * Send string and receive Question.
     */
    GET_QUESTION,
    /**
     * Send string and receive list of categories.
     */
    AUTOCOMPLETE_CATEGORY,
    /**
     * Send list of questions that needs to be changed.
     */
    CHANGE_QUESTIONS,
    /**
     * Send a question that needs to be deleted.
     */
    DELETE_QUESTION;
}

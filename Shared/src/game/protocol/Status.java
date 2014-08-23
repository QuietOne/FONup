package game.protocol;

/**
 * Enum for booking response made by server.
 *
 * @author Tihomir Radosavljevic
 * @version 1.0
 */
public enum Status {

    /**
     * Operation has been successfully done.
     */
    COMPLETE,
    /**
     * Error has been made.
     */
    NOT_COMPLETE,
    /**
     * Unknown protocol call has been made.
     */
    PROTOCOL_UNKNOWN,
    /**
     * Exception has been made and operation failed.
     */
    EXCEPTION;
}

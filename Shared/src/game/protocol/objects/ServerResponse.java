package game.protocol.objects;

import game.protocol.Status;
import java.io.Serializable;

/**
 *
 * @author Lazar Ristic <lazar.ristic91@gmail.com>
 * @author Tihomir Radosavljevic
 * @version 1.0
 */
public class ServerResponse implements Serializable {

    /**
     * Object that needs transferring.
     */
    private Object object;
    /**
     * Is everything done successfully.
     */
    private Status status;
    /**
     * Message that comes from the server.
     */
    private String message;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

package game.protocol.objects;

import game.protocol.GameProtocol;
import java.io.Serializable;

/**
 *
 * @author Lazar Ristic <lazar.ristic91@gmail.com>
 * @author Tihomir Radosavljevic
 * @version 1.0
 */
public class ClientRequest implements Serializable{
    /**
     * Object that needs transferring.
     */
    private Object object;
    /**
     * What to do with it.
     */
    private GameProtocol gameProtocol;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public GameProtocol getGameProtocol() {
        return gameProtocol;
    }

    public void setGameProtocol(GameProtocol gameProtocol) {
        this.gameProtocol = gameProtocol;
    }
    
    
}

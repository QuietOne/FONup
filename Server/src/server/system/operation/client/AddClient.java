package server.system.operation.client;

import database.DataBaseBroker;
import domain.Client;
import server.system.operation.GenericSystemOperation;

/**
 * Method for adding the client to the database.
 *
 * @author Tihomir Radosavljevic
 * @version 1.0
 */
public class AddClient extends GenericSystemOperation {

    @Override
    protected void checkPrecondition(DataBaseBroker dbb, Object object) throws Exception {
        if (!(object instanceof Client)) {
            throw new Exception("Wrong paramether");
        }
    }

    @Override
    protected void controlExecute(DataBaseBroker dbb, Object object) throws Exception {
        dbb.insert((Client) object);
    }

}

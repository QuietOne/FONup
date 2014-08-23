package server.system.operation.client;

import database.DataBaseBroker;
import domain.Client;
import server.system.operation.GenericSystemOperation;

/**
 * Method for changing client data. It changes everything except id.
 *
 * @author Tihomir Radosavljević
 * @version 1.0
 */
public class ChangeClientData extends GenericSystemOperation {

    @Override
    protected void checkPrecondition(DataBaseBroker dbb, Object object) throws Exception {
        if (!(object instanceof Client)) {
            throw new Exception("Wrong paramether");
        }
    }

    @Override
    protected void controlExecute(DataBaseBroker dbb, Object object) throws Exception {
        dbb.overwriteClient((Client) object);
    }

}

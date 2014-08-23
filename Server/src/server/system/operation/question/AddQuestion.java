package server.system.operation.question;

import database.DataBaseBroker;
import domain.Question;
import server.system.operation.GenericSystemOperation;

/**
 * Method for adding the question to the database.
 *
 * @author Tihomir Radosavljević
 * @version 1.0
 */
public class AddQuestion extends GenericSystemOperation {

    @Override
    protected void checkPrecondition(DataBaseBroker dbb, Object object) throws Exception {
        if (!(object instanceof Question)) {
            throw new Exception("Wrong paramether");
        }
    }

    @Override
    protected void controlExecute(DataBaseBroker dbb, Object object) throws Exception {
        dbb.insert((Question) object);
    }

}

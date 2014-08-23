package server.system.operation.category;

import database.DataBaseBroker;
import domain.Category;
import server.system.operation.GenericSystemOperation;

/**
 * Method for adding the category to the database.
 *
 * @author Tihomir Radosavljević
 * @version 1.0
 */
public class AddCategory extends GenericSystemOperation {

    @Override
    protected void checkPrecondition(DataBaseBroker dbb, Object object) throws Exception {
        if (!(object instanceof Category)) {
            throw new Exception("Wrong paramether");
        }
    }

    @Override
    protected void controlExecute(DataBaseBroker dbb, Object object) throws Exception {
        dbb.insert((Category) object);
    }

}

package server.system.operation;

import database.DataBaseBroker;

/**
 *
 * @author Tihomir Radosavljevic
 * @version 1.0
 */
public abstract class GenericSystemOperation {

    public final void execute(Object object) throws Exception {
        DataBaseBroker dbb = null;
        try {
            dbb = new DataBaseBroker();
            dbb.loadDriver();
            dbb.establishConnection();
            checkPrecondition(dbb, object);
            controlExecute(dbb, object);
            dbb.commit();
        } catch (Exception ex) {
            if (dbb != null) {
                dbb.rollback();
            }
            throw ex;
        } finally {
            if (dbb != null) {
                dbb.closeConnection();
            }
        }
    }

    protected abstract void checkPrecondition(DataBaseBroker dbb, Object object) throws Exception;

    protected abstract void controlExecute(DataBaseBroker dbb, Object object) throws Exception;
}

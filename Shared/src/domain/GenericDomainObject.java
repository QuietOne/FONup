package domain;

import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Tihomir Radosavljević
 * @version 1.0
 */
public interface GenericDomainObject {

    public String getTableName();

    public List<GenericDomainObject> getAll(ResultSet rs) throws Exception;

    public String getInsertValues();
}

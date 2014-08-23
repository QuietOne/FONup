package server.gui.utils;

import database.DataBaseUtils;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Table Model for all configurations of data base.
 *
 * @author Tihomir Radosavljevic
 */
public class ConfigurationTableModel extends AbstractTableModel {

    @Override
    public int getRowCount() {
        return DataBaseUtils.getInstance().getDBManagementSystems().size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        List<String> list = DataBaseUtils.getInstance().getDBManagementSystems();
        String dbms = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return dbms;
            case 1:
                return DataBaseUtils.getInstance().getURL(dbms);
            case 2:
                return DataBaseUtils.getInstance().getDriver(dbms);
            case 3:
                return DataBaseUtils.getInstance().getUser(dbms);
            case 4:
                return DataBaseUtils.getInstance().getPassword(dbms);
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "SUBP";
            case 1:
                return "URL";
            case 2:
                return "Drajver";
            case 3:
                return "Username";
            case 4:
                return "Password";
            default:
                return "Greska";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        List<String> list = DataBaseUtils.getInstance().getDBManagementSystems();
        String dbms = list.get(rowIndex);
        switch (columnIndex) {
            case 1:
                DataBaseUtils.getInstance().setURL(dbms, (String) aValue);
                break;
            case 2:
                DataBaseUtils.getInstance().setDriver(dbms, (String) aValue);
                break;
            case 3:
                DataBaseUtils.getInstance().setUser(dbms, (String) aValue);
                break;
            case 4:
                DataBaseUtils.getInstance().setPassword(dbms, (String) aValue);
                break;
        }
    }

}

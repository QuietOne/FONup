/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utils;

import domain.Client;
import game.logic.Controller;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author tihomir
 */
public class ClientTableModel extends AbstractTableModel {

    @Override
    public int getRowCount() {
        return Controller.getInstance().getDeleteClients().size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Client client = Controller.getInstance().getDeleteClients().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return client.getId();
            case 1:
                return client.getUsername();
            case 2:
                return client.getFirstName();
            case 3:
                return client.getLastName();
            default:
                return "";
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "ID";
            case 1:
                return "Username";
            case 2:
                return "Ime";
            case 3:
                return "Prezime";
            default:
                return "";
        }

    }

}

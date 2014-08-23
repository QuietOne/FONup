/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utils;

import domain.Category;
import game.logic.Controller;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author tihomir
 */
public class CategoryTableModel extends AbstractTableModel {

    @Override
    public int getRowCount() {
        return Controller.getInstance().getApproveCategories().size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Category category = Controller.getInstance().getApproveCategories().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return category.getId();
            case 1:
                return category.getName();
            case 2:
                return category.isApproved();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "ID";
            case 1:
                return "Ime kategorije";
            case 2:
                return "Odobrena";
            default:
                return "";
        }
    }

}

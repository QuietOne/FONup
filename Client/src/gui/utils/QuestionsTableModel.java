/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utils;

import domain.Question;
import game.logic.Controller;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jelena Tabaš
 */
public class QuestionsTableModel extends AbstractTableModel {

    @Override
    public int getRowCount() {
        return Controller.getInstance().getApprovedQuestions().size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Question question = Controller.getInstance().getApprovedQuestions().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return question.getId();
            case 1:
                return question.getText();
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
                return "Text";
            default:
                return "";
        }

    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        if (i1 == 1) {
            return true;
        }
        return false;
    }

    @Override
    public void setValueAt(Object o, int i, int i1) {
        Question question = Controller.getInstance().getApprovedQuestions().get(i);
        question.setText((String) o);
    }

}

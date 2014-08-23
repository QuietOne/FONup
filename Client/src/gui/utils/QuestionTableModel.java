package gui.utils;

import domain.Question;
import game.logic.Controller;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author tihomir
 */
public class QuestionTableModel extends AbstractTableModel {

    @Override
    public int getRowCount() {
        return Controller.getInstance().getApproveQuestions().size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Question question = Controller.getInstance().getApproveQuestions().get(rowIndex);
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
}

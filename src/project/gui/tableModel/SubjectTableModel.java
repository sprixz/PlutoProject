/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.gui.tableModel;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import project.logic.DataSource;
import project.controllers.exceptions.NonexistentEntityException;
import project.logic.entities.Subject;
import project.logic.entities.User;

/**
 *
 * @author Varga Balázs
 */
public class SubjectTableModel extends AbstractTableModel {

    @Override
    public int getRowCount() {
        return DataSource.getInstance().getSubjectController().getSubjectCount();
    }

    @Override
    public int getColumnCount() {
        return Subject.fields.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Subject subjects = DataSource.getInstance().getSubjectController().findSubjectEntities().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return subjects.getSubjectName();
            case 1:
                return subjects.getRoom();
            case 2:
                return subjects.getSubDay();
            case 3:
                return subjects.getSubHour();
            case 4:
                return subjects.getRegistered_sem();
            case 5:
                return subjects.getRequirement();
            case 6:
                return subjects.getTeacherName();
            default:
                return null;
        }

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 6;
    }

    @Override
    public String getColumnName(int column) {
        return Subject.fields[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return Integer.class;
            case 5:
                return Subject.class;
            case 6:
                return String.class;
            default:
                return String.class;
        }

    }

    @Override
    public void setValueAt(Object inValue, int inRow, int inCol) {

        Subject currsub = DataSource.getInstance().getSubjectController().findSubjectEntities().get(inRow);
        /*if (currsub.isFulfilled()) {
         currsub.setFulfilled(false);

         } else {
         currsub.setFulfilled(true);
         }*/

        try {
            DataSource.getInstance().getSubjectController().edit(currsub);
        } catch (Exception ex) {
            Logger.getLogger(SubjectTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        fireTableCellUpdated(inRow, inCol);

    }

    public Object[] currentSubjects() {

        int arraysize = getRowCount();
        Object[] subarray = new String[arraysize];

        for (int i = 0; i < getRowCount(); i++) {

            Subject currentsub = DataSource.getInstance().getSubjectController().findSubjectEntities().get(i);

            subarray[i] = currentsub.getSubjectName();

        }
        return subarray;

    }

    public String[] currentSubjectsId() {
        int arraysize = getRowCount();
        String[] subarray = new String[arraysize];

        for (int i = 0; i < getRowCount(); i++) {

            Subject currentsub = DataSource.getInstance().getSubjectController().findSubjectEntities().get(i);
            subarray[i] = currentsub.getId().toString();

        }
        return subarray;

    }

    public String[] currentReqSubject(String subname) {
        ArrayList<String> segedlista = new ArrayList<>();
        String[] subarray = new String[segedlista.size()];

        for (int i = 0; i < getRowCount(); i++) {

            Subject sub = DataSource.getInstance().getSubjectController().findSubjectEntities().get(i);
            if (!(subname).equals(sub.getSubjectName())) {
                segedlista.add(sub.getSubjectName());
            }

        }

        for (int i = 0; i < segedlista.size(); i++) {
            System.out.println(segedlista.get(i));
        }

        return subarray;

    }

    public void deleteThisSubject(int currentid) {

        for (int i = 0; i < getRowCount(); i++) {
            Subject currsub = DataSource.getInstance().getSubjectController().findSubjectEntities().get(i);
            if (currsub.getId() == currentid) {
                try {
                    DataSource.getInstance().getSubjectController().destroy(currsub.getId());
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(SubjectTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public Subject getmySubject(int id) {
        Subject mysub = new Subject();
        for (int i = 0; i < getRowCount(); i++) {
            Subject compare = DataSource.getInstance().getSubjectController().findSubjectEntities().get(i);
            if (compare.getId() == id) {
                mysub = compare;
            }
        }

        return mysub;
    }

    public Object[][] getThisUserData(User user) {

        Object[][] data = new Object[user.getSubjects().size()][getColumnCount()];
        for (int i = 0; i < user.getSubjects().size(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                data[i][j] = getValueAt(i, j);
            }

        }
        return data;
    }

    public String[] getColumnName() {
        String[] columnName = new String[getColumnCount()];
        for (int i = 0; i < getColumnCount(); i++) {
            columnName[i] = getColumnName(i);
        }
        return columnName;
    }

}

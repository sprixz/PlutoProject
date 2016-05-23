/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.gui.tableModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import project.logic.DataSource;
import project.logic.entities.User;
import project.logic.entities.Subject;
import project.gui.tableModel.SubjectTableModel;

/**
 *
 * @author Varga Balázs
 */
public class UserTableModel extends AbstractTableModel {

    @Override
    public int getRowCount() {
        return DataSource.getInstance().getUserController().getUserCount();

    }

    @Override
    public int getColumnCount() {
        return User.fields.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User users = DataSource.getInstance().getUserController().findUserEntities().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return users.getName();
            case 1:
                return users.getUsername();
            case 2:
                return users.getPassoword();
            case 3:
                return users.isTeacher();
            case 4:
                return users.isOnline();
            default:
                return null;

        }
    }

    @Override
    public String getColumnName(int column) {
        return User.fields[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 2;
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
            default:
                return Boolean.class;
        }

    }

    public int amountofstudent() {
        int db = 0;
        for (int i = 0; i < getRowCount(); i++) {
            User student = DataSource.getInstance().getUserController().findUserEntities().get(i);
            if (!(student.isTeacher())) {
                db++;
            }
        }
        return db;

    }

    public Object[] currentstudent() {

        int arraysize = amountofstudent();
        Object[] subarray = new String[arraysize];
        int currentsize = 0;

        for (int i = 0; i < getRowCount(); i++) {
            User currentstud = DataSource.getInstance().getUserController().findUserEntities().get(i);
            if (!(currentstud.isTeacher())) {
                subarray[currentsize] = currentstud.getName() + "-" + currentstud.getUsername();
                currentsize++;
            }

        }
        return subarray;

    }

    public int amountofteacher() {
        int db = 0;
        for (int i = 0; i < getRowCount(); i++) {
            User student = DataSource.getInstance().getUserController().findUserEntities().get(i);
            if ((student.isTeacher())) {
                db++;
            }
        }
        return db;

    }

    public String[] currentteahcer() {

        int arraysize = amountofteacher();
        String[] subarray = new String[arraysize];
        int currentsize = 0;

        for (int i = 0; i < getRowCount(); i++) {

            User currentstud = DataSource.getInstance().getUserController().findUserEntities().get(i);
            if ((currentstud.isTeacher())) {
                subarray[currentsize] = currentstud.getName() + "-" + currentstud.getUsername();
                currentsize++;
            }

        }
        return subarray;

    }

    public void turnOnlie(User user) {
        User newuser = new User();
        newuser = user;
        newuser.setOnline(true);

        for (int i = 0; i < getRowCount(); i++) {
            User user2 = DataSource.getInstance().getUserController().findUserEntities().get(i);
            if (newuser.getUsername().equals(user2.getUsername())) {
                try {
                    DataSource.getInstance().getUserController().edit(newuser);
                } catch (Exception ex) {
                    Logger.getLogger(UserTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void turnOffline(User user) {
        User newuser = new User();
        newuser = user;
        newuser.setOnline(false);

        for (int i = 0; i < getRowCount(); i++) {
            User user2 = DataSource.getInstance().getUserController().findUserEntities().get(i);
            if (newuser.getUsername().equals(user2.getUsername())) {
                try {
                    DataSource.getInstance().getUserController().edit(newuser);
                } catch (Exception ex) {
                    Logger.getLogger(UserTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public ArrayList<User> whoIsOnline() {
        ArrayList<User> online = new ArrayList<>();
        for (int i = 0; i < getRowCount(); i++) {
            User user = DataSource.getInstance().getUserController().findUserEntities().get(i);
            if (user.isOnline() && user.isTeacher() == false) {
                online.add(user);
            }
        }

        return online;

    }

    public void addSubjectToMyList(String user, Subject subject) {
        User thisuser = getThisUser(user);

        if (thisuser.getSubjects().isEmpty()) {
            List<Subject> mynewsubject = new ArrayList<>();
            mynewsubject.add(subject);

            thisuser.setSubjects(mynewsubject);
            try {
                DataSource.getInstance().getUserController().edit(thisuser);
                fireTableDataChanged();
            } catch (Exception ex) {
                Logger.getLogger(UserTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            List<Subject> alreadyhavealist = thisuser.getSubjects();
            alreadyhavealist.add(subject);

            List<Subject> targy = alreadyhavealist;
            thisuser.setSubjects(targy);

            try {
                DataSource.getInstance().getUserController().edit(thisuser);
                fireTableDataChanged();
            } catch (Exception ex) {
                Logger.getLogger(UserTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public boolean youAlreadyHaveThisSubject(String name, List<Integer> list) {
        boolean ok = false;
        User thisuser = getThisUser(name);
        List<Subject> thislist = getThisSubject(list);
        if (thislist.isEmpty()) {
            ok = false;
        } else {
            for (int i = 0; i < thisuser.getSubjects().size(); i++) {
                if (thisuser.getSubjects().get(i).equals(thislist.get(i))) {
                    ok = true;
                } else {
                    ok = false;
                }
            }
        }

        return ok;

    }

    public User getThisUser(String username) {
        User thisuser = new User();

        for (int i = 0; i < getRowCount(); i++) {
            User compare = DataSource.getInstance().getUserController().findUserEntities().get(i);
            if (compare.getUsername().equals(username)) {
                thisuser = compare;
            }
        }
        return thisuser;
    }

    public List<Subject> getThisSubject(List<Integer> mysubject) {
        List<Subject> iwts = new ArrayList<>();

        for (int i = 0; i < mysubject.size(); i++) {
            Subject compare = DataSource.getInstance().getSubjectController().findSubjectEntities().get(i);
            if (Objects.equals(compare.getId(), mysubject.get(i))) {
                iwts.add(compare);
            }

        }

        return iwts;

    }

    public void updateHisList(User user, int id) {
        List<Subject> alreadyhavealist = user.getSubjects();
        Subject deletethis = new Subject();
        for (int i = 0; i < alreadyhavealist.size(); i++) {
            if (alreadyhavealist.get(i).getId() == id) {
                deletethis = alreadyhavealist.get(i);
            }
        }
        alreadyhavealist.remove(deletethis);
        //System.out.println("ez a tárgy akar kitörlődni"+ deletethis.getSubjectName());
        user.setSubjects(alreadyhavealist);
        try {
            DataSource.getInstance().getUserController().edit(user);
        } catch (Exception ex) {
            Logger.getLogger(UserTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        fireTableDataChanged();

    }

    public List<User> theyHaveThisSubject(int subid) {
        List<User> students = new ArrayList<>();

        for (int i = 0; i < getRowCount(); i++) {
            User compare = DataSource.getInstance().getUserController().findUserEntities().get(i);
            if (compare.getSubjects().isEmpty()) {
            } else {
                for (int j = 0; j < compare.getSubjects().size(); j++) {
                    if (compare.getSubjects().get(j).getId() == subid) {
                        students.add(compare);
                    }
                }

            }
        }
        return students;
    }

    public boolean requirementCheck(Subject selectedsubject, String studentname) {
        boolean ok = false;

        if (selectedsubject.getRequirement() == null) {
            ok = true;
        } else {
            User thisuser = getThisUser(studentname);
            Subject thisreq = selectedsubject.getRequirement();
            int i = 0;
            while (i < thisuser.getSubjects().size()) {
                /*if ((thisuser.getSubjects().get(i).equals(thisreq)) && (thisuser.getSubjects().get(i).isFulfilled())) {
                    ok = true;
                }*/
                i++;
            }
        }

        return ok;
    }

    public String[] getMySubjectArray(List<Subject> mysub) {
        String[] mys = new String[mysub.size()];
        for (int i = 0; i < mysub.size(); i++) {
            mys[i] = mysub.get(i).getSubjectName();
        }
        return mys;
    }

    public Subject getThisSubjectFromMyList(String selectedsub, User student) {
        Subject thissubject = new Subject();
        for (int i = 0; i < student.getSubjects().size(); i++) {
            if (student.getSubjects().get(i).getSubjectName().equals(selectedsub)) {
                thissubject = student.getSubjects().get(i);
            }
        }
        return thissubject;
    }
}

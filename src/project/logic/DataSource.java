/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.logic;

import javax.persistence.Cache;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import project.controllers.SubjectJpaController;
import project.controllers.UserJpaController;

/**
 *
 * @author Varga Bence
 */
public class DataSource {

    private static DataSource dataSource = null;
    private EntityManagerFactory emf;
    private final Cache cache = getEntityManagerFactory().getCache();

    private SubjectJpaController subjectController;
    private UserJpaController userController;

    public static DataSource getInstance() {
        if (dataSource == null) {
            dataSource = new DataSource();

        }
        return dataSource;
    }

    public SubjectJpaController getSubjectController() {
        return subjectController;
    }

    public UserJpaController getUserController() {
        return userController;
    }
    
    

    private DataSource() {
        userController = new UserJpaController(getEntityManagerFactory());
        subjectController = new SubjectJpaController(getEntityManagerFactory());
    }

    public final EntityManagerFactory getEntityManagerFactory() {
        emf = Persistence.createEntityManagerFactory("ProjectPU");
        return emf;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

}

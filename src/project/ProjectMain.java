/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.awt.EventQueue;
import project.gui.PlutoFrame;
import project.logic.DataSource;

/**
 *
 * @author Varga Bence
 */
public class ProjectMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        DataSource.getInstance().getEntityManagerFactory().createEntityManager().close();
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                
                PlutoFrame plutoFrame = new PlutoFrame();
            }
        });
        
    }
    
}

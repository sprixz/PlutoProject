/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 *
 * @author Varga Balázs
 */
public class PlutoFrame extends JPanel {

    JTextField username = new JTextField(10);
    JPasswordField password = new JPasswordField(10);
    JPanel panel = new JPanel();
    JFrame pluto = new JFrame();
    JLabel mainlabel = new JLabel("Belépés");
    JLabel userlabel = new JLabel("Felhasználó név:");
    JLabel passwlabel = new JLabel("Jelszó:");
    ImageIcon plutojpg = new ImageIcon(getClass().getResource("pluto.jpg"));
    JLabel plutopic = new JLabel(plutojpg);
    JCheckBox isteacher = new JCheckBox("Tanár");
    JButton login = new JButton("Belépés");
    JButton registration = new JButton("Regisztráció");
    ImageIcon realpluto = new ImageIcon(getClass().getResource("plutoreal.jpg"));
    JLabel realplutopic = new JLabel(realpluto);

    public PlutoFrame() {
        gui();
    }

    private void gui() {
        // FRAME beállítás
        pluto.setVisible(true);
        pluto.setTitle("Plutó hallgatói tanulmányirendszer");
        pluto.setDefaultCloseOperation(EXIT_ON_CLOSE);
        pluto.setSize(500, 250);
        pluto.setLocationRelativeTo(null);
        pluto.setResizable(false);
        SpringLayout sl = new SpringLayout();
        panel.setLayout(sl);
    }
}

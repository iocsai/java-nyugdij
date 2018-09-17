/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyugdij;

import java.awt.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import static java.lang.System.exit;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author Ócsai
 */
public class Grafikus {
    
    private static JFrame mainWindow;
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenuItem newFile;
    private JMenuItem exitFile;
    private JMenu menuEdit;
    private JMenuItem setNyugdijEdit;
    private JMenuItem setSzabiEdit;
    
    
    public Grafikus(String title) {
        mainWindow = new JFrame(title);
    }
    
    public void setWindowProp(int width, int height, boolean isCenter) {
        mainWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainWindow.setSize(width, height);
        if (isCenter) {
            CenterOnScreen(mainWindow);
        }
        mainWindow.setVisible(true);
    }
    
    public void CenterOnScreen(JFrame frame) {
        Toolkit toolkit = frame.getToolkit();
        Dimension size = toolkit.getScreenSize();
        frame.setLocation(size.width/2 - frame.getWidth()/2,
                size.height/2 - frame.getHeight()/2);
    }

    public void menuBar() {
        menuBar = new JMenuBar();
        fileMenu();
        editMenu();
        mainWindow.setJMenuBar(menuBar);
    }

    private JMenu createMenu(String title, int event) {
        JMenu nMenu = new JMenu(title);
        menuBar.add(nMenu);
        nMenu.setMnemonic(event);
        return nMenu;
    }

    private void fileMenu() {
        menuFile = createMenu("File", KeyEvent.VK_F);
        
        //Új nyugdíj számolás menüpont
        newFile = new JMenuItem("Új számolás", KeyEvent.VK_N);
        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, 
                ActionEvent.ALT_MASK));
        newFile.getAccessibleContext()
                .setAccessibleDescription("Új számolás egy másik dátummal.");
        //ide jön a hátralévő napok kiszámolására készülő metódus hívása!!!
        menuFile.add(newFile);
        
        //Kilépés menüpont
        exitFile = new JMenuItem("Kilépés", KeyEvent.VK_F4);
        exitFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 
                ActionEvent.ALT_MASK));
        
        menuFile.add(exitFile);
    }

    private void editMenu() {
        menuEdit = createMenu("Edit", KeyEvent.VK_E);
        
        //Nyugdíj első napjának módosítása
        setNyugdijEdit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 
                ActionEvent.ALT_MASK));
        
    }

    public void AButton() {
        JButton button = new JButton("Kiszámol");
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.out.println("I was selected");
                System.out.printf("Nyugdíjig hátralévő munkanapok száma: %d%n", 
                        Nyugdij.retire.workdays());
            }
        };
        button.addActionListener(listener);
        mainWindow.add(button, BorderLayout.SOUTH);
    }
    
    
}

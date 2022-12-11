package com.maseratirex;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;

public class Click4Ever extends JFrame {
    JTabbedPane tabbedPane;
    public Click4Ever() throws IOException, ClassNotFoundException {
        setTitle("Click4Ever");
        setLayout(new BorderLayout());
        setSize(300, 450);
        setResizable(false);

        tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT); //Allows you to scroll through AutoClicker tabs
        tabbedPane.setFocusable(false);

        loadAutoClickers(); //Load AutoClicker objects from config.txt, add them to AutoClicker.clickers array. If we can't load, add a default AutoClicker
        for(int i = 0; i < AutoClicker.clickers.size(); i++){
            AutoClicker.clickers.get(i).setup(tabbedPane); //Create GUIs for each AutoClicker
        }
        add(tabbedPane);

        JMenuBar menuBar = new JMenuBar(); //Now add a button for adding new auto clickers that will be situated in the menu bar
        JMenu addTab = new JMenu("Add new auto clicker");
        addTab.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                AutoClicker clicker = new AutoClicker();
                AutoClicker.clickers.add(clicker);
                clicker.setup(tabbedPane);
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });
        menuBar.add(addTab);
        menuBar.setLayout(new GridBagLayout());
        setJMenuBar(menuBar);

        addWindowListener(new WindowAdapter() { //On close, save all AutoClicker objects to config.txt
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    FileOutputStream fos = new FileOutputStream("src/main/resources/config.txt");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(AutoClicker.clickers);
                    oos.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.exit(0);
            }
        });

        setVisible(true);
    }
    private void loadAutoClickers(){
        try { //Try to load AutoClicker objects from config.txt
            FileInputStream fis = new FileInputStream("src/main/resources/config.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            AutoClicker.clickers = (ArrayList<AutoClicker>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) { //If we cannot load, create a default AutoClicker
            AutoClicker.clickers.add(new AutoClicker());
            try {
                FileOutputStream fos = new FileOutputStream("src/main/resources/config.txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(AutoClicker.clickers);
                oos.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if(System.getProperty("os.name").toLowerCase().contains("mac")){
            System.setProperty( "apple.awt.application.appearance", "system" ); //Header bar will be dark/light based on macOS theme
            System.setProperty("apple.eawt.quitStrategy", "CLOSE_ALL_WINDOWS"); //So that Command-Q properly closes app
        }

        FlatDarkLaf.setup();
        new Click4Ever();
    }
}

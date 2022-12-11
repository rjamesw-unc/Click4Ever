package com.maseratirex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;

public class AutoClicker implements Serializable {
    public static ArrayList<AutoClicker> clickers = new ArrayList<>();
    private String name;
    private String toggleKey = "A";
    private String toggleButton = "";
    private double minCPS = 8.0;
    private double maxCPS = 12.0;
    private boolean isNamed;

    public AutoClicker(){
        isNamed = false;
    }
    public void setup(JTabbedPane tabbedPane){
        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
        setupTab(tabbedPane, mainPane); //Creates a tab in tabbedPane with mainPane as the tab's body
        JPanel upperArea = new JPanel();
        setupToggleArea(upperArea);
        setupClickTypeArea(upperArea);
        setupRepeatOptionsArea(upperArea);
        setupSetCPSArea(upperArea);
        mainPane.add(upperArea);
        setupDelete(tabbedPane, mainPane);
    }
    private void setupTab(JTabbedPane tabbedPane, JPanel mainPane){
        tabbedPane.addTab("", mainPane); //Add mainPane to the tab
        int tabCount = tabbedPane.getTabCount();
        JPanel panelInsideTabComponent = new JPanel(); //A panel inside the tab component (situated in the tab bar)
        if(isNamed){
            panelInsideTabComponent.add(new JLabel(name));
            tabbedPane.setTabComponentAt(tabCount-1, panelInsideTabComponent);
        } else {
            JTextField fieldForAutoClickerName = new JTextField("Unnamed");
            //If user presses enter this listener will be fired. This then sets the name of the clicker and removes the text field
            fieldForAutoClickerName.addActionListener(e -> {
                name = fieldForAutoClickerName.getText();
                isNamed = true;

                panelInsideTabComponent.remove(fieldForAutoClickerName);
                panelInsideTabComponent.add(new JLabel(name));
                tabbedPane.getTopLevelAncestor().revalidate();
                tabbedPane.getTopLevelAncestor().repaint();
            });
            panelInsideTabComponent.add(fieldForAutoClickerName);
            tabbedPane.setTabComponentAt(tabCount-1, panelInsideTabComponent);
            tabbedPane.setSelectedIndex(tabCount-1);
            fieldForAutoClickerName.requestFocus();
            tabbedPane.setComponentAt(tabCount-1, mainPane);
        }
    }
    private void setupToggleArea(JPanel mainPane){
        JPanel toggleArea = new JPanel();
        toggleArea.setLayout(new BoxLayout(toggleArea, BoxLayout.LINE_AXIS));
        toggleArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Toggling"),
                BorderFactory.createEmptyBorder(5,5,5,5)));

        JLabel setToggleKeyLabel = new JLabel("Toggle Key/Button");
        toggleArea.add(setToggleKeyLabel);

        toggleArea.add(Box.createRigidArea(new Dimension(10,0)));

        JTextField toggleKeyField = new JTextField();
        toggleKeyField.setHorizontalAlignment(JTextField.CENTER);
        toggleKeyField.setText(toggleButton + toggleKey);
        toggleKeyField.setEditable(false);
        toggleKeyField.setOpaque(true);
        toggleKeyField.setBackground(new Color(0x323232));
        toggleKeyField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        toggleKeyField.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        toggleKeyField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                toggleButton = "Mouse " + e.getButton();
                toggleKey = "";
                toggleKeyField.setText(toggleButton);
                toggleKeyField.setFocusable(false);
                toggleKeyField.setFocusable(true);
            }
        });
        toggleKeyField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                toggleKey = KeyEvent.getKeyText(keyCode);
                toggleButton = "";
                toggleKeyField.setText(toggleKey);
                toggleKeyField.setFocusable(false);
                toggleKeyField.setFocusable(true);
            }
        });
        toggleArea.add(toggleKeyField);
        mainPane.add(toggleArea);
    }
    private void setupClickTypeArea(JPanel mainPane){
        JPanel clickTypeArea = new JPanel();
        //clickTypeArea.setLayout(new BoxLayout(clickTypeArea, BoxLayout.LINE_AXIS));
        clickTypeArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Click Type"),
                BorderFactory.createEmptyBorder(5,5,5,5)));

        ButtonGroup clickTypeChoices = new ButtonGroup();
        JRadioButton leftClickButton = new JRadioButton("Left Click");
        JRadioButton rightClickButton = new JRadioButton("Right Click");
        clickTypeChoices.add(leftClickButton);
        clickTypeChoices.add(rightClickButton);
        mainPane.add(clickTypeArea);
        clickTypeArea.add(leftClickButton);
        clickTypeArea.add(rightClickButton);
    }
    private void setupRepeatOptionsArea(JPanel mainPane){
        JPanel repeatOptionsArea = new JPanel();
        repeatOptionsArea.setLayout(new BoxLayout(repeatOptionsArea, BoxLayout.Y_AXIS));
        repeatOptionsArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Repeat Type"),
                BorderFactory.createEmptyBorder(5,5,5,5)));

        ButtonGroup repeatTypeChoices = new ButtonGroup();
        JRadioButton whileHeldDown = new JRadioButton("Repeat While Toggle Key Held Down");
        JRadioButton whileToggled = new JRadioButton("Repeat Until Toggle Key Pressed Again");
        repeatTypeChoices.add(whileHeldDown);
        repeatTypeChoices.add(whileToggled);
        mainPane.add(repeatOptionsArea);
        repeatOptionsArea.add(whileHeldDown);
        repeatOptionsArea.add(whileToggled);
    }
    private void setupSetCPSArea(JPanel mainPane){
        JPanel setCPSArea = new JPanel();
        setCPSArea.setLayout(new BoxLayout(setCPSArea, BoxLayout.Y_AXIS));
        setCPSArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Set CPS"),
                BorderFactory.createEmptyBorder(5,5,5,5)));

        JPanel minCPSArea = new JPanel();
        JLabel setMinCPSLabel = new JLabel("Minimum CPS");
        minCPSArea.add(setMinCPSLabel);

        minCPSArea.add(Box.createRigidArea(new Dimension(10,0)));

        JTextField minCPSField = new JTextField();
        minCPSField.setHorizontalAlignment(JTextField.CENTER);
        minCPSField.setText(String.valueOf(minCPS));
        minCPSField.setOpaque(true);
        minCPSField.setBackground(new Color(0x323232));
        minCPSField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        minCPSField.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        minCPSArea.add(minCPSField);
        setCPSArea.add(minCPSArea);

        JPanel maxCPSArea = new JPanel();
        JLabel setMaxCPSLabel = new JLabel("Maximum CPS");
        maxCPSArea.add(setMaxCPSLabel);

        maxCPSArea.add(Box.createRigidArea(new Dimension(10,0)));

        JTextField maxCPSField = new JTextField();
        maxCPSField.setHorizontalAlignment(JTextField.CENTER);
        maxCPSField.setText(String.valueOf(maxCPS));
        maxCPSField.setOpaque(true);
        maxCPSField.setBackground(new Color(0x323232));
        maxCPSField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        maxCPSField.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        maxCPSArea.add(maxCPSField);
        setCPSArea.add(maxCPSArea);
        mainPane.add(setCPSArea);
    }
    private void setupDelete(JTabbedPane tabbedPane, JPanel mainPane){
        //Add button to tab for deleting auto clicker.
        JButton deleteButton = new JButton("Delete");
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPane.add(deleteButton);

        deleteButton.addActionListener(e -> {
            if(tabbedPane.getTabCount()>1){
                tabbedPane.remove(mainPane);
                clickers.remove(AutoClicker.this);
                tabbedPane.getTopLevelAncestor().revalidate();
                tabbedPane.getTopLevelAncestor().repaint();
            }
        });
    }
}

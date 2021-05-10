import javax.swing.*;

import java.awt.Dimension;
import java.awt.event.*;
import java.io.*;

public class SweeperFrame extends JFrame{

    private SweeperModel model;
    private SweeperPanel panel;

    public SweeperFrame(){
        model = new SweeperModel("Easy");
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel = new SweeperPanel(this));
        setResizable(false);

        try{
            JMenuBar menuBar = new JMenuBar();
            JMenu menu = new JMenu("File");

            JMenuItem saveItem = new JMenuItem("Save");
            JMenuItem loadItem = new JMenuItem("Load");
            JMenuItem newItem = new JMenuItem("New");
            JMenuItem quitItem = new JMenuItem("Quit");
            JFileChooser chooser = new JFileChooser();

            saveItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    try {
                        chooser.showSaveDialog(null);
                        if(chooser.getSelectedFile() != null){
                            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(chooser.getSelectedFile()));
                            out.writeObject(model);
                            out.close();
                        }
                    }
                    catch(IOException e){
                        System.out.println(e.getMessage());
                    }
                }
            });

            loadItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    try{
                        chooser.showOpenDialog(null);
                        if(chooser.getSelectedFile() != null){
                            ObjectInputStream in = new ObjectInputStream(new FileInputStream(chooser.getSelectedFile()));
                            Object obj = in.readObject();
                            newPanel((SweeperModel) obj);
                            in.close();
                        }
                    }
                    catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            });

            newItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    try{
                        JFrame select = new JFrame();
                        select.setTitle("Select Difficulty");
                        select.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        JPanel selectPanel = new JPanel(null);
                        selectPanel.setPreferredSize(new Dimension(130,130));
                        JComboBox<String> combo = new JComboBox<>();

                        combo.setBounds(20,30,100,20);
                        combo.addItem("Easy");
                        combo.addItem("Medium");
                        combo.addItem("Hard");
                        selectPanel.add(combo);

                        JButton enter = new JButton("Enter");
                        enter.addActionListener(new ActionListener(){
                            public void actionPerformed(ActionEvent ev) {
                                select.dispose();
                                newPanel(new SweeperModel(combo.getSelectedItem().toString()));
                            }
                        });

                        enter.setBounds(20,100,100,20);
                        selectPanel.add(enter);
                        select.add(selectPanel);
                        select.pack();
                        select.setVisible(true);

                    }
                    catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            });

            quitItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    System.exit(0);
                }
            });

            menu.add(newItem);
            menu.add(saveItem);
            menu.add(loadItem);
            menu.add(quitItem);
            menuBar.add(menu);
            setJMenuBar(menuBar);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        pack();
        setVisible(true);
    }

    public SweeperModel getModel() {
	    return model;
    }
    public void newPanel(SweeperModel newModel){
        this.model = newModel;
        setSize(new Dimension(model.getTileWidth() * (model.getTileArray())[0].length,
        model.getTileHeight() * ((model.getTileArray()).length+1) + 13));
        panel.rebuild();
        validate();
    }  
}
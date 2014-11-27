import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowStateListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Tyler_Atiburcio
 */
public class AtiburcioTyler5 {

    public static void main(String[] args) {
        new TextEditor();
    }

    protected static class TextEditor extends JFrame {

        //Frame Stuff
        private final int INITAL_WIDTH = 500, INITAL_HEIGHT = 500;
        protected final TextEditor INSTANCE = this;
        private final String TITLE_SUFIX = " - CSCI 2912 Editor";

        //Panel Stuff
        private ArrayList<Container> containers = new ArrayList<Container>();
        private JPanel panel = new JPanel();

        //Container Stuff
        private JScrollPane scrollPane;
        private JTextArea textArea;
        private JScrollBar vBar;

        //Offsets
        private final int OFFSET_WIDTH = 20;
        private final int OFFSET_HEIGHT = 40;
        
        public TextEditor() {

            //Frame stuff
            this.setTitle("Untitled" + TITLE_SUFIX);
            this.setSize(INITAL_WIDTH, INITAL_HEIGHT);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);

            //Used to center the Window to the center of the screen no matter what computer you are using
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            this.setMaximizedBounds(null);
            //this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

            //To adjust the size of the text area when the frame size is adjusted
            this.addComponentListener(new JFrameComponentAdaptor());

            //Container stuff
            this.makeContainers();
            for (Container c : this.containers) {
                this.panel.add(c);
            }
            this.add(this.panel);

            //make it visable to the user
            this.setVisible(true);

        }

        public void makeContainers() {
            //Make Text Area
            this.textArea = new JTextArea();
            //this.textArea.setPreferredSize(this.getSize());
            this.textArea.setWrapStyleWord(true);
            //this.containers.add(this.textArea);
            
            //Make ScrollPane
            this.scrollPane = new JScrollPane(this.textArea);
            this.getContentPane().add(this.scrollPane);
            this.scrollPane.setAutoscrolls(true);
            this.scrollPane.setViewportView(this.textArea);
            this.scrollPane.setBounds(0, 0, this.getWidth()-OFFSET_WIDTH, this.getHeight()-OFFSET_HEIGHT);
            this.scrollPane.setPreferredSize(new Dimension(this.getWidth()-OFFSET_WIDTH, this.getHeight()-OFFSET_HEIGHT));
            this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        }
        
        /**
         * Adjusts Text Area field when the JFrame size is adjusted
         */
        private class JFrameComponentAdaptor extends ComponentAdapter {

            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                INSTANCE.scrollPane.setSize(new Dimension(INSTANCE.getWidth()-OFFSET_WIDTH, INSTANCE.getHeight()-OFFSET_HEIGHT));
                INSTANCE.scrollPane.setPreferredSize(new Dimension(INSTANCE.getWidth()-OFFSET_WIDTH, INSTANCE.getHeight()-OFFSET_HEIGHT));
                INSTANCE.scrollPane.setBounds(0, 0, INSTANCE.getWidth()-OFFSET_WIDTH, INSTANCE.getHeight()-OFFSET_HEIGHT);

            }
        }

    }

}

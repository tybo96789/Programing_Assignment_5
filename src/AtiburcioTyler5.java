import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**Programming Assignment 5
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
        //private JScrollBar vBar;                                              //mark for removal
        private JMenu menu;
        private JMenuBar menuBar;
        private JMenuItem newItem;
        private JMenuItem openItem;
        private JMenuItem saveItem;
        private JMenuItem saveAsItem;
        private JMenuItem exitItem;
        

        //Offsets
        private final int MENU_BAR_HEIGHT = 20;
        private final int SCROLL_PANE_YOFFSET = MENU_BAR_HEIGHT;
        private final int SCROLL_PANE_OFFSET_WIDTH = 5;                         //Check if this works...
        private final int SCROLL_PANE_OFFSET_HEIGHT = 25 + MENU_BAR_HEIGHT;     //Not sure if this is the same for different computers
        
        public TextEditor() {

            //Frame stuff
            this.setTitle("Untitled" + TITLE_SUFIX);
            this.setSize(INITAL_WIDTH, INITAL_HEIGHT);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);

            //Used to center the Window to the center of the screen no matter what computer you are using
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            this.setMaximizedBounds(null);
            this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

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
            //Make menu bar
            this.menuBar = new JMenuBar();
            this.menuBar.setPreferredSize(new Dimension(this.getWidth(),MENU_BAR_HEIGHT));
            this.menuBar.setBounds(0, 0, this.menuBar.getWidth(), MENU_BAR_HEIGHT);
            this.menu = new JMenu("File");
            this.menuBar.add(this.menu);
            this.containers.add(this.menuBar);
            
            //Make MenuItems
            this.newItem = new JMenuItem("New");
            this.openItem = new JMenuItem("Open");
            this.saveItem = new JMenuItem("Save");
            this.saveAsItem = new JMenuItem("Save as");
            this.exitItem = new JMenuItem("Exit");
            this.menu.add(this.newItem);
            this.menu.add(this.openItem);
            this.menu.add(this.saveItem);
            this.menu.add(this.saveAsItem);
            this.menu.add(this.exitItem);
            
            
            
            //Make Text Area
            this.textArea = new JTextArea();
            //this.textArea.setPreferredSize(this.getSize());                   //mark for removal
            this.textArea.setWrapStyleWord(true);                               //unsure if its work
            //this.containers.add(this.textArea);                               //mark for removal
            this.textArea.setFont(new Font(Font.MONOSPACED,Font.PLAIN,14));
            
            //Make ScrollPane
            this.scrollPane = new JScrollPane(this.textArea);
            this.getContentPane().add(this.scrollPane);
            this.scrollPane.setAutoscrolls(true);
            this.scrollPane.setViewportView(this.textArea);
            this.scrollPane.setBounds(0, SCROLL_PANE_YOFFSET, this.getWidth()-SCROLL_PANE_OFFSET_WIDTH, this.getHeight()-SCROLL_PANE_OFFSET_HEIGHT);
            this.scrollPane.setPreferredSize(new Dimension(this.getWidth()-SCROLL_PANE_OFFSET_WIDTH, this.getHeight()-SCROLL_PANE_OFFSET_HEIGHT));
            this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            
            
        }
        
        /**
         * Adjusts Text Area field (Scrollpane) when the JFrame size is adjusted
         */
        private class JFrameComponentAdaptor extends ComponentAdapter {

            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                //Adjust ScrollPane Size
                INSTANCE.scrollPane.setSize(new Dimension(INSTANCE.getWidth()-SCROLL_PANE_OFFSET_WIDTH, INSTANCE.getHeight()-SCROLL_PANE_OFFSET_HEIGHT));
                INSTANCE.scrollPane.setPreferredSize(new Dimension(INSTANCE.getWidth()-SCROLL_PANE_OFFSET_WIDTH, INSTANCE.getHeight()-SCROLL_PANE_OFFSET_HEIGHT));
                INSTANCE.scrollPane.setBounds(0, SCROLL_PANE_YOFFSET, INSTANCE.getWidth()-SCROLL_PANE_OFFSET_WIDTH, INSTANCE.getHeight()-SCROLL_PANE_OFFSET_HEIGHT);
                
                //Adjust MenuBar size
                INSTANCE.menuBar.setSize(new Dimension(INSTANCE.getWidth(),MENU_BAR_HEIGHT));
                INSTANCE.menuBar.setPreferredSize(new Dimension(INSTANCE.getWidth(),MENU_BAR_HEIGHT));
                INSTANCE.menuBar.setBounds(0, 0, INSTANCE.menuBar.getWidth(), MENU_BAR_HEIGHT);            
            }
        }

    }

}

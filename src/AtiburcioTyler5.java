import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileFilter;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

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
        
        //Document Stuff
        private File file;
        private ArrayList<Character> data;                                         //Check usage
        protected boolean isModifyed = false;

        //Offsets
        //private final int MENU_BAR_HEIGHT = 20;                               //not needed
        //private final int SCROLL_PANE_YOFFSET = MENU_BAR_HEIGHT;              //not needed
        private final int SCROLL_PANE_OFFSET_WIDTH = 5;               //5          //Check if this works...
        private final int SCROLL_PANE_OFFSET_HEIGHT = 25;               //25            //Not sure if this is the same for different computers
        
        public TextEditor() {

            //Frame stuff
            this.setTitle("Untitled" + TITLE_SUFIX);
            this.setSize(INITAL_WIDTH, INITAL_HEIGHT);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.addWindowListener(new CloseWindowListener());

            //Used to center the Window to the center of the screen no matter what computer you are using
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            this.setMaximizedBounds(null);
            this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

            //To adjust the size of the text area when the frame size is adjusted
            this.addComponentListener(new JFrameComponentAdaptor());            //Check Usage

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
            //this.menuBar.setPreferredSize(new Dimension(this.getWidth(),MENU_BAR_HEIGHT));
            //this.menuBar.setBounds(0, 0, this.menuBar.getWidth(), MENU_BAR_HEIGHT);
            this.menu = new JMenu("File");
            this.setJMenuBar(menuBar);
            this.menuBar.add(this.menu);
            //this.containers.add(this.menuBar);                                //Replaced to this.setJMenuBar
            
            //Make MenuItems
            this.newItem = new JMenuItem("New");
            this.openItem = new JMenuItem("Open");
            this.saveItem = new JMenuItem("Save");
            this.saveAsItem = new JMenuItem("Save as");
            this.exitItem = new JMenuItem("Exit");
            
            
            //Make MenuItems Accelerators
            this.newItem.setAccelerator(KeyStroke.getKeyStroke('N',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(),true));
            this.openItem.setAccelerator(KeyStroke.getKeyStroke('O',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(),true));
            this.saveItem.setAccelerator(KeyStroke.getKeyStroke('S',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(),true));
            this.saveAsItem.setAccelerator(KeyStroke.getKeyStroke('S',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() + InputEvent.SHIFT_DOWN_MASK,true));
            
            //Make MenuItems Mnemonic
            this.newItem.setMnemonic('N');
            this.openItem.setMnemonic('O');
            this.saveItem.setMnemonic('S');
            this.saveAsItem.setMnemonic('A');
            this.exitItem.setMnemonic('X');
            
            //Register Listeners to menuItems
            this.newItem.addActionListener(new NewDocumentListener());
            this.openItem.addActionListener(new OpenDocumentListener());
            this.saveItem.addActionListener(new SaveDocumentListener());
            this.saveAsItem.addActionListener(new SaveAsDocumentListener());
            this.exitItem.addActionListener(new ExitListener());
            
            //Add to Menu
            this.menu.add(this.newItem);
            this.menu.add(this.openItem);
            this.menu.add(this.saveItem);
            this.menu.add(this.saveAsItem);
            this.menu.add(this.exitItem);
            
            
            
            //Make Text Area
            this.textArea = new JTextArea();
            //this.textArea.setPreferredSize(this.getSize());                   //mark for removal
            this.textArea.setWrapStyleWord(true);                               //unsure if its work
            //set bounds for text area
            //this.containers.add(this.textArea);                               //mark for removal
            this.textArea.setFont(new Font(Font.MONOSPACED,Font.PLAIN,14));
            this.textArea.setLineWrap(true);                                    //Broken
            //this.textArea.setBounds(0, 0, this.getWidth()-SCROLL_PANE_OFFSET_WIDTH, this.getHeight()-SCROLL_PANE_OFFSET_HEIGHT);
            //this.textArea.addKeyListener(new KeyPressedListener());

            
            
            //Make ScrollPane
            this.scrollPane = new JScrollPane(this.textArea);
            this.getContentPane().add(this.scrollPane, BorderLayout.CENTER);
            this.scrollPane.setAutoscrolls(true);
            this.scrollPane.setViewportView(this.textArea);
            this.scrollPane.setBounds(0, 0, this.getWidth()-SCROLL_PANE_OFFSET_WIDTH, this.getHeight()-SCROLL_PANE_OFFSET_HEIGHT);
            this.scrollPane.setPreferredSize(new Dimension(this.getWidth()-SCROLL_PANE_OFFSET_WIDTH, this.getHeight()-SCROLL_PANE_OFFSET_HEIGHT));
            //this.scrollPane.setPreferredSize(this.getSize());
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
                //Removed because BorderLayout.CENTER is suppose to fix this problem
                
                INSTANCE.scrollPane.setSize(new Dimension(INSTANCE.getWidth()-SCROLL_PANE_OFFSET_WIDTH, INSTANCE.getHeight()-SCROLL_PANE_OFFSET_HEIGHT));
                INSTANCE.scrollPane.setPreferredSize(new Dimension(INSTANCE.getWidth()-SCROLL_PANE_OFFSET_WIDTH, INSTANCE.getHeight()-SCROLL_PANE_OFFSET_HEIGHT));
                INSTANCE.scrollPane.setBounds(0, 0, INSTANCE.getWidth()-SCROLL_PANE_OFFSET_WIDTH, INSTANCE.getHeight()-SCROLL_PANE_OFFSET_HEIGHT);
                
                //INSTANCE.textArea.setBounds(0, 0, INSTANCE.getWidth()-SCROLL_PANE_OFFSET_WIDTH, INSTANCE.getHeight()-SCROLL_PANE_OFFSET_HEIGHT);
                //INSTANCE.textArea.setLineWrap(rootPaneCheckingEnabled);
                
                //Adjust MenuBar size 
                //Removed because this.setJmenubar handles menu size
                /*
                INSTANCE.menuBar.setSize(new Dimension(INSTANCE.getWidth(),MENU_BAR_HEIGHT));
                INSTANCE.menuBar.setPreferredSize(new Dimension(INSTANCE.getWidth(),MENU_BAR_HEIGHT));
                INSTANCE.menuBar.setBounds(0, 0, INSTANCE.menuBar.getWidth(), MENU_BAR_HEIGHT); 
                */
                
            }
        }
        
        private class NewDocumentListener implements ActionListener
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                INSTANCE.detectChanges();
                if(INSTANCE.isModifyed)
                {
                    switch(JOptionPane.showConfirmDialog(null, "Do you want to save changes?", "Hey User!", JOptionPane.YES_NO_CANCEL_OPTION))
                {
                    case 0:
                    {
                        if(INSTANCE.file != null)
                        {
                            INSTANCE.saveData();
                            INSTANCE.dispose();
                            System.exit(0);
                            break;
                        }
                    }
                    
                    case 1:
                    {
                        INSTANCE.textArea.setText("");
                        INSTANCE.setTitle("Untitled"+TITLE_SUFIX);
                        break;
                    }
                }
                }
                else
                {
                    INSTANCE.textArea.setText("");
                    INSTANCE.setTitle("Untitled"+TITLE_SUFIX);
                }
            }
            
        }
        
        private class OpenDocumentListener implements ActionListener
        {
            private JFileChooser chooser = new JFileChooser();
            private Scanner fileScan = null;
            private ArrayList<String> dataImport = new ArrayList<String>();
            private StringBuilder builder = new StringBuilder();
            @Override
            public void actionPerformed(ActionEvent e) {
                this.chooser.showOpenDialog(null);
                try{
                    if(this.chooser.getSelectedFile() != null && !INSTANCE.isModifyed)
                    {
                        INSTANCE.file = this.chooser.getSelectedFile();
                        if(!INSTANCE.file.isFile()) throw new Exception("Not a File");
                        this.fileScan = new Scanner(INSTANCE.file);
                        while(this.fileScan.hasNext()) this.dataImport.add(this.fileScan.nextLine());
                        for(String s : this.dataImport) this.builder.append(s).append("\n");
                        INSTANCE.setTitle(INSTANCE.file.getName() + INSTANCE.TITLE_SUFIX);
                        INSTANCE.textArea.setText(this.builder.toString());
                    }
                    else if (INSTANCE.isModifyed)
                    {
                        if(INSTANCE.file != null) INSTANCE.saveData();
                        else INSTANCE.saveDialog();
                        
                        INSTANCE.file = this.chooser.getSelectedFile();
                        if(!INSTANCE.file.isFile()) throw new Exception("Not a File");
                        this.fileScan = new Scanner(INSTANCE.file);
                        while(this.fileScan.hasNext()) this.dataImport.add(this.fileScan.nextLine());
                        for(String s : this.dataImport) this.builder.append(s).append("\n");
                        INSTANCE.setTitle(INSTANCE.file.getName() + INSTANCE.TITLE_SUFIX);
                        INSTANCE.textArea.setText(this.builder.toString());
                        
                    }
                }catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error Opening File", JOptionPane.ERROR_MESSAGE);
                }
                finally
                {
                    if(this.fileScan != null) this.fileScan.close();
                }
                
            }
            
        }
        
        private void saveData()
        {
            PrintStream out  = null;
                    try{
                        out =  new PrintStream(INSTANCE.file);
                        for(String s : copyDataToArray()) out.println(s);
                        out.flush();
                        out.close();
                        INSTANCE.isModifyed = false;
                    
                            }catch(Exception ex)
                            {
                                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error Opening File", JOptionPane.ERROR_MESSAGE);
                            }
        }
        
        private class SaveDocumentListener implements ActionListener
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(INSTANCE.file == null) INSTANCE.saveDialog();
                else
                {
                    INSTANCE.saveData();
                }
            }
            
        }
        
        private void saveDialog()
        {
            JFileChooser chooser = new JFileChooser();
            PrintStream out  = null;
            chooser.showSaveDialog(null);
            this.file = chooser.getSelectedFile();
            
            //To attempt to limit the file extension type to a text file
            FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
            chooser.setFileFilter(filter);
            
            try{
                
                if(file != null){
                    out =  new PrintStream(this.file);
                    for(String s : copyDataToArray()) out.println(s);
                    out.flush();
                    out.close();
                    this.setTitle(this.file.getName()+TITLE_SUFIX);
                    INSTANCE.isModifyed = false;
                }
                
            }catch(Exception e)
            {                    
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error Saving File", JOptionPane.ERROR_MESSAGE);
 
            }
            
        }
        
        private class SaveAsDocumentListener implements ActionListener
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                INSTANCE.saveDialog();
            }
            
        }
        
        private class ExitListener implements ActionListener
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                INSTANCE.endingAction();
            }
            
        }
        
        private void endingAction()
        {
            INSTANCE.detectChanges();
            if(INSTANCE.isModifyed)
            {
                //System.out.println(JOptionPane.showConfirmDialog(null, "Do you want to save unsaved changes?", "Hey User!", JOptionPane.YES_NO_CANCEL_OPTION));
                switch(JOptionPane.showConfirmDialog(null, "Do you want to save changes?", "Hey User!", JOptionPane.YES_NO_CANCEL_OPTION))
                {
                    case 0:
                    {
                        if(this.file != null)
                        {
                            INSTANCE.saveData();
                            INSTANCE.dispose();
                            System.exit(0);
                            break;
                        }
                    }
                    
                    case 1:
                    {
                        INSTANCE.dispose();
                        System.exit(0);
                        break;
                    }
                }
                        
                
                //cancel op 2
                //no op 1
                //yes op 0
            }
            else
            {
                INSTANCE.dispose();
                System.exit(0);
            }
        }
        
        private ArrayList<String> copyDataToArray()
        {
            ArrayList<String> temp = new ArrayList<String>();
            StringTokenizer token = new StringTokenizer(INSTANCE.textArea.getText(),"\n",true);
            while(token.hasMoreElements()) temp.add(token.nextToken());
            return temp;
        }
        
        private class CloseWindowListener implements WindowListener {

        public CloseWindowListener() {
        }

        @Override
        public void windowOpened(WindowEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void windowClosing(WindowEvent e) {
            INSTANCE.endingAction();
        }

        @Override
        public void windowClosed(WindowEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void windowIconified(WindowEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void windowActivated(WindowEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

        private void detectChanges()
        {
            if(this.data == null) this.isModifyed = true;
            else
            {
                for(char c : this.data) for(char v : this.textArea.getText().toCharArray()) 
                    if(c != v)
                    {
                        this.isModifyed = true;
                        break;
                    }
                    
            }
        }
        
    }

    

}

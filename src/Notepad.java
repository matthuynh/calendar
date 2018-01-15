//Import Packages
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/* Notepad Class
 * This Class creates a basic mockup of the famous program Notepad. 
 * This has a JTextArea where the user can write their note or whatever they need to. 
 * 
 * The user will also, using the Menu and Menubar class have the option to Save, Open or Close any file. 
 * MenuBar will allow to create a bar which will add options to Save/Close/Open
 * Menu will allow to create individual buttons, that will do certain task. 
 *     The Save Menu option will allow the user to save the file
 *     The Close Menu Option will allow the user to close the file
 *     The Open Menu option will allow the user to open any file through the JFileChooser
 * 
 * Each Menu Option will have its own Shortcut - KeyEvent Class
 * 
 * @since Jan 20, 2017
 * @author N.Patel
 */ 
public class Notepad extends JPanel implements ActionListener {
     
     //Instance Variables for the Notepad class 
     
     private JFrame mainFrame = new JFrame(); 
     
     private JTextArea text = new JTextArea(); //Area for the User to input text
     private JScrollPane textArea = new JScrollPane(text); //When the User adds too much text, it creates more space to move to see text
     
     private JMenuBar menuBar = new JMenuBar(); // MenuBar Item - Create Bar with Menu Options
     private JMenu menuSelection = new JMenu(); // This creates individual tabs which will display differnt menu options
     
     private JMenuItem openFile = new JMenuItem();  // Menu Option - Opens JFileChooser to open file
     private JMenuItem saveFile = new JMenuItem(); // Menu Option - Save the current file using JFileChooser
     private JMenuItem close = new JMenuItem(); // Menu Option - Close Currently Open File
     
     /*Default Constructor*/ 
     public Notepad() {
          
          //Frame Initialization
          text.setLineWrap(true); 
          mainFrame.setSize(500, 500); 
          mainFrame.setLocation(300,100);
          mainFrame.setTitle("Awesome(ish) Notepad"); 
          mainFrame.setMinimumSize(new Dimension(300, 300)); 
          mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
          textArea.setFont(new Font("Times New Roman", Font.PLAIN, 12)); // set a default font for the TextArea
          mainFrame.getContentPane().add(textArea);
          mainFrame.setVisible(true); 
          
          // add our menu bar into the GUI
          mainFrame.setJMenuBar(this.menuBar); //set the menubar
          menuBar.add(this.menuSelection); // MenuSelection is like a panel, which will hold the 3 menu options
          menuSelection.setLabel("Notepad Options");

          //Adding the Opening 
          openFile.setLabel("Open a File"); // set the label of the menu item
          openFile.addActionListener(this);
          openFile.setAccelerator(KeyStroke.getKeyStroke('O',java.awt.event.InputEvent.CTRL_DOWN_MASK));
          menuSelection.add(this.openFile); // add it to the menu
          
          //Adding the Save
          saveFile.setLabel("Save a File");
          saveFile.addActionListener(this);
          saveFile.setAccelerator(KeyStroke.getKeyStroke('S',java.awt.event.InputEvent.CTRL_DOWN_MASK));
          menuSelection.add(this.saveFile);
          
          //Adding the Close
          close.setLabel("Close the File");
          close.setAccelerator(KeyStroke.getKeyStroke('X',java.awt.event.InputEvent.CTRL_DOWN_MASK));
          this.close.addActionListener(this);
          this.menuSelection.add(this.close);
     }
     
     public void actionPerformed (ActionEvent e) 
     {//Start of Actionperformed 
          //If Hit Close
          if (e.getSource() == this.close)
          {
               mainFrame.dispose(); //Clears the notepad, and closes it. 
          }
          
          //If Hit Open
          //Using the JFileChooser, will choose the file that the user wants to display
          else if (e.getSource() == this.openFile)
          {//start of Else if for Open
               JFileChooser open = new JFileChooser();
               int option = open.showOpenDialog(this); 
               
               if (option == JFileChooser.APPROVE_OPTION)
               {
                    this.text.setText(""); // clear the TextArea before applying the file contents
                    try {
                         Scanner scan = new Scanner(new FileReader(open.getSelectedFile()));
                         while (scan.hasNext())
                         {
                              this.text.append(scan.nextLine() + "\n");
                         }
                    } catch (Exception ex) 
                    { 
                         System.out.println(ex.getMessage());
                    }
               }
          }//End of Else if for Open
          
          //Save option
          else if (e.getSource() == this.saveFile) 
          {//Start of Else if for Save
               JFileChooser save = new JFileChooser(); 
               save.setSelectedFile(new File(".txt"));
               int option = save.showSaveDialog(this); 
               
               if (option == JFileChooser.APPROVE_OPTION) 
               {
                    try {
                         // create a buffered writer to write to a file
                         PrintWriter out = new PrintWriter(new FileWriter(save.getSelectedFile()));
                         out.write(this.text.getText()); // write the contents of the TextArea to the file
                         out.close(); // close file
                    } catch (Exception ex)
                    { 
                         System.out.println(ex.getMessage());
                    }
               }
          }//End of Else if for Save
     }//End of Action Performed.
}//End of Class
//import packages
import javax.swing.*; 
import java.awt.*; 

/**ReminderView
  * Displays when the user first starts up the program, and if there is an event for that day
  * @authors M.Huynh, M. Zaidi, N. Patel, R. Chan
  * @since Jan 22, 2017
  */ 
public class ReminderView extends JPanel{
  //Creating Swing Components for the Event View
  //Main Frame
  private JFrame mainFrame = new JFrame(); 
  private JLabel textLabel;
  //private JButton closeWindow = new JButton("OK");

  /*Constructor for EventView
   * @param passedModel - an object of the Model class
   */
  public ReminderView(){
    super();
    this.displayGUI();
    //this.registerControllers();
  }
  
  /*Draws the GUI by adding all JPanels and JComponents
   */ 
  private void displayGUI(){
    //Panels
    JPanel mainPanel = new JPanel(); //Main panel, bring everything together
    JPanel textPanel = new JPanel(); //holds the error text
    
    //Error Text Panel
    this.textLabel = new JLabel("You have an event today! Click on today's day on the calendar to view your event(s)!");
    textPanel.add(this.textLabel);
    
    BorderLayout layout = new BorderLayout();
    mainPanel.setLayout(layout);
    mainPanel.add(textPanel, BorderLayout.CENTER);
    //mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    
    mainFrame.setSize(500,100); 
    mainFrame.setLocation(300,200);
    mainFrame.setMinimumSize(new Dimension(500,50));
    mainFrame.setContentPane(mainPanel);
    mainFrame.setTitle("You have an event today");
    mainFrame.setVisible(true); 
    mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
  }
}//End of Class loop
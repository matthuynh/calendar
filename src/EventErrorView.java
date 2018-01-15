//import packages
import javax.swing.*; 
import java.awt.*; 

/**EventErrorView
  * Displays if the user tries to add an Event on an invalid date (eg. Feb 31), then this GUI will popup explaining why the user cannot add an event for that date
  * @authors M.Zaidi, R.Chan, N.Patel, M.Huynh
  * @since Jan 22, 2017
  */ 
public class EventErrorView extends JPanel{
  //Creating Swing Components for the Event View
  //Main Frame
  private JFrame mainFrame = new JFrame(); 
  private JLabel errorLabel;
  private JButton closeWindow = new JButton("OK");
  private String errorMessage;

  /*Constructor for EventView
   * @param passedModel - an object of the Model class
   */
  public EventErrorView(String message){
    super();
    this.errorMessage = message;
    this.displayGUI();
    this.registerControllers();
  }
  
  
  /*Draws the GUI by adding all JPanels and JComponents
   */ 
  private void displayGUI(){
    //Panels
    JPanel mainPanel = new JPanel(); //Main panel, bring everything together
    JPanel textPanel = new JPanel(); //holds the error text
    JPanel buttonPanel = new JPanel(); //holds the button
    
    //Error Text Panel
    this.errorLabel = new JLabel(this.errorMessage);
    textPanel.add(this.errorLabel);
    
    //Button panel
    this.closeWindow.setPreferredSize(new Dimension(100,50)); 
    buttonPanel.add(this.closeWindow);
    
    BorderLayout layout = new BorderLayout();
    mainPanel.setLayout(layout);
    mainPanel.add(textPanel, BorderLayout.CENTER);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    
    mainFrame.setSize(500,150); 
    mainFrame.setLocation(300,200);
    mainFrame.setMinimumSize(new Dimension(500,150));
    mainFrame.setContentPane(mainPanel);
    mainFrame.setTitle("Error");
    mainFrame.setVisible(true); 
    mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
  }
  
  /* Hides the EventErrorView and clears the text
   * This method is called in the EventErrorViewController, and should run after the user clicks "OK"*/
  public void clearWindow(){
    mainFrame.setVisible(false);
  }
  
  /*Assigns the EventErrorViewController to the "Finalize Event" JButton*/
  private void registerControllers(){
    EventErrorViewController controller = new EventErrorViewController(this);
    this.closeWindow.addActionListener(controller);
  }  
}//End of Class loop
//import packages
import javax.swing.*; 
import java.awt.*; 
import java.util.*; 
import java.lang.StringBuffer;

/* ShowAllEventView Class
 * This Class shows all the events. 
 * Once the user Clicks "Show All Events" This view would be engaged, and would display all the events in a specific order. 
 * @author M.Huynh, M.Zaidi
 * @@since Jan 22, 2017
 */
public class ShowAllEventsView extends JPanel{
  //Instance Variables
  private Model model;
  private Event event;
 
  //Creating Swing Components for the Event View  
  private JFrame mainFrame = new JFrame();//main frame
  private JPanel mainPanel = new JPanel(); //Main panel, bring everything together
  private JPanel userTopInputPanel = new JPanel(); //holds the user instruction text
  private JPanel userBottomInputPanel = new JPanel(); //holds the user text input field and button
  private JLabel userInstructions = new JLabel("<html>You may choose to remove a single event, remove all events, or sort events by newest/oldest.<br>You may choose to remove events by typing in their event number below and clicking \"Remove Event\"<br>Careful! Choosing to \"Remove All Events\" will erase EVERYTHING.</html>", SwingConstants.CENTER);; //displays the instructions on how to remove an event
  private JButton removeEventButton = new JButton("Remove Event");   //Button to confirm the removal of event
  private JButton removeAllEventsButton = new JButton("Remove All Events");
  private JButton sortByNewestDateButton = new JButton("<html>Sort by Date<br>(Newest Events First)</html>");
  private JButton sortByOldestDateButton = new JButton("<html>Sort by Date<br>(Oldest Events First)</html>");
  private JTextField userInputNumber = new JTextField("", 40); //user inputs the number of the event
  private JTextArea events = new JTextArea(""); //text box that holds all the events
  private JScrollPane eventsScrollPane;
  private ArrayList<Event> eventList; //Stores all Events
  private int[] eventsDateValue;
    
  /*Constructor
   *@param Model - An Instance of the model is passed
   */
  public ShowAllEventsView(Model passedModel){
    super();
    this.model = passedModel;
    this.eventList = this.model.getEventList();
    eventsDateValue = new int[this.eventList.size()];
    this.registerControllers();
  }
  
  /* DisplayGUI Method - Displays the GUI of the ShowAllEvents
   */ 
  public void displayGUI(){  
    Boolean hasEvents = false;
    
    //Method that fills up the events text area
    for(int i = 0; i < this.eventList.size(); i++){
      hasEvents = true;
      StringBuffer message = new StringBuffer();
      message.append("-----------------------------\n"); 
      message.append("Event: " + (i+1) + "\n");
      message.append("Day: " + String.valueOf(eventList.get(i).getDayDate()) + "\n");
      message.append("Month: " + this.model.convertMonthToString(eventList.get(i).getMonthDate()) + "\n");
      message.append("Year: " + String.valueOf(eventList.get(i).getYearDate())+ "\n");
      message.append("Title: " + eventList.get(i).getEventName() + "\n");
      message.append("Description: " + eventList.get(i).getEventDescription() + "\n");
      message.append("-----------------------------\n");
      this.events.append(message.toString());
    }
    //If events exist, then the buttons will be enabled for use
    if(hasEvents){
      this.userInputNumber.setEditable(true);
      this.removeEventButton.setEnabled(true);
      this.removeAllEventsButton.setEnabled(true);
      this.sortByNewestDateButton.setEnabled(true);
      this.sortByOldestDateButton.setEnabled(true);
    }
    //If no events exist, then the buttons will be disabled
    else{
      this.events.append("There are currently no events!"); 
      disableInput();
    }
    events.setCaretPosition(0);
    this.eventsScrollPane = new JScrollPane(events);
    this.eventsScrollPane.setWheelScrollingEnabled(true);
    this.events.setBorder(BorderFactory.createTitledBorder("Events"));   
    this.events.setEditable(false);
    
    //user top panel
    BorderLayout topInputLayout = new BorderLayout();
    this.userTopInputPanel.setLayout(topInputLayout);
    this.userTopInputPanel.add(this.sortByNewestDateButton, BorderLayout.EAST);
    this.userTopInputPanel.add(this.sortByOldestDateButton, BorderLayout.WEST);
    this.userTopInputPanel.add(this.userInstructions, BorderLayout.CENTER);
    
    //User input panel
    BorderLayout bottomInputLayout = new BorderLayout();
    this.userBottomInputPanel.setLayout(bottomInputLayout);
    this.userBottomInputPanel.add(this.removeAllEventsButton, BorderLayout.WEST);
    this.userBottomInputPanel.add(this.userInputNumber, BorderLayout.CENTER);
    this.userBottomInputPanel.add(this.removeEventButton, BorderLayout.EAST);

    //Main panel
    BorderLayout mainLayout = new BorderLayout();
    this.mainPanel.setLayout(mainLayout);
    this.mainPanel.add(this.userTopInputPanel, BorderLayout.NORTH);
    this.mainPanel.add(this.eventsScrollPane, BorderLayout.CENTER);
    this.mainPanel.add(this.userBottomInputPanel, BorderLayout.SOUTH);
   
    this.mainFrame.setSize(900,400); 
    this.mainFrame.setLocation(300,200);
    this.mainFrame.setContentPane(this.mainPanel);
    this.mainFrame.setTitle("Show All Events");
    this.mainFrame.setVisible(true); 
    this.mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); 
    this.mainFrame.setMinimumSize(new Dimension(900, 400));
  }
  
  /*Sorts the EventList array by placing older events first
   */ 
  public void sortOldest(){ 
    Collections.sort(this.eventList, new Comparator<Event>() {
      public int compare(Event one, Event other){
        return one.getIntValueInString().compareTo(other.getIntValueInString());
      }
    });
    
    this.model.refreshText(this.eventList);
  }
  
  /*Sorts the EventList array by placing newer events first
   */
  public void sortNewest(){ 
    Collections.sort(this.eventList, new Comparator<Event>() {
      public int compare(Event one, Event other){
        return one.getIntValueInString().compareTo(other.getIntValueInString()) * -1;
      }
    });
    
    this.model.refreshText(this.eventList);
  }
  
  
  /**Assigns the controllers
    * This runs when the user clicks "Remove Event"
    */
  private void registerControllers(){
    ShowAllEventsViewController controller1 = new ShowAllEventsViewController(this.model, this.userInputNumber, this, this.removeEventButton);
    this.removeEventButton.addActionListener(controller1);
    
    ShowAllEventsViewController controller2 = new ShowAllEventsViewController(this.model, this.userInputNumber, this, this.removeAllEventsButton);
    this.removeAllEventsButton.addActionListener(controller2);
    
    ShowAllEventsViewController controller3 = new ShowAllEventsViewController(this.model, this.userInputNumber, this, this.sortByNewestDateButton);
    this.sortByNewestDateButton.addActionListener(controller3);
    
    ShowAllEventsViewController controller4 = new ShowAllEventsViewController(this.model, this.userInputNumber, this, this.sortByOldestDateButton);
    this.sortByOldestDateButton.addActionListener(controller4);
  }
  
  public void hideWindow(){
    this.mainFrame.setVisible(false);
  } 
  
  /*Disables the text field and Jbutton
   * Called from the ShowAllEventsViewController after the user successfully removes an event
   */
  public void disableInput(){
    this.userInputNumber.setEditable(false);
    this.removeEventButton.setEnabled(false);
    this.removeAllEventsButton.setEnabled(false);
    this.sortByNewestDateButton.setEnabled(false);
    this.sortByOldestDateButton.setEnabled(false);
  }
}
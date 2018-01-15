//import packages
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.StringBuffer;

/**DayView
  * This class creates a GUI that shows any Events that exist on the Day selected
  * @author M. Zaidi, N. Patel, M. Huynh, R. Chan
  * @since Jan 22, 2017
  */ 
public class DayView extends JPanel
{
  //Instance Variables
  private static JFrame day = new JFrame();
  private static JPanel date = new JPanel();
  private static JPanel displayEvents = new JPanel();
  private JLabel selectedDay = new JLabel();
  private JTextArea eventDisplay = new JTextArea("");
  private JScrollPane eventScrollPane = new JScrollPane(eventDisplay);
  private JButton addEventButton = new JButton("Add Event");
  private String dayN;
  private ArrayList<Event> eventList = new ArrayList<Event>(); //Stores all Events that take place on the current date
  private Model model;
  
  /* Class Constructor
   * The DayView constructer is called in the CalendarController, when the user clicks on a Day button
   * @param String d - the "Day" eg. (Jan 22 will have a d value of 22)
   * @param Model aModel - the Model
   */ 
  public DayView(String d, Model aModel){
    this.dayN = d;
    this.model = aModel;
    this.selectedDay.setText("Day: " + this.dayN);
    
    //Calls upon the getEvents() method to check if there are any events on the given day
    if(getEvents()){
      for(int i = 0; i < this.eventList.size(); i++){ //runs for every event there is on the given day
        StringBuffer message = new StringBuffer();
        message.append("-----------------------------\n"); 
        message.append("Day: " + String.valueOf(eventList.get(i).getDayDate()) + "\n");
        message.append("Month: " + this.model.convertMonthToString(eventList.get(i).getMonthDate()) + "\n");
        message.append("Year: " + String.valueOf(eventList.get(i).getYearDate())+ "\n");
        message.append("Title: " + eventList.get(i).getEventName() + "\n");
        message.append("Description: " + eventList.get(i).getEventDescription() + "\n");
        message.append("-----------------------------\n");
        this.eventDisplay.append(message.toString());
      }
    }
    else{
     this.eventDisplay.append("There are currently no events for this day! You may add an event by clicking \"Add Event\"."); 
    }
    
    this.registerControllers();
    this.displayGUI();
  }
  

  /*Draws the GUI
   */ 
  public void displayGUI()
  {
    eventList.clear(); //clears any preexisting events that may have existed in a DayView for a separate day
    date.removeAll();
    date.revalidate();
    
    //panel for date
    date.setLayout(new BorderLayout());
    date.add(selectedDay, BorderLayout.CENTER);
    date.add(addEventButton, BorderLayout.EAST);
    date.setBorder(BorderFactory.createTitledBorder("Date"));   
    
    displayEvents.removeAll();
    displayEvents.revalidate();
    
    //panel for events
    displayEvents.setLayout(new BorderLayout());
    displayEvents.add(this.eventScrollPane, BorderLayout.CENTER);
    displayEvents.setBorder(BorderFactory.createTitledBorder("Events"));  
    eventDisplay.setEditable(false); //disables the user's ability to edit the JTextArea
     
    this.removeAll();
    this.revalidate();
    
    //complete layout
    this.setLayout(new BorderLayout());
    this.add(date, BorderLayout.NORTH);
    this.add(displayEvents, BorderLayout.CENTER);
    
    //main frame
    day.setSize(new Dimension(800, 600));
    //day.setMinimumSize(new Dimension(800, 600));
    day.setLocation(300,200);
    day.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    day.setContentPane(this);
    day.setTitle("Day View");
    day.setVisible(true);  
  }
  
  /*Registers the Controllers for the "Add Event" button
   */ 
  private void registerControllers(){
    DayViewController controller1 = new DayViewController(this.model, this, this.dayN, String.valueOf(this.model.returnCurrentMonthInt()), String.valueOf(this.model.returnCurrentYearInt()));
    this.addEventButton.addActionListener(controller1);    
  }
  
  /*Gets all of the Event objects from the Model, and only sorts through the Event objects that match the date of this DayView
   * @returns - true if the DayView has any events to show
   * @returns - false if the DayView has no events to show
   */ 
  private Boolean getEvents(){
    Boolean hasEvents = false; //stores whether or not any events match
    int currentDay = Integer.valueOf(this.dayN);
    int currentMonth = this.model.returnCurrentMonthInt();
    int currentYear = this.model.returnCurrentYearInt();
    ArrayList<Event> tempList = this.model.getEventList(); //A temporary ArrayList that will store a list of all the events
    
    //Goes through the tempList to see if any of the Events match with the current day
    for(int i = 0; i < tempList.size(); i++){
      if((tempList.get(i).getDayDate() == currentDay) && (tempList.get(i).getMonthDate() == currentMonth) && (tempList.get(i).getYearDate() == currentYear)){
        this.eventList.add(tempList.get(i)); //If the Events match, then the object from the tempList will be added to the eventList
        hasEvents=true;
      } 
    }
    return hasEvents;
  }
  
  /* Hides the DayView and clears the text
   * This method is called in the DayViewController, and should run after the user chooses to add an event*/
  public void clearWindow(){
    day.setVisible(false);
  }
}
//import packages
import javax.swing.*; 
import java.awt.*; 

/**Event View
  * Creates a GUI that allows the user to add an event, given a date, title and description
  * @author N.Patel, M.Zaidi, R.Chan, M.Huynh
  * @since Jan 22, 2017
  */ 
public class EventView extends JPanel{
  private Model mainModel;
  //Creating Swing Components for the Event View
  //Main Frame
  private JFrame mainFrame = new JFrame();
  //Labels for the Event View 
//  private JLabel lblMonth = new JLabel("Month: ");
//  private JLabel lblDate = new JLabel("Date: ");
//  private JLabel lblYear = new JLabel("Year: ");
  private JLabel lblTitle = new JLabel("Please enter the Event Title"); 
  private JLabel lblDescrip = new JLabel("Please enter any additional information for the Event"); 
  
  //Button to confirm the addition of event
  private JButton addEvent = new JButton("Finalize Event");
  
  //Event Title and description 
  private JTextArea eventTitle = new JTextArea();
  private JTextArea eventDescrip = new JTextArea(); 
  private JScrollPane eventTitleScroll = new JScrollPane(eventTitle); 
  private JScrollPane eventDescripScroll = new JScrollPane(eventDescrip);
  
  //JComboBoxes
  private JComboBox<String> monthBox;
  private JComboBox<String> dateBox;
  private JComboBox<String> yearBox;
    
  /*Constructor for EventView
   * @param passedModel - an object of the Model class
   */
  public EventView(Model passedModel){
    super();
    this.mainModel = passedModel;
    this.registerControllers();
  }
  
  
  /*Draws the GUI by adding all JPanels and JComponents
   * This method is called in the CalendarController class and DayView class
   * @param presetDay - when called in the CalendarController class, value is null. when called in the DayView class, value is the current day of the DayView class
   * @param presetMonth - when called in the CalendarController class, value is String "0". when called in the DayView class, value is the current month of the DayView class
   * @param presetYear - when called in the CalendarController class, value is null. when called in the DayView class, value is the current year of the DayView class
   */ 
  public void displayGUI(String presetDay, String presetMonth, String presetYear){
    //Panels
    JPanel mainPanel = new JPanel(); //Main panel, bring everything together
    JPanel comboPanel = new JPanel(); //Panel that holds the 3 other panels - Month,Date and Year
    JPanel monthPanel = new JPanel(); //Month panel
    JPanel datePanel = new JPanel(); //Date Panel
    JPanel yearPanel = new JPanel(); //Year Panel
    
    //Variable Declaration
    int dateValue = 1; 
    int yearValue = 2017; 
    
    String[] months = new String[] {"January", "February", "March", "April","May","June","July","August", "September","October", "November", "December"};
    Integer [] date = new Integer[31];
    Integer [] year = new Integer[51];
    
    //Adds days 1 - 31 to the day JComboBox
    for(int x = 0; x<date.length ; x++){
      date[x] = dateValue; 
      dateValue++; 
    }
    
    //Adding the years 2017 to 2067 to the year JComboBox
    for(int y = 0; y <year.length; y++){
      year[y] = yearValue; 
      yearValue++; 
    }
    
    //JComboBox
    //this.monthBox = new JComboBox(months);
    this.monthBox = new JComboBox(months);
    this.dateBox = new JComboBox(date);
    this.yearBox = new JComboBox(year);
    //If the EventView was created from a DayView, then the JComboBox values will automatically be preset (eg. creating an event on January 7 will open an EventView with Janaury 7 already selected)
    if(!(presetMonth.equals("0"))){
      this.dateBox.setSelectedItem(date[Integer.valueOf(presetDay) - 1]);
      this.monthBox.setSelectedItem(months[Integer.valueOf(presetMonth) - 1]);
      this.yearBox.setSelectedItem(year[Integer.valueOf(presetYear) - 2017]);
    }
    
    //Adding LINEWRAP for the textArea's
    this.eventTitle.setLineWrap(true); 
    this.eventDescrip.setLineWrap(true); 
    
    //Setting the size of the button
    this.addEvent.setPreferredSize(new Dimension(10,5)); 
    
    
    //Month Panel
    this.monthBox.setPreferredSize(new Dimension(100, 20));
    GridLayout layout = new GridLayout(2,1);
    monthPanel.setLayout(layout);
    monthPanel.add(new JLabel("Months: "));
    monthPanel.add(monthBox);
    
    
    //Date Panel
    dateBox.setPreferredSize(new Dimension(100, 20));
    //GridLayout layout2 = new GridLayout(2,1);
    datePanel.setLayout(layout);
    datePanel.add(new JLabel("Date: "));
    datePanel.add(this.dateBox);
    
    //Year Panel
    this.yearBox.setPreferredSize(new Dimension(100, 20));
    //GridLayout layout3 = new GridLayout(2,1);
    yearPanel.setLayout(layout);
    yearPanel.add(new JLabel("Year: "));
    yearPanel.add(this.yearBox);
    
    comboPanel.add(monthPanel); 
    comboPanel.add(datePanel);
    comboPanel.add(yearPanel); 
    
    
    //Adding Componetns to main Panel to display to the Frame
    mainPanel.setLayout(new GridLayout(6,1)); 
    
    //Adding the combobox for the Month,date and year
    mainPanel.add(comboPanel);
    
    //Adding the Title Label and the Title TextArea
    mainPanel.add(this.lblTitle);
    mainPanel.add(this.eventTitleScroll); 
    
    //Adding the Descrip label and the Descrip TextArea
    mainPanel.add(this.lblDescrip); 
    mainPanel.add(this.eventDescripScroll); 
    //Adding the Button
    mainPanel.add(this.addEvent); 
    mainPanel.setBorder(BorderFactory.createTitledBorder("Event View"));
    
    
    mainFrame.setSize(500,400); 
    mainFrame.setLocation(300,200);
    mainFrame.setContentPane(mainPanel);
    mainFrame.setTitle("Add Events");
    mainFrame.setVisible(true); 
    mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
    mainFrame.setMinimumSize(new Dimension(500, 400));
  }
  
  /* Hides the EventView and clears the text
   * This method is called in the EventViewController, and should run after the user clicks "Finalize Event"*/
  public void clearWindow(){
    mainFrame.setVisible(false);
    this.eventTitle.setText(""); 
    this.eventDescrip.setText(""); 
  }
  
  /*Assigns the EventViewController to the "Finalize Event" JButton*/
  private void registerControllers(){
    EventViewController controller = new EventViewController(this.mainModel, this.eventTitle, this.eventDescrip, this);
    this.addEvent.addActionListener(controller);
  }
  
  /*Returns the day that the user selects from the EventView day JComboBox
   * @returns this.dateBox.getSelectedItem().toString(); - the variable stored in the JComboBox*/ 
  public String getDateComboBoxValue(){
    return(this.dateBox.getSelectedItem().toString());
  }
  
  /*Returns the month that the user selects from the EventView month JComboBox
   * @returns this.monthBox.getSelectedItem().toString(); - the variable stored in the JComboBox*/ 
  public String getMonthComboBoxValue(){
    return(this.monthBox.getSelectedItem().toString());
  }
    
  /*Returns the year that the user selects from the EventView year JComboBox
   * @returns this.yearBox.getSelectedItem().toString(); - the variable stored in the JComboBox*/ 
  public String getYearComboBoxValue(){
    return(this.yearBox.getSelectedItem().toString());
  }
}//End of Class loop
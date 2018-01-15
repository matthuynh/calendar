//import packages
import java.text.SimpleDateFormat;
import javax.swing.*;
import java.awt.*;
import java.util.*;

/* CalendarView Class
 * This creates the view of the Calendar.
 * Addes the dates, adds the buttons and everything else that is going to be visible to the user. 
 * @author M.Zaidi, N. Patel, R.Chan, M. Huynh
 * @since Jan 22, 2017
 */
public class CalendarView extends JPanel  
{
  //Instance Variables 
  private JButton notePad; //Once Clicked will open a basic notepad
  private JButton addEvent; //AddEvent JButton
  private JButton showEvents; //Remove Event JButton
  private JButton dayButton; //Individual Day Button 
  private JButton previousMonth; //"<<" or Previous Month Button
  private JButton nextMonth; // ">>" or Next Month button
  private JButton goToToday; //Go to Today Button
  private JButton goToYear; //Go to specific year button 
  private JButton changeBG; //Changing bg color
  private JButton changeCD; // changing current day color
  private JButton[] dayButtons; //ArrayList of all day buttons
  private JButton reminderButton; //button that shows whether or not an event is taking place that day
  public JComboBox selectYear; //JComboBox for all the years
  private Color backgroundColor;
  private Color currentDayColor;
  private Color eventColor;
  private JTextArea currentDate; //Displays the current Date 
  
  private Model model; //Creates instance of the Model
  private GregorianCalendar gCal; //Creates an instance of the GregorianCalendar
  private GregorianCalendar realTimeGCal; //Creates an instance of the GregorianCalendar in REAL TIME (eg. the Day/Month/Year values of this GregorianCalendar will never be changed)
  private GregorianCalendar temp; //Creates a temp instance of the Gregoian Calendar
  private EventView eview; //Instance of the eventView
  private ShowAllEventsView sview; //Instance of the ShowAllEventView
  
  private JPanel flowPanel; //Temp Panel to add NotePad button 
  private JPanel calendarPanel; //Panel for the Calendar
  private JPanel monthPanel; //Panel for the Month
  private JPanel datePanel; //Panel for the Date
  private JPanel top; //Panel for the Top of the Calendar View
  private JPanel buttonPanel; //Panel for the Buttons at the bottom of the Calendar View
  private JPanel changeYear; //Label to naviagate the user to change the year
  private JPanel legendPanel; //Panel for the color legend 
  private JPanel secondTop; //Panel that holds the monthPanel, legendPanel, and reminder button
  private JLabel currentMonth; //Label for the current Month
  private JLabel currentYear; //label for the current Year
  private JLabel currentDayColorExplanation;
  private JLabel eventColorExplanation;
  
  
  private int nodim;  //Number of day
  private int dayOfWeek; //Day of the week it is (0-6)
  private int dayOfMonth; //Day of the Month 1-31
  
  
  private Date dateObject; //Instance of the Date object
  private Date realTimeDateObject; //Instance of the Real Time Date
  private SimpleDateFormat mf; //SimpleDateFormat for the month
  private String monthInString; //String of the month
  private String yearInString; //String of the year
  private SimpleDateFormat df;//SimpleDateFormat for the date
  private String dateInString; //Date in STring
  private String[] year; //Array of All the years (50+2017)  
  private int firstDayOfMonth;//First Date of the Month - What specific date does it fall on - Monday =1, 
  private String realTimeDateInString; //String of the real time date. the real time date is the current time, it cannot be changed.
  private int realTimeYear; //String of the real year
  private int realTimeMonth; //String of the real month
  private int realTimeDay; //String of the real day
  
  /*Class Constructor
   * @param aModel - the model provided to the CalendarView class from the StartUp
   */ 
  public CalendarView(Model aModel){
    super();
    this.model = aModel; //Assigning the model
    this.gCal = this.model.getCal(); //gets the master time from the model
    this.realTimeGCal = this.model.getRealTime(); //gets the real time from the model
    
    //Initializing all other instance variables
    notePad = new JButton("Open Notepad");
    notePad.setMaximumSize(new Dimension(10,10));
    addEvent = new JButton("Add Event");
    showEvents = new JButton("Show Events");
    previousMonth = new JButton("<<");
    nextMonth = new JButton(">>");
    goToToday = new JButton("Go To Today");
    dayButton = new JButton();
    goToYear = new JButton("Change Year");
    changeBG = new JButton("Change Buttons Colour");
    changeCD = new JButton("Change Current Day Colour");
    reminderButton = new JButton();
    goToYear.setEnabled(false);
    calendarPanel = new JPanel(new GridLayout(0, 7));
    flowPanel = new JPanel(); 
    monthPanel= new JPanel();
    datePanel= new JPanel();
    top= new JPanel();
    buttonPanel= new JPanel();
    changeYear = new JPanel();
    legendPanel = new JPanel();
    secondTop = new JPanel();
    currentMonth = new JLabel();
    currentYear = new JLabel();
    currentDate = new JTextArea();
    currentDayColorExplanation = new JLabel("Current Day Color");
    eventColorExplanation = new JLabel("Event Color");
    
    backgroundColor = this.model.getBG();
    currentDayColor = this.model.getCD();
    eventColor = new Color(160,0,0);
    currentDayColorExplanation.setForeground(currentDayColor);
    eventColorExplanation.setForeground(eventColor);
    mf =  new SimpleDateFormat("MMMMMMMMMMM");
    df = new SimpleDateFormat("EEE, MMM dd, yyyy");
    year = new String[]{"Select Year","2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029",
      "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040", "2041", "2042", "2043", "2044",
      "2045", "2046", "2047", "2048", "2049", "2050", "2051", "2052", "2053", "2054", "2055", "2056", "2057", "2058", "2058",
      "2060", "2061", "2062", "2063", "2064", "2065", "2066", "2067"};
    selectYear = new JComboBox(year);
    
    dayButtons = new JButton[gCal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)+1];
    temp = new GregorianCalendar();
    
    this.layoutView();
    this.registerControllers();
  }
  /* SetGUI Method
   * This method sets the GUI for the entire model*/ 
  public void setGUI(){
    this.model.setGUI(this);  
  }
  
  
  /*LayoutView
   * This displays the objects and organizes the way of which they will appear on the screen*/
  public void layoutView()
  {
    
    //Assigning the NotePad button to a standard flow layout. 
    //This is to keep the button size the same when adding to DatePanel
    this.flowPanel.add(this.notePad); 
        
    this.gCal = this.model.getCal();
    this.dateObject = this.gCal.getTime();
    this.realTimeDateObject = this.realTimeGCal.getTime();
    
    //date panel
    this.datePanel.removeAll();
    this.datePanel.revalidate();
    this.datePanel.setLayout(new BorderLayout());
    
    this.dateInString = this.df.format(this.dateObject);    //Attaching the format to the dateInString
    this.realTimeDateInString = this.df.format(this.realTimeDateObject); 
    this.realTimeMonth =  this.model.convertMonthToInt(this.mf.format(this.realTimeDateObject));
    this.realTimeYear =  Integer.valueOf(this.realTimeDateInString.substring(13,17));
    this.realTimeDay =  Integer.valueOf(this.realTimeDateInString.substring(9,11));
    this.yearInString = this.dateInString.substring(13,17);
    this.currentDate.setText("Date: " + this.realTimeDateInString); //Assigning current date to be displayed
    this.currentDate.setEditable(false);
    this.currentDate.setOpaque(false);
    this.datePanel.add(this.goToToday,BorderLayout.WEST); 
    this.datePanel.add(flowPanel, BorderLayout.CENTER); 
    this.datePanel.add(this.currentDate,BorderLayout.EAST);
    this.datePanel.revalidate();
    
    //month selector panel
    this.monthPanel.removeAll();
    this.monthPanel.revalidate();
    
    this.monthInString = this.mf.format(this.dateObject); //Assigning the monthInString a format and setting the text
    this.currentMonth.setText(this.monthInString);
    this.currentYear.setText(this.yearInString);
    
    this.monthPanel.add(this.previousMonth);
    this.monthPanel.add(this.currentMonth);
    this.monthPanel.add(this.currentYear);
    this.monthPanel.add(this.nextMonth);
    this.monthPanel.revalidate();
    
    //legend panel
    this.legendPanel.setLayout(new BorderLayout());
    //this.legendPanel.setBackground(Color.BLACK);
    this.legendPanel.add(this.currentDayColorExplanation, BorderLayout.NORTH);
    this.legendPanel.add(this.eventColorExplanation, BorderLayout.SOUTH);

    //reminder button
//    System.out.println(this.realTimeMonth); //the real month
//    System.out.println(this.realTimeDay);
//    System.out.println(this.realTimeYear);
    if(this.model.checkForEvents(this.realTimeDay,this.realTimeMonth,this.realTimeYear)){
      this.reminderButton.setText("<html>There is an event today!<br>Click to view</html>");
      this.reminderButton.setEnabled(true);
    }
    else{
      this.reminderButton.setText("There are no events today");
      this.reminderButton.setEnabled(false);
    }
      
    
    //second top panel
    this.secondTop.add(Box.createRigidArea(new Dimension(50,10)));
    this.secondTop.add(this.legendPanel);
    this.secondTop.add(Box.createRigidArea(new Dimension(100,10)));
    this.secondTop.add(this.monthPanel);
    this.secondTop.add(Box.createRigidArea(new Dimension(100,10)));
    this.secondTop.add(this.reminderButton);
    
    //If the calendar is displaying January 2017, then the previousMonth "<<" button is disabled
    if(this.yearInString.equals("2017") && this.monthInString.equals("January")){
      this.previousMonth.setEnabled(false); 
    }
    if(this.yearInString.equals("2067") && this.monthInString.equals("December")){
     this.nextMonth.setEnabled(false); 
    }
    //date + month selector panel
    this.top.removeAll();
    this.top.revalidate();
    this.top.setLayout(new BorderLayout());
    this.top.add(this.datePanel, BorderLayout.NORTH); 
    this.top.add(this.secondTop, BorderLayout.CENTER);
    //this.top.add(this.legendPanel, BorderLayout.WEST);
    this.top.revalidate();
    
    //button panel
     this.buttonPanel.removeAll();
     this.buttonPanel.revalidate();
    
    this.layoutCalendar();

    //changeYear panel
    this.changeYear.add(this.changeBG);
    this.changeYear.add(this.selectYear);
    this.changeYear.add(this.goToYear);
    this.changeYear.add(this.changeCD);
    
    this.buttonPanel.setLayout(new BorderLayout());
    this.buttonPanel.add(this.addEvent, BorderLayout.WEST);
    this.buttonPanel.add(this.changeYear, BorderLayout.CENTER);
    this.buttonPanel.add(this.showEvents, BorderLayout.EAST);
    this.buttonPanel.revalidate();
    
    //main panel
    this.removeAll();
    this.revalidate();
    this.setLayout(new BorderLayout());
    this.add(this.top, BorderLayout.NORTH);
    this.add(this.calendarPanel, BorderLayout.CENTER);
    this.add(this.buttonPanel, BorderLayout.SOUTH);
  }
  
  
  /* Actually layingout the Calendar. 
   * The Buttons for the days
   */
  public void layoutCalendar(){
    
    
    //calendar panel
    this.calendarPanel.removeAll();
    this.calendarPanel.validate();
    this.calendarPanel.repaint();
    
    this.nodim = this.gCal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
    this.dayOfWeek = this.gCal.get(GregorianCalendar.DAY_OF_WEEK);
    this.dayOfMonth = this.gCal.get(GregorianCalendar.DAY_OF_MONTH);
    //System.out.println("day of week "+this.dayOfWeek);
    

    //JLabels for the days of the week. 
    JLabel sun = new JLabel("Sunday",SwingConstants.CENTER);
    JLabel mon = new JLabel("Monday",SwingConstants.CENTER);
    JLabel tues = new JLabel("Tuesday",SwingConstants.CENTER);
    JLabel wed = new JLabel("Wednesday",SwingConstants.CENTER);
    JLabel thurs = new JLabel("Thursday",SwingConstants.CENTER);
    JLabel fri = new JLabel("Friday",SwingConstants.CENTER);
    JLabel sat = new JLabel("Saturday",SwingConstants.CENTER);
    
    this.calendarPanel.add(sun); 
    this.calendarPanel.add(mon);
    this.calendarPanel.add(tues); 
    this.calendarPanel.add(wed);
    this.calendarPanel.add(thurs);
    this.calendarPanel.add(fri);
    this.calendarPanel.add(sat);
    
    
    //Seeing what day the first day of the month is at. black button for those
    if(this.dayOfWeek <= 7 && this.dayOfMonth <= 7){
      this.firstDayOfMonth = this.dayOfWeek;
    }
    else{
    this.temp.set(this.gCal.DAY_OF_MONTH, 1);
    this.firstDayOfMonth = this.temp.get(this.gCal.DAY_OF_WEEK);
    }
    //System.out.println("firstDayOfMonth: "+this.firstDayOfMonth);
    
    this.dayButton.removeAll();
    this.dayButton.revalidate();
    
    
   //Creating the day buttons and their values and colour. 
    int days = 1;
    int n = 1;
    while(days<=this.nodim){
      if (n < this.firstDayOfMonth){
        n++;
        this.dayButton = new JButton("");
        this.dayButton.setBackground(Color.BLACK);
        this.dayButton.setForeground(Color.BLACK);
        this.dayButton.setVisible(false);
        this.calendarPanel.add(this.dayButton);         
      }
      else{
        n++;
        this.dayButtons[days] = new JButton(String.valueOf(days));
        Calendar temp = this.gCal; //gCal Instance of the Today's Calendar
        int month = temp.get(Calendar.MONTH)+1; //Gets Today's month
        int year = temp.get(Calendar.YEAR); //Gets Today's Year
        Calendar realTimeCal = this.realTimeGCal;
        //System.out.println(days + " " + this.model.checkForEvents(days,month,year));
        if((days == this.realTimeGCal.get(GregorianCalendar.DAY_OF_MONTH)) && (month == realTimeCal.get(Calendar.MONTH)+1) && (year == realTimeCal.get(Calendar.YEAR))){
          dayButtons[days].setBackground(currentDayColor);
        }
        else{
          dayButtons[days].setBackground(backgroundColor);
        }
        
        if(this.model.checkForEvents(days,month,year)){
          dayButtons[days].setBackground(eventColor);
        }
        dayButtons[days].setForeground(Color.WHITE);
        this.calendarPanel.add(dayButtons[days]);
        days++;
      }
    }
    
    this.calendarPanel.revalidate();
    this.calendarPanel.repaint();
  }
  
  /* Register Controllers 
   * Allows the manipulation of the button upon action. 
   */
  public void registerControllers(){
    this.eview = new EventView(this.model);
    this.sview = new ShowAllEventsView(this.model);
    
    CalendarController c0 = new CalendarController(this.model,this.goToToday,"",this.eview, this.sview);
    this.goToToday.addActionListener(c0);
    
    CalendarController c1 = new CalendarController(this.model,this.addEvent,"",this.eview, this.sview);
    this.addEvent.addActionListener(c1);
    
    CalendarController c2 = new CalendarController(this.model,this.showEvents,"",this.eview, this.sview);
    this.showEvents.addActionListener(c2);
    
    CalendarController c3 = new CalendarController(this.model, this.nextMonth,"",this.eview, this.sview);
    this.nextMonth.addActionListener(c3);
    
    CalendarController c4 = new CalendarController(this.model, this.previousMonth,"",this.eview, this.sview);
    this.previousMonth.addActionListener(c4);
    
    for(int x = 1;x<=this.gCal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);x++){
      CalendarController c5 = new CalendarController(this.model,this.dayButtons[x],this.dayButtons[x].getText(),this.eview, this.sview);
      this.dayButtons[x].addActionListener(c5);
    }
    
    CalendarComboBoxController c6 = new CalendarComboBoxController(this.model,this,this.selectYear);
    this.selectYear.addActionListener(c6);
      
    CalendarController c7 = new CalendarController(this.model, this.goToYear,"",this.eview, this.sview);
    this.goToYear.addActionListener(c7);
    
    CalendarController c8 = new CalendarController(this.model, this.changeBG,"",this.eview, this.sview);
    this.changeBG.addActionListener(c8);
    
    CalendarController c9 = new CalendarController(this.model, this.changeCD,"",this.eview, this.sview);
    this.changeCD.addActionListener(c9);

    CalendarController c10 = new CalendarController(this.model,this.reminderButton,String.valueOf(this.realTimeDay),  this.eview, this.sview);
    this.reminderButton.addActionListener(c10);
    
    CalendarController c11 = new CalendarController(this.model,this.notePad,"",this.eview, this.sview);
    this.notePad.addActionListener(c11); 
  }
  
  /* Enables the year selection combo box
   * Allows the manipulation of the combobox upon action. 
   */
  public void enableGoToYear(boolean b){
    disableAll();
    this.goToYear.setEnabled(b);
  }
  
  /* Enables the year selection combo box selector
   * Disables all other buttons
   */
  private void disableAll(){
    this.addEvent.setEnabled(false);
    this.showEvents.setEnabled(false);
    this.dayButton.setEnabled(false);
    this.previousMonth.setEnabled(false);
    this.nextMonth.setEnabled(false);
    this.changeBG.setEnabled(false);
    this.changeCD.setEnabled(false);
    this.reminderButton.setEnabled(false);
    this.goToToday.setEnabled(false);
    for(int x = 1;x<=this.gCal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);x++){
      this.dayButtons[x].setEnabled(false);
    }
  }
  
  /* Returns the value of the year selected
   * 
   */ 
  public String getYearValue(){
    return this.selectYear.getSelectedItem().toString();
  }
  
  /* Sets the values for the buttons Colours
   * 
   */ 
  public void setBG(Color a){
    this.backgroundColor = a;
  }
  
   /* Sets the values for the current day Colour
   * 
   */ 
  public void setCD(Color a){
    this.currentDayColor = a;
  }
 
  
  /* UPDATES THE VIEW
   * This updates the entire calendar when called depending on what was actioned upon.
   */
  public void update(){
    this.removeAll();
    this.setVisible(false);
  }
    
}
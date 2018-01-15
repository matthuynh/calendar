//import packages
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.*; //for collection classes
import java.awt.*; //For colours

/* Model Class
 * This is where all the data is manipulated. 
 * This is where the data is stored and is accessed through accessor methods. 
 * 
 * Month and Date are calculated, Events are ordered, All the manipulation is done through model, and newly created data is accessed by other classes through accessor methods. 
 * 
 * @author M.Zaidi, M.Huynh, N. Patel, R. Chan
 * @since Jan 22, 2017
 */ 
public class Model extends Object{
  //Instance Variables
  private CalendarView view;
  private int currentDayOfMonth; //stores the current day in int (eg. 01 - 31)
  private int currentMonthOfYear; //stores the current year in int (eg. January is 01)
  private int currentYear; //stores the current year in int (eg. 2017)
  private int nodim; //stores the number of days in the current Month (number of days in month)
  private int dayOfWeek; //stores the day of the week in int - returns 1 if the day is Sunday, returns 3 if the day is Tuesday, returns 7 if the day is Saturday, etc. 
  private String dateInString; //stores the Date object as a String
  private int[][] cv; //declaration of the calendar view 2D array. This 2D array stores Day objects
  private ArrayList<Event> eventList;
  private GregorianCalendar gCal;
  private GregorianCalendar realTime;
  private FileWorker fw = new FileWorker();
  private Color bg; //Buttons colour
  private Color cd; //Current Day colour
  
  /*Constructor
   */ 
  public Model(){
    super();
    //Gets the current date using a GreogorianCalendar object, and stores it in a Date object so that its String display can be manipulated
    gCal = new GregorianCalendar();
    realTime = new GregorianCalendar();
    Date dateObject = gCal.getTime();
    view = new CalendarView(this);
    SimpleDateFormat df = new SimpleDateFormat("EEEddMMyyyy"); //Converts the date object to String using SimpleDateFormat - see comment at bottom for formatting
    this.dateInString = df.format(dateObject);//Formats a Date into a date/time string
    //System.out.println(this.dateInString + "\n");
    
    this.currentDayOfMonth = Integer.parseInt(this.dateInString.substring(3,5));
    this.currentMonthOfYear = Integer.parseInt(this.dateInString.substring(5,7));
    this.currentYear = Integer.parseInt(this.dateInString.substring(7,11));
    this.nodim = gCal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH); //should usually return a 30 or 31, 28 or 29 for February
    this.dayOfWeek = gCal.get(GregorianCalendar.DAY_OF_WEEK);
    this.bg = new Color(Integer.parseInt(fw.getBG()));
    this.cd = new Color(Integer.parseInt(fw.getCD()));
    
    this.eventList = new ArrayList <Event>();
  }
   
  
  /*Method to change to next month
   * When the user chooses to change the month (with the ">>" button)
   */
  public void changeToNextMonth(){
    int nextMonth; //temporary variable used to store the int value of the next month
    //System.out.println("The current month is (in number)" + this.currentMonthOfYear);
    if(this.currentMonthOfYear == 12){
      nextMonth = 01;
      this.currentYear++;
    }
    else{
      nextMonth = this.currentMonthOfYear + 1; 
    }
    changeDate(this.currentYear, nextMonth);
  }
  
  
  /*Method to change to previous month
   * When the user chooses to change the month (with the "<<" button)
   */
  public void changeToPreviousMonth(){
    int previousMonth; //temporary variable used to store the int value of the previous month
    if(this.currentMonthOfYear == 01){
      previousMonth = 12;
      this.currentYear--;
    }
    else{
      previousMonth = this.currentMonthOfYear - 1;   
    }
    changeDate(this.currentYear, previousMonth);
  }
  
  
  /*Alters the GregorianCalendar object (used when the user decides to change the month or year)
   * @param year - the year that the user wants to change to
   * @param month - the month that the user wants to change to
   */
  public void changeDate(int year, int month){
    //Constructs a GregorianCalendar with the given date set in the default time zone with the default locale. (int year, int month, int dayOfMonth)
    GregorianCalendar newCal = new GregorianCalendar(year, month-1, 1); //day is set to 1 by default to make calculations faster
    //Converts the date object to String using SimpleDateFormat
    SimpleDateFormat df = new SimpleDateFormat("EEEddMMyyyy"); //HH:mm:ss Z D u"); //see comment at bottom for formatting
    Date dateObject = newCal.getTime();
    this.dateInString = df.format(dateObject);//Formats a Date into a date/time string
    //System.out.println(this.dateInString + "\n");
    
    this.currentDayOfMonth = 1; //set to 1 by default, this makes it easier to determine the format of the calendar
    this.currentMonthOfYear = Integer.parseInt(this.dateInString.substring(5,7));
    this.currentYear = Integer.parseInt(this.dateInString.substring(7,11));
    
    this.nodim = newCal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH); //should usually return a 30 or 31, 28 or 29 for February
    this.dayOfWeek = newCal.get(GregorianCalendar.DAY_OF_WEEK);
    
    this.gCal = newCal; //sets the master time of the model equal to this new time
  }
  
  
  /*Method overloading to use changeDate method for another command
   * @param int year - User Selected year
   * @param int month - User Selected Month ( Int value)
   * @param int day - user Selected DayValue
   */ 
  public void changeDate(int year, int month,int day){
    //Constructs a GregorianCalendar with the given date set in the default time zone with the default locale. (int year, int month, int dayOfMonth)
    GregorianCalendar newCal = new GregorianCalendar(year, month-1, day); //day is set to 1 by default to make calculations faster
    //Converts the date object to String using SimpleDateFormat
    SimpleDateFormat df = new SimpleDateFormat("EEEddMMyyyy"); //HH:mm:ss Z D u"); //see comment at bottom for formatting
    Date dateObject = newCal.getTime();
    this.dateInString = df.format(dateObject);//Formats a Date into a date/time string
    //System.out.println(this.dateInString + "\n");
    
    this.currentDayOfMonth = 1; //set to 1 by default, this makes it easier to determine the format of the calendar
    this.currentMonthOfYear = Integer.parseInt(this.dateInString.substring(5,7));
    this.currentYear = Integer.parseInt(this.dateInString.substring(7,11));
    
    this.nodim = newCal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH); //should usually return a 30 or 31, 28 or 29 for February
    this.dayOfWeek = newCal.get(GregorianCalendar.DAY_OF_WEEK);
    
    this.gCal = newCal; //sets the master time of the model equal to this new time
  }
  
  
  /*Method used to convert a month string to its int equivalent
    * This method is used throughout the program when it is necessary to conver a month to its integer value
    * eg. January = 01
    * eg. June = 05
    *@return String Month - the Month that is going to be converted
    */
  public int convertMonthToInt(String month){
    int monthInt; //stores the int value of the month
    if(month.equals("January")){
      monthInt = 1;
    }
    else if (month.equals("February")){
      monthInt = 2;
    }
    else if (month.equals("March")){
      monthInt = 3;
    }
    else if (month.equals("April")){
      monthInt = 4;
    }   
    else if (month.equals("May")){
      monthInt = 5;
    }
    else if (month.equals("June")){
      monthInt = 6;
    }
    else if (month.equals("July")){
      monthInt = 7;
    }
    else if (month.equals("August")){
      monthInt = 8;
    }
    else if (month.equals("September")){
      monthInt = 9;
    }
    else if (month.equals("October")){
      monthInt = 10;
    }
    else if (month.equals("November")){
      monthInt = 11;
    }
    else if (month.equals("December")){
      monthInt = 12;
    }
    else
      monthInt = 123456789; //error
    return monthInt;
  }
  
  
  /**Method used to convert a month int to its string equivalent
    * This method is used throughout the program when it is necessary to conver a month to its String value
    * eg. 01 = January
    * eg. 05 = June
    * @param int month - the month that is going to be converted
    * @return String - the converted month
    */
  public String convertMonthToString(int month){
    String monthString; //stores the int value of the month
    if(month == 1){
      monthString = "January";
    }
    else if (month == 2){
      monthString = "February";
    }
    else if (month == 3){
      monthString = "March";
    }
    else if (month == 4){
      monthString = "April";
    }   
    else if (month == 5){
      monthString = "May";
    }
    else if (month == 6){
      monthString = "June";
    }
    else if (month == 7){
      monthString = "July";
    }
    else if (month == 8){
      monthString = "August";
    }
    else if (month == 9){
      monthString = "September";
    }
    else if (month == 10){
      monthString = "October";
    }
    else if (month == 11){
      monthString = "November";
    }
    else if (month == 12){
      monthString = "December";
    }
    else
      monthString = "error"; //error
    return monthString;
  }
  
  
  /*Imports events from text file, crawls through the text data and separates them into individual event objects, to be stored into the eventList ArrayList
   */
  private void fillEventListFromFile(){
    ArrayList<Event> tempList;
    tempList = fw.returnEventList();
    this.eventList = tempList;
    //showList();
  } 
  
  
  /*Method to add an event
    * When the user clicks on the Add Event button, fills in the text fields, and presses Finalize Event
    * @param String Day - The Date of the Event
    * @param String month - The Month of the Event
    * @param String year - The Year of the Event
    * @param String eventName - The Name of the Event
    * @param String eventDescription - The Decription of the Event
    */
  public void addEventToFile(String day, String month, String year, String eventName, String eventDescription){  
    //Converts the month to its int equivalent (eg. June = 05)
    int monthInt = convertMonthToInt(month);
    //Converts day and year to int
    int dayInt = Integer.parseInt(day);
    int yearInt = Integer.parseInt(year);
    
    this.eventList.add(new Event(dayInt, monthInt, yearInt, eventName, eventDescription));
    fw.writeEventToFile(eventList.get(eventList.size() - 1)); //adds the most recently added event to the text file
  }
  
  
  /*Removes an Event from the eventList and Text Save File
   * This is called from the ShowAllEventsViewController
   * 
   */ 
  public void removeEvent(int eventNumber){
    //Removes the event at index (eventNumber - 1) of the eventList
    this.eventList.remove(eventNumber - 1); 
    this.fw.refresh(this.eventList);
  }
  
  //Called from the ShowALlEventsView to get rid of the temporary "empty" Event object
  public void clearEventList(){
    this.eventList.clear();
  }
  
  //Caled from the ShowAllEventsViewController
  public void removeAllEvents(){
    this.eventList.clear();
    this.fw.clearTextFile();
  }
  
  
  /*Checks to see if any events exists on a given Day, Month, and Year
   * Called in the CalendarView and CalendarController class
   * @param int day - the day to be checked
   * @param int month - the month to be checked
   * @param int year - the year to be checked
   * @return true - if an event exists
   * @return false - if an event does not exist
   */
  public Boolean checkForEvents(int day, int month, int year){
    fillEventListFromFile();
    for(int i = 0; i < this.eventList.size(); i++){
      if((this.eventList.get(i).getDayDate() == day) && (this.eventList.get(i).getMonthDate() == month) && (this.eventList.get(i).getYearDate() ==  year)){
        return true; //if the attributes of the Event object (day, month, year) match those provided to this method, then that means that an event exists on that day
      } 
    }
    return false;
  }
    
  /* Sets the values for the buttons Colours
   */ 
  public void setBG(Color a){
    this.bg = (a);
    if(this.bg.getRGB() != 0){
      fw.writeBG(Integer.toString(this.bg.getRGB()));
    }
  }
  
  /* Sets the values for the current Day Colour
   */ 
  public void setCD(Color a){
    this.cd = (a);
    if(this.cd.getRGB() != 0){
      fw.writeCD(Integer.toString(this.cd.getRGB()));
    }
  }
  
  
  /*Refreshes the text file by resorting it
   * Called in the ShowAllEventsView
   */
  public void refreshText(ArrayList a){
    fw.refresh(a);
  }
  
  
  /*Sets the view for the calendar
   * @param currentGUI - the View used to display the game
   * this method is called in the calendar view class, it is used to connect the View to the Model
   */
  public void setGUI(CalendarView currentGUI){
    this.view = currentGUI;
  }
  
  
  /**  Updates the calendarView in the GUI*/
  public void updateView()
  {
    //calls the update method in the View class
    this.view.update();
  }
  
  
  /* GregorianCalendar Accessor Method 
   * @return instance of gregorian Calendar */
  public GregorianCalendar getCal(){
    return this.gCal; 
  }
  
  
  /* Real Time Accessor Method
   * @return instance of GreogiranCalendar*/
  public GregorianCalendar getRealTime(){
    return this.realTime; 
  }
  
  
  /* CurrentMonthInt return Method
   * @return int value of the specific month*/ 
  public int returnCurrentMonthInt(){
    int month = this.currentMonthOfYear;
    //System.out.println(month);
    return month;
  }
  
  
  /* CurrentYearInt return Method
   * @return int value of the specific year*/
  public int returnCurrentYearInt(){
    int year = this.currentYear;
    //System.out.println(year);
    return year;
  }
  
  
  /* getYearValue accessor Method
   * @return getYearValue - Returns the year value */ 
  public String getYearValue(){
    return this.view.getYearValue();
  }
  
  
  /* Sets the values for the buttons Colours
   */ 
  public Color getBG(){
    return this.bg;
  }
  
  
  /* Sets the values for the curren Day Colour
   */ 
  public Color getCD(){
    return this.cd;
  }
  
  
  /*Returns an ArrayList filled with ALL Events (to be called from the RemoveEventView)
   * Eventually, the RemoveEventView should be renamed to "ShowAllEventView", as it is a more appropiate name
   */
  public ArrayList<Event> getEventList(){
    fillEventListFromFile();
    return(this.eventList);
  }
}


/**SimpleDateFormat constructor
  * EEE - day name in week
  * d - day in month
  * MMM - month in year (word - eg July)
  * MM - month in year (number - eg 07)
  * yyyy - year
  * HH - hour in day (from 0 to 23)
  * mm - minute in hour
  * ss - second in minute
  * Z - time zone
  * D - day in year
  * 
  * note: an alternative to using 'EEE' is 'u', which returns the day number of the week (1 = monday... 7 = sunday)
  */                            


//  /*Shows a list of all the events
//   */ 
//  private void showList(){
//    for(int i = 0; i < eventList.size(); i++){
//      System.out.println("Event Number: " + i); //currently set as the index of event in EventList arraylist
//      System.out.println("Day: " + eventList.get(i).getDayDate());
//      System.out.println("Month: " + eventList.get(i).getMonthDate());
//      System.out.println("Year: " + eventList.get(i).getYearDate());
//      System.out.println("Name: " + eventList.get(i).getEventName());
//      System.out.println("Description: " + eventList.get(i).getEventDescription());
//      System.out.println();
//    }
//    System.out.println("File Load Successful");
//  }


//  /*Method to change year
//   * When the user chooses to change the year (with the year combobox)
//   * @param int year - The User Selected Year
//   */ 
//  public void changeYear(int year){ 
//    changeDate(year, 1); //when changing year view, the first month will be shown as January by default
//    this.dayList.clear();
//    updateMonth();
//  }


//Update Method - Helper method
//  public void updateMonth(){
//    createMonth();
//    fillMonth();
//  }


//  /*After creating the month with creatMonth(), this method populates a 2D array with Day objects
//   * The 2D array represents a calendar and stores Day objects inside of it
//   */ 
//  private void fillMonth(){
//    cv = new int[6][7]; //there are 6 rows and 7 columns. The columns will store the day of the week. January 2017 will only need to make use of the first 5 rows 
//    //Fills in each element of the array, with the days of the month in their proper column (columns represent days of the week)
//    int days = 1; //stores the day of the month in int
//    
//    for(int i = 0; i < cv.length; i++){
//      for(int j = 0; j < cv[i].length; j++){
//        if(i == 0 && j > (dayOfWeek-2)){ //why is it dayOfWeek - 2 //This runs only for the first row of the calendar
//          cv[i][j] = days;
//          days++;
//        }
//        else if(i != 0){ //runs for every other row of the calendar that is not the first one
//          cv[i][j] = days;
//          days++;
//        }
//        this.dayList.add(new Day(days, this.currentMonthOfYear, this.currentYear));
//        if(days > nodim) //checks if "days" is greater than the number of days in the month
//          break;
//      }
//      if(days > nodim)
//        break;
//    }
//    
//    //Prints out the array
//    for(int i = 0; i < cv.length; i++){
//      for(int j = 0; j < cv[i].length; j++){
//        System.out.print(cv[i][j] + "    ");
//      }
//      System.out.println();
//    }  
//  }


//  /*This method determines where each day goes with each day of the week, given any day of the month
//   * 
//   */ 
//  private void createMonth(){
//    int difference; //stores the number of days between the current day and the first day of the month
//    
//    //Using the information given above, this will determine what day of the week the first day of the month falls on
//    while(this.currentDayOfMonth >= 8){
//      this.currentDayOfMonth = this.currentDayOfMonth - 7; //eg. if it is Tuesday on Jan 17, it will also be Tuesday on Jan 10 and Jan 3
//    }
//    
//    //Finds the difference between the "current day of the month" and the first day of the month 
//    difference = this.currentDayOfMonth - 1; //1 represents the first day of the month. eg. Jan 17 is equal to Jan 3 which is 2 days away from Jan 1
//    //System.out.println(difference);
//    
//    this.dayOfWeek = this.dayOfWeek - difference; //this should give you the day of the week for the first day of the month
//    //System.out.println(dayOfWeek); //eg. for the month of Feb 2017, this should return a "4", as Feb 1 falls on a Wednesday which is a 4
//    
//    if(this.dayOfWeek == 0) //find out if the difference can ever be greater than the day of the week, as this will result in a negative day of week
//      this.dayOfWeek = 7;
//    
//    
//    String tempDate; //stores the day of the week
//    if(this.dayOfWeek == 1)
//      tempDate = "Sunday";
//    else if(this.dayOfWeek == 2)
//      tempDate = "Monday";
//    else if(this.dayOfWeek == 3)
//      tempDate = "Tuesday";
//    else if(this.dayOfWeek == 4)
//      tempDate = "Wednesday";
//    else if(this.dayOfWeek == 5)
//      tempDate = "Thursday";
//    else if(this.dayOfWeek == 6)
//      tempDate = "Friday";
//    else if(this.dayOfWeek == 7)
//      tempDate = "Saturday";
//    else
//      tempDate = "Error";
//    //System.out.println("The first day of the month is " + tempDate);
//  }
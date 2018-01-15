/**Event
  * This class is for the Event object, which is used to let the user add, view, and edit Events to the Event save textfile
  * 
  * When crawling through the Event save textFile, each event will be separated by a delimiter
  * Information about each event will include:
  * Date of event (day, month, and year)
  * Name of event
  * Description of event
  * 
  * Event objects are then instantiated, and placed into an EventList ArrayList
  * 
  * If the user decides to edit or remove a preexisting event, then the event will be have to removed in the text save file
  * "editing" an event just creates a new event in the text save file
  * 
  * @author M.Zaidi, M.Huynh
  * @since Jan 22, 2017
  */
public class Event{
  //Instance Variables
  private int dayDate; //stores the day (0-31)
  private int monthDate; //stores the month (0-12)
  private int yearDate; //stores the year (2017 - 2057)
  private int intValue;
  private String eventName; //stores the event name
  private String eventDescription; //stores the event description
  
  /*Constructor used to create an Event object 
   * Called from the Model and FileWorker
   * @param day - int value from 0-31
   * @param month - int value from 1-12 (january is 1)
   * @param year - int value from 2017-2057
   * @param eventName - the String name of the event
   * @param eventDescription - the String description of the event
   */
  public Event(int day, int month, int year, String eventName, String eventDescription){
    this.dayDate = day;
    this.monthDate = month;
    this.yearDate = year;
    this.eventName = eventName;
    this.eventDescription = eventDescription;
    this.intValue = this.dayDate + this.monthDate + this.yearDate;
    
  }
  
  /*Returns the int value date of the event object
    */
  public String getIntValueInString(){
    return String.valueOf(this.intValue);
  }
  
  /*Returns the day date of the event object
    */
  public int getDayDate(){
    return this.dayDate;
  }
  
  /*Returns the month date of the event object
    */
  public int getMonthDate(){
    return this.monthDate;
  }
  
  /*Returns the year date of the event object
    */
  public int getYearDate(){
    return this.yearDate;
  }
  
  /*Returns the event name
   */
  public String getEventName(){
    return this.eventName;
  }
  
  /*Returns the event description
   */
  public String getEventDescription(){
    return this.eventDescription;
  }

}
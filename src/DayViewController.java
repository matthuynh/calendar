//import packages
import java.awt.event.*;
import java.util.*; 

/** DayViewController class
  * The controller for the Day buttons
  * @since Jan 22, 2017
  * @author M.Huynh, R.Chan
  */ 
public class DayViewController implements ActionListener{
  private Model model; //The calendar model
  private DayView dView; //the day view
  private String day; //the current day of the day view
  private String month; //the current month of the day view
  private String year; //the current year of the day view
  
  /**Default constructor
    * Links the text field component to the Model
    * @param aCalendar  - the model
    * @param dayView - the DayView
    * @param aDay - the day of the DayView
    * @param aMonth - the month of the DayView
    * @param aYear - the year of the DayView
    */
  public DayViewController(Model aCalendar, DayView dayView, String aDay, String aMonth, String aYear){
   this.model = aCalendar;
   this.dView = dayView;
   this.day = aDay;
   this.month = aMonth;
   this.year = aYear;
  }
  
  /**Stores the number from the text field into a variable in the Model class
    * @param e  - the event sent from the textbox component
    */
  public void actionPerformed(ActionEvent e){
     EventView eview = new EventView(this.model); //Creates instance of EventView Class
     eview.displayGUI(this.day, this.month, this.year);
     this.dView.clearWindow();  
  }
}
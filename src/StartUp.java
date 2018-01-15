//import packages
import javax.swing.*; 
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/*Start Up Class
 * Creates Instances of the Model and the View and combines them to display and run the program 
 * Using a main frame, the view gets displayed based on the instance of the model
 * @since Jan 22, 2017
 * @authors M.Zaidi
 */ 
public class StartUp
{
  //Instance Variables
  private static JFrame f = new JFrame("Productivity Suite"); //Creating the mainFrame that will hold the view and model
  private static CalendarView calView; //Instance of the View
  private static Model model = new Model(); //Instance of the Model
 
  public static void main(String[]args)
  {//Start of Main
       
    //Giving the view access to the model
    calView = new CalendarView(model);
    
    //Initialize the Frame
    f.setSize(995,550);
    f.setLocation(300,100);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setContentPane(calView);
    f.setMinimumSize(new Dimension(995,550));
    f.setVisible(true);
    

    reminderStartUp(); //Checks to see if any events exist for the current day
  }//End of Main
  
  //**** METHOD ****
  /* Refresh Method
   * This "refreshes" the view of the calendar, allowing for changes to be displayed.*/ 
  
  public static void refresh()
  {//Start of Refresh Method
       
    f.remove(calView);
    //f.setVisible(false);
    CalendarView calView2 = new CalendarView(model);
    f.setContentPane(calView2);
    f.revalidate();
    f.repaint();
    f.setVisible(true);
  }//End of Refresh Method
  
  /*Checks to see if the user has an event on the current day when the program starts
   * @return true - the user has an event
   * @return false - the user does not have an event
   */ 
  private static void reminderStartUp(){
    GregorianCalendar gCal = new GregorianCalendar(); //Gets the current date using a GreogorianCalendar object, and stores it in a Date object so that its String display can be manipulated
    Date dateObject = gCal.getTime(); //Creates a date object based on the GreogorianCalendar
    SimpleDateFormat df = new SimpleDateFormat("EEEddMMyyyy"); //Converts the date object to String using SimpleDateFormat - see comment at bottom for formatting
    String dateInString = df.format(dateObject);//Formats a Date into a date/time string
    
    int currentDayOfMonth = Integer.parseInt(dateInString.substring(3,5));
    int currentMonthOfYear = Integer.parseInt(dateInString.substring(5,7));
    int currentYear = Integer.parseInt(dateInString.substring(7,11));
    
    //Checks to see if any events exist for the current day
    if(model.checkForEvents(currentDayOfMonth, currentMonthOfYear, currentYear)){
      ReminderView rView = new ReminderView();
    }
  }
}//End of Class
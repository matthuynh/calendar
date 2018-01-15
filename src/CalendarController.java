//import packages
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

/* CalendarController
 * This Class Contains the controllers for the CalendarView
 * @author M.Zaidi
 * @since Jan 22, 2017
 */
public class CalendarController implements ActionListener{
  //Instance Variables
  private Model model; //Creates Instance of the Model
  private JButton button; //Creates JButton
  private EventView eview; //Creates instance of EventView Class
  private ShowAllEventsView sview; //Creates Instance of the ShowAllEventView class
  private DayView dview; //Creates Instance of the DayView Class
  private String day;
  
  /*Default Constructor
   * @param Model - Creates instance of the Model
   * @param JButton - Creates an instance of the Button that was clicked
   * @param EventView - Creates an Instance of the EventView which will be changed
   * @param ShowAllEventView - Creates an Instance of the ShowAllEvents 
   */ 
  public CalendarController(Model main,JButton in, String d, EventView view, ShowAllEventsView allEventsView){  //
    this.button = in;
    this.model = main;
    this.sview = allEventsView;
    this.eview = view;
    if(!d.equals(null)){
      this.day = d;
    }
  }

  /*If any of the buttons in the CalendarView have been actioned upon
   * @param e - Created Event Object for what happened. 
   */ 
  public void actionPerformed(ActionEvent e){   
    //GoToToday
       //If the User clikced go to today button, it would take them to the date "today" is. 
    if(this.button.getText().equals("Go To Today")){
      Calendar temp = Calendar.getInstance(); //Temp Instance of the Calendar
      int year = temp.get(Calendar.YEAR); //Gets the Today Year
      int month = temp.get(Calendar.MONTH)+1; //Gets Today's month
      int day =  temp.get(Calendar.DATE); //Gets Today Date
      
      //Changing the Date
      this.model.changeDate(year,month,day); 
      
      this.model.updateView();
      StartUp.refresh();
    }
    
    //If the User Selectes AddButton 
    if(this.button.getText().equals("Add Event")){
      this.eview.displayGUI(null, "0", null);
    }
    
    //If the user selected Show ALl Events
    if(this.button.getText().equals("Show Events")){
      this.sview = new ShowAllEventsView(this.model);
      this.sview.displayGUI();
    }
    
    /**********CHANGING MONTHS ***********/ 
    //If the User Selected NextMonth    
    if(this.button.getText().equals(">>")){
      //Changing to the next month through Model
      this.model.changeToNextMonth();
      //Updating the Calendar
      this.model.updateView();
      StartUp.refresh();
    }
    
    //If the user selected go to previous month
    if(this.button.getText().equals("<<")){
      //Changes to Previous Month
      this.model.changeToPreviousMonth();
      //Update Calendar View
      this.model.updateView();
      StartUp.refresh();
    }
    
    
    //CREATING THE DAY BUTTONS WITH THE CORRECT NUMBER OF THE DAY
    for(int x = 0;x<32;x++){
      if (this.button.getText().equals(String.valueOf(x))){
        //Adding the Button with the correct number to the calendar
        this.dview = new DayView(this.button.getText(), this.model); 
        this.dview.displayGUI();
      }
    }
    
    //CHANGING THE YEAR 
    if(this.button.getText().equals("Change Year")){
      this.model.updateView();
      StartUp.refresh();
    }
    
    //CHANGING BUTTON COLOURS
    if(this.button.getText().equals("Change Buttons Colour")){
      Color bg = JColorChooser.showDialog(null, "Choose a Color", this.button.getForeground());
      if(bg == null){
        bg = this.model.getBG();
      }
      this.model.setBG(bg);
      this.model.updateView();
      StartUp.refresh();
    }
    
    //CHANGING CURRENT DAY COLOUR
    if(this.button.getText().equals("Change Current Day Colour")){
      Color cd = JColorChooser.showDialog(null, "Choose a Color", this.button.getForeground());
      if(cd == null){
        cd = this.model.getCD();
      }
      this.model.setCD(cd);
      this.model.updateView();
      StartUp.refresh();
    }        
    
    //Reminder Button - shows today's events
    if(this.button.getText().equals("<html>There is an event today!<br>Click to view</html>")){
      Calendar temp = Calendar.getInstance(); //Temp Instance of the Calendar
      int year = temp.get(Calendar.YEAR); //Gets the Today Year
      int month = temp.get(Calendar.MONTH)+1; //Gets Today's month
      int day =  temp.get(Calendar.DATE); //Gets Today Date
      //Changing the Date in the model to the current date
      this.model.changeDate(year,month,day); 
      
      this.dview = new DayView(this.day, this.model); 
      this.dview.displayGUI();
    }
    
    if(this.button.getText().equals("Open Notepad"))
    {
         
         Notepad newNotePad = new Notepad(); 
    }
    
  }   //End of Actionperformed Method
}//End of Class
//import packages
import javax.swing.*;
import java.awt.event.*;

/** EventViewController
  * The controller for the the year,month,day JComboBox and the two text areas
  * @author M.Huynh, N. Patel, R. Chan, M. Zaidi
  * @since Jan 22, 2017
  */ 
public class EventViewController implements ActionListener{
  //Variable Instantiation
  private Model model;
  private JTextArea eventTitle;
  private JTextArea eventDescrip;
  private EventView eview;
  private String errorMessage; //the error message created if the user selects the wrong day combination (Eg. Feb 31)
  private EventErrorView errorView;
  
  
  /*Constructor 
   * @param aModel - the Model object is passed
   * @param titleText - the Event title, provided by the user in the EventView
   * @param descriptionText - the Event description, provided by the user in the EventView
   * @param view - the EventView is passed
   * 
   * Note: The JComboBoxes cannot be passed to this class, the selected object from the JComboBox isn't passed properly with the JComboox
   */ 
  public EventViewController(Model aModel, JTextArea titleText, JTextArea descriptionText, EventView view){
    this.model = aModel;
    this.eventTitle = titleText;
    this.eventDescrip = descriptionText;
    this.eview = view;
  }
  
  /**This is run when the user hits "Finalize Event"
    * @param e - e is an object of an ActionEvent, triggered when the user clicks on "Finalize Event" in the EventView
    */
  public void actionPerformed(ActionEvent e){
    //Variable Declaration
    String titleText;
    String descripText;
    String monthString;
    String dateString;
    String yearString;
    int dayInt;
    int yearInt;
    
    //Gets the title and description text
    titleText = this.eventTitle.getText();
    descripText = this.eventDescrip.getText();
    
    //Gets the month, date, and year as a String from the JComboBoxes in the EventView
    monthString = this.eview.getMonthComboBoxValue();
    dateString = this.eview.getDateComboBoxValue();
    yearString = this.eview.getYearComboBoxValue();
    dayInt = Integer.valueOf(dateString);
    yearInt = Integer.valueOf(yearString);
    
    if(checkIfValidDay(yearInt, monthString, dayInt)){
      this.model.addEventToFile(dateString, monthString, yearString, titleText, descripText); //Outputs and saves the Event to the text save file
      this.model.updateView();
      StartUp.refresh();
    }
    else{
      this.errorView = new EventErrorView(this.errorMessage);
     //If user enters invalid day 
      //this.errorMessage = "";
    }
    this.eview.clearWindow(); //hides the EventView by calling the hideWindow() method in the EventView
  }
  
  /*Checks if the user entered a wrong day (eg. February 31)
   */ 
  private Boolean checkIfValidDay(int year, String month, int day){
    Boolean isValid = true;
    //Months that have 30 days 
    if(month.equals("April") || month.equals("June") || month.equals("September") || month.equals("November")){
      if(day > 30)
        isValid = false;
      this.errorMessage = (month + " does not have 31 days! You must choose between days 1 to 30"); 
    }
    
    //February
    if(month.equals("February")){
      //Determines if the year is a leap year
      if((year % 4 == 0 && year % 100 != 0)||(year % 400 ==0)){
        //Is a leap year 
        if(day > 29){
          this.errorMessage = ("While this is a leap year, February only has up to 29 days. Please enter between 1 to 29 days.");
          isValid = false;
        }
      }
      else{
        //Is not a leap year
        if(day > 28){
          this.errorMessage = (year + " is not a leap year! Please enter a day between 1 to 28 days.");
          isValid = false;
        }
      }
    }
    //System.out.println(isValid);
    return isValid;
  }
}

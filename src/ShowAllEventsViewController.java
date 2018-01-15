//import packages
import javax.swing.*;
import java.awt.event.*;
import java.util.*; 

/** ShowAllEventsViewController class
  * Controller class for the ShowAllEventView Class
  * @author M.Huynh, M.Zaidi
  * @since Jan 22, 2017
  */ 
public class ShowAllEventsViewController implements ActionListener{
  private Model model; //The calulator model //whats this for
  private JTextField userInput;
  private ShowAllEventsView sview;
  private int numEvents; //the number of events that currently exist. this variable is used for data validation
  private ArrayList<Event> eventList; //Stores all Events
  private JButton button;
  
  /**Default constructor
    */  
  public ShowAllEventsViewController(Model aModel, JTextField userInputNumber, ShowAllEventsView view, JButton in){ 
    this.model = aModel;
    this.userInput = userInputNumber;
    this.eventList = this.model.getEventList();
    this.numEvents = this.eventList.size();
    this.sview = view;
    this.button = in;
  }
  
  /**This is run when the user hits any of the buttons
    * It removes the chosen event from the file
    * @param e 
    */
  public void actionPerformed(ActionEvent e){
    //If the User selects "Remove All Events"
    if(this.button.getText().equals("Remove All Events")){
      this.model.removeAllEvents();
      this.userInput.setText("Successfully removed all events. The window will now close to refresh.");
      this.sview.hideWindow();
      this.sview.disableInput();
      this.model.updateView();
      StartUp.refresh();
    }
       
    //If the User selects "Sort By Date (Newest Events First)"
    if(this.button.getText().equals("<html>Sort by Date<br>(Newest Events First)</html>")){
      this.sview.sortNewest();
      this.userInput.setText("Successfully sorted events by newest events first. Please close the window to refresh.");
      this.sview.disableInput();
    }
    
    //If the User selects "Sort By Date (Oldest Events First)"
    if(this.button.getText().equals("<html>Sort by Date<br>(Oldest Events First)</html>")){
     this.sview.sortOldest();
     this.userInput.setText("Successfully sorted events by oldest events first. Please close the window to refresh."); 
     this.sview.disableInput();      
    }
    
    //If the User selects "Remove Event"
    if(this.button.getText().equals("Remove Event")){
      Boolean isValidInt; //stores whether the user has input a valid data type. True = valid
      String inputNumberString = this.userInput.getText();
      int inputNumber = 0;
      try{
        //Tries to turn the user input into an int
        inputNumber = Integer.valueOf(inputNumberString);
        isValidInt = true;
      }
      catch(NumberFormatException f){
        //If the user doesn't enter an int
        this.userInput.setText("You must enter a number!");
        isValidInt = false;
      }
      
      //Runs if the user enters a number
      if(isValidInt){
        //If the user enters an incorrect event number
        if(inputNumber <= 0 || inputNumber > this.numEvents){
          this.userInput.setText("That is an invalid Event Number! Enter an Event Number between 1 and " + this.numEvents);
        }
        else{
          this.model.removeEvent(inputNumber);
          this.userInput.setText("Successfully removed Event " + inputNumber + ". Please close the window to refresh.");
          this.sview.disableInput();
          this.model.updateView();
          StartUp.refresh();
        }
      }
    }
   

  }
}
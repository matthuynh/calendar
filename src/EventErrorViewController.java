//import packages
import java.awt.event.*;

/** EventErrorViewController
  * The code used to determine if the user entered an invalid day are found in the EventViewController
  * It is more efficient to leave the code there as the variables for the Day, Month, and Year already exist there
  * Passing those three variables onto this class would be a waste of resources.
  * Instead, this class still exists so that the user can close the window by clicking the "OK" button instead of the X
  * 
  * @since Jan 22, 2017
  * @author M. Huynh
  */ 
public class EventErrorViewController implements ActionListener{
  private EventErrorView view;
  /**Default constructor
    * Links the text field component to the Model
    * @param aCalendar  - the model
    */
  public EventErrorViewController(EventErrorView aView){
    this.view = aView;
  }
  
  /**Closes the EventErrorView GUI when the user clicks the "OK" button
    * @param e  - the event sent from the "OK" button
    */
  public void actionPerformed(ActionEvent e){
    this.view.clearWindow();       
  }
}
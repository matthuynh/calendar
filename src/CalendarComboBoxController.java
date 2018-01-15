//import packages
import java.awt.event.*;
import javax.swing.*;

/* CalendarComboBoxController Class
 * This class contains the code to allow the combobox to select a specific year
 * @author M.Zaidi
 * @since Jan 22, 2017
 */ 
public class CalendarComboBoxController implements ActionListener{
  //Instance Variables 
  private Model model;
  private CalendarView view;
  private String year;
  private JComboBox comboBox;
  
  /*Default Constructor
   * @param Model - An instance of the Model
   * @param CalendarView - An instance of the View
   * @param JComboBox - An instance of the ComboBox that is being manipulated
   */ 
  public CalendarComboBoxController(Model main, CalendarView aView,JComboBox jcb){  //
    this.model = main; //Setting the model
    this.view = aView; //Setting the view
    this.comboBox = jcb; //setting the comboBox
  }
  
  /* Returns the selected year by the user, and changes the calendar accordingly. 
   * @param e - The Event sent from the JComboBox
   */ 
  public void actionPerformed(ActionEvent e){
      //Getting the selected Value, 
      this.year = (String)this.comboBox.getSelectedItem();
      //System.out.println(this.year);
      
      if(!this.year.equals("Select Year")){
        //Updating/Changing the calendar based on the selected year by the user.
        this.model.changeDate(Integer.valueOf(this.year),1);
        this.comboBox.setEnabled(false);
        this.view.enableGoToYear(true); 
      }
  }   
}
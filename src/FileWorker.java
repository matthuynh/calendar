//import packages
import java.util.*;
import java.io.*;
import java.io.FileWriter;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/* FileWorker
 * This Class Contains the methods to write the Events to the .txt file and to get the events from the file
 * It also contains methods to store and change the Color selections of the Day buttons made by the user
 * @authors M.Zaidi, M. Huynh
 * @since Jan 22, 2017
 */
public class FileWorker{
  //Instance Variables
  private Model model; //The model
  private CalendarView view; //The GUI
  private File file; //File object
  private File bgColor;
  private File cdColor;
  private PrintWriter out; //used to write to the output stream
  private FileWriter fw; //used to write to the output stream
  private BufferedWriter bw; //used to write to the output stream

  private int dayDate; //stores the day (0-31)
  private int monthDate; //stores the month (0-12)
  private int yearDate; //stores the year (2017 - 2057)
  private String eventName; //stores the event name
  private String eventDescription; //stores the event description
  
  private ArrayList<Event> tempList; //Stores the events recieved from the file
  
  //Used to set a delimiter of "::::::"
  private static String stringPattern = "::::::"; //The delimeter set in the file
  private static Pattern r = Pattern.compile(stringPattern); //The pattern 
  private static Matcher m = r.matcher(stringPattern); //The matcher 
  
  /*Class Constructor
   * Calls upon the files
   */ 
  public FileWorker(){
    super();
    
    file = new File("Events.txt");
    bgColor = new File("bgColor.txt"); //bg = background
    cdColor = new File("cdColor.txt"); //cd = current day
    
    if(!file.exists()){
      Event nullEvent = new Event(0,0,0,"","");
      this.writeEventToFile(nullEvent);
      this.clearTextFile();
    }
    
    if(!bgColor.exists()){
      this.writeBG("-12197731");
    }
    
    if(!cdColor.exists()){
      this.writeCD("-7536691");
    }
  }
  

  /*Color Saver
   * Writes the background colour to a file
   */
  public void writeBG(String s){
    try{
     FileWriter bgFW = new FileWriter(bgColor,false);
     BufferedWriter bgBW = new BufferedWriter(bgFW);
     PrintWriter bgOUT = new PrintWriter(bgBW);
     
     bgOUT.println(s);
     bgOUT.close();
    }catch(IOException ex){
      System.out.println(ex.getMessage());
      System.exit(1); 
    } 
  }
  
  /*Color Saver
   * Writes the current day colour to a file
   */
  public void writeCD(String s){
    try{
     FileWriter cdFW = new FileWriter(cdColor);
     BufferedWriter cdBW = new BufferedWriter(cdFW);
     PrintWriter cdOUT = new PrintWriter(cdBW);
     
     cdOUT.println(s);
     cdOUT.close();
    }catch(IOException ex){
      System.out.println(ex.getMessage());
      System.exit(1); 
    } 
  }
  
  /*Gets background color
   * Gets the background colour from a file
   */
  public String getBG(){
    Scanner in = null;
    try{
      in = new Scanner(bgColor);
    }catch(FileNotFoundException ex){
      System.out.println(ex.getMessage());
      System.out.println("in"+System.getProperty("user.dir"));
      System.exit(1);
    }
    if(in.hasNext()){
      String colorValue = in.nextLine();
      in.close();
      return colorValue;
    }
    else{
      return "-152155156";
    }
  }
  
  /*Gets current day color
   * Gets the current day colour from a file
   */ 
  public String getCD(){
    Scanner in = null;
    try{
      in = new Scanner(cdColor);
    }catch(FileNotFoundException ex){
      System.out.println(ex.getMessage());
      System.out.println("in"+System.getProperty("user.dir"));
      System.exit(1);
    }
    if(in.hasNext()){
      String colorValue = in.nextLine();
      in.close();
      return colorValue;
    }
    else{
      return "-464848";
    }
  }
  
  
  /*Event Writer
   * Writes the event to the file
   */ 
  public void writeEventToFile(Event in){
    this.dayDate = in.getDayDate();
    this.monthDate = in.getMonthDate();
    this.yearDate = in.getYearDate();
    this.eventName = in.getEventName();
    this.eventDescription = in.getEventDescription();
    try{
      fw = new FileWriter(this.file,true);
      bw = new BufferedWriter(this.fw);
      out = new PrintWriter(this.bw);
      out.print(this.dayDate+"::::::"+this.monthDate+"::::::"+this.yearDate+"::::::"+this.eventName+"::::::"+this.eventDescription+"::::::");
      out.close();    
    }catch(IOException ex){
      System.out.println(ex.getMessage());
      System.exit(1);
    }
  }
  
  
  /*Getting the Events
   * Returns an ArrayList filled with events to the Model
   */ 
  public ArrayList<Event> returnEventList(){
    this.tempList = new ArrayList<Event>();
    fillTempList();
    return(this.tempList);
  }
  
  private void fillTempList(){
    //Boolean hasEvents = false; //Stores whether or not the Events file has any events. True = has events. False = empty file
    Scanner in = null;
    Event tempEvent;
    try{
      in = new Scanner(file);
    }catch(FileNotFoundException ex){
      System.out.println(ex.getMessage());
      System.out.println("in"+System.getProperty("user.dir"));
      System.exit(1);
    }
    
    in.useDelimiter(r);
    //If there is still another line of text
    while(in.hasNext()){
      //hasEvents = true;
      this.dayDate = Integer.valueOf(in.next());
      this.monthDate = Integer.valueOf(in.next());
      this.yearDate = Integer.valueOf(in.next());
      this.eventName = in.next();
      this.eventDescription = in.next();
      tempEvent = new Event(this.dayDate,this.monthDate,this.yearDate,this.eventName,this.eventDescription);
      this.tempList.add(tempEvent);
    }    
    in.close();
    //return hasEvents;
  }
  
  
  /*After the user chooses to remove an Event, that particular Event is removed from the text file
   * This is done by clearing the text file, then refilling it without the removed event
   * This method is called in the Model
   * @param eventList - the ArrayList that stores all the events
   */ 
  public void refresh(ArrayList<Event> eventList){
    try{
      fw = new FileWriter(this.file, false); //by setting the keyword false, the FileWriter will overwrite and clear the pre-existing text 
      bw = new BufferedWriter(this.fw);
      out = new PrintWriter(this.bw);
      
      //Prints out the event data, with each piece of data being separated by the delimiter '::::::'
      for(int i = 0; i < eventList.size(); i++){
        out.print(eventList.get(i).getDayDate() + "::::::" + (eventList.get(i).getMonthDate()) + "::::::" + (eventList.get(i).getYearDate()) + "::::::" + (eventList.get(i).getEventName()) + "::::::" + (eventList.get(i).getEventDescription()) + "::::::");
      }
      out.close();
    }
    catch(IOException ex){
      System.out.println(ex.getMessage());
      System.exit(1);
    }
  }
  
  /*Clears the text file when the user chooses to "Remove All Events"
   * Called from the Model
   */ 
  public void clearTextFile(){
    try{
      fw = new FileWriter(this.file, false); //by setting the keyword false, the FileWriter will overwrite and clear the pre-existing text     
    }
    catch(IOException ex){
      System.out.println(ex.getMessage());
      System.exit(1);
    }
  }
}




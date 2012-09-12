package models;

import java.util.*;
import javax.persistence.*;

import org.hibernate.annotations.CollectionOfElements;
 
import play.db.jpa.*;
 
@Entity
public class Event extends Model {
 
    public String title;
    public Date date;
    public boolean vegetarian;
    public int slots;
    
    @Lob
    public String details;
    
    @CollectionOfElements
    public List<String> users;
    
    @CollectionOfElements
    public List<String> vegetarians;
     
    public Event(String title, String details, Date date, int slots, boolean vegetarian) { 
        this.users = new ArrayList<String>();
        this.vegetarians = new ArrayList<String>();
        this.title = title;
        this.slots = slots;
        this.details = details;
        this.date = date;
        this.vegetarian = vegetarian;
    }
 
    public List<String> validate(boolean edit) {
    	List<String> errors = new ArrayList<String>();
    	if (date instanceof Date != true ){
    		 errors.add("Datum in falschem Format");
    	}
    	else {
        	Date aktDate = new Date();
    		if (date.compareTo(aktDate) < 0)
        	{
        		errors.add("Datum schon vergangen");
        	}
    	}
    	
    	
    	if (edit == false)
    	{
    	System.out.print(Event.find("byDate", date).first());
    	
    	if (Event.find("byDate", date).first() != null)
    	{
    		errors.add("Event an diesem Datum schon vorhanden");
    	}
    	}
    
    	
    	if (title == null || title.isEmpty())
    	{
    		errors.add("Speisentitel fehlt");
    	}
    	
		return errors; 
       
    }
    
    public List<String> validateBooking() {
    	List<String> errors = new ArrayList<String>();
    	
    	Date aktDate = new Date();
		if (date.compareTo(aktDate) < 0)
    	{
    		errors.add("Buchungen sind nicht mehr änderbar. Bitte Anja oder einen Admin fragen, wenn trotzdem etwas geändert werden soll.");
    		errors.add("Nadd");
    	}
		else {
			// Berechne Freitag der letzten Woche
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			
			c.add(Calendar.WEEK_OF_YEAR, -1);
		    c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		    
		    Date lastFriday = c.getTime();

		    if (lastFriday.compareTo(aktDate) < 0)
	    	{
	    		errors.add("Achtung: Diese Buchungsänderung ist nicht vor Freitag vorgenommen worden. Bitte diese zeitig an Anja melden.");
	    		errors.add("add");
	    	}
		}
    	
    	return errors;
    }
    
}

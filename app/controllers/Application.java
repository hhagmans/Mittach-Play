package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {


    @Before
    static void setConnectedUser() {
            User user = new User("Test", false);
            renderArgs.put("user", user);
    }
	
    public static void index() {
    	List<Event> events = Event.find(
                "order by date desc"
            ).from(1).fetch(10);
        render(events);
    }
    
    public static void adminIndex() {
    	List<Event> events = Event.find(
                "order by date desc"
            ).from(1).fetch(10);
        render(events);
    }

    public static void saveEvent(String title, String details, Date date, int slots, boolean vegetarian) {

        if (details == null)
        {
        	details = "";
        }
    	// Create event
        Event event = new Event(title, details, date, slots, vegetarian);
        // Set tags list
        
        
        // Validate
        validation.valid(event);
        if(validation.hasErrors()) {
            render("@adminIndex");
        }
        // Save
        event.save();
        adminIndex();
    }
    
    public static void deleteEvent(long id){
    	Event event = Event.findById(id);
    	event.delete();
    	adminIndex();
    	
    }
    
    public static void editEvent(long id){
    	Event event = Event.findById(id);
    	render(event);	
    }
    
    public static void editEventSave(long id, String title, String details, Date date, int slots, boolean vegetarian){
    	Event event = Event.findById(id);
    	if (event != null){
    	event.title = title;
       	event.details = details;
       	event.date = date;
       	event.slots = slots;
      	event.vegetarian = vegetarian;
       	event.save();
    	}
    	adminIndex();
    }
    
    public static void addBooking(long id, String username){
    	Event event = Event.findById(id);
    	event.users.add(username);
    	event.save();
    	index();
    	
    }
    public static void removeBooking(long id, String username){
    	Event event = Event.findById(id);
    	event.users.remove(username);
    	event.save();
    	index();
    	
    }
    
    public static void editBooking(long id){
    	Event event = Event.findById(id);
    	render(event);
    }
    
    public static void editBookingSave(long id, String user, boolean vegetarian){
    	Event event = Event.findById(id);
    	if (vegetarian == true){
    		event.users.remove(user);
    	}
    	else {
    		if (event.users.contains(user) == false && user != "")
    		event.users.add(user);
    	}
    	event.save();
    	adminIndex();
    	
    }
    
}
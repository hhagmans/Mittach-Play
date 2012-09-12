package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {


    @Before
    static void setConnectedUser() {
            User aktUser = new User("Test", false);
            renderArgs.put("aktUser", aktUser);
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
        
        if (slots == 0)
        {
        	slots = -1;
        }
        
    	// Create event
        Event event = new Event(title, details, date, slots, vegetarian);
        
        // Validate
        List<String> errors = event.validate(false);
        if(errors.size() == 0) {
            // Save
            event.save();
            adminIndex();
        }
        else{
            createEventF(title, details, date, slots, vegetarian, errors);
        }
    }
    
    public static void createEventF(String title, String details, Date date, int slots, boolean vegetarian, List<String> erro){
    	if (date instanceof Date == true ){
    		render(title, details, date.toString(), slots, vegetarian, erro);
    	}
    	else {
    	render(title, details, date, slots, vegetarian, erro);
    	}	
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
    	
    	if (details == null)
        {
        	details = "";
        }
        
        if (slots == 0)
        {
        	slots = -1;
        }
    	
        Event eventtmp = new Event(title, details, date, slots, vegetarian);
        List<String> errors = eventtmp.validate(true);
        if(errors.size() == 0 && event != null) {
        	event.title = title;
           	event.details = details;
           	event.date = date;
           	event.slots = slots;
          	event.vegetarian = vegetarian;
           	event.save();
            adminIndex();
        }
        else{
            editEventF(event.id, title, details, date, slots, vegetarian, errors);
        }
    }
    
    public static void editEventF(long id, String title, String details, Date date, int slots, boolean vegetarian, List<String> erro){
    	if (date instanceof Date == true ){
    		render(id, title, details, date.toString(), slots, vegetarian, erro);
    	}
    	else {
    	render(id, title, details, date, slots, vegetarian, erro);
    	}
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
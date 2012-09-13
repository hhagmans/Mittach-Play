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
	
    public static void root() {
    	index(1);
    }
    
    public static void index(int page) {
    	List<Event> events = Event.find(
                "order by date desc"
            ).from(0 + (10*(page-1))).fetch(10 + (10*(page-1)));
    	long cpages = Event.count()/11 + 1;
        render(events, cpages, page);
    }
    
    public static void indexF(int page, List<String> erro) {
    	List<Event> events = Event.find(
                "order by date desc"
            ).from(0 + (10*(page-1))).fetch(10 + (10*(page-1)));
    	long cpages = Event.count()/11 + 1;
        render(events, cpages, page, erro);
    }
    
    public static void adminIndex(int page) {
    	List<Event> events = Event.find(
                "order by date desc"
            ).from(0 + (10*(page-1))).fetch(10 + (10*(page-1)));
    	long cpages = Event.count()/11 + 1;
        render(events, cpages, page);
    }

    public static void showUsers(Event event) {
    	render(event);
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
            adminIndex(1);
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
    
    public static void deleteEvent(long id, int page){
    	Event event = Event.findById(id);
    	event.delete();
    	adminIndex(page);
    	
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
            adminIndex(1);
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
    
    public static void addBooking(long id, String username, int page){
    	Event event = Event.findById(id);
    	List<String> errors = event.validateBooking();
    	if (errors.size() == 0 && event != null) {
    	event.users.add(username);
    	event.save();
    	index(1);
    	}
    	else{
    		if (errors.get(1) == "Nadd"){
    			errors.remove(1);
    			indexF(page, errors);
    		}
    		else {
    			event.users.add(username);
    	    	event.save();
    	    	errors.remove(1);
    	    	indexF(page, errors);
    		}
    	}
    	
    }
    public static void removeBooking(long id, String username, int page){
    	Event event = Event.findById(id);
    	List<String> errors = event.validateBooking();
    	if (errors.size() == 0 && event != null) {
    	event.users.remove(username);
    	event.save();
    	index(1);
    	}
    	else{
    		if (errors.get(1) == "Nadd"){
    			errors.remove(1);
    			indexF(page, errors);
    		}
    		else {
    			event.users.remove(username);
    	    	event.save();
    	    	errors.remove(1);
    	    	indexF(page, errors);
    		}
    	}
    	
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
    	adminIndex(1);
    	
    }
    
}
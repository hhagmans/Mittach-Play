package controllers;

import play.*;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.mvc.*;
import play.data.validation.Error;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import models.*;

public class Application extends Controller {

	static final int MAXEVENTS = 10;

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
            ).from(MAXEVENTS*(page-1)).fetch(MAXEVENTS);
    	long cpages = Event.count()/MAXEVENTS + 1;
        render(events, cpages, page);
    }
    
    public static void adminIndex(int page) {
    	List<Event> events = Event.find(
                "order by date desc"
            ).from(MAXEVENTS*(page-1)).fetch(MAXEVENTS);
    	long cpages = Event.count()/MAXEVENTS + 1;
        render(events, cpages, page);
    }

    public static void showUsers(Event event) {
    	render(event);
    }
    
    public static void newEvent() {

    	String title = params.get("title");
    	String details = params.get("details");
    	 SimpleDateFormat formatter = new SimpleDateFormat(
                 "yyyy-MM-dd");
    	String date = params.get("date");
    	Date formattedDate;
		try {
			formattedDate = (Date)formatter.parse(params.get("date"));
		} catch (ParseException e) {
			formattedDate = null;
		}
		int slots;
		try{
	    slots = Integer.parseInt(params.get("slots"));
		} catch (NumberFormatException e)
		{
			slots = 0;
		}
    	boolean vegetarian = Boolean.parseBoolean(params.get("vegetarian"));
    	
        if (details == null)
        {
        	details = "";
        }
        
        if (slots == 0)
        {
        	slots = -1;
        }
        
    	// Create event
        Event event = new Event(title, details, formattedDate, slots, vegetarian);
        
        // Validate
        if (validation.valid(event).ok){
        	// Save
            event.save();
            adminIndex(1);
        }   
        else{
        	if(validation.hasErrors()) {
                params.flash(); // add http parameters to the flash scope
                validation.keep(); // keep the errors for the next request
                
                render(title, details, date, slots, vegetarian);
        	}
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
        if(validation.valid(eventtmp).ok && event != null) {
        	event.title = title;
           	event.details = details;
           	event.date = date;
           	event.slots = slots;
          	event.vegetarian = vegetarian;
           	event.save();
            adminIndex(1);
        }
        else{
        	if(validation.hasErrors()) {
                params.flash(); // add http parameters to the flash scope
                validation.keep(); // keep the errors for the next request
                editEvent(event.id);
        	}
        }
    }
    
    public static void addBooking(@Valid Event event, String username, int page){
    	if (event != null) {
    	if(validation.hasErrors()) {
          params.flash(); // add http parameters to the flash scope
          validation.keep(); // keep the errors for the next request
          index(1);
    	}
    	event.users.add(username);
    	event.save();
    	index(1);
    	}
    	
    }
    public static void removeBooking(@Valid Event event, String username, int page){
    	if (event != null) {
        if(validation.hasErrors()) {
            params.flash(); // add http parameters to the flash scope
            validation.keep(); // keep the errors for the next request
            index(1);
        }
    	event.users.remove(username);
    	event.save();
    	index(1);
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
    
    public static void Reports(Date start, Date end) {
    	List<Event> events = Event.findAll();
    	List<Event> events2 = new ArrayList();
    	
    	// Get beginning and end of the last month
    	if (start == null || end == null){
    	start = new Date();
    	end = new Date();
    	Calendar c = Calendar.getInstance();
		c.setTime(start);
		c.add(Calendar.MONTH, -1);
	    c.set(Calendar.DAY_OF_MONTH, 1);
	    c.set(Calendar.HOUR_OF_DAY, 0);   
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);                
	    c.set(Calendar.MILLISECOND, 0); 
	    start = c.getTime();
	    int lastDate = c.getActualMaximum(Calendar.DATE);
	    c.set(Calendar.DATE, lastDate); 
	    end = c.getTime();
    	}
	    
    	ListIterator<Event> iter = events.listIterator();
    	while (iter.hasNext()){
    		Event ev = iter.next();
    		if (start.compareTo(ev.date) <= 0 && ev.date.compareTo(end) <= 0){
    			events2.add(ev);
    		}
    	}
    	ListIterator<Event> iter2 = events2.listIterator();
    	Map<String,List<Date>> users = new HashMap<String,List<Date>>();
    	while (iter2.hasNext()){
    		Event event = iter2.next();
    		ListIterator<String> useriter = event.users.listIterator();
    		while (useriter.hasNext()){
	    		String user = useriter.next();
	    		Date date = event.date;
	    		List<Date> dates = new ArrayList<Date>();
	    		if (users.containsKey(user)){
	    		dates = users.get(user);
	    		}
				dates.add(date);
	    		users.put(user, dates);
    		}
    	}
    	response.contentType = "text/csv";
    	response.setHeader("Content-Disposition",
    	"attachment;filename=Reports.csv");
    	render(users);
    }
    
    
    public static void manualReports() {
    render();
    }
}
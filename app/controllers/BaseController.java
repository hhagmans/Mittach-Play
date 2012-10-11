package controllers;

import java.util.List;

import models.Event;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;

public class BaseController extends Controller {

	static final int MAXEVENTS = 10;
	
	@Before
    static void setConnectedUser() {
            User aktUser = User.find("byShortName", "Test").first();
            if (aktUser == null){
            	aktUser = new User("Test", false);
                aktUser.save();
            }
            renderArgs.put("aktUser", aktUser);
    }
	
    public static void showUsers(long id) {
    	Event event = Event.findById(id);
    	render("Basecontroller/showUsers.html",event);
    }
    
    public static List<Event> getEventsPaginated(Integer page){
    	if (page == null || page.intValue() == 0){
    		page = 1;
    	}
    	List<Event> events = Event.find(
                "order by date desc"
            ).from(MAXEVENTS*(page-1)).fetch(MAXEVENTS);
    	return events;
    }
	
}

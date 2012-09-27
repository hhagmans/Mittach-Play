package util.validation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.Event;

import play.data.validation.Check;
import play.mvc.Http.Request;

public class EventValidator extends Check {

	@Override
	public boolean isSatisfied(Object validatedObject, Object value) {
		boolean checkOK = true;
		if (Request.current().url.contains("admin")){
		Event event = (Event) validatedObject;
    	if (event.date instanceof Date != true ){
    		checkOK = false;
    		this.setMessage("Datum in falschem Format");
    	}
    	else {
        	Date aktDate = new Date();
    		if (event.date.compareTo(aktDate) < 0)
        	{	
    			checkOK = false;
    			this.setMessage("Datum schon vergangen");
        	}
    	}
    	
    	
    	if (event.id != null)
    	{
    	
    	if (Event.find("byDate", event.date).first() != null && Request.current().url.contains("edit") == false)
    	{
    		checkOK = false;
    		this.setMessage("Event an diesem Datum schon vorhanden");
    	}
    	}
    
    	
    	if (event.title == null || event.title.isEmpty())
    	{
    		checkOK = false;
    		this.setMessage("Speisentitel fehlt");
    	}
		}
		return checkOK; 
       
	}

}
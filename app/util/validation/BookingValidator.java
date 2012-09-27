package util.validation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.Event;

import play.data.validation.Check;
import play.mvc.Http.Request;

public class BookingValidator extends Check {

	@Override
	public boolean isSatisfied(Object validatedObject, Object value) {
		if (Request.current().url.contains("admin") == false){
    	Date aktDate = new Date();
    	Event event = (Event) validatedObject;
		if (event.date.compareTo(aktDate) < 0)
    	{
    		this.setMessage("Buchungen sind nicht mehr änderbar. Bitte Anja oder einen Admin fragen, wenn trotzdem etwas geändert werden soll.", "date");
    		return false;
    	}
		else {
			// Berechne Freitag der letzten Woche
			Calendar c = Calendar.getInstance();
			c.setTime(event.date);
			
			c.add(Calendar.WEEK_OF_YEAR, -1);
		    c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		    
		    Date lastFriday = c.getTime();

		    if (lastFriday.compareTo(aktDate) < 0)
	    	{
	    		this.setMessage("Achtung: Diese Buchungsänderung ist nicht vor Freitag vorgenommen worden. Bitte diese zeitig an Anja melden.", "date");
	    		return false;
	    	}
		}
		}
    	return true;
	}

}
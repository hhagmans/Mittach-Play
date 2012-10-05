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

public class Bookingcontroller extends Controller{

	public static void addBooking(@Valid Event event, long UserID, int page){
    	if (event != null) {
    	if(validation.hasErrors()) {
          params.flash(); // add http parameters to the flash scope
          validation.keep(); // keep the errors for the next request
          Application.index(1);
    	}
    	boolean vegetarian;
    	if (params.get("vegetarian") != null){
    		vegetarian = true;
    	} else {
    		vegetarian = false;
    	}
    	
    	Booking booking = new Booking(event.id, UserID, vegetarian);
    	booking.save();
    	event.bookings.add(booking);
    	event.save();
    	Application.index(1);
    	}
    	
    }
    public static void removeBooking(@Valid Event event, long id, int page){
    	if (event != null) {
        if(validation.hasErrors()) {
            params.flash(); // add http parameters to the flash scope
            validation.keep(); // keep the errors for the next request
            Application.index(1);
        }
        ListIterator<Booking> iter = event.bookings.listIterator();
        Booking book = null;
        while (iter.hasNext()){
    		book = iter.next();
    		if (book.EventID == event.id && book.UserID == id){
    			break;
    		}
    		}
        event.bookings.remove(book);
    	event.save();
    	Application.index(1);
    	}
    	
    }
    
    public static void editBooking(long id){
    	Event event = Event.findById(id);
    	render(event);
    }
    
    public static void editBookingSave(long id, String username, boolean vegetarian){
    	Event event = Event.findById(id);
    	User user = User.find("byShortname", username).first();
    	if (vegetarian == true){
    	if (user != null){
    		ListIterator<Booking> iter = event.bookings.listIterator();
            while (iter.hasNext()){
        		Booking book = iter.next();
        		if (book.EventID == event.id && book.UserID == user.id){
        			iter.remove();
        		}
        		
        		}
        	event.save();
    	}
    	}
    	else {
    		if (user == null){
    			user = new User(username,false);
    			user.save();
    		}
    		Booking booking = new Booking(event.id, user.id, false);
    		if (event.getUsers().contains(username) == false && user.shortname != ""){
        	booking.save();
    		event.bookings.add(booking);
        	event.save();
    		}
    	}
    	Application.adminIndex(1);
    	
    }
	
}

package models;

import java.util.*;

import javax.persistence.*;

 
import play.data.validation.Check;
import play.data.validation.CheckWith;
import play.db.jpa.*;

import util.validation.*;
 
@Entity
public class Event extends Model {
 
    public String title;
    @CheckWith(value=EventValidator.class, message="other")
    public Date date;
    
    public boolean vegetarian_opt;
    public int slots;
    
    @Lob
    public String details;
    
    @CheckWith(value=BookingValidator.class, message="other")
    @OneToMany(mappedBy="Event")
    public List<Booking> bookings;
    
     
    public Event(String title, String details, Date date, int slots, boolean vegetarian) { 
        this.bookings = new ArrayList<Booking>();
        this.title = title;
        this.slots = slots;
        this.details = details;
        this.date = date;
        this.vegetarian_opt = vegetarian;
    }
    
    public List<String> getUsers(){
    	ListIterator<Booking> iter = this.bookings.listIterator();
    	List<String> users = new ArrayList<String>();;
    	User user;
    	while (iter.hasNext()){
    		Booking book = iter.next();
    		user = User.findById(book.user.getId());
    		if (user != null)
    		users.add(user.shortname);
    }
    	return users;
    }
    
    public List<String> getVegetarians(){
    	ListIterator<Booking> iter = this.bookings.listIterator();
    	List<String> users = new ArrayList<String>();;
    	User user;
    	while (iter.hasNext()){
    		Booking book = iter.next();
    		user = User.findById(book.user.getId());
    		if (user != null && book.vegetarian)
    		users.add(user.shortname);
    }
    	return users;
    }
 
}

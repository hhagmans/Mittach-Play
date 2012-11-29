package models;

import java.util.*;
import javax.persistence.*;

 
import play.data.validation.Check;
import play.data.validation.CheckWith;
import play.db.jpa.*;

import util.validation.*;

@Entity
public class Booking extends Model{
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="event_id")
	public Event event;
	
	@ManyToOne
	public User user;
	public boolean vegetarian;
	
	
	public Booking(Event event, User user, boolean vegetarian){
		this.event = event;
		this.user = user;
		this.vegetarian = vegetarian;
	}
	
	@Override
	public String toString() {
		return "Booking[" + this.user.toString() + ", " + this.event.title + "]";
	}

}

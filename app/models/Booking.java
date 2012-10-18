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
	public Event Event;
	
	@ManyToOne
	public User user;
	public boolean vegetarian;
	
	
	public Booking(Event event, User user, boolean vegetarian){
		this.Event = event;
		this.user = user;
		this.vegetarian = vegetarian;
	}

}

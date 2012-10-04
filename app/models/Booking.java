package models;

import java.util.*;
import javax.persistence.*;

 
import play.data.validation.Check;
import play.data.validation.CheckWith;
import play.db.jpa.*;

import util.validation.*;

@Entity
public class Booking extends Model{
	
	public long EventID;
	public long UserID;
	public boolean vegetarian;
	
	public Booking(long event, long user, boolean vegetarian){
		this.EventID = event;
		this.UserID = user;
		this.vegetarian = vegetarian;
	}

}

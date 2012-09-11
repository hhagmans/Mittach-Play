package models;

import java.util.*;
import javax.persistence.*;

import org.hibernate.annotations.CollectionOfElements;
 
import play.db.jpa.*;
 
@Entity
public class Event extends Model {
 
    public String title;
    public Date date;
    public boolean vegetarian;
    public int slots;
    
    @Lob
    public String details;
    
    @CollectionOfElements
    public List<String> users;
    
    @CollectionOfElements
    public List<String> vegetarians;
     
    public Event(String title, String details, Date date, int slots, boolean vegetarian) { 
        this.users = new ArrayList<String>();
        this.vegetarians = new ArrayList<String>();
        this.title = title;
        this.slots = slots;
        this.details = details;
        this.date = date;
        this.vegetarian = vegetarian;
    }
 
}

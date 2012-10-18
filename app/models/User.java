package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class User extends Model {

	public String shortname;
	public boolean isAdmin;

	public User(String shortname, boolean isAdmin) {
		this.shortname = shortname;
		this.isAdmin = isAdmin;
	}

	public static User connect(String shortname) {
		return find("byShortname", shortname).first();
	}

}

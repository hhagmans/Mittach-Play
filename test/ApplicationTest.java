import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.junit.*;
import org.junit.Before;

import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;

public class ApplicationTest extends FunctionalTest {
	
	@Before
	public void setUp() {
	    Fixtures.deleteDatabase();
	    Fixtures.loadModels("fixtures.yml");
	}

    @Test
    public void testThatIndexPageWorks() {
        Response response = GET("/");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
        
        assertContentMatch("Pommes", response);
        assertContentMatch("Schnitzel", response);
        assertContentMatch("Spaghetti", response);
        
        //assertContentMatch("Auflauf", response);
    }
    
    @Test
    public void testBookings() {
    	assertEquals(2, User.count());
    	assertEquals(3, Event.count());
    	assertEquals(3, Booking.count());
    	
    	List<Booking> bookings = Booking.all().fetch();
    	
    	List<Event> events = Event.find("byTitle", "Pommes").fetch();
    	
    	for (Event event : events) {
    		assertEquals(1, event.bookings.size());
    		for (ListIterator<Booking> iter = event.bookings.listIterator(); iter.hasNext();) {
    			assertNotNull(iter.next().user);
    		}
		}
    }
    
}
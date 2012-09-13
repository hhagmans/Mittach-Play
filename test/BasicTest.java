import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

	@Before
    public void setup() {
        Fixtures.deleteAll();
    }
	
	@Test
	public void createAndRetrieveUser() {
	    // Create a new user and save it
	    new User("Bob", false).save();
	    
	    // Retrieve the user with name Bob
	    User bob = User.find("byShortname", "Bob").first();
	    
	    // Test 
	    assertNotNull(bob);
	    assertEquals("Bob", bob.shortname);
	}

	@Test
	public void createPost() {
	    
	    // Create a new event
	    new Event("Essen", "Essensdetails", new Date(), -1, false).save();
	    
	    // Test that the event has been created
	    assertEquals(1, Event.count());
	    
	    // Retrieve all events with title "Essen"
	    List<Event> allPosts = Event.find("byTitle", "Essen").fetch();
	    
	    // Tests
	    assertEquals(1, allPosts.size());
	    Event firstPost = allPosts.get(0);
	    assertNotNull(firstPost);
	    assertEquals("Essen", firstPost.title);
	    assertEquals("Essensdetails", firstPost.details);
	    assertNotNull(firstPost.date);
	}

}

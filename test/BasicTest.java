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
	    
	    // Create a new post
	    new Event("Essen", "Essensdetails", new Date(), -1, false).save();
	    
	    // Test that the post has been created
	    assertEquals(1, Event.count());
	    
	    // Retrieve all posts created by Bob
	    List<Event> allPosts = Event.find("byTitle", "Essen").fetch();
	    
	    // Tests
	    assertEquals(1, allPosts.size());
	    Event firstPost = allPosts.get(0);
	    assertNotNull(firstPost);
	    assertEquals("My first post", firstPost.title);
	    assertEquals("Hello world", firstPost.details);
	    assertNotNull(firstPost.date);
	}

}

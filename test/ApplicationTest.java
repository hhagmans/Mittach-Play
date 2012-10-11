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
    
}
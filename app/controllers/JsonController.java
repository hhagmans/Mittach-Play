package controllers;

import models.Event;

public class JsonController extends BaseController {
	
	public static void getEventAsJson(long eventID) {
		Event event = Event.findById(eventID);
		response.contentType = "application/json";
		renderJSON(event);
		
	}

}

package controllers;

import play.*;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.mvc.*;
import play.data.validation.Error;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import models.*;

public class IndexController extends BaseController {

	public static void index(Integer page) {
		if (page == null || page.intValue() == 0) {
			page = 1;
		}
		List<Event> events = getEventsPaginated(page);
		long cpages = Event.count() / MAXEVENTS + 1;
		render(events, cpages, page);
	}

	public static void addBooking(@Valid Event event, User user, int page) {
		if (event != null) {
			if (validation.hasErrors()) {
				params.flash(); // add http parameters to the flash scope
				validation.keep(); // keep the errors for the next request
				index(page);
			}
			boolean vegetarian;
			if (params.get("vegetarian") != null) {
				vegetarian = true;
			} else {
				vegetarian = false;
			}
			
			User myUser = User.findById(user.getId());

			Booking booking = new Booking(event, myUser, vegetarian);
			booking.save();
			event.bookings.add(booking);
			event.save();
			index(page);
		}

	}

	public static void removeBooking(@Valid Event event, User user, int page) {
		if (event != null) {
			if (validation.hasErrors()) {
				params.flash(); // add http parameters to the flash scope
				validation.keep(); // keep the errors for the next request
				index(page);
			}
			ListIterator<Booking> iter = event.bookings.listIterator();
			Booking book = null;
			while (iter.hasNext()) {
				book = iter.next();
				if (book.user.getId() == user.getId()) {
					break;
				}
			}
			//event.bookings.remove(book);
			book.delete();
			event.save();
			index(page);
		}

	}
}
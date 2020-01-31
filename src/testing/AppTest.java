package testing;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import elements.Event;
import elements.EventLister;
import main.EventHandler;

/**
 * Test: 
 * -Add calendar events.
 * -Delete calendar events.
 * -See all calendar events within a time frame.
 * 
 * @author Jens Benz
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTest {

	private static Event[] events;

	@BeforeAll
	static void setup() {
		new EventHandler().uniqueInit("saved.test");
		events = new Event[12];
		for (int i = 0; i < events.length; i++) {
			try {
				events[i] = new Event("test" + i, LocalDateTime.now().plusDays(i).minusHours(1),
						LocalDateTime.now().plusDays(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	void testSavingAndAdding() {
		Assertions.assertTrue(EventHandler.isEmpty());

		Event newEvent = null;
		try {
			newEvent = new Event("test1", LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		EventHandler.add(newEvent);

		Assertions.assertFalse(EventHandler.isEmpty());
		Assertions.assertTrue(EventHandler.contains(newEvent));
	}

	@Test
	@Order(1)
	void testAdding() {

		for (Event event : events) {
			EventHandler.add(event);
		}

		for (Event event : events) {
			Assertions.assertTrue(EventHandler.contains(event));
		}
	}

	@Test
	@Order(2)
	void testSearch() {
		EventLister lister = new EventLister();

		LocalDateTime before = LocalDateTime.now();
		LocalDateTime from = LocalDateTime.now().plusDays(2);
		LocalDateTime to = LocalDateTime.now().plusDays(4);
		LocalDateTime after = LocalDateTime.now().plusDays(8);

		lister.show(from, to);

		Event[] events = lister.getShownEvents();

		for (Event e : events) {
			Assertions.assertTrue(e.isWithinTime(from, to));
			if (!e.isSameDay(from.toLocalDate()))
				Assertions.assertFalse(e.isWithinTime(before, from));
			if (!e.isSameDay(to.toLocalDate()))
				Assertions.assertFalse(e.isWithinTime(to, after));
		}
	}

	@Test
	@Order(3)
	void testSortedRightWay() {
		EventLister lister = new EventLister();

		LocalDateTime from = LocalDateTime.now().plusDays(2);
		LocalDateTime to = LocalDateTime.now().plusDays(4);

		lister.show(from, to);
		
		Event[] events = lister.getShownEvents();
		Event last = events[0];

		for (int i = 1; i < events.length; i++) {
			Event curr = events[i];
			Assertions.assertTrue(curr.isBefore(last));
			last = curr;
		}
	}

	@Test
	@Order(4)
	void testRemove() {
		
		EventLister lister = new EventLister();

		LocalDateTime from = LocalDateTime.now().plusDays(2);
		LocalDateTime to = LocalDateTime.now().plusDays(4);

		lister.show(from, to);

		for (Event e : lister.getShownEvents()) {
			lister.removeShownElement(e);
		}
		Assertions.assertTrue(lister.getShownEvents().length == 0);

		for (Event e : EventHandler.list()) {
			EventHandler.remove(e);
		}

		Assertions.assertTrue(EventHandler.isEmpty());
	}

	@AfterAll
	static void tear() {
		System.out.println("@AfterAll executed");
	}
}
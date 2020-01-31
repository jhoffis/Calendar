package main;

import java.util.Iterator;
import java.util.TreeSet;

import elements.Event;
import files.FileUtil;

/**
 * Manipulates the events that are or will be on this computer. Ubiquitous access to events.
 * 
 * @author Jens Benz
 *
 */
public class EventHandler {

	private static TreeSet<Event> events;
	private static FileUtil file;
	
	/**
	 * Creates temporary memory of the events that will be forgotten after the program closes
	 * 
	 * @param filename location of file with type
	 */
	public void uniqueInit(String filename) {
		file = new FileUtil();
		file.uniqueInit(filename);
		init();
	}
	
	/**
	 * Sets up a savefile to remember the events the user wants to have or already has.
	 * 
	 * @param filename location of file with type
	 */
	public void init(String filename) {
		file = new FileUtil();
		file.init(filename);
		init();
	}
	
	private void init() {
		events = file.getEvents();
	}
	
	/**
	 * Add event from memory and hdd
	 */
	public static void add(Event event) {
		events.add(event);
		file.addEvent(event);
	}

	/**
	 * Remove event from memory and hdd
	 */
	public static void remove(Event event) {
		events.remove(event);
		file.removeEvent(event);
	}

	public static boolean isEmpty() {
		return events.isEmpty();
	}

	public static Iterator<Event> iterator() {
		return events.iterator();
	}

	/**
	 * @return sorted array of events in memory
	 */
	public static Event[] list() {
		return events.toArray(new Event[events.size()]);
	}
	
	/**
	 * @return whether or not event is saved onto the computer
	 */
	public static boolean contains(Event event) {
		return events.contains(event);
	}
	
}

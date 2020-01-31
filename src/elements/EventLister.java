package elements;

import java.awt.Dimension;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import main.EventHandler;

/**
 * 
 * A visual, scrollable list of events that allows for selection of single
 * events. Selected events can be fetched and if need be deleted. Though that is
 * not the listers responsibility.
 * 
 * @author Jens Benz
 *
 */
public class EventLister extends JScrollPane implements Element {

	private static final long serialVersionUID = -2818655613226824125L;
	private static DefaultListModel<Event> model = new DefaultListModel<Event>();
	private static JList<Event> list;

	private int fromX;
	private int fromY;
	private int w;
	private int h;

	public EventLister() {
		super(list = new JList<Event>(model));
	}

	/**
	 * Resets and shows the given events
	 */
	public void show(ArrayList<Event> events) {

		model.removeAllElements();

		if (events.isEmpty()) {
			return;
		}

		for (Event e : events) {
			model.addElement(e);
		}

	}

	/**
	 * Find and show events within a timeframe onto the list.
	 */
	public void show(LocalDateTime from, LocalDateTime to) {
		ArrayList<Event> foundEvents = new ArrayList<Event>();

		for (Event event : EventHandler.list()) {
			if (event.isWithinTime(from, to))
				foundEvents.add(event);
		}

		show(foundEvents);
	}

	/**
	 * @return event user has chosen
	 */
	public Event getSelectedEvent() {
		return list.getSelectedValue();
	}

	/**
	 * @return all events in the list
	 */
	public Event[] getShownEvents() {
		Event[] events = new Event[model.getSize()];
		for (int i = 0; i < events.length; i++) {
			events[i] = model.get(i);
		}
		return events;
	}
	
	/**
	 * Remove locally from the list
	 */
	public void removeShownElement(Event event) {
		model.removeElement(event);
	}

	@Override
	public void setFromPoint(int x, int y) {
		this.fromX = x;
		this.fromY = y;
	}

	@Override
	public int getFromPointX() {
		return fromX;
	}

	@Override
	public int getFromPointY() {
		return fromY;
	}

	@Override
	public void setPlacement(int x, int y, int w, int h) {
		this.w = w;
		this.h = h;

		setPreferredSize(new Dimension(w, h));
		setBounds(fromX + x, fromY + y, w, h);
	}

	@Override
	public int getPlacementHeight() {
		return h;
	}

	@Override
	public int getPlacementWidth() {
		return w;
	}
}

package elements;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * An event that lasts from one timestamp to another. Therefore an event can
 * last minutes or years.
 * 
 * @author Jens Benz
 *
 */
public class Event implements Comparable<Event> {

	public static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm-dd/LL/yyyy");
	private String title;
	private LocalDateTime from, to;

	/**
	 * @throws Exception if from is after to
	 */
	public Event(String title, LocalDateTime from, LocalDateTime to) throws Exception {
		this.title = title;
		if (from.isAfter(to))
			throw new Exception();
		this.from = from;
		this.to = to;
	}

	/**
	 * Used mainly to interpret user input from textfields to this class Event.
	 * 
	 * @throws Exception if from or to cannot be understood or from is after to
	 */
	public Event(String title, String from, String to) throws Exception {
		this(title, LocalDateTime.parse(from, FORMATTER), LocalDateTime.parse(to, FORMATTER));
	}

	/**
	 * Used mainly to interpret the saved events to this class Event
	 * 
	 * @param line from file to be split
	 */
	public Event(String line) {

		// Converting 'dd-MMM-yyyy' String format to LocalDate
		String[] lines = line.split("=");
		title = lines[0];

		String from = lines[1].split("#")[0];
		String to = lines[1].split("#")[1];

		this.from = LocalDateTime.parse(from, FORMATTER);
		this.to = LocalDateTime.parse(to, FORMATTER);
	}

	/**
	 * Creates a compact string representing this event that can easially be stored
	 */
	public String toStringLine() {
		return title + "=" + from.format(FORMATTER) + "#" + to.format(FORMATTER);
	}

	/**
	 * Creates a stylized string representing this event
	 */
	@Override
	public String toString() {
		return "<html>\"" + title + "\" from <br/>" + getTime(from) + "<br/> to <br/>" + getTime(to)
				+ "<br/><br/></html>";
	}

	private String getTime(LocalDateTime time) {
		return time.getDayOfMonth() + "/" + time.getMonthValue() + " at " + time.getHour() + ":" + time.getMinute();
	}

	/**
	 * @param writtenLine
	 * @return is the string equal to this event object
	 */
	public boolean equalsWritten(String writtenLine) {
		return writtenLine != null && writtenLine.equals(toStringLine());
	}

	@Override
	public boolean equals(Object o) {
		if(!o.getClass().equals(Event.class))
			return false;
		
		Event other = (Event) o;
		return other.toStringLine().equals(this.toStringLine()) && other.getTitle().equals(this.title);
	}

	/**
	 * Used to sort lists
	 */
	@Override
	public int compareTo(Event other) {
		if (other.equals(this))
			return 0;
		else
			return from.isBefore(other.getFrom()) ? 1 : -1;
	}

	/**
	 * Is this event partly or completely within the timeframe from -> to
	 */
	public boolean isWithinTime(LocalDateTime from, LocalDateTime to) {
		return isSameDay(from.toLocalDate()) || isSameDay(to.toLocalDate())
				|| (this.from.isBefore(from) && this.to.isAfter(from)
						|| (this.from.isAfter(from) && (this.from.isBefore(to))));
	}

	/**
	 * @return this event contains same day
	 */
	public boolean isContainingDate(LocalDate date) {
		return isSameDay(date) || (date.isAfter(from.toLocalDate()) && date.isBefore(to.toLocalDate()));
	}

	public boolean isSameDay(LocalDate date) {
		return date.isEqual(from.toLocalDate()) || date.isEqual(to.toLocalDate());
	}

	public boolean isBefore(Event other) {
		return to.isBefore(other.getFrom());
	}

	public LocalDateTime getFrom() {
		return from;
	}

	public void setFrom(LocalDateTime from) {
		this.from = from;
	}

	public LocalDateTime getTo() {
		return to;
	}

	public void setTo(LocalDateTime to) {
		this.to = to;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}

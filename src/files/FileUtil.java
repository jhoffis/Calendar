package files;

import java.util.TreeSet;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import elements.Event;

/**
 * Reads and writes Event-s to whereever
 * 
 * @author Jens Benz
 *
 */
public class FileUtil {

	private File file;
	private List<String> lines;

	/**
	 * Creates a temporary text file that is deleted after the program closes.
	 * Primarily used for testing
	 * 
	 * @param filename location of file with type
	 */
	public void uniqueInit(String filename) {
		file = new File(filename);

		try {
			if (file.isFile())
				file.delete();
			file.createNewFile();
			file.deleteOnExit();
			readLines();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Creates a save file to store events. Checks whether or not it already exists.
	 * Gets the events if it already does.
	 * 
	 * @param filename location of the file with the type
	 */
	public void init(String filename) {
		file = new File(filename);

		try {
			if (!file.isFile()) {
				if (file.createNewFile()) {
					PrintWriter pw = new PrintWriter(file);
					pw.flush();
					pw.close();
				}
			}
			readLines();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Gets all lines in file as string
	 */
	private void readLines() throws IOException {
		lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
	}

	/**
	 * Writes all lines in file
	 */
	private void writeLines() throws IOException {
		Files.write(file.toPath(), lines, StandardCharsets.UTF_8);
	}

	/**
	 * overrides the previous line, but be vary of wrong linenr order
	 */
	private void writeToLine(String line, int linenr) {

		while (linenr >= lines.size()) {
			lines.add("null");
		}

		lines.set(linenr, line);

		try {
			writeLines();
			readLines();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Event getEvent(int linenr) {
		Event res = null;

		try {
			if (linenr < lines.size())
				res = new Event(lines.get(linenr));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	/**
	 * Get all events in a sorted manner
	 */
	public TreeSet<Event> getEvents() {

		TreeSet<Event> res = new TreeSet<Event>();

		for (int i = 0; i < lines.size(); i++) {
			res.add(getEvent(i));
		}

		return res;
	}

	/**
	 * Rewrites and adds event to the file. Events are always placed at the bottom
	 * in an unsorted way.
	 */
	public void addEvent(Event event) {
		writeToLine(event.toStringLine(), lines.size());
	}

	/**
	 * Rewrites and removes event and moves indirectly all lines under up one line
	 * in the files.
	 */
	public void removeEvent(Event event) {
		for (int i = 0; i < lines.size(); i++) {
			if (event.equalsWritten(lines.get(i)))
				lines.remove(i);
		}

		try {
			writeLines();
			readLines();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

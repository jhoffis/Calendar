package scenes.subscenes;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;

import elements.Button;
import elements.DateTile;
import elements.Event;
import elements.EventLister;
import main.EventHandler;
import scenes.SceneChangeAction;
import scenes.SubScenes;
import scenes.whole_scenes.Scene;

/**
 * Graphically distribute tiles across this subscene so that the user can check
 * out which days are taken and what events they possess.
 * 
 * @author Jens Benz
 *
 */
public class TilesSubScene extends SubScene {

	private EventLister eventLister;
	private HashMap<LocalDate, DateTile> tiles;

	// "now" is used for navigating months, beginning with now.
	private LocalDate now;

	public TilesSubScene() {
		super(SubScenes.TILES);
	}

	@Override
	public void setSubSceneChangeAction(SceneChangeAction sceneChange) {

	}

	@Override
	public void render(Scene ontoScene) {
		if (tiles == null)
			return;

		for (Entry<LocalDate, DateTile> tile : tiles.entrySet()) {
			tile.getValue().render(ontoScene);
		}
	}

	@Override
	public void addElementsToScene(Scene scene) {
		if (tiles == null)
			return;

		for (Entry<LocalDate, DateTile> tile : tiles.entrySet()) {
			scene.addComponent(tile.getValue());
		}
	}

	@Override
	public void removeElementsToScene(Scene scene) {
		if (tiles == null)
			return;

		for (Entry<LocalDate, DateTile> tile : tiles.entrySet()) {
			scene.removeComponent(tile.getValue());
		}
	}

	@Override
	public void init() {
		// Show this month
		now = LocalDate.now();

		setMonthTiles();
	}

	/**
	 * Update tiles this subscene contains to the next months days and their events.
	 */
	public void goToNextMonth() {
		now = now.plusMonths(1);
		setMonthTiles();
	}

	/**
	 * Update tiles this subscene contains to the previous months days and their
	 * events.
	 */
	public void goToPreviousMonth() {
		now = now.minusMonths(1);
		setMonthTiles();
	}

	/**
	 * Change color of every tile going through each day and look for whether or not the
	 * events contains the day.
	 */
	public void updateColorStatusTiles() {
		if (EventHandler.isEmpty())
			return;

		Iterator<Event> it = EventHandler.iterator();
		for (Entry<LocalDate, DateTile> tile : tiles.entrySet()) {

			while (it.hasNext()) {
				if (it.next().isContainingDate(tile.getKey())) {
					tile.getValue().setStatusColor(Color.RED);
					break;
				}
			}

			// Reset
			it = EventHandler.iterator();
		}
	}

	/**
	 * Go through every day of the current month, add action to each tile and remember them.
	 */
	public void setMonthTiles() {

		tiles = new HashMap<LocalDate, DateTile>();

		int year = now.getYear();
		Month month = now.getMonth();
		int lengthOfMonth = now.lengthOfMonth();

		LocalDate from = LocalDate.of(year, month, 1);
		LocalDate to = LocalDate.of(year, month, lengthOfMonth);

		long days = ChronoUnit.DAYS.between(from, to) + 1;

		LocalDate currentDate = from;

		for (int i = 0; i < days; i++) {

			DateTile currentTile = new DateTile(currentDate);

			AtomicReference<LocalDate> dateRef = new AtomicReference<LocalDate>(currentDate);
			currentTile.addClickAction((ActionEvent e) -> {
				eventLister.show(getEvents(dateRef.get()));
			});

			tiles.put(currentDate, currentTile);
			addTile(currentDate, currentTile);

			currentDate = currentDate.plusDays(1);
		}

		updateColorStatusTiles();
	}

	/**
	 * Finds every event that contains the date of your choice
	 */
	private ArrayList<Event> getEvents(LocalDate date) {
		ArrayList<Event> res = new ArrayList<Event>();
		Iterator<Event> it = EventHandler.iterator();

		while (it.hasNext()) {
			Event e = it.next();
			if (e.isContainingDate(date)) {
				res.add(e);
			}
		}

		return res;
	}

	/**
	 * Adds tile to the screen
	 */
	private void addTile(LocalDate currentDate, Button currentTile) {
		// place properly
		int amountTilesShowing = tiles.size();
		int amountPerLine = 7;

		int tileSize = w / amountPerLine;
		int tileMargin = (int) (tileSize * 0.2);
		tileSize -= tileMargin * 2;

		int col = amountTilesShowing % amountPerLine;
		int row = amountTilesShowing / amountPerLine;

		currentTile.setFromPoint(fromX, fromY);

		int x = tileMargin + tileMargin * col + tileSize * col;
		int y = tileMargin + tileMargin * row + tileSize * row;

		currentTile.setPlacement(x, y, tileSize, tileSize);

	}

	public void setEventLister(EventLister eventLister) {
		this.eventLister = eventLister;
	}

	@Override
	public void update() {
		if (now != null)
			setMonthTiles();
	}

}

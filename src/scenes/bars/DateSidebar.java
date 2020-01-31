package scenes.bars;

import elements.Button;
import elements.Event;
import elements.EventLister;
import main.EventHandler;
import scenes.SceneChangeAction;
import scenes.SceneComponent;
import scenes.whole_scenes.Scene;

/**
 * Handles an eventLister graphically and allows for deletion of selected event
 * from list graphically.
 * 
 * @author Jens Benz
 *
 */
public class DateSidebar implements SceneComponent {

	@SuppressWarnings("unused")
	private SceneChangeAction sceneChange;

	private Button[] buttons;
	private EventLister eventLister;
	private int fromX, fromY, w, h;

	public DateSidebar() {
		this.buttons = new Button[0];
		eventLister = new EventLister();
	}

	/**
	 * User removes from the list, memory and hdd
	 */
	public void removeSelectedEvent() {
		Event event = eventLister.getSelectedEvent();
		if (event != null) {
			eventLister.removeShownElement(event);
			EventHandler.remove(event);
		}
	}

	/**
	 * TODO Could be used to make it possible to add a new event from a date. Then
	 * the textfields could already be filled in with current date.
	 */
	@Override
	public void setSubSceneChangeAction(SceneChangeAction sceneChange) {
		this.sceneChange = sceneChange;
	}

	@Override
	public void setFromPoint(int x, int y) {
		this.fromX = x;
		this.fromY = y;
		// Place with the bar
		for (Button btn : buttons) {
			btn.setFromPoint(x, y);
		}
		eventLister.setFromPoint(x, y);
	}

	@Override
	public void setPlacement(int x, int y, int w, int h) {
		this.w = w;
		this.h = h;

		// Figure out where to place the buttons
		eventLister.setPlacement(x, y, w, h);
	}

	@Override
	public int getPlacementHeight() {
		return h;
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
	public int getPlacementWidth() {
		return w;
	}

	@Override
	public void addElementsToScene(Scene scene) {
		// Add buttons to the scene
		for (Button btn : buttons) {
			scene.addComponent(btn);
		}

		scene.addComponent(eventLister);
	}

	public EventLister getEventListener() {
		return eventLister;
	}

	public void setEventListener(EventLister eventScrollPane) {
		this.eventLister = eventScrollPane;
	}

	@Override
	public void removeElementsToScene(Scene scene) {
		for (Button btn : buttons) {
			scene.removeComponent(btn);
		}

		scene.removeComponent(eventLister);
	}

}

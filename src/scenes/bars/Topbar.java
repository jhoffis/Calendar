package scenes.bars;

import java.awt.event.ActionEvent;

import elements.Button;
import elements.RegularButton;
import io.Window;
import scenes.SceneChangeAction;
import scenes.SceneComponent;
import scenes.SubScenes;
import scenes.subscenes.TilesSubScene;
import scenes.whole_scenes.Scene;

/**
 * User control-center used to manipulate the software graphically and
 * manipulate events.
 * 
 * @author Jens Benz
 *
 */
public class Topbar implements SceneComponent {

	private Button[] buttons;
	private SceneChangeAction sceneChange;
	private int fromX, fromY, w, h;

	/**
	 * Sets up buttons and their actions.
	 */
	public void init(TilesSubScene tilesSubScene, Scene scene, Window window, DateSidebar dateSidebar) {

		buttons = new Button[5];
		Button addEvent = new RegularButton();
		Button removeEvent = new RegularButton();
		Button searchEvents = new RegularButton();
		Button prevMonth = new RegularButton();
		Button nextMonth = new RegularButton();

		addEvent.setTitle("Add");
		removeEvent.setTitle("Remove");
		searchEvents.setTitle("Search");
		prevMonth.setTitle("Prev. month");
		nextMonth.setTitle("Next month");

		addEvent.addClickAction((ActionEvent e) -> {
			sceneChange.change(SubScenes.CREATE);
		});
		removeEvent.addClickAction((ActionEvent e) -> {
			dateSidebar.removeSelectedEvent();
			sceneChange.change(SubScenes.CURRENT);
		});
		searchEvents.addClickAction((ActionEvent e) -> {
			sceneChange.change(SubScenes.SEARCH);
		});
		prevMonth.addClickAction((ActionEvent e) -> {
			if (SubScenes.TILES == SubScenes.CURRENT) {
				tilesSubScene.removeElementsToScene(scene);
				tilesSubScene.goToPreviousMonth();
				tilesSubScene.addElementsToScene(scene);
				scene.render(window);
			}
		});
		nextMonth.addClickAction((ActionEvent e) -> {
			if (SubScenes.TILES == SubScenes.CURRENT) {
				tilesSubScene.removeElementsToScene(scene);
				tilesSubScene.goToNextMonth();
				tilesSubScene.addElementsToScene(scene);
				scene.render(window);
			}
		});

		buttons[0] = addEvent;
		buttons[1] = removeEvent;
		buttons[2] = searchEvents;
		buttons[3] = prevMonth;
		buttons[4] = nextMonth;

	}

	@Override
	public void setSubSceneChangeAction(SceneChangeAction sceneChange) {
		this.sceneChange = sceneChange;
	}

	/**
	 *  Place with the bar
	 */
	@Override
	public void setFromPoint(int x, int y) {
		this.fromX = x;
		this.fromY = y;
		for (Button btn : buttons) {
			btn.setFromPoint(x, y);
		}
	}

	/**
	 * Place buttons so that they stay within their bounds dynamically
	 */
	@Override
	public void setPlacement(int x, int y, int w, int h) {
		this.w = w;
		this.h = h;

		// Figure out where to place the buttons
		int btnMarginTopStd = h / 16;
		int btnMarginSideStd = btnMarginTopStd * 2;

		int btnW = w / buttons.length - (btnMarginSideStd * (buttons.length + 1));
		int btnH = h - (btnMarginTopStd * 2);

		int btnMarginLeft = btnMarginSideStd;
		int btnMarginTop = btnMarginTopStd;
		// Actually place
		for (Button btn : buttons) {
			btn.setPlacement(btnMarginLeft, btnMarginTop, btnW, btnH);
			btn.render(null);

			btnMarginLeft += btnW + btnMarginSideStd;
		}

	}

	/**
	 * Add buttons to the scene
	 */
	@Override
	public void addElementsToScene(Scene scene) {
		for (Button btn : buttons) {
			scene.addComponent(btn);
		}
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
	public void removeElementsToScene(Scene scene) {
		for (Button btn : buttons) {
			scene.removeComponent(btn);
		}
	}

}

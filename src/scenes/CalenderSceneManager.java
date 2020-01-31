package scenes;

import io.Window;
import scenes.bars.DateSidebar;
import scenes.bars.Topbar;
import scenes.subscenes.CreateSubScene;
import scenes.subscenes.SearchSubScene;
import scenes.subscenes.SubScene;
import scenes.subscenes.TilesSubScene;
import scenes.whole_scenes.MainScene;
import scenes.whole_scenes.Scene;

/**
 * Main controller that watches and tells what should be drawn on the window.
 * 
 * Holds SubScenes
 * 
 * @author Jens Benz
 *
 */
public class CalenderSceneManager {

	private Scene[] scenes;
	private SubScene[] subScenes;
	private Topbar topbar;
	private DateSidebar dateSidebar;

	public CalenderSceneManager() {

		subScenes = new SubScene[3];
		subScenes[SubScenes.TILES] = new TilesSubScene();
		subScenes[SubScenes.CREATE] = new CreateSubScene();
		subScenes[SubScenes.SEARCH] = new SearchSubScene();

		topbar = new Topbar();
		dateSidebar = new DateSidebar();

		scenes = new Scene[1];
		scenes[Scenes.MAIN] = new MainScene();
	}

	/**
	 * Place and configure all scenecomponents, subscenes and scene.
	 * @param window to enable redrawing of screen.
	 */
	public void init(Window window) {
		changeSubScene(SubScenes.TILES);
		TilesSubScene tileSubScene = (TilesSubScene) getCurrentSubScene();

		topbar.init(tileSubScene, getCurrentScene(), window, dateSidebar);
		topbar.setFromPoint(0, 0);
		topbar.setPlacement(0, 0, (int) (Window.WIDTH * 0.8), (int) (Window.HEIGHT * 0.11));
		topbar.addElementsToScene(getCurrentScene());

		int width = (int) (topbar.getPlacementWidth() * 0.92);
		dateSidebar.setFromPoint(width, 0);
		dateSidebar.setPlacement(0, 0, Window.WIDTH - width, Window.HEIGHT);
		dateSidebar.addElementsToScene(getCurrentScene());

		SearchSubScene searchSubScene = (SearchSubScene) subScenes[SubScenes.SEARCH];
		searchSubScene.setEventLister(dateSidebar.getEventListener());

		tileSubScene.setEventLister(dateSidebar.getEventListener());
		for (SubScene subScene : subScenes) {
			subScene.setFromPoint(0, topbar.getPlacementHeight());
			subScene.setPlacement(0, 0, topbar.getPlacementWidth(), Window.HEIGHT - topbar.getPlacementHeight());
			subScene.init();
		}
		tileSubScene.addElementsToScene(getCurrentScene());

		window.addScene(getCurrentScene());
		getCurrentScene().render(window);
	}

	/**
	 * Used to allow lower entities to interchange without everything above
	 * babysitting.
	 * 
	 */
	public void initChangeSubSceneActions(Window window) {

		SceneChangeAction sceneChange = (scenenr) -> {
			changeSubScene(scenenr);
			getCurrentScene().render(window);
		};
		for (SubScene ss : subScenes) {
			ss.setSubSceneChangeAction(sceneChange);
		}
		topbar.setSubSceneChangeAction(sceneChange);
		dateSidebar.setSubSceneChangeAction(sceneChange);
	}

	/**
	 * Updates and changes the subscene. Saves a reference to the previous one in case
	 * of a return button. Also updates the current scene.
	 * 
	 * @param scenenr
	 */
	public void changeSubScene(int scenenr) {
		SubScenes.PREVIOUS = SubScenes.CURRENT;
		SubScenes.CURRENT = scenenr;

		subScenes[SubScenes.PREVIOUS].removeElementsToScene(getCurrentScene());
		getCurrentScene().setSubScene(getCurrentSubScene());
		getCurrentSubScene().update();
		getCurrentSubScene().addElementsToScene(getCurrentScene());
	}

	public SubScene getCurrentSubScene() {
		return subScenes[SubScenes.CURRENT];
	}

	/**
	 * Regular all-encompassing scene change
	 */
	public void changeScene(int scenenr) {
		Scenes.PREVIOUS = Scenes.CURRENT;
		Scenes.CURRENT = scenenr;
	}

	/**
	 * Retrive the current scene in use
	 * 
	 * Used mainly so that the programmer does not need to worry about whether
	 * scenes is an arraylist or something else
	 */
	public Scene getCurrentScene() {
		return scenes[Scenes.CURRENT];
	}

}

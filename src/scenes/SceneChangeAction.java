package scenes;

/**
 * Used to be able to switch scenes from within another scene or bar.
 * 
 * Can switch subscenes or regular scenes. TODO Although switching between
 * regular scenes is not implemented.
 * 
 * @author Jens Benz
 *
 */
public interface SceneChangeAction {

	/**
	 * @param scenenr from Scenes or SubScenes
	 */
	public void change(int scenenr);
}

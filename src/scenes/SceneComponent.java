package scenes;

import elements.Element;
import scenes.whole_scenes.Scene;

/**
 * Used by larger containers like a bar or subscene within a regular scene.
 * 
 * @author Jens Benz
 *
 */
public interface SceneComponent extends Element {

	/**
	 * Change the subscene from within this component instead of externally
	 * @param sceneChange
	 */
	public abstract void setSubSceneChangeAction(SceneChangeAction sceneChange);
	
	/**
	 * Add all elements that this scenecomponent holds onto the scene
	 * @param scene
	 */
	public abstract void addElementsToScene(Scene scene);
	
	/**
	 * Remove all elements that this scenecomponent holds onto the scene
	 * @param scene
	 */
	public abstract void removeElementsToScene(Scene scene);

}

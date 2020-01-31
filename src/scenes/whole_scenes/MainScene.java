package scenes.whole_scenes;

import java.awt.Component;

import javax.swing.JPanel;

import elements.Element;
import io.Window;
import scenes.subscenes.SubScene;

/**
 * Holds all current Elements as well as the current subScene at a certain time.
 * Renders them.
 * 
 * @author Jens Benz
 *
 */
public class MainScene extends JPanel implements Scene {

	private static final long serialVersionUID = -9089574696420528594L;
	private SubScene subScene;

	/**
	 * Sets up a scene that explicitly places the underlying elements by pixel from
	 * top left corner (0,0)
	 */
	public MainScene() {
		setLayout(null);
	}

	@Override
	public void addComponent(Element comp) {
		add((Component) comp);
	}

	@Override
	public void removeComponent(Element comp) {
		remove((Component) comp);
	}

	@Override
	public void setSubScene(SubScene subScene) {
		this.subScene = subScene;
	}

	@Override
	public void render(Window window) {
		subScene.render(this);

		window.repaintWindow();
	}

	@Override
	public boolean isCurrentSubScene(int scene) {
		return subScene.getID() == scene;
	}

}

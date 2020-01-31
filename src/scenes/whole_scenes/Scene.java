package scenes.whole_scenes;

import elements.Element;
import io.Window;
import scenes.subscenes.SubScene;

public interface Scene {

	void addComponent(Element comp);
	void removeComponent(Element comp);
	void setSubScene(SubScene subScene);
	void render(Window window);
	boolean isCurrentSubScene(int tiles);

}

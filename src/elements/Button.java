package elements;

import java.awt.event.ActionListener;

import scenes.whole_scenes.Scene;

public interface Button extends Element {

	void addClickAction(ActionListener action);
	
	/**
	 * Text that is written onto the button.
	 */
	void setTitle(String title);
	
	/**
	 * Draws the button onto the window
	 */
	void render(Scene ontoScene);

}

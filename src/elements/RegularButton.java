package elements;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import scenes.whole_scenes.Scene;

/**
 * A button that does what you expect from a button. Runs an action when pressed
 * by the user and has text in the middle of itself.
 * 
 * @author Jens Benz
 *
 */
public class RegularButton extends JButton implements Button {

	private static final long serialVersionUID = -8455518464314374708L;
	private int fromX, fromY, x, y, w, h;

	public RegularButton() {
		setText("Button");
	}

	public RegularButton(String title) {
		setText(title);
	}

	@Override
	public void addClickAction(ActionListener action) {
		addActionListener(action);
	}

	@Override
	public void setTitle(String title) {
		setText(title);
	}

	@Override
	public void setFromPoint(int x, int y) {
		fromX = x;
		fromY = y;
	}

	@Override
	public void setPlacement(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	@Override
	public void render(Scene ontoScene) {
		setBounds(fromX + x, fromY + y, w, h);
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
	public int getPlacementHeight() {
		return h;
	}

	@Override
	public int getPlacementWidth() {
		return w;
	}

}

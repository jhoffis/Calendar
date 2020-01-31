package elements;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import scenes.whole_scenes.Scene;

/**
 * Allows the user to edit a single line of text. Can support having an action
 * to happen if pressed, like a button
 * 
 * @author Jens Benz
 *
 */
public class TextInput extends JTextField implements Button {

	private static final long serialVersionUID = -8455518464314374708L;
	private int fromX, fromY, x, y, w, h;

	/**
	 * @param firstText is the text that the textfield should already contain.
	 */
	public TextInput(String firstText) {
		setText(firstText);
	}

	/**
	 * What does the single line of text contain?
	 */
	public String getText() {
		return super.getText();
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
		setPreferredSize(new Dimension(w, h));
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

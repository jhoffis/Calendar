package elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import scenes.whole_scenes.Scene;

/**
 * A box that acts as a button. Used to represent a day in a month visually to
 * the user. Shows different colors based upon it's status. For instance whether
 * or not it has an event.
 * 
 * @author Jens Benz
 *
 */
public class DateTile extends JButton implements Button {

	private static final long serialVersionUID = 2573303972574324307L;
	private Color statusColor;
	private LocalDate date;

	private int fromX, fromY, x, y, w, h;

	/**
	 * @param date What day should this tile show?
	 */
	public DateTile(LocalDate date) {
		this.date = date;
		setStatusColor(Color.BLUE);
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
	public void addClickAction(ActionListener action) {
		addActionListener(action);
	}

	@Override
	public void setTitle(String title) {
		setText(title);
	}

	@Override
	public void render(Scene ontoScene) {
		setBounds(fromX + x, fromY + y, w, h);

		BufferedImage tileImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = tileImage.createGraphics();

		// Draw box or tile
		g2.setColor(statusColor);
		g2.fillRect(0, 0, w, h);

		// Draw text upon tile
		g2.setColor(Color.WHITE);
		drawString(g2, date.getDayOfWeek().toString().substring(0, 3) + "\n" + date.getDayOfMonth() + " "
				+ date.getMonth().toString().substring(0, 3) + "\n" + date.getYear(), w / 12, 0);

		// Place box or tile upon the this button
		ImageIcon icon = new ImageIcon(tileImage);
		this.setIcon(icon);
	}

	/**
	 * Used to draw multiple lines of text upon the tile. So that all lines fit
	 * within the box/tile
	 */
	private void drawString(Graphics2D g2, String text, int x, int y) {
		for (String line : text.split("\n"))
			g2.drawString(line, x, y += g2.getFontMetrics().getHeight() * 0.9f);
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

	public Color getStatusColor() {
		return statusColor;
	}

	public void setStatusColor(Color statusColor) {
		this.statusColor = statusColor;
	}

}

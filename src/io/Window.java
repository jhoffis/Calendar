package io;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;

import scenes.whole_scenes.Scene;

/**
 * Unresizable window that holds underlying scene and stats about itself. Bases
 * size upon current resolution.
 * 
 * @author Jens Benz
 *
 */
public class Window extends JFrame {

	public static int WIDTH, HEIGHT;

	/**
	 * Generated ID
	 */
	private static final long serialVersionUID = -5781174098463759017L;
	private String title;

	public Window() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		HEIGHT = gd.getDisplayMode().getHeight() * 2 / 3;
		WIDTH = HEIGHT * 4 / 3;
		title = "Calender";

		setTitle(title);
		setBounds(50, 50, WIDTH, HEIGHT);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	/**
	 * Update window so that it shows the correct and current elements on the
	 * screen.
	 */
	public void repaintWindow() {
		invalidate();
		validate();
		repaint();
	}

	public void addScene(Scene scene) {
		add((JPanel) scene);
	}

}

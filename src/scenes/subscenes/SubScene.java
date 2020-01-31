package scenes.subscenes;

import scenes.SceneComponent;
import scenes.whole_scenes.Scene;

/**
 * 
 * This is the representation of the interaction window within the actual window
 * (frame). Used to show DateTile-s and direct main user interaction.
 * 
 * @author Jens Benz
 *
 */
public abstract class SubScene implements SceneComponent {

	protected int fromX, fromY, x, y, w, h;
	private int id;

	/**
	 * @param id is used for comparing subscenes. For instance if the current one is
	 *           the same as this one.
	 */
	public SubScene(int id) {
		this.id = id;
	}

	/**
	 * Draw the content of this subscene onto the scene
	 * 
	 * @param ontoScene ; a scene
	 */
	public abstract void render(Scene ontoScene);

	/**
	 * Update content like elements of this subscene
	 */
	public abstract void update();

	public abstract void init();

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

	public int getID() {
		return id;
	}

}

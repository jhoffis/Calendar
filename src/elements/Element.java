package elements;

public interface Element {

	/**
	 * Used to tell the element where (0,0) should be on the window for this
	 * element.
	 */
	void setFromPoint(int x, int y);
	
	/**
	 * @return point x where this element thinks (0,0) is on the window
	 */
	int getFromPointX();
	
	/**
	 * @return point y where this element thinks (0,0) is on the window
	 */
	int getFromPointY();

	/**
	 * Used to avoid standard size or no size on the element at all.
	 * 
	 * @param x, placement of the left of the element from left of the window
	 * @param y, placement of the top of the element from top of the window
	 * @param w, addition to X for place of the right of the element from left of
	 *           the element
	 * @param h, addition to Y for place of the bottom of the element from top of
	 *           the element
	 */
	void setPlacement(int x, int y, int w, int h);

	/**
	 * @return height of element not dependent on x and y coordinates
	 */
	int getPlacementHeight();
	
	/**
	 * @return width of element not dependent on x and y coordinates
	 */
	int getPlacementWidth();
	
	
}

package window;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

public class Table {
	public static final int WIDTH = 10;
	public static final int HEIGHT = 20;
	public static final int RECT_SIZE = 20;
	
	private Rectangle[][] table;
	
	public boolean isCollided(Pair<Integer, Integer> position) {
		if(position.getKey() < 0 || position.getKey() >= HEIGHT) {
			return false;
		}
		
		if(position.getValue() < 0 || position.getValue() >= WIDTH) {
			return false;
		}
		
		return table[position.getKey()][position.getValue()].equals(createBackground());
	}
	
	public Rectangle createBackground() {
		return new Rectangle(RECT_SIZE, RECT_SIZE, Color.LIGHTGRAY);
	}
}

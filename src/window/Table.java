package window;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;
import utility.Pair;

public class Table extends GridPane {
	public static final int WIDTH = 10;
	public static final int HEIGHT = 20;
	public static final int RECT_SIZE = 20;
	
	private Rectangle[][] table;
	
	public Table() {
		setPadding(new Insets(5));
		setHgap(2);
		setVgap(2);
		
		table = new Rectangle[HEIGHT][WIDTH];
		for(int i = 0; i < HEIGHT; ++i) {
			for(int j = 0; j < WIDTH; ++j) {
				table[i][j] = createBackground();
				add(table[i][j], j, i);
			}
		}
	}
	
	public boolean isCollided(Pair position) {
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

package window;

import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import tile.*;

public class PreviewBox extends StackPane {
	public static final int RECT_SIZE = 10;
	
	private int tableSize;
	private Rectangle[][] table;
	private GridPane gridpane;
	private Tile tile;
	
	public PreviewBox(Tile tile) {
		gridpane = new GridPane();
		gridpane.setHgap(2);
		gridpane.setVgap(2);
		gridpane.setAlignment(Pos.CENTER);
		
		tableSize = tile.getBlockSize() + 1;
		
		table = new Rectangle[tableSize][tableSize];
		for(int i = 0; i < tableSize; ++i) {
			for(int j = 0; j < tableSize; ++j) {
				table[i][j] = new Rectangle(RECT_SIZE, RECT_SIZE, Color.WHITE);
			}
		}
		
		for(int i = 0; i < 4; ++i) {
			table[tile.getShape()[i].getKey()][tile.getShape()[i].getValue()] =
					new Rectangle(RECT_SIZE, RECT_SIZE, tile.getColor());
		}
		
		for(int i = 0; i < tableSize; ++i) {
			for(int j = 0; j < tableSize; ++j) {
				gridpane.add(table[i][j], j, i);
			}
		}
		
		getChildren().add(gridpane);
		
		setAlignment(Pos.CENTER);
		setPrefSize(46, 46);
	}
	
	public Tile getTile() {
		return tile;
	}
	
	public void clear() {
		clear();
		for(int i = 0; i < tile.getBlockSize(); ++i) {
			for(int j = 0; j < tile.getBlockSize(); ++j) {
				table[i][j] = new Rectangle(RECT_SIZE, RECT_SIZE, Color.GRAY);
			}
		}
	}
}

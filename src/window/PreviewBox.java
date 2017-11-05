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
	private Rectangle overlay;
	
	public PreviewBox() {
		gridpane = new GridPane();
		gridpane.setHgap(2);
		gridpane.setVgap(2);
		gridpane.setAlignment(Pos.CENTER);
		
		overlay = new Rectangle(46, 46,Color.LIGHTGRAY);
		overlay.setOpacity(0);
		
		getChildren().addAll(new Rectangle(46, 46, Color.GRAY), gridpane, overlay);
		
		setAlignment(Pos.CENTER);
		setPrefSize(46, 46);
	}
	
	public void pause() {
		overlay.setOpacity(0.85);
	}
	
	public void unpause() {
		overlay.setOpacity(0);
	}
	
	public void setTile(Tile tile) {
		clear();
		
		this.tile = tile;
		tableSize = tile.getBlockSize() + 1;
		
		table = new Rectangle[tableSize][tableSize];
		for(int i = 0; i < tableSize; ++i) {
			for(int j = 0; j < tableSize; ++j) {
				table[i][j] = createBackground();
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
	}
	
	public Tile getTile() {
		return tile;
	}
	
	public void clear() {
		tile = null;
		gridpane.getChildren().clear();
	}
	
	private Rectangle createBackground() {
		Rectangle background = new Rectangle(RECT_SIZE, RECT_SIZE);
		background.setOpacity(0);
		return background;
	}
}

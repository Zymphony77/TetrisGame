package window;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;
import tile.Tile;
import utility.Pair;

public class Table extends GridPane {
	public static final int WIDTH = 10;
	public static final int HEIGHT = 20;
	public static final int RECT_SIZE = 20;
	public static final Color GHOST_COLOR = Color.FLORALWHITE;
	public static final Color CLEAR_COLOR = Color.WHITE;
	
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
		if(position.getKey() >= HEIGHT) {
			return true;
		}
		
		if(position.getValue() < 0 || position.getValue() >= WIDTH) {
			return true;
		}
		
		if(position.getKey() < 0) {
			return false;
		}
		
		return !(table[position.getKey()][position.getValue()].getFill().equals(createBackground().getFill()) ||
				table[position.getKey()][position.getValue()].getFill().equals(GHOST_COLOR));
	}
	
	public int colorFullLine() {
		int lineCount = 0;
		boolean clear;
		for(int i = HEIGHT - 1; i >= 0; --i) {
			clear = true;
			if(clear) {
				for(int j = 0; j < WIDTH; ++j) {
					if(table[i][j].getFill().equals(createBackground().getFill())) {
						clear = false;
						break;
					}
				}
				if(clear) {
					for(int j = 0; j < WIDTH; ++j) {
						table[i][j].setFill(CLEAR_COLOR);
					}
					++lineCount;
				}
			}
		}
		return lineCount;
	}
	
	public void lineClear() {
		boolean clear;
		for(int i = HEIGHT - 1; i >= 0; --i) {
			clear = true;
			while(clear) {
				for(int j = 0; j < WIDTH; ++j) {
					if(table[i][j].getFill().equals(createBackground().getFill())) {
						clear = false;
						break;
					}
				}
				if(clear) {
					shiftDown(i);
				}
			}
		}
	}
	
	public void shiftDown(int start) {
		for(int i = start; i >= 0; --i) {
			for(int j = 0; j < WIDTH; ++j) {
				if(i == start) {
					getChildren().remove(table[i][j]);
				}
				if(i > 0) {
					table[i][j] = table[i - 1][j];
					getChildren().remove(table[i - 1][j]);
				} else {
					table[i][j] = createBackground();
				}
				add(table[i][j], j, i);
			}
		}
	}
	
	public void draw(Tile tile) {
		for(int i = 0; i < 4; ++i) {
			int x = tile.getRefPoint().getKey() + tile.getShape()[i].getKey();
			int y = tile.getRefPoint().getValue() + tile.getShape()[i].getValue();
			
			if(x < 0) {
				continue;
			}
			
			getChildren().remove(table[x][y]);
			
			table[x][y] = new Rectangle(RECT_SIZE, RECT_SIZE, tile.getColor());
			add(table[x][y], y, x);
		}
	}
	
	public void undraw(Tile tile) {
		for(int i = 0; i < 4; ++i) {
			int x = tile.getRefPoint().getKey() + tile.getShape()[i].getKey();
			int y = tile.getRefPoint().getValue() + tile.getShape()[i].getValue();
			
			if(x < 0) {
				continue;
			}
			
			getChildren().remove(table[x][y]);
			
			table[x][y] = createBackground();
			add(table[x][y], y, x);
		}
	}
	
	public void drawGhost(Tile tile) {
		Pair refPoint = new Pair(tile.getRefPoint());
		tile.hardDrop();
		
		for(int i = 0; i < 4; ++i) {
			int x = tile.getRefPoint().getKey() + tile.getShape()[i].getKey();
			int y = tile.getRefPoint().getValue() + tile.getShape()[i].getValue();
			
			if(x < 0) {
				continue;
			}
			
			getChildren().remove(table[x][y]);
			
			table[x][y] = new Rectangle(RECT_SIZE, RECT_SIZE, GHOST_COLOR);
			table[x][y].setOpacity(0.5);
			add(table[x][y], y, x);
		}
		
		tile.setRefPoint(refPoint);
	}
	
	public void undrawGhost(Tile tile) {
		Pair refPoint = new Pair(tile.getRefPoint());
		tile.hardDrop();
		
		for(int i = 0; i < 4; ++i) {
			int x = tile.getRefPoint().getKey() + tile.getShape()[i].getKey();
			int y = tile.getRefPoint().getValue() + tile.getShape()[i].getValue();
			
			if(x < 0) {
				continue;
			}
			
			if(table[x][y].getFill().equals(createBackground().getFill())) {
				continue;
			}
			
			getChildren().remove(table[x][y]);
			
			table[x][y] = createBackground();
			add(table[x][y], y, x);
		}
		
		tile.setRefPoint(refPoint);
	}
	
	public void clear() {
		getChildren().clear();
		for(int i = 0; i < HEIGHT; ++i) {
			for(int j = 0; j < WIDTH; ++j) {
				table[i][j] = createBackground();
				add(table[i][j], j, i);
			}
			
		}
	}
	
	public boolean insideTable(Tile tile) {
		for(int i = 0; i < 4; ++i) {
			if(tile.getRefPoint().getKey() + tile.getShape()[i].getKey() >= 0) {
				return true;
			}
		}
		return false;
	}
	
	public boolean outsideTable(Tile tile) {
		for(int i = 0; i < 4; ++i) {
			if(tile.getRefPoint().getKey() + tile.getShape()[i].getKey() < 0) {
				return true;
			}
		}
		return false;
	}
	
	private Rectangle createBackground() {
		return new Rectangle(RECT_SIZE, RECT_SIZE, Color.GRAY);
	}
}

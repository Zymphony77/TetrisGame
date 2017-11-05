package tile;

import window.Table;
import javafx.scene.paint.Color;
import utility.Pair;

public class TetrisO extends Tile {
	private static Color COLOR;
	private static Pair[] template = new Pair[4];
	private static int blockSize;
	
	static {
		COLOR = Color.GOLD;
		blockSize = 1;
		for(int i = 0; i < 4; ++i) {
			template[i] = new Pair(i/2, i%2);
		}
	}
	
	public TetrisO(int x, int y, Table table) {
		super(x, y, table);
	}
	
	public Pair[] getTemplate() {
		return template;
	}
	
	public int getBlockSize() {
		return blockSize;
	}
	
	public Color getColor() {
		return COLOR;
	}
}

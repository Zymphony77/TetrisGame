package tile;

import window.Table;
import javafx.scene.paint.Color;
import javafx.util.Pair;

public class TetrisO extends Tile {
	static {
		COLOR = Color.GOLD;
		blockSize = 1;
		for(int i = 0; i < 4; ++i) {
			template[i] = new Pair<Integer, Integer>(i/2, i%2);
		}
	}
	
	public TetrisO(int x, int y, Table table) {
		super(x, y, table);
	}
}

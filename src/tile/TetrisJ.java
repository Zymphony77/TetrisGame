package tile;

import window.Table;
import javafx.scene.paint.Color;
import javafx.util.Pair;

public class TetrisJ extends Tile {
	static {
		COLOR = Color.DODGERBLUE;
		blockSize = 2;
		for(int i = 0; i < 3; ++i) {
			template[i] = new Pair<Integer, Integer>(1, i);
		}
		template[3] = new Pair<Integer, Integer>(0, 0);
	}
	
	public TetrisJ(int x, int y, Table table) {
		super(x, y, table);
	}
}
package tile;

import window.Table;
import javafx.scene.paint.Color;
import utility.Pair;

public class TetrisL extends Tile {
	static {
		COLOR = Color.ORANGE;
		blockSize = 2;
		for(int i = 0; i < 3; ++i) {
			template[i] = new Pair(1, i);
		}
		template[3] = new Pair(0, 2);
	}
	
	public TetrisL(int x, int y, Table table) {
		super(x, y, table);
	}
}

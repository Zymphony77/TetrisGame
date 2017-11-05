package tile;

import window.Table;
import javafx.scene.paint.Color;
import utility.Pair;

public class TetrisL extends Tile {
	private static Color COLOR;
	private static Pair[] template = new Pair[4];
	private static int blockSize;
	
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

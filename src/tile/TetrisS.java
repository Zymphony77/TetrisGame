package tile;

import window.Table;
import javafx.scene.paint.Color;
import javafx.util.Pair;

public class TetrisS extends Tile implements EquivalentTurn {
	static {
		COLOR = Color.DARKGREEN;
		blockSize = 2;
		for(int i = 0; i < 2; ++i) {
			template[i] = new Pair<Integer, Integer>(0, i + 1);
			template[i + 2] = new Pair<Integer, Integer>(1, i);
		}
	}
	
	public TetrisS(int x, int y, Table table) {
		super(x, y, table);
	}
	
	public void changeTurn() {
		if(turnState == 0) {
			refPoint = new Pair<Integer, Integer>(refPoint.getKey() - 1, refPoint.getValue());
		} else if(turnState == 1) {
			refPoint = new Pair<Integer, Integer>(refPoint.getKey(), refPoint.getValue() + 1);
		} else if(turnState == 2) {
			refPoint = new Pair<Integer, Integer>(refPoint.getKey() + 1, refPoint.getValue());
		} else {
			refPoint = new Pair<Integer, Integer>(refPoint.getKey(), refPoint.getValue() - 1);
		}
		turnState = (turnState + 2) % 4;
	}
}

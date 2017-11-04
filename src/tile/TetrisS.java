package tile;

import window.Table;
import javafx.scene.paint.Color;
import utility.Pair;

public class TetrisS extends Tile implements EquivalentTurn {
	static {
		COLOR = Color.GREENYELLOW;
		blockSize = 2;
		for(int i = 0; i < 2; ++i) {
			template[i] = new Pair(0, i + 1);
			template[i + 2] = new Pair(1, i);
		}
	}
	
	public TetrisS(int x, int y, Table table) {
		super(x, y, table);
	}
	
	public void changeTurn() {
		if(turnState == 0) {
			refPoint.setKey(refPoint.getKey() - 1);
		} else if(turnState == 1) {
			refPoint.setValue(refPoint.getValue() + 1);
		} else if(turnState == 2) {
			refPoint.setKey(refPoint.getKey() + 1);
		} else {
			refPoint.setValue(refPoint.getValue() - 1);
		}
		turnState = (turnState + 2) % 4;
	}
}

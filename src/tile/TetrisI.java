package tile;

import window.Table;
import javafx.scene.paint.Color;
import utility.Pair;

public class TetrisI extends Tile implements EquivalentTurn {
	static {
		COLOR = Color.AQUA;
		blockSize = 3;
		for(int i = 0; i < 4; ++i) {
			template[i] = new Pair(2, i);
		}
	}
	
	public TetrisI(int x, int y, Table table) {
		super(x, y, table);
	}
	
	public void changeTurn() {
		if(turnState == 0) {
			refPoint.setKey(refPoint.getKey() + 1);
		} else if(turnState == 1) {
			refPoint.setValue(refPoint.getValue() - 1);
		} else if(turnState == 2) {
			refPoint.setKey(refPoint.getKey() - 1);
		} else {
			refPoint.setValue(refPoint.getValue() + 1);
		}
		turnState = (turnState + 2) % 4;
	}
}

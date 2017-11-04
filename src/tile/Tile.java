package tile;

import window.Table;
import javafx.util.Pair;
import javafx.scene.paint.Color;

public class Tile {
	protected static int RECT_SIZE = 20;
	protected static Color COLOR;
	
	protected static Pair<Integer, Integer>[] template;
	protected static int blockSize;
	
	protected Pair<Integer, Integer> refPoint;
	protected Pair<Integer, Integer>[] shape;
	protected int turnState;
	protected Table table;
	
	protected Tile(int x, int y, Table table) {
		turnState = 0;
		refPoint = new Pair<Integer, Integer>(x, y);
		
		this.table = table;
		
		for(int i = 0; i < 4; ++i) {
			shape[i] = new Pair<Integer, Integer>(template[i].getKey(), template[i].getValue());
		}
	}
	
	public boolean moveDown() {
		for(int i = 0; i < 4; ++i) {
			int newx = refPoint.getKey() + shape[i].getKey() + 1;
			int newy = refPoint.getValue() + shape[i].getValue();
			if(table.isCollided(new Pair<Integer, Integer>(newx, newy))) {
				return false;
			}
		}
		refPoint = new Pair<Integer, Integer>(refPoint.getKey() + 1, refPoint.getValue());
		return true;
	}
	
	public boolean canMoveLeft() {
		for(int i = 0; i < 4; ++i) {
			int newx = refPoint.getKey() + shape[i].getKey();
			int newy = refPoint.getValue() + shape[i].getValue() - 1;
			if(table.isCollided(new Pair<Integer, Integer>(newx, newy))) {
				return false;
			}
		}
		refPoint = new Pair<Integer, Integer>(refPoint.getKey(), refPoint.getValue() - 1);
		return true;
	}
	
	public boolean canMoveRight() {
		for(int i = 0; i < 4; ++i) {
			int newx = refPoint.getKey() + shape[i].getKey();
			int newy = refPoint.getValue() + shape[i].getValue() + 1;
			if(table.isCollided(new Pair<Integer, Integer>(newx, newy))) {
				return false;
			}
		}
		refPoint = new Pair<Integer, Integer>(refPoint.getKey(), refPoint.getValue() + 1);
		return true;
	}
	
	protected boolean canTurnClockwise(int shiftUp) {
		for(int i = 0; i < 4; ++i) {
			int newx = shape[i].getValue() - shiftUp;
			int newy = blockSize - shape[i].getKey();
			if(table.isCollided(new Pair<Integer, Integer>(newx, newy))) {
				return false;
			}
		}
		return true;
	}
	
	protected void justTurnClockwise() {
		turnState = (turnState + 1) % 4;
		for(int i = 0; i < 4; ++i) {
			shape[i] = new Pair<Integer, Integer>(shape[i].getValue(), blockSize - shape[i].getKey());
		}
	}
	
	protected boolean tryTurningClockwise() {
		// Normal Turn
		if(canTurnClockwise(0)) {
			justTurnClockwise();
			return true;
		}
		
		// Normal Turn with Push Upwards
		if(canTurnClockwise(1)) {
			justTurnClockwise();
			pushUpwards();
			return true;
		}
		
		// Equivalent Turn
		if(this instanceof EquivalentTurn) {
			((EquivalentTurn)this).changeTurn();
			
			// Without Push Upwards
			if(canTurnClockwise(0)) {
				justTurnClockwise();
				return true;
			}
			
			// With Push Upwards
			if(canTurnClockwise(1)) {
				justTurnClockwise();
				pushUpwards();
				return true;
			}
			
			((EquivalentTurn)this).changeTurn();
		}
		return false;
	}
	
	public boolean turnClockwise() {
		if(tryTurningClockwise()) {
			return true;
		}
		
		// Push first
		if(push()) {
			
			if(tryTurningClockwise()) {
				return true;
			}
			
			unpush();
		}
		
		return false;
	}
	
	protected boolean pushUpwards() {
		for(int i = 0; i < 4; ++i) {
			int newx = refPoint.getKey() + shape[i].getKey() - 1;
			int newy = refPoint.getValue() + shape[i].getValue();
			if(table.isCollided(new Pair<Integer, Integer>(newx, newy))) {
				return false;
			}
		}
		refPoint = new Pair<Integer, Integer>(refPoint.getKey() - 1, refPoint.getValue());
		return true;
	}
	
	protected boolean push() {
		if(this instanceof TetrisO) {
			return false;
		}
		
		if(turnState == 0) {
			refPoint = new Pair<Integer, Integer>(refPoint.getKey() - 1, refPoint.getValue());
			for(int i = 0; i < 4; ++i) {
				int newx = refPoint.getKey() + shape[i].getKey();
				int newy = refPoint.getValue() + shape[i].getValue();
				if(table.isCollided(new Pair<Integer, Integer>(newx, newy))) {
					refPoint = new Pair<Integer, Integer>(refPoint.getKey() + 1, refPoint.getValue());
					return false;
				}
			}
		} else if(turnState == 1) {
			refPoint = new Pair<Integer, Integer>(refPoint.getKey(), refPoint.getValue() + 1);
			for(int i = 0; i < 4; ++i) {
				int newx = refPoint.getKey() + shape[i].getKey();
				int newy = refPoint.getValue() + shape[i].getValue();
				if(table.isCollided(new Pair<Integer, Integer>(newx, newy))) {
					refPoint = new Pair<Integer, Integer>(refPoint.getKey(), refPoint.getValue() - 1);
					return false;
				}
			}
		} else if(turnState == 3) {
			refPoint = new Pair<Integer, Integer>(refPoint.getKey(), refPoint.getValue() - 1);
			for(int i = 0; i < 4; ++i) {
				int newx = refPoint.getKey() + shape[i].getKey();
				int newy = refPoint.getValue() + shape[i].getValue();
				if(table.isCollided(new Pair<Integer, Integer>(newx, newy))) {
					refPoint = new Pair<Integer, Integer>(refPoint.getKey(), refPoint.getValue() + 1);
					return false;
				}
			}
		}
		
		return true;
	}
	
	protected void unpush() {
		if(turnState == 0) {
			refPoint = new Pair<Integer, Integer>(refPoint.getKey() + 1, refPoint.getValue());
		} else if(turnState == 1) {
			refPoint = new Pair<Integer, Integer>(refPoint.getKey(), refPoint.getValue() - 1);
		} else if(turnState == 3) {
			refPoint = new Pair<Integer, Integer>(refPoint.getKey(), refPoint.getValue() + 1);
		}
	}
	
	public void hardDrop() {
		while(moveDown());
	}
}

package tile;

import window.Table;
import utility.Pair;
import javafx.scene.paint.Color;

public abstract class Tile {
	protected static int RECT_SIZE = 20;
	
	protected Pair refPoint;
	protected Pair[] shape;
	protected int turnState;
	protected Table table;
	
	protected Tile(int x, int y, Table table) {
		shape = new Pair[4];
		turnState = 0;
		refPoint = new Pair(x, y);
		
		this.table = table;
		
		for(int i = 0; i < 4; ++i) {
			shape[i] = new Pair(getTemplate()[i].getKey(), getTemplate()[i].getValue());
		}
	}
	
	public boolean moveDown() {
		for(int i = 0; i < 4; ++i) {
			int newx = refPoint.getKey() + shape[i].getKey() + 1;
			int newy = refPoint.getValue() + shape[i].getValue();
			if(table.isCollided(new Pair(newx, newy))) {
				return false;
			}
		}
		refPoint.setKey(refPoint.getKey() + 1);
		return true;
	}
	
	public boolean moveLeft() {
		for(int i = 0; i < 4; ++i) {
			int newx = refPoint.getKey() + shape[i].getKey();
			int newy = refPoint.getValue() + shape[i].getValue() - 1;
			if(table.isCollided(new Pair(newx, newy))) {
				return false;
			}
		}
		refPoint.setValue(refPoint.getValue() - 1);
		return true;
	}
	
	public boolean moveRight() {
		for(int i = 0; i < 4; ++i) {
			int newx = refPoint.getKey() + shape[i].getKey();
			int newy = refPoint.getValue() + shape[i].getValue() + 1;
			if(table.isCollided(new Pair(newx, newy))) {
				return false;
			}
		}
		refPoint.setValue(refPoint.getValue() + 1);
		return true;
	}
	
	protected boolean canTurnClockwise(int shiftUp) {
		for(int i = 0; i < 4; ++i) {
			int newx = refPoint.getKey() + shape[i].getValue() - shiftUp;
			int newy = refPoint.getValue() + getBlockSize() - shape[i].getKey();
			if(table.isCollided(new Pair(newx, newy))) {
				return false;
			}
		}
		return true;
	}
	
	protected void justTurnClockwise() {
		turnState = (turnState + 1) % 4;
		for(int i = 0; i < 4; ++i) {
			shape[i] = new Pair(shape[i].getValue(), getBlockSize() - shape[i].getKey());
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
			((EquivalentTurn) this).changeTurn();
			
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
			
			((EquivalentTurn) this).changeTurn();
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
		
		// Equivalent Push
		if(this instanceof EquivalentTurn) {
			((EquivalentTurn) this).changeTurn();
			if(push()) {
				if(tryTurningClockwise()) {
					return true;
				}
				unpush();
			}
			((EquivalentTurn) this).changeTurn();
		}
		
		return false;
	}
	
	protected boolean pushUpwards() {
		for(int i = 0; i < 4; ++i) {
			int newx = refPoint.getKey() + shape[i].getKey() - 1;
			int newy = refPoint.getValue() + shape[i].getValue();
			if(table.isCollided(new Pair(newx, newy))) {
				return false;
			}
		}
		refPoint.setKey(refPoint.getKey() - 1);
		return true;
	}
	
	protected boolean push() {
		if(this instanceof TetrisO) {
			return false;
		}
		
		if(turnState == 0) {
			refPoint.setKey(refPoint.getKey() - 1);
			for(int i = 0; i < 4; ++i) {
				int newx = refPoint.getKey() + shape[i].getKey();
				int newy = refPoint.getValue() + shape[i].getValue();
				if(table.isCollided(new Pair(newx, newy))) {
					refPoint.setKey(refPoint.getKey() + 1);
					return false;
				}
			}
		} else if(turnState == 1) {
			refPoint.setValue(refPoint.getValue() + 1);
			for(int i = 0; i < 4; ++i) {
				int newx = refPoint.getKey() + shape[i].getKey();
				int newy = refPoint.getValue() + shape[i].getValue();
				if(table.isCollided(new Pair(newx, newy))) {
					refPoint.setValue(refPoint.getValue() - 1);
					return false;
				}
			}
		} else if(turnState == 3) {
			refPoint.setValue(refPoint.getValue() - 1);
			for(int i = 0; i < 4; ++i) {
				int newx = refPoint.getKey() + shape[i].getKey();
				int newy = refPoint.getValue() + shape[i].getValue();
				if(table.isCollided(new Pair(newx, newy))) {
					refPoint.setValue(refPoint.getValue() + 1);
					return false;
				}
			}
		}
		
		return true;
	}
	
	protected void unpush() {
		if(turnState == 0) {
			refPoint.setKey(refPoint.getKey() + 1);
		} else if(turnState == 1) {
			refPoint.setValue(refPoint.getValue() - 1);
		} else if(turnState == 3) {
			refPoint.setValue(refPoint.getValue() + 1);
		}
	}
	
	public void hardDrop() {
		while(moveDown());
	}
	
	public Pair getRefPoint() {
		return refPoint;
	}
	
	public Pair[] getShape() {
		return shape;
	}
	
	public int getTurnState() {
		return turnState;
	}
	
	public abstract Pair[] getTemplate();
	public abstract int getBlockSize();
	public abstract Color getColor();
}

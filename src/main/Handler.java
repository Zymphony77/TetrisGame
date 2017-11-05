package main;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import java.util.Random;
import tile.*;
import window.*;
import utility.*;

public class Handler {
	public static void keyPressed(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER) {
			if(!Main.isStarted) {
				Main.isStarted = true;
				Main.timeline.play();
				update();
			}
			
			if(!Main.isDead) {
				unpause();
			}
			
			return;
		}
		
		if(!Main.isStarted || Main.isDead) {
			return;
		}
		
		if(event.getCode() == KeyCode.P) {
			if(!Main.isPausing) {
				pause();
			}
			return;
		}
		
		if(event.getCode() == KeyCode.LEFT) {
			Main.tablePanel.undraw(Main.randomTile);
			Main.randomTile.moveLeft();
			Main.tablePanel.draw(Main.randomTile);
		}
		
		if(event.getCode() == KeyCode.RIGHT) {
			Main.tablePanel.undraw(Main.randomTile);
			Main.randomTile.moveRight();
			Main.tablePanel.draw(Main.randomTile);
		}
		
		if(event.getCode() == KeyCode.DOWN) {
			Main.tablePanel.undraw(Main.randomTile);
			Main.randomTile.moveDown();
			Main.tablePanel.draw(Main.randomTile);
		}
		
		if(event.getCode() == KeyCode.UP) {
			Main.tablePanel.undraw(Main.randomTile);
			Main.randomTile.turnClockwise();
			Main.tablePanel.draw(Main.randomTile);
		}
		
		if(event.getCode() == KeyCode.SPACE) {
			Main.tablePanel.undraw(Main.randomTile);
			Main.randomTile.hardDrop();
			Main.tablePanel.draw(Main.randomTile);
			
			Main.timeline.stop();
			update();
			Main.timeline.play();
		}
		
		if(event.getCode() == KeyCode.SHIFT && !Main.holdChanged) {
			Main.holdChanged = true;
			
			Main.tablePanel.undraw(Main.randomTile);
			
			Main.randomTile.setRefPoint(new Pair(-4, 4));
			
			Tile tmp = Main.randomTile;
			Main.randomTile = Main.holdPanel.getTile();
			Main.holdPanel.setTile(tmp);
			
			if(Main.randomTile == null) {
				update();
			}
			
			while(!Main.tablePanel.insideTable(Main.randomTile)) {
				if(!Main.randomTile.moveDown()) {
					break;
				}
			}
			
			Main.tablePanel.draw(Main.randomTile);
		}
	}
	
	public static void update() {
		if(Main.randomTile == null) {
			if(Main.nextPanel[0].getTile() == null) {
				Main.randomTile = randomTile();
				for(int i = 0; i < 3; ++i) {
					Main.nextPanel[i].setTile(randomTile());
				}
			} else {
				Main.randomTile = Main.nextPanel[0].getTile();
				Main.nextPanel[0].setTile(Main.nextPanel[1].getTile());
				Main.nextPanel[1].setTile(Main.nextPanel[2].getTile());
				Main.nextPanel[2].setTile(randomTile());
			}
			
			while(!Main.tablePanel.insideTable(Main.randomTile)) {
				if(!Main.randomTile.moveDown()) {
					break;
				}
			}
			
			Main.tablePanel.draw(Main.randomTile);
			return;
		} else {
			Main.tablePanel.undraw(Main.randomTile);
		}
		
		boolean moveDown = Main.randomTile.moveDown();
		Main.tablePanel.draw(Main.randomTile);
		
		if(!moveDown) {
			Main.holdChanged = false;
			
			if(Main.tablePanel.outsideTable(Main.randomTile)) {
				lose();
			} else {
				Main.randomTile = null;
				updateLineCount();
				update();
			}
		}
	}
	
	private static void lose() {
		Main.isDead = true;
		Main.tablePause.dead();
		Main.holdPanel.pause();
		for(int i = 0; i < 3; ++i) {
			Main.nextPanel[i].pause();
		}
		Main.timeline.stop();
	}
	
	private static void updateLineCount() {
		int lineClear = Main.tablePanel.lineClear();
		
		if(lineClear == 0) {
			return;
		}
		
		Main.lineCount += lineClear;
		Main.lineCountText.setText("" + Main.lineCount);
	}
	
	private static Tile randomTile() {
		Random random = new Random();
		int type = random.nextInt(7);
		Tile tile;
		
		switch(type) {
		case 0:
			tile = new TetrisI(-4, 4, Main.tablePanel);
			break;
		case 1:
			tile = new TetrisJ(-4, 4, Main.tablePanel);
			break;
		case 2:
			tile = new TetrisL(-4, 4, Main.tablePanel);
			break;
		case 3:
			tile = new TetrisO(-4, 4, Main.tablePanel);
			break;
		case 4:
			tile = new TetrisS(-4, 4, Main.tablePanel);
			break;
		case 5:
			tile = new TetrisT(-4, 4, Main.tablePanel);
			break;
		default:
			tile = new TetrisZ(-4, 4, Main.tablePanel);
			break;
		}
				
		return tile;
	}
	
	private static void pause() {
		Main.timeline.stop();
		
		Main.isPausing = true;
		Main.tablePause.pause();
		Main.holdPanel.pause();
		for(int i = 0; i < 3; ++i) {
			Main.nextPanel[i].pause();
		}
		Main.pauseText.setText("[PAUSE]");
	}
	
	private static void unpause() {
		Main.timeline.play();
		
		Main.isPausing = false;
		Main.tablePause.unpause();
		Main.holdPanel.unpause();
		for(int i = 0; i < 3; ++i) {
			Main.nextPanel[i].unpause();
		}
		Main.pauseText.setText("");
	}
}

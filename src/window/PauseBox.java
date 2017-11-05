package window;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PauseBox extends StackPane {
	private Rectangle overlay;
	private Text text;
	
	public PauseBox() {
		overlay = new Rectangle(218, 438, Color.LIGHTGRAY);
		overlay.setOpacity(0);
		
		text = new Text();
		text.setFont(Font.font("Helvatical Neue", FontWeight.BOLD, 18));
		text.setFill(Color.WHITE);
		
		getChildren().addAll(overlay, text);
	}
	
	public void pauseAtStart() {
		text.setText("Press \'Enter\' to start!");
		overlay.setOpacity(0.85);
	}
	
	public void pause() {
		text.setText("Press \'Enter\' to continue");
		overlay.setOpacity(0.85);
	}
	
	public void dead() {
		text.setText("YOU LOSE!");
		overlay.setOpacity(0.85);
	}
	
	public void unpause() {
		text.setText("");
		overlay.setOpacity(0);
	}
}

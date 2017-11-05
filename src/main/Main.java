package main;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import tile.*;
import window.*;

public class Main extends Application {
	static HBox root;
	static VBox leftPanel;
	static StackPane middlePanel;
	static Table tablePanel;
	static PauseBox tablePause;
	static VBox rightPanel;
	static PreviewBox holdPanel;
	static PreviewBox[] nextPanel;
	static Text lineCountText;
	static Text pauseText;
	static Button resetButton;
	static Tile randomTile;
	static Timeline timeline;
	static int lineCount;
	static boolean isStarted;
	static boolean isPausing;
	static boolean isDead;
	static boolean holdChanged;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		
		// setup leftPanel
		Text holdText = new Text("HOLD:");
		holdText.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 18));
		holdText.setFill(Color.WHITE);
		
		holdPanel = new PreviewBox();
		holdPanel.pause();
		
		leftPanel = new VBox(10);
		leftPanel.getChildren().addAll(holdText, holdPanel);
		
		// setup middlePanel
		tablePanel = new Table();
		
		tablePause = new PauseBox();
		tablePause.pauseAtStart();
		
		middlePanel = new StackPane();
		middlePanel.getChildren().addAll(tablePanel, tablePause);
		
		// setup rightPanel
		Text nextText = new Text("NEXT:");
		nextText.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 18));
		nextText.setFill(Color.WHITE);

		nextPanel = new PreviewBox[3];
		for(int i = 0; i < 3; ++i) {
			nextPanel[i] = new PreviewBox();
			nextPanel[i].pause();
		}
		
		VBox topSpacing = new VBox();
		topSpacing.setPrefHeight(50);
		
		Text lineText = new Text("LINE:");
		lineText.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 18));
		lineText.setFill(Color.WHITE);
		
		lineCountText = new Text("0");
		lineCountText.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 20));
		lineCountText.setFill(Color.WHITE);
		
		VBox bottomSpacing = new VBox();
		bottomSpacing.setPrefHeight(50);
		
		pauseText = new Text();
		pauseText.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 16));
		pauseText.setFill(Color.YELLOW);
		
		resetButton = new Button("Reset");
		resetButton.setFocusTraversable(false);
		resetButton.setOnAction(event -> {
			holdPanel.clear();
			holdPanel.pause();
			
			tablePanel.clear();
			tablePause.pauseAtStart();
			
			timeline.stop();
			
			for(int i = 0; i < 3; ++i) {
				nextPanel[i].clear();
				nextPanel[i].pause();
			}
			
			pauseText.setText("");
			
			lineCount = 0;
			isStarted = false;
			isPausing = true;
			isDead = false;
			holdChanged = false;
			randomTile = null;
		});
		
		rightPanel = new VBox(10);
		rightPanel.getChildren().add(nextText);
		for(int i = 0; i < 3; ++i) {
			rightPanel.getChildren().add(nextPanel[i]);
		}
		rightPanel.getChildren().addAll(topSpacing, lineText, lineCountText, bottomSpacing, pauseText, resetButton);
		
		// setup root
		root = new HBox(15);
		root.setPadding(new Insets(10));
		root.getChildren().addAll(leftPanel, middlePanel, rightPanel);
		root.setStyle("-fx-background-color: #555555");
		
		lineCount = 0;
		isStarted = false;
		isPausing = true;
		isDead = false;
		
		// setup timeline
		timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> Handler.update()));
		timeline.setCycleCount(Animation.INDEFINITE);
		
		// setup scene + stage
		Scene scene = new Scene(root);
		scene.setOnKeyPressed(event -> Handler.keyPressed(event));

		primaryStage.setScene(scene);
		primaryStage.setTitle("Tetris");
		primaryStage.setOnCloseRequest(event -> timeline.stop());
		primaryStage.show();
	}
}

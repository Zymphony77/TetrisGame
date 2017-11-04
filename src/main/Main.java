package main;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import tile.*;
import window.*;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		VBox root = new VBox(10);
		root.setPadding(new Insets(5));
		Table table = new Table();
		root.getChildren().add(new PreviewBox(new TetrisI(0, 0, table)));
		root.getChildren().add(new PreviewBox(new TetrisJ(0, 0, table)));
		root.getChildren().add(new PreviewBox(new TetrisL(0, 0, table)));
		root.getChildren().add(new PreviewBox(new TetrisO(0, 0, table)));
		root.getChildren().add(new PreviewBox(new TetrisS(0, 0, table)));
		root.getChildren().add(new PreviewBox(new TetrisT(0, 0, table)));
		root.getChildren().add(new PreviewBox(new TetrisZ(0, 0, table)));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}

package frontend;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class IExtendedScene extends Scene {

	public IExtendedScene(Stage primaryStage, Parent root, double i, double j) {
		super(root, i, j);
		init(primaryStage);
	}

	public IExtendedScene(Stage primaryStage, Parent root) {
		super(root);
		primaryStage.setMaximized(true);
		primaryStage.sizeToScene();
		init(primaryStage);
	}
	
	private void init(Stage primaryStage) {
		primaryStage.setTitle("BachelorUtilities");
		primaryStage.setOpacity(0.92);
		primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		primaryStage.setResizable(false);
		String cssFile = getClass().getResource("NewFile.css").toExternalForm();
		getStylesheets().add(cssFile);

		setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ESCAPE) primaryStage.close(); });
		primaryStage.show();
	}
}

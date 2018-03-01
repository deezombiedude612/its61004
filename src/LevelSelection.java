import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;

public class LevelSelection extends Application {
	private final double WIDTH = 1000;
	private final double HEIGHT = 560;
	
	@Override
	public void start(Stage stage) {
		GridPane pane = new GridPane();
		pane.setPrefSize(WIDTH, HEIGHT);
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(15));
		pane.getStyleClass().add("lobby");
		pane.setVgap(20);
		pane.setHgap(100);
		
		/* title text - SELECT LEVEL */
		Text menuText = new Text("CHOOSE AN ACTIVITY");
		menuText.setFont(Font.font("Arial", FontWeight.BOLD, 62.5));
		menuText.setFill(Color.WHITE);
		pane.add(menuText, 0, 0, 3, 1);
		GridPane.setHalignment(menuText, HPos.CENTER);
		
		/* level selection buttons */
		ImageView easyBtn = new ImageView(new Image(getClass().getResourceAsStream("img/Easy.png"), 150, 150, true, true));
		pane.add(easyBtn,0, 1);
		GridPane.setHalignment(easyBtn, HPos.CENTER);
		easyBtn.setOnMouseClicked(e -> {
			try {
				new MCQ().start(stage);
			} catch (IOException ex) {
				new Oops().start(new Stage());
				new Lobby().start(stage);
			} catch(InterruptedException ex) {
			
			}
		});
		
		Text easyText = new Text("Easy ABC!");
		easyText.setFont(Font.font("Arial", FontWeight.BOLD, 35));
		easyText.setFill(Color.WHITE);
		easyText.setTextAlignment(TextAlignment.CENTER);
		pane.add(easyText, 0, 2);
		GridPane.setHalignment(easyText, HPos.CENTER);
		
		ImageView mediumBtn = new ImageView(new Image(getClass().getResourceAsStream("img/Medium.png"), 150, 150, true, true));
		pane.add(mediumBtn, 1, 1);
		GridPane.setHalignment(mediumBtn, HPos.CENTER);
		mediumBtn.setOnMouseClicked(e -> new DragRecycle().start(stage));
		
		Text mediumText = new Text("Recycle It!");
		mediumText.setFont(Font.font("Arial", FontWeight.BOLD, 35));
		mediumText.setFill(Color.WHITE);
		mediumText.setTextAlignment(TextAlignment.CENTER);
		pane.add(mediumText, 1, 2);
		GridPane.setHalignment(mediumText, HPos.CENTER);
		
		ImageView hardBtn = new ImageView(new Image(getClass().getResourceAsStream("img/Hard.png"), 150, 150, true, true));
		pane.add(hardBtn, 2, 1);
		GridPane.setHalignment(hardBtn, HPos.CENTER);
		hardBtn.setOnMouseClicked(e -> new KeyboardBin().start(stage));
		
		Text hardText = new Text("Green Maze!");
		hardText.setFont(Font.font("Arial", FontWeight.BOLD, 35));
		hardText.setFill(Color.WHITE);
		hardText.setTextAlignment(TextAlignment.CENTER);
		pane.add(hardText, 2, 2);
		GridPane.setHalignment(hardText, HPos.CENTER);
		
		ImageView backBtn = new ImageView(new Image(getClass().getResourceAsStream("img/arrowBack.png"), 75, 75, true, true));
		pane.add(backBtn, 0, 4, 3, 1);
		backBtn.setOnMouseClicked(e -> new Lobby().start(stage));
		
		Scene scene = new Scene(pane);
		scene.getStylesheets().addAll(this.getClass().getResource("styles.css").toExternalForm());
		
		stage.setScene(scene);
		stage.show();
	}
}

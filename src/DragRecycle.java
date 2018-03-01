import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DragRecycle extends Application {
	private final double WIDTH = 1000;
	private final double HEIGHT = 560;
	
	private final int STANDARD_SCORE = 500;
	private int score = 0;
	
	@Override
	public void start(Stage stage) {
		/* create a new file "temp.txt" */
		try {
			/* store score changes into a temporary text file which can be read by the leaderboard class by the time it ends */
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("temp.txt"));
			bufferedWriter.write("2 0");
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch(IOException ex) {
			new Oops().start(new Stage());	// display error message
			new Lobby().start(stage);		// return to lobby
		}
		
		BorderPane bdPane = new BorderPane();
		bdPane.setStyle("-fx-background-image: url('img/PlainBackground.png');");
		bdPane.setPadding(new Insets(20, 0, 0, 0));
		
		/* instructions */
		Text txt_instructions = new Text("Where does this go? Drag it to the correct bin!");
		txt_instructions.setFont(Font.font("Arial", FontWeight.BOLD, 35));
		txt_instructions.setFill(Color.WHITE);
		txt_instructions.setTextAlignment(TextAlignment.CENTER);
		bdPane.setTop(txt_instructions);
		BorderPane.setAlignment(txt_instructions, Pos.CENTER);
		txt_instructions.setLineSpacing(30);
		
		/* draggable shtuff region */
		Pane pane = new Pane();
		bdPane.setCenter(pane);
		ImageView plasticBin = new ImageView(new Image(getClass().getResourceAsStream("img/plasticBin.jpg"), 125, 125, true, true));
		pane.getChildren().add(plasticBin);
		plasticBin.setY(50);
		ImageView paperBin = new ImageView(new Image(getClass().getResourceAsStream("img/paperBin.png"), 125, 250, true, true));
		pane.getChildren().add(paperBin);
		paperBin.setY(200);
		
		/* draggable object */
		ImageView bottle = new ImageView(new Image(getClass().getResourceAsStream("img/bottle.png"), 125, 125, true, true));
		bottle.setX(600);
		bottle.setY(280);
		bottle.setOnMouseDragged(e -> {	// retrieve position of mouse (may be inaccurate)
			bottle.setX(e.getX());
			bottle.setY(e.getY());
		});
		bottle.setOnMouseReleased(e -> {
			/* done based on estimate test values */
			if(bottle.getX() <= 125) {
				if(bottle.getY() > 150)
					new Wrong2().start(stage);
				else if(bottle.getY() < 100) {
					try {
						/* store score changes into a temporary text file which can be read by the leaderboard class by the time it ends */
						PrintWriter tempScore = new PrintWriter(new File("temp.txt"));
						tempScore.write("2 ");	// to indicate what game is being played at first
						score += STANDARD_SCORE;	// add or update score influenced by time left (the more the better)
						tempScore.write(String.valueOf(score));
						tempScore.flush();
						tempScore.close();
					} catch(IOException ex) {
						new Oops().start(new Stage());	// display error message
						new Lobby().start(stage);		// return to lobby
					}
					new Correct2().start(stage);
				}
			}
		});
		pane.getChildren().add(bottle);
		
		/* back button */
		ImageView backBtn = new ImageView(new Image(getClass().getResourceAsStream("img/arrowBack.png"), 75, 75, true, true));
		bdPane.setLeft(backBtn);
		BorderPane.setAlignment(backBtn, Pos.BOTTOM_LEFT);
		backBtn.setOnMouseClicked(e -> {
			if(new File("temp.txt").exists())
				new File("temp.txt").delete();
			new Lobby().start(stage);
		});
		
		Scene scene = new Scene (bdPane, WIDTH, HEIGHT);
		stage.setScene(scene);
		stage.show();
	}
}

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.*;

public class KeyboardBin extends Application {
	private final double WIDTH = 1000;
	private final double HEIGHT = 560;
	
	private final int STANDARD_SCORE = 500;
	private int score = 0;
	
	@Override
	public void start(Stage stage) {
		try {
			/* store score changes into a temporary text file which can be read by the leaderboard class by the time it ends */
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("temp.txt"));
			bufferedWriter.write("3 0");
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
		Text txt_instructions = new Text("Move the garbage to the right bin using the keyboard.");
		txt_instructions.setFont(Font.font("Arial", FontWeight.BOLD, 35));
		txt_instructions.setFill(Color.WHITE);
		txt_instructions.setTextAlignment(TextAlignment.CENTER);
		bdPane.setTop(txt_instructions);
		BorderPane.setAlignment(txt_instructions, Pos.CENTER);
		txt_instructions.setLineSpacing(30);
		
		/* keyboard play area */
		Pane pane = new Pane();
		bdPane.setCenter(pane);
		ImageView plasticBin = new ImageView(new Image(getClass().getResourceAsStream("img/plasticBin.jpg"), 125, 125, true, true));
		pane.getChildren().add(plasticBin);
		plasticBin.setY(50);
		ImageView paperBin = new ImageView(new Image(getClass().getResourceAsStream("img/paperBin.png"), 125, 250, true, true));
		pane.getChildren().add(paperBin);
		paperBin.setY(200);
		
		/* movable object */
		ImageView crumpledPaper = new ImageView(new Image(getClass().getResourceAsStream("img/crumpled_paper_1.jpg"), 125, 125, true, true));
		pane.getChildren().add(crumpledPaper);
		crumpledPaper.setX(600);
		crumpledPaper.setY(80);
		
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
		scene.setOnKeyPressed(e -> {	// location matching is done based on estimate test values
			/* detect key input */
			if(e.getCode() == KeyCode.RIGHT && crumpledPaper.getX() < 775)
				crumpledPaper.setX(crumpledPaper.getX() + 10);
			else if(e.getCode() == KeyCode.LEFT && crumpledPaper.getX() > 0)
				crumpledPaper.setX(crumpledPaper.getX() - 10);
			else if(e.getCode() == KeyCode.DOWN && crumpledPaper.getY() < 300)
				crumpledPaper.setY(crumpledPaper.getY() + 10);
			else if(e.getCode() == KeyCode.UP && crumpledPaper.getY() > 15)
				crumpledPaper.setY(crumpledPaper.getY() - 10);
			
			/* track location of garbage */
			if(crumpledPaper.getX() <= 75) {
				if(crumpledPaper.getY() < 100)
					new Wrong2().start(stage);
				else if(crumpledPaper.getY() > 150) {
					try {
						/* store score changes into a temporary text file which can be read by the leaderboard class by the time it ends */
						PrintWriter tempScore = new PrintWriter(new File("temp.txt"));
						tempScore.write("3 ");	// to indicate what game is being played at first
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
		stage.setScene(scene);
		stage.show();
	}
}

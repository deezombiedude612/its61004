import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
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

public class Lobby extends Application {
	private final double WIDTH = 1000;
	private final double HEIGHT = 560;
	
	private Stage window;
	
	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		
		window.setScene(root());
		
		window.setTitle("Green Trivia");
		window.setResizable(false);
		window.show();
		window.setOnCloseRequest(e -> Platform.exit());	// if main window closes, exit immediately
	}
	
	/* menu scene */
	public Scene root() {
		GridPane root = new GridPane();
		root.setPrefSize(WIDTH, HEIGHT);
		root.getStyleClass().add("lobby");
		root.setAlignment(Pos.CENTER);
		root.setVgap(30);
		root.setHgap(50);
		
		/* title text - GREEN TRIVIA */
		Text menuText = new Text("GREEN TRIVIA");
		menuText.setFont(Font.font("Arial", FontWeight.BOLD, 75));
		menuText.setFill(Color.WHITE);
		root.add(menuText, 0, 0, 3, 1);
		GridPane.setHalignment(menuText, HPos.CENTER);
		
		/* play button */
		ImageView playBtn = new ImageView(new Image("img/start.png"));
		playBtn.setPreserveRatio(true);
		playBtn.setFitHeight(125);
		GridPane.setHalignment(playBtn, HPos.CENTER);
		root.add(playBtn, 0, 1);
		playBtn.setOnMouseClicked(e -> new LevelSelection().start(window));
		
		/* help button */
		ImageView helpBtn = new ImageView(new Image("img/help.png"));
		helpBtn.setPreserveRatio(true);
		helpBtn.setFitHeight(125);
		GridPane.setHalignment(helpBtn, HPos.CENTER);
		root.add(helpBtn, 1, 1);
		helpBtn.setOnMouseClicked(e -> new Instructions().start(window));
		
		/* leaderboard button */
		ImageView leaderboardBtn = new ImageView(new Image("img/leaderboard.png"));
		leaderboardBtn.setPreserveRatio(true);
		leaderboardBtn.setFitHeight(125);
		GridPane.setHalignment(leaderboardBtn, HPos.CENTER);
		root.add(leaderboardBtn, 2, 1);
		leaderboardBtn.setOnMouseClicked(e -> new Leaderboard().start(window));
		
		/* text under buttons */
		Text txt_play = new Text("PLAY");
		txt_play.setFont(Font.font("Arial", FontWeight.BOLD, 35));
		txt_play.setFill(Color.WHITE);
		txt_play.setTextAlignment(TextAlignment.CENTER);
		root.add(txt_play, 0, 2);
		GridPane.setHalignment(txt_play, HPos.CENTER);
		
		Text txt_help = new Text("HELP");
		txt_help.setFont(Font.font("Arial", FontWeight.BOLD, 35));
		txt_help.setFill(Color.WHITE);
		txt_help.setTextAlignment(TextAlignment.CENTER);
		root.add(txt_help, 1, 2);
		GridPane.setHalignment(txt_help, HPos.CENTER);
		
		Text txt_leaderboard = new Text("HIGH\nSCORES");
		txt_leaderboard.setFont(Font.font("Arial", FontWeight.BOLD, 35));
		txt_leaderboard.setFill(Color.WHITE);
		txt_leaderboard.setTextAlignment(TextAlignment.CENTER);
		root.add(txt_leaderboard, 2, 2);
		GridPane.setHalignment(txt_leaderboard, HPos.CENTER);
		
		Scene scene = new Scene(root);
		scene.getStylesheets().addAll(this.getClass().getResource("styles.css").toExternalForm());
		
		return scene;
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}

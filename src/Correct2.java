import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/* only displayed for DragRecycle and KeyboardBin */
public class Correct2 extends Application {
	private final double WIDTH = 1000;
	private final double HEIGHT = 560;
	
	@Override
	public void start(Stage stage) {
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-background-image: url('img/correct2bg.png')");
		
		BorderPane bottom = new BorderPane();
		bottom.setPadding(new Insets(30));
		pane.setBottom(bottom);
		
		/* back button */
		ImageView backBtn = new ImageView(new Image(getClass().getResourceAsStream("img/arrowBack.png"), 75, 75, true, true));
		bottom.setLeft(backBtn);
		BorderPane.setAlignment(backBtn, Pos.BOTTOM_LEFT);
		backBtn.setOnMouseClicked(e -> new Lobby().start(stage));
		
		/* next button */
		ImageView nextBtn = new ImageView(new Image(getClass().getResourceAsStream("img/arrow.png"), 75, 75, true, true));
		bottom.setRight(nextBtn);
		BorderPane.setAlignment(nextBtn, Pos.BOTTOM_RIGHT);
		nextBtn.setOnMouseClicked(e -> new Leaderboard().start(stage));
		
		Scene scene = new Scene(pane, WIDTH, HEIGHT);
		stage.setScene(scene);
		stage.show();
	}
}

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class RetrieveChampName extends Leaderboard {
	/* this new class is only to retrieve the new winner's name, nothing else! */
	private final double WIDTH = 400;
	private final double HEIGHT = 200;
	
	@Override
	public void start(Stage stage) {
		VBox pane = new VBox();
		pane.setPrefSize(WIDTH, HEIGHT);
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(15));
		pane.setSpacing(30);
		pane.getStyleClass().add("lobby");
		
		int positionAttained = 1;
		
		/* Congratulations text */
		Text text = new Text(new String("Congratulations in snagging position #" + positionAttained + "!" +
				"\nPlease enter your name."));
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.WHITE);
		text.setFont(Font.font("Arial", 20));
		text.setLineSpacing(15);
		pane.getChildren().add(text);
		
		TextField textField = new TextField();
		textField.setPrefWidth(100);
		pane.getChildren().add(textField);
		
		Button btOk = new Button("OK");
		pane.getChildren().add(btOk);
		
		btOk.setOnAction(e -> {
			String championName = textField.getText();
			if(championName.length() == 0)
				championName = "NewPlayer";
			try {
				PrintWriter printWriter = new PrintWriter(new File("championName.txt"));
				printWriter.write(championName);
				printWriter.flush();
				printWriter.close();
				// new Leaderboard().start(stage);
				stage.close();
			} catch(IOException ex) {
				// do nothing
			}
		});
		
		Scene scene = new Scene(pane, WIDTH, HEIGHT);
		scene.getStylesheets().addAll(this.getClass().getResource("styles.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("New High Score");
		stage.setResizable(false);
		stage.show();
	}
}

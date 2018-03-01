import javafx.application.Application;
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

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Instructions extends Application {
	private final double WIDTH = 1000;
	private final double HEIGHT = 560;
	
	@Override
	public void start(Stage stage) {
		GridPane pane = new GridPane();
		pane.setPrefSize(WIDTH, HEIGHT);
		pane.getStyleClass().add("lobby");
		pane.setAlignment(Pos.CENTER);
		pane.setVgap(0);
		pane.setHgap(50);
		
		/* title text - GREEN TRIVIA */
		Text titleText = new Text("INSTRUCTIONS");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 75));
		titleText.setFill(Color.WHITE);
		pane.add(titleText, 0, 0, 2, 1);
		GridPane.setHalignment(titleText, HPos.CENTER);
		
		/* display instructions */
		try {
			Scanner fileInput = new Scanner(new File("instructions.txt"));
			while(fileInput.hasNext()) {
				int instructionType = fileInput.nextInt();
				String instructionsFmFile = fileInput.nextLine();
				// System.out.println(instructionType + "\t" + instructionsFmFile); // to check whether all the instructions were read properly
				Text instructions = new Text(instructionsFmFile.substring(1));
				instructions.setFont(Font.font("Arial", FontWeight.BOLD, 30));
				instructions.setFill(Color.WHITE);
				if(instructionType == 1) {
					ImageView easyIcon = new ImageView(new Image(getClass().getResourceAsStream("img/Easy.png"), 100, 100, true, true));
					pane.add(easyIcon,0, 1);
					pane.add(instructions, 1, 1);
				} else if(instructionType == 2) {
					ImageView mediumIcon = new ImageView(new Image(getClass().getResourceAsStream("img/Medium.png"), 100, 100, true, true));
					pane.add(mediumIcon,0, 2);
					pane.add(instructions, 1, 2);
				} else if(instructionType == 3) {
					ImageView hardIcon = new ImageView(new Image(getClass().getResourceAsStream("img/Hard.png"), 100, 100, true, true));
					pane.add(hardIcon,0, 3);
					pane.add(instructions, 1, 3);
				}
			}
			fileInput.close();
		} catch(IOException | InputMismatchException ex) {	// if instructions.txt cannot be read or found, display error message
			Text errorText = new Text("Oops, something went wrong..\nKids, quickly tell your parents about this!");
			errorText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
			errorText.setFill(Color.WHITE);
			errorText.setTextAlignment(TextAlignment.CENTER);
			pane.add(errorText, 0, 1);
		}
		
		/* back button - to go back to main menu */
		ImageView backBtn = new ImageView(new Image(getClass().getResourceAsStream("img/arrowBack.png"), 75, 75, true, true));
		pane.add(backBtn, 0, 4);
		backBtn.setOnMouseClicked(e -> new Lobby().start(stage));
		
		/* about us button - credits, basically. */
		ImageView abtUsBtn = new ImageView(new Image("img/help.png"));
		abtUsBtn.setPreserveRatio(true);
		abtUsBtn.setFitHeight(50);
		GridPane.setHalignment(abtUsBtn, HPos.RIGHT);
		pane.add(abtUsBtn, 1, 4);
		abtUsBtn.setOnMouseClicked(e -> new AboutUs().start(new Stage()));
		
		Scene scene = new Scene(pane, WIDTH, HEIGHT);
		scene.getStylesheets().addAll(this.getClass().getResource("styles.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
}

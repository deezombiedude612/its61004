import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class AboutUs extends Application {
	private final double WIDTH = 400;
	private final double HEIGHT = 200;
	
	@Override
	public void start(Stage stage) {
		VBox pane = new VBox();
		pane.setPrefSize(WIDTH, HEIGHT);
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(15));
		pane.getStyleClass().add("lobby");
		
		Text txt_abtUs = new Text(new String("GREEN TRIVIA - An OOP2 Assignment"
				+ "\nCreated by Henry Heng, Andrew Ng, Wei Liang & Ying Chiing."
				+ "\nSpecial thanks to Ms Saro"
				+ "\nAll rights reserved."));
		txt_abtUs.setTextAlignment(TextAlignment.CENTER);
		txt_abtUs.setFill(Color.WHITE);
		txt_abtUs.setFont(Font.font("Arial", 20));
		pane.getChildren().add(txt_abtUs);
		
		Button btn_Ok = new Button("OK");
		btn_Ok.setPadding(new Insets(10, 20, 10, 20));
		pane.getChildren().add(btn_Ok);
		btn_Ok.setOnAction(e -> stage.close());		// closes this window only
		
		Scene abtUsScene = new Scene(pane);
		abtUsScene.getStylesheets().addAll(this.getClass().getResource("styles.css").toExternalForm());
		
		stage.setScene(abtUsScene);
		stage.setTitle("About Us");
		stage.setResizable(false);
		stage.show();
	}
}

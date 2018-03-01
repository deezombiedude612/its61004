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

public class Oops extends Application {
	private final double WIDTH = 400;
	private final double HEIGHT = 200;
	
	@Override
	public void start(Stage stage) {
		/* this new stage only functions to prompt error alert in a new window, nothing else. */
		VBox pane = new VBox();
		pane.setPrefSize(WIDTH, HEIGHT);
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(15));
		pane.getStyleClass().add("lobby");
		
		Text errorText = new Text(new String("Whoops, something went wrong! Kids, tell your parents!"));
		errorText.setTextAlignment(TextAlignment.CENTER);
		errorText.setFill(Color.WHITE);
		errorText.setFont(Font.font("Arial", 20));
		errorText.setLineSpacing(15);
		pane.getChildren().add(errorText);
		
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

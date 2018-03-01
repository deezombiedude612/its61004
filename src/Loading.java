import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Loading extends Application {
	@Override
	public void start(Stage primaryStage) {
		BorderPane bdPane = new BorderPane();
		
		GridPane ssOrder = new GridPane();
		ssOrder.setHgap(20);
		ssOrder.setVgap(15);
		ssOrder.setPadding(new Insets(10,10,10,10));
		ssOrder.setStyle("-fx-background-image: url('a.jpg')");
		bdPane.setCenter(ssOrder);
		
		Scene scene = new Scene (bdPane,731,410);
		primaryStage.setTitle("The Whiz Quiz");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}

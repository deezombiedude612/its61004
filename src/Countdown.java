import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Countdown extends MCQ {
	private Timeline timeline;
	private Text timerText;
	private Stage stage;
	
	public Countdown(Text timerText) {
		timeline = new Timeline();
		this.timerText = timerText;
		
		if(timeline != null)
			timeline.stop();
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), e -> {
			timeLeft--;
			if(timeLeft <= 0) {
				timeline.stop();
				timeline.getKeyFrames().removeAll();
				// System.out.println("Time up");	// to test whether the countdown works properly
				new TimeUp().start(stage);
			}
			else {
				this.timerText.setText("Time left: " + timeLeft);
				// System.out.println(timeLeft);	// to test whether the countdown works properly
			}
		});
		timeline.getKeyFrames().add(keyFrame);
	}
	
	public void startCountdown(Stage stage) {
		this.stage = stage;
		timeline.playFromStart();
	}
	
	public void stopCountdown() {
		timeline.stop();
	}
	
	public int getTimeLeft() {
		return timeLeft;
	}
}

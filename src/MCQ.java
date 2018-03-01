import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MCQ extends Application {
	private final double WIDTH = 1000;
	private final double HEIGHT = 560;
	private final int STANDARD_SCORE = 100;
	protected final int MAX_TIME = 10;
	protected int timeLeft = MAX_TIME;
	
	private List<MCQuestion> questions = new LinkedList<>();
	private int score = 0;
	protected Text timerText;
	private Countdown countdown;
	private Stage window;
	
	@Override
	public void start(Stage stage) throws IOException, InterruptedException {
		window = stage;
		BorderPane bdPane = new BorderPane();
		
		GridPane ssOrder = new GridPane();
		ssOrder.setHgap(20);
		ssOrder.setVgap(15);
		ssOrder.setPadding(new Insets(10,10,10,10));
		ssOrder.setStyle("-fx-background-image: url('img/PlainBackground.png');");
		ssOrder.setAlignment(Pos.CENTER);
		bdPane.setCenter(ssOrder);
		
		/* create a new file "temp.txt" */
		try {
			/* use IO streams to store score changes into a temporary text file which can be read by the leaderboard class by the time it ends */
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("temp.txt"));
			bufferedWriter.write("1 0");
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch(IOException ex) {
			new Oops().start(new Stage());	// display error message
			new Lobby().start(window);		// return to lobby
		}

		/* instructions */
		Text txt_instructions = new Text("sample text");
		txt_instructions.setFont(Font.font("Arial", FontWeight.BOLD, 35));
		txt_instructions.setFill(Color.WHITE);
		txt_instructions.setTextAlignment(TextAlignment.CENTER);
		ssOrder.add(txt_instructions, 0, 0, 2, 1);
		GridPane.setHalignment(txt_instructions, HPos.CENTER);
		
		/* answer regions */
		Rectangle[] optionRegion = new Rectangle[4];
		for(int i = 0; i < optionRegion.length; i++)
			optionRegion[i] = optionBox();
		Text[] answerText = new Text[4];
		for(int i = 0; i < 4; i++) {
			answerText[i] = new Text();
			answerText[i].setFont(Font.font("Arial", FontWeight.BOLD, 24));
			answerText[i].setFill(Color.BLACK);
			GridPane.setHalignment(answerText[i], HPos.CENTER);
		}
		int a = 0, b = 2;	// position of answers in the grid
		for(int i = 0; i < optionRegion.length; i++) {
			ssOrder.add(optionRegion[i], a, b);
			ssOrder.add(answerText[i], a, b);
			if(a != 1)
				a++;
			else {
				a--;
				b++;
			}
		}
		
		/* timer display region */
		timerText = new Text("Time left: " + MAX_TIME);
		timerText.setFont(Font.font("Arial", FontWeight.BOLD, 28));
		timerText.setFill(Color.WHITE);
		GridPane.setHalignment(timerText, HPos.RIGHT);
		ssOrder.add(timerText, 1, 1);
		countdown = new Countdown(timerText);
		
		initQuestions();	// retrieve question files
		
		/* run through questions from question list */
		for(MCQuestion ques: questions) {
			txt_instructions.setText(ques.getqDesc());
			
			int i = 0;	// option number
			for(MCQAnswer ans: ques.getAnswers()) {
				answerText[i].setText(ans.getAnsDesc());
				if(ans.isCorrect())
					optionRegion[i].setOnMouseClicked(e -> {
						try {
							/* store score changes into a temporary text file which can be read by the leaderboard class by the time it ends */
							PrintWriter tempScore = new PrintWriter(new File("temp.txt"));
							tempScore.write("1 ");							// to indicate what game is being played at first
							score += STANDARD_SCORE * countdown.getTimeLeft();	// add or update score influenced by time left (the more the better)
							tempScore.write(String.valueOf(score));
							tempScore.flush();
							tempScore.close();
						} catch(IOException ex) {
							new Oops().start(new Stage());	// display error message
							new Lobby().start(window);		// return to lobby
						}
						countdown.stopCountdown();
						new Correct().start(window);
					});
				else
					optionRegion[i].setOnMouseClicked(e -> {
						try {
							/* store score changes into a temporary text file which can be read by the leaderboard class by the time it ends */
							BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("temp.txt"));
							bufferedWriter.write("1 " + score);
							bufferedWriter.flush();
							bufferedWriter.close();
						} catch(IOException ex) {
							new Oops().start(new Stage());	// display error message
							new Lobby().start(window);		// return to lobby
						}
						countdown.stopCountdown();	// stop countdown
						new Wrong().start(window);
					});
				i++;
			}
			countdown.startCountdown(window);	// pass window to Countdown method for invoking Wrong class if possible
		}

		/* back button */
		ImageView backBtn = new ImageView(new Image(getClass().getResourceAsStream("img/arrowBack.png"), 75, 75, true, true));
		ssOrder.add(backBtn, 0, 4);
		backBtn.setOnMouseClicked(e -> {
			if(new File("temp.txt").exists())
				new File("temp.txt").delete();		// consider score accumulated burnt when game is ended prematurely
			countdown.stopCountdown();
			new Lobby().start(stage);
		});
		
		Scene scene = new Scene (bdPane, WIDTH, HEIGHT);
		window.setScene(scene);
		window.show();
	}
	
	/* retrieve question files */
	public void initQuestions() throws IOException {
		Scanner mcqIn = new Scanner(new File("MCQ.txt"));
		
		while(mcqIn.hasNext()) {
			MCQuestion thisQuestion = new MCQuestion();
			thisQuestion.setqDesc(mcqIn.nextLine());
			
			while(mcqIn.hasNextInt()) {
				mcqIn.nextInt();
				boolean ansCorrect = mcqIn.nextBoolean();
				String ansDesc = mcqIn.nextLine();
				thisQuestion.addAnswers(ansCorrect, ansDesc);
			}
			questions.add(thisQuestion);
		}
		mcqIn.close();
	}
	
	/* method makes shape making up an answer region */
	public Rectangle optionBox() {
		Rectangle thisRect = new Rectangle(450, 100);
		thisRect.setArcHeight(50);
		thisRect.setArcWidth(50);
		thisRect.setFill(Color.LIGHTYELLOW);
		
		return thisRect;
	}
}

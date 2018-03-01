import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Leaderboard extends Application{
	private final double WIDTH = 1000;
	private final double HEIGHT = 560;
	private final String SCORE_PLACEHOLDER = "NardoPolo\t\t0";
	
	private Stage window;
	private String[] gameFileNames = {"scores_game1.txt", "scores_game2.txt", "scores_game3.txt"};
	private Text[][] scoreDisplays = new Text[3][3];
	private List<String> topPlayers1 = new LinkedList<>();
	private List<Integer> topScores1 = new LinkedList<>();
	private List<String> topPlayers2 = new LinkedList<>();
	private List<Integer> topScores2 = new LinkedList<>();
	private List<String> topPlayers3 = new LinkedList<>();
	private List<Integer> topScores3 = new LinkedList<>();
	
	@Override
	public void start(Stage stage) {
		window = stage;
		BorderPane bdPane = new BorderPane();
		bdPane.setPrefSize(WIDTH, HEIGHT);
		bdPane.setStyle("-fx-background-image: url('img/leaderboardbg.jpg')");
		
		/* top space waster for fitting aesthetics */
		FlowPane topSpace = new FlowPane();
		topSpace.setPadding(new Insets(80));
		bdPane.setTop(topSpace);
		
		/* score display area */
		HBox scorePane = new HBox();
		scorePane.setSpacing(55);
		scorePane.setPadding(new Insets(160, 20, 0, 20));
		VBox[] scoreGrid = new VBox[3];
		for(int i = 0; i < scoreGrid.length; i++) {
			scoreGrid[i] = new VBox();
			scoreGrid[i].setSpacing(30);
			for(int j = 0; j < 3; j++) {
				scoreDisplays[i][j] = gameScoreText(SCORE_PLACEHOLDER);
				scoreGrid[i].getChildren().add(scoreDisplays[i][j]);
			}
			scorePane.getChildren().add(scoreGrid[i]);
		}
		bdPane.setCenter(scorePane);
		
		/* retrieve recent score and type of game recently played, if any */
		int gameType = 0, recentScore = 0;
		File tempFile = new File("temp.txt");
		if(tempFile.exists()) {
			try {
				Scanner reader = new Scanner(tempFile);
				gameType = reader.nextInt();
				recentScore = reader.nextInt();
				reader.close();
			} catch(IOException ex) {
				// do nothing
			}
			tempFile.delete();
			System.out.println(gameType + "\t" + recentScore);
		}
		
		/* storing previous scores from text file */
		for(int i = 0; i < 3; i++) {
			File readFile = new File(gameFileNames[i]);
			if(!readFile.exists())
				createMissingGameFile(gameFileNames[i]);
		}
		try {
			Scanner scanScores = new Scanner(new File(gameFileNames[0]));
			for(int i = 0; i < 3; i++) {
				if(scanScores.hasNext()) {
					scanScores.nextInt();
					topScores1.add(scanScores.nextInt());
					topPlayers1.add(scanScores.nextLine());
					System.out.println(topScores1 + "\t" + topPlayers1);
				} else {
					topScores1.add(0);
					topPlayers1.add("NardoPolo");
				}
				if(topPlayers1.get(i).length() >= 15)
					scoreDisplays[0][i].setText(topPlayers1.get(i) + "\t" + topScores1.get(i));
				else
					scoreDisplays[0][i].setText(topPlayers1.get(i) + "\t\t" + topScores1.get(i));
			}
			scanScores.close();
			scanScores = new Scanner(new File(gameFileNames[1]));
			for(int i = 0; i < 3; i++) {
				if(scanScores.hasNext()) {
					scanScores.nextInt();
					topScores2.add(scanScores.nextInt());
					topPlayers2.add(scanScores.nextLine());
				} else {
					topScores2.add(0);
					topPlayers2.add("NardoPolo");
				}
				if(topPlayers2.get(i).length() >= 15)
					scoreDisplays[1][i].setText(topPlayers2.get(i) + "\t" + topScores2.get(i));
				else
					scoreDisplays[1][i].setText(topPlayers2.get(i) + "\t\t" + topScores2.get(i));
			}
			scanScores.close();
			scanScores = new Scanner(new File(gameFileNames[2]));
			for(int i = 0; i < 3; i++) {
				if(scanScores.hasNext()) {
					scanScores.nextInt();
					topScores3.add(scanScores.nextInt());
					topPlayers3.add(scanScores.nextLine());
				} else {
					topScores3.add(0);
					topPlayers3.add("NardoPolo");
				}
				if(topPlayers3.get(i).length() >= 15)
					scoreDisplays[2][i].setText(topPlayers3.get(i) + "\t" + topScores3.get(i));
				else
					scoreDisplays[2][i].setText(topPlayers3.get(i) + "\t\t" + topScores3.get(i));
			}
			scanScores.close();
		} catch(IOException ex) {
			new Oops().start(new Stage());
			new Lobby().start(stage);
		}
		
		/* comparing stored scores with recently achieved score */
		/*
		int tempScoreHolder;
		boolean startSwap = false;
		String person = "Champ";
		int index = 0;
		switch(gameType) {
			case 1:
				for(int scores: topScores1) {
					if(scores < recentScore) {
						tempScoreHolder = scores;
						scores = recentScore;
						recentScore = tempScoreHolder;
					}
					
					/* retrieve new champion if not yet identified *//*
					if(!startSwap) {
						startSwap = true;
						new RetrieveChampName().start(new Stage());		// retrieve name of lucky user
						
						if(new File("championName.txt").exists()) {
							try {
								Scanner scanChamp = new Scanner(new File("championName.txt"));
								person = scanChamp.nextLine();
								scanChamp.close();
								// new File("championName.txt").delete();
							} catch(IOException ex) {
								new Oops().start(new Stage());
								new Lobby().start(stage);
							}
						}
					}
					/* swap with current holder *//*
					String tempHolder = topPlayers1.get(1 * 0 + index);
					topPlayers1.set(1 * 0 + index, person);
					person = tempHolder;
					index++;
				}
				if(startSwap) {
					/* update the text *//*
					for(int j = 0; j < 3; j++) {
						scoreDisplays[0][j].setText(topPlayers1.get(j) + "\t" + topScores1.get(j));
					}
				}
				break;
				
			case 2:
				break;
				
			case 3:
				break;
				
			default:
		}*/
		/*
		if(gameType > 0 && gameType <= 3) {
			boolean startSwap = false;
			String person = "Champ";
			for(int i = 0; i < 3; i++) {
				if(topScores.get(1 * 0 + i) < recentScore) {
					/* swap with current score *//*
					int tempScore = topScores.get(1 * 0 + i);
					topScores.set(1 * 0 + i, recentScore);
					recentScore = tempScore;
					System.out.println(topScores);
					
					/* retrieve new champion if not yet identified *//*
					if(!startSwap) {
						startSwap = true;
						new RetrieveChampName().start(window);		// retrieve name of lucky user
						/*
						if(new File("championName.txt").exists()) {
							try {
								Scanner scanChamp = new Scanner(new File("championName.txt"));
								person = scanChamp.nextLine();
								scanChamp.close();
								// new File("championName.txt").delete();
							} catch(IOException ex) {
								new Oops().start(new Stage());
								new Lobby().start(stage);
							}
						}
						*//*
					}
					System.out.println(topPlayers);
					
					/* swap with current holder *//*
					String tempHolder = topPlayers.get(1 * 0 + i);
					topPlayers.set(1 * 0 + i, person);
					person = tempHolder;
				}
			}
			
			if(startSwap) {
				for(int i = 0; i < 3; i++)
					for(int j = 0; j < 3; j++) {
						scoreDisplays[i][j].setText(topPlayers.get(i * 1 + j) + "\t" + topScores.get(i * 1 + j));
					}
				
				try {
					Scanner readScores = new Scanner(new File(gameFileNames[gameType - 1]));
					String tempFileHolder = "";
					for(int k = 2; readScores.hasNext(); k++) {
						readScores.nextInt();
						tempFileHolder += ("\n" + k + readScores.nextLine());
					}
					readScores.close();
					PrintWriter toFile = new PrintWriter(new File(gameFileNames[gameType - 1]));
					toFile.write("1 " + recentScore + " " + person);
					toFile.flush();
					toFile.write(tempFileHolder);
					toFile.flush();
					toFile.close();
				} catch(IOException ex) {
					new Oops().start(new Stage());
					new Lobby().start(stage);
				}
			}
		}*/
		
		
		ImageView backBtn = new ImageView(new Image(getClass().getResourceAsStream("img/arrowBack.png"), 75, 75, true, true));
		bdPane.setBottom(backBtn);
		BorderPane.setAlignment(backBtn, Pos.BOTTOM_LEFT);
		backBtn.setOnMouseClicked(e -> new Lobby().start(stage));

		Scene scene = new Scene (bdPane, WIDTH, HEIGHT);
		// primaryStage.setTitle("Leaderboard");
		window.setScene(scene);
		window.show();
	}
	
	/* creates missing game files */
	public void createMissingGameFile(String path) {
		try {
			PrintWriter writer = new PrintWriter(new File(path));	// creates a new game file
			writer.close();
		} catch(IOException ex) {
			new Oops().start(new Stage());
			new Lobby().start(window);
		}
	}
	
	public Text gameScoreText(String text) {
		Text thisText = new Text(text);
		thisText.setFill(Color.WHITE);
		thisText.setFont(Font.font("Arial", FontWeight.BOLD, 28));
		return thisText;
	}
	
	
}

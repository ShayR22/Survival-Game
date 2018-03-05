package Menu;

import InterfacesAndTech.IFinals;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Game_Frame_Fx extends BorderPane implements IFinals {

	private HBox menuPane = new HBox();

	private Button start;
	private Button pause;
	private Button restartGame;
	private Button options;
	private Button description;

	private Game_Pane gp;

	private Game_Option_Frame_Fx gof = new Game_Option_Frame_Fx();
	private Game_Description gd = new Game_Description();

	private Stage stage;

	public Game_Frame_Fx() {
		this.setOnKeyPressed(e -> {
			e.consume();
			gp.setFocusTraversable(true);
			gp.requestFocus();

		});

		this.setPrefSize(FRAME_WIDTH, FRAME_HEIGHT);

		this.stage = new Stage();
		gp = new Game_Pane();

		initializeButtons();
		setButtonsListeners();

		menuPane.getChildren().addAll(start, pause, restartGame, description, options);
		this.setTop(menuPane);
		this.setCenter(gp);

		Scene scene = new Scene(this);
		this.stage.setScene(scene);
		this.stage.setAlwaysOnTop(true);
		this.stage.setResizable(false);

	}

	public void init() {
		this.stage.show();
		gp.requestFocus();
	}

	private void initializeButtons() {
		start = new Button("Start");
		start.setPrefSize(FRAME_WIDTH / 5, 50);
		start.setStyle("-fx-font-size: 20");

		pause = new Button("Pause");
		pause.setPrefSize(FRAME_WIDTH / 5, 50);
		pause.setStyle("-fx-font-size: 20");

		restartGame = new Button("Restart Game");
		restartGame.setPrefSize(FRAME_WIDTH / 5, 50);
		restartGame.setStyle("-fx-font-size: 20");

		options = new Button("Options");
		options.setPrefSize(FRAME_WIDTH / 5, 50);
		options.setStyle("-fx-font-size: 20");

		description = new Button("Description");
		description.setPrefSize(FRAME_WIDTH / 5, 50);
		description.setStyle("-fx-font-size: 20");

	}

	private void setButtonsListeners() {
		start.setOnAction(e -> {
			gp.playGame();
			gp.requestFocus();
		});

		pause.setOnAction(e -> {
			gp.pauseGame();
			gp.requestFocus();
		});

		restartGame.setOnAction(e -> {
			gp.restartGame();
			gp.requestFocus();
		});

		description.setOnAction(e -> {
			gd.showMySelf();
		});

		options.setOnAction(e -> {
			gp.pauseGame();
			gof.showMySelf();
		});
	}

	public Game_Pane getMyPane() {
		return this.gp;
	}

}

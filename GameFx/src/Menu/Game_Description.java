package Menu;

import InterfacesAndTech.IFinals;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game_Description extends VBox implements IFinals {

	private Image img = new Image("/Images/stars2.jpg", true);
	private BackgroundImage bgi = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
			BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

	private VBox textVBox = new VBox(10);
	private Text descKeys = new Text("Dont let anything hit u, Use arrow keys Right and Left to move and Up to jump. \n"
			+ "Space to teleport urself to bottom screen without losing speed");
	private Text descBindings = new Text("keyBindings are:");
	private Text descP = new Text("P to pause game");
	private Text descEnter = new Text("Enter to restart current level");

	private Button backToGame = new Button("Back to the Game");

	// TODO implements scrollpane;
	// private ScrollPane sp = new ScrollPane();

	private Stage stage = new Stage();

	public Game_Description() {
		descKeys.setWrappingWidth(OPTION_FRAME_WIDTH);

		this.setBackground(new Background(bgi));
		this.setSpacing(60);

		this.setPrefSize(DESCRIPTION_WIDTH, DESCRIPTION_HEIGHT);

		// set button

		backToGame.setFont(Font.font("serif", FontWeight.BOLD, 30));
		backToGame.setAlignment(Pos.CENTER);
		backToGame.setOnAction(e -> {
			this.stage.close();
		});

		// set color
		descKeys.setFill(Color.WHITE);
		descBindings.setFill(Color.WHITE);
		descP.setFill(Color.WHITE);
		descEnter.setFill(Color.WHITE);

		// set font
		descKeys.setFont(Font.font("serif", FontWeight.BOLD, 30));
		descBindings.setFont(Font.font("serif", FontWeight.BOLD, 30));
		descP.setFont(Font.font("serif", FontWeight.BOLD, 30));
		descEnter.setFont(Font.font("serif", FontWeight.BOLD, 30));

		textVBox.getChildren().addAll(descKeys, descBindings, descP, descEnter);

		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(textVBox, backToGame);

		Scene scene = new Scene(this);
		this.stage.setScene(scene);
		this.stage.setResizable(false);
		this.stage.setAlwaysOnTop(true);
	}

	public void showMySelf() {
		this.stage.show();
	}
}

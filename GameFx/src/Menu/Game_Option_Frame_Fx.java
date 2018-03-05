package Menu;

import Game_Objects.Game_Ball_Fx;
import Game_Objects.Game_BeamBall_Fx;
import Game_Objects.Game_BossBeamBall_Fx;
import Game_Objects.Game_Star_Fx;
import InterfacesAndTech.IFinals;
import Levels.BasicLevel;
import Levels.StarLevel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Game_Option_Frame_Fx extends BorderPane implements IFinals {

	private Stage stage;

	private BorderPane topPane;
	private BorderPane middlePane;
	private BorderPane bottomPane;

	private Label speedDiff;

	private final ObservableList<String> speeds = FXCollections.observableArrayList(ULTRA_LOW, LOW, MEDIUM, HIGH,
			ULTRA_HIGH);
	private ComboBox<String> speedDifficultyComboBox;
	private myRelativeSpeed gameSpeed = myRelativeSpeed.MEDIUM;

	private Label radiusSize;
	private Slider ballSize;
	private int ballSizeValue = Game_Ball_Fx.getRadius();

	private Button done;

	private Image img = new Image("/Images/stars2.jpg", true);
	private ImageView image = new ImageView(img);

	public Game_Option_Frame_Fx() {

		this.getChildren().add(image);

		this.setPrefSize(OPTION_FRAME_WIDTH, OPTION_FRAME_HEIGHT);

		initializeButtons();
		setTopPane();
		setMiddlePane();
		setBottomPane();

		setLiseteners();

		this.setTop(topPane);
		this.setCenter(middlePane);
		this.setBottom(bottomPane);

		this.stage = new Stage();
		Scene scene = new Scene(this);
		this.stage.setScene(scene);
		this.stage.setAlwaysOnTop(true);
		this.stage.setResizable(false);
	}

	private void initializeButtons() {

		speedDiff = new Label("Speed Diffuclty");
		speedDiff.setStyle("-fx-font-size: 30");
		speedDiff.setTextFill(Color.WHITE);

		speedDifficultyComboBox = new ComboBox<String>(speeds);
		speedDifficultyComboBox.setPrefSize(OPTION_FRAME_WIDTH / 4, OPTION_FRAME_HEIGHT / 15);
		speedDifficultyComboBox.getSelectionModel().select(2);
		speedDifficultyComboBox.setStyle("-fx-background-color: WHITE");

		radiusSize = new Label("Balls Size");
		radiusSize.setStyle("-fx-font-size: 30");
		radiusSize.setTextFill(Color.WHITE);

		ballSize = new Slider(10, 30, 20);
		ballSize.setShowTickLabels(true);
		ballSize.setShowTickMarks(true);
		ballSize.setMajorTickUnit(10);
		ballSize.setMinorTickCount(1);
		ballSize.setBlockIncrement(5);
		ballSize.setStyle("-fx-background-color: WHITE");

		done = new Button("Done");
		done.setPrefSize(250, 100);
		done.setStyle("-fx-font-size: 30");

		topPane = new BorderPane();
		topPane.setStyle("-fx-border-color: WHITE");
		topPane.setPrefSize(OPTION_FRAME_WIDTH, OPTION_FRAME_HEIGHT / 3);

		middlePane = new BorderPane();
		middlePane.setPrefSize(OPTION_FRAME_WIDTH, OPTION_FRAME_HEIGHT / 3);
		middlePane.setStyle("-fx-border-color: WHITE");

		bottomPane = new BorderPane();
		bottomPane.setPrefSize(OPTION_FRAME_WIDTH, OPTION_FRAME_HEIGHT / 3);
		bottomPane.setStyle("-fx-border-color: BLACK");
	}

	private void setTopPane() {
		topPane.setCenter(speedDiff);

		BorderPane.setAlignment(speedDifficultyComboBox, Pos.BOTTOM_CENTER);
		topPane.setBottom(speedDifficultyComboBox);
	}

	private void setMiddlePane() {
		middlePane.setCenter(radiusSize);
		middlePane.setBottom(ballSize);
	}

	private void setBottomPane() {
		bottomPane.setCenter(done);
	}

	private void setLiseteners() {

		ballSize.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				System.out.println(newValue.intValue());
				ballSizeValue = newValue.intValue();
			}
		});

		speedDifficultyComboBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String mode = speedDifficultyComboBox.getValue();
				switch (mode) {
				case (ULTRA_LOW): {
					gameSpeed = myRelativeSpeed.SLOW;
					break;
				}

				case (LOW): {
					gameSpeed = myRelativeSpeed.SLOW;
					break;
				}
				case (MEDIUM): {
					gameSpeed = myRelativeSpeed.MEDIUM;
					break;
				}
				case (HIGH): {
					gameSpeed = myRelativeSpeed.HIGH;
					break;
				}
				case (ULTRA_HIGH): {
					gameSpeed = myRelativeSpeed.HIGH;
					break;
				}

				}
			}
		});

		done.setOnAction(e -> {
			// change the radiuses
			Game_Ball_Fx.ChangeRadius(ballSizeValue);
			// change all speeds
			Game_Ball_Fx.setStaticGameSpeed(gameSpeed);
			Game_BeamBall_Fx.setStaticGameSpeed(gameSpeed);
			Game_Star_Fx.setStaticGameSpeed(gameSpeed);
			Game_BossBeamBall_Fx.setStaticGameSpeed(gameSpeed);
			BasicLevel.setStaticGameSpeed(gameSpeed);
			StarLevel.setStaticGameSpeed(gameSpeed);

			this.stage.close();
		});
	}

	public void showMySelf() {
		this.stage.show();
	}
}

package myDialog;

import Menu.Game_Pane;
import InterfacesAndTech.IFinals;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MyDialog extends BorderPane implements IFinals {
	private Text message;
	private Game_Pane gp;
	private Button ok;

	private Stage stage;

	private boolean gameFinished;

	private Image img = new Image("/Images/stars3.jpg", true);
	private ImageView backGroundImage = new ImageView(img);

	public MyDialog(Game_Pane gp, String text, boolean gameFinished) {
		this.getChildren().add(backGroundImage);

		this.gameFinished = gameFinished;

		message = new Text(text);
		message.setFont(Font.font("serif", FontWeight.BOLD, 25));
		message.setFill(Color.WHITE);
		message.setWrappingWidth(MY_DIALOG_WIDTH);
		this.gp = gp;

		setOK();

		this.setPrefSize(MY_DIALOG_WIDTH, MY_DIALOG_HEIGHT);

		this.setCenter(message);

		BorderPane.setAlignment(ok, Pos.BOTTOM_CENTER);
		this.setBottom(ok);

		stage = new Stage();
		Scene scene = new Scene(this);
		stage.setScene(scene);
		stage.setAlwaysOnTop(true);
		stage.setResizable(false);
		stage.show();
	}

	private void setOK() {
		ok = new Button("OK");
		ok.setPrefSize(MY_DIALOG_WIDTH / 2, MY_DIALOG_HEIGHT / 4);
		ok.setStyle("-fx-font-size: 30");

		ok.setOnAction(e -> {
			if (gameFinished) {
				gp.gameFinished();
			} else {
				gp.nextLevel();
			}
			stage.close();
		});

		this.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				ok.fire();
			}
		});

	}
}

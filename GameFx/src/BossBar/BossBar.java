package BossBar;

import InterfacesAndTech.IFinals;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class BossBar extends Parent implements IFinals {

	private ProgressBar myBar;
	private Text precentage;

	public BossBar() {
		precentage = new Text("100%");
		precentage.setFont(Font.font("serif", FontWeight.BOLD, 30));

		myBar = new ProgressBar(1);
		myBar.setPrefSize(PANE_WIDTH - 2 * BOSS_BAR_X_SPACE, BOSS_BAR_Y_HEIGHT);
		myBar.setStyle("-fx-accent: red");

		myBar.setTranslateX(BOSS_BAR_X_SPACE);
		myBar.setTranslateY(BOSS_BAR_Y_SPACE);

		precentage.setTranslateX(BOSS_BAR_X_SPACE);
		precentage.setTranslateY(BOSS_BAR_Y_SPACE);

		this.getChildren().add(new StackPane(myBar, precentage));
	}

	public void setProgress(double progress) {
		myBar.setProgress(progress);
		int textValue = (int) Math.ceil(progress * 100);
		precentage.setText(textValue + "%");
	}
}
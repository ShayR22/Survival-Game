
import Menu.Game_Frame_Fx;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage ps) throws Exception {

		Game_Frame_Fx fx = new Game_Frame_Fx();
		fx.init(); 
	}
}

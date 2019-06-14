import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TipCalculateMain extends Application {
	
	

	public static void main(String[] args) {
		// method in the Application class that sets up your program.
		launch(args);
	}

	// Method that starts your program
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("TipCalculatorGUI.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Tip Calculator");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	

}

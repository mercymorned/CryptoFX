package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * 
 * Created for SWEN 504 @ Victoria University Wellington.
 * 
 * @author Ronan Griffin
 *         <a href="mailto:griffirona@myvuw.ac.nz">griffirona@myvuw.ac.nz</a>
 * 
 *         App image credit: Seabranddesign
 *         https://www.vecteezy.com/vector-art/517680-cyber-security-illustration
 */

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("CryptoFX_Login.fxml"));
			Scene scene = new Scene(root, 718, 700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setTitle("CryptoFX Cryptography App");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}

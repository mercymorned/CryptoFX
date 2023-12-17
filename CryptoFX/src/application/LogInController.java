package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LogInController implements Initializable {

    @FXML
    private Button buttonLogIn;

    @FXML
    private PasswordField enterPass;

    @FXML
    private TextField enterUsername;
    
    @FXML
    private AnchorPane anchorpane;
    
    //attempt to use database for log ins, did not work
	String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/CSV_DB 9";
	String USERNAME = "root";
	String PASSWORD = "";
	
    @FXML
    private void buttonLogIn (MouseEvent event) throws IOException {
		try {
			//register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
			Statement statement = connection.createStatement();
			
			String userName = enterUsername.getText();
			String password = enterPass.getText();
			
			String sql = "SELECT * FROM logininfo where username = \"" + userName +"\" and password = \"" +password +"\"";
			ResultSet resultSet = statement.executeQuery(sql);
			
			//go through resultSet to print it
			if (resultSet.next()) {
				Parent root = FXMLLoader.load(getClass().getResource("CryptoFX.fxml"));
				Stage stage = new Stage();
				Scene scene = new Scene(root, 1100, 700);
				stage.setScene(scene);
				stage.show();
				anchorpane.getScene().getWindow().hide();
			} else {
	    		Alert alert = new Alert(Alert.AlertType.ERROR);
	    		alert.setTitle("Login Alert");
	    		alert.setContentText("Username or password is invalid");
	    		alert.setHeaderText("Log In Failed");
	    		alert.show();
			}
			
			//close external resources
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    private void tooltip() {
    	Tooltip usernameTip = new Tooltip("Enter your username");
    	enterUsername.setTooltip(usernameTip);
    	
    	Tooltip passwordTip = new Tooltip("Enter your password");
    	enterPass.setTooltip(passwordTip);
    }
    
//    @FXML
//    private void buttonLogIn (MouseEvent event) throws IOException {
//    	if (enterUsername.getText().equals("admin") && enterPass.getText().equals("password")) {
//			Parent root = FXMLLoader.load(getClass().getResource("CryptoFX.fxml"));
//			Stage stage = new Stage();
//			Scene scene = new Scene(root, 1100, 700);
//			stage.setScene(scene);
//			stage.show();
//			anchorpane.getScene().getWindow().hide();
//    	} else {
//    		Alert alert = new Alert(Alert.AlertType.ERROR);
//    		alert.setTitle("Login Alert");
//    		alert.setContentText("Username or password is invalid");
//    		alert.setHeaderText("Log In Failed");
//    		alert.show();
//    	}
//    }
    
    @Override
    public void initialize(URL url, ResourceBundle arg) {
    	tooltip();
    }

}

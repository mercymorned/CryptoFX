package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Controller implements Initializable {
	static String alphabet = "abcdefghijklmnopqrstuvwxyz";
	String textCaesarKey;
	int caesarKey;
	String caesarInput;
	

    @FXML
    private Button buttonDecrypt;

    @FXML
    private Button buttonEncrypt;

    @FXML
    private TextField enterKey;

    @FXML
    private TextField enterText;

    @FXML
    private Label instructionAES;

    @FXML
    private Label instructionCC;

    @FXML
    private Label instructionDES;

    @FXML
    private RadioButton radioAES;

    @FXML
    private RadioButton radioCC;

    @FXML
    private RadioButton radioDES;

    @FXML
    private TextArea resultText;
    
    public static String caesarEncrypt(String caesarInput, String textCaesarKey) {
    	int caesarKey = Integer.parseInt(textCaesarKey); 
    	return applyShift(caesarInput, caesarKey);
    }
    
    public static String caesarDecrypt(String caesarEncodeResult, String textCaesarKey) {
    	int caesarKey = Integer.parseInt(textCaesarKey); 
    	return applyShift(caesarEncodeResult, 26 - caesarKey);
    }
    
    private static String applyShift(String message, int caesarKey) {
    	char[] chars = message.toCharArray();
    	for (int i = 0; i < chars.length; i++) {
    		char c = chars[i];
    		if (c >= 'A' && c <= 'Z')
    			chars[i] = (char) ((c - 'A' +caesarKey) % 26 + 'A');
    		else if (c >= 'a' && c <= 'z')
    			chars[i] = (char) ((c - 'a' + caesarKey) % 26 + 'a');
    	} 	
    	return new String(chars);
    }
    
    @FXML
    private void buttonEncrypt(MouseEvent event) throws Exception {
    	if (enterKey.getText().trim().isEmpty() && enterText.getText().trim().isEmpty()) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setContentText("Text and Key can't be empty");
    		alert.setHeaderText(null);
    		alert.setTitle("Encryption Error");
    		alert.showAndWait();
    	} else if (enterText.getText().trim().isEmpty()) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setContentText("Text can't be empty");
    		alert.setHeaderText(null);
    		alert.setTitle("Encryption Error");
    		alert.showAndWait();
    	}  else if (enterKey.getText().trim().isEmpty()) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setContentText("Key can't be empty");
    		alert.setHeaderText(null);
    		alert.setTitle("Encryption Error");
    		alert.showAndWait();
    	} else {
    		if (radioCC.isSelected()) {
    			String caesarInput = enterText.getText().toString();
    			String textCaesarKey = enterKey.getText().toString();
    			String caesarCipherText = caesarEncrypt(caesarInput, textCaesarKey).toString();
    			resultText.setText(caesarCipherText);
    			//confirming in console
    			System.out.println("The encrypted message is: " + caesarCipherText);
    			System.out.println("The decrypted message is: " + (caesarDecrypt(caesarCipherText, textCaesarKey)));
    		} else if (radioAES.isSelected()) {
    			
    		} else if (radioDES.isSelected()) {
    			
    		} else {
        		Alert alert = new Alert(Alert.AlertType.ERROR);
        		alert.setContentText("Must select an encryption type");
        		alert.setHeaderText(null);
        		alert.setTitle("Encryption Error");
        		alert.showAndWait();
    		}
    	}
    }
    
    @FXML
    private void buttonDecrypt(MouseEvent event) throws Exception {
    	if (enterKey.getText().trim().isEmpty() && enterText.getText().trim().isEmpty()) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setContentText("Text and Key can't be empty");
    		alert.setHeaderText(null);
    		alert.setTitle("Encryption Error");
    		alert.showAndWait();
    	} else if (enterText.getText().trim().isEmpty()) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setContentText("Text can't be empty");
    		alert.setHeaderText(null);
    		alert.setTitle("Encryption Error");
    		alert.showAndWait();
    	}  else if (enterKey.getText().trim().isEmpty()) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setContentText("Key can't be empty");
    		alert.setHeaderText(null);
    		alert.setTitle("Encryption Error");
    		alert.showAndWait();
    	} else {
    		if (radioCC.isSelected()) {
    			
    			String caesarEncodeResult = enterText.getText().toString();
    			String textCaesarKey = enterKey.getText().toString();
    			String caesarCipherPlainText = caesarDecrypt(caesarEncodeResult, textCaesarKey).toString();
    			resultText.setText(caesarCipherPlainText);
    			//confirming in console
    			System.out.println("The decrypted message is: " + caesarCipherPlainText);
    			System.out.println("The encrypted message is: " + (caesarEncrypt(caesarCipherPlainText, textCaesarKey)));
    		} else if (radioAES.isSelected()) {
    			
    		} else if (radioDES.isSelected()) {
    			
    		} else {
        		Alert alert = new Alert(Alert.AlertType.ERROR);
        		alert.setContentText("Must select an encryption type");
        		alert.setHeaderText(null);
        		alert.setTitle("Encryption Error");
        		alert.showAndWait();
    		}
    	}
    }
    
    @FXML
    private void radioAES(MouseEvent event) {
    	radioCC.setSelected(false);
    	radioDES.setSelected(false);
    }

    @FXML
    private void radioCC(MouseEvent event) {
    	radioAES.setSelected(false);
    	radioDES.setSelected(false);
    }

    @FXML
    private void radioDES(MouseEvent event) {
    	radioAES.setSelected(false);
    	radioCC.setSelected(false);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle arg) {
    	
    }

}

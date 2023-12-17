package application;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.ResourceBundle;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

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
	String aesInput;
	String aesKey;
	String desInput;
	String desKey;
	private static final String SALT = "ThisIsSalt";
	private static Cipher cipher;

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
		int caesarKey = stringToInt(textCaesarKey);
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
				chars[i] = (char) ((c - 'A' + caesarKey) % 26 + 'A');
			else if (c >= 'a' && c <= 'z')
				chars[i] = (char) ((c - 'a' + caesarKey) % 26 + 'a');
		}
		return new String(chars);
	}

	//attempt to catch non-int strings for caesar ciper but does not catch
	public static int stringToInt(String textCaesarKey) {
		try {
			int caesarKey = Integer.parseInt(textCaesarKey);
			System.out.println(textCaesarKey + " is a valid int");
		} catch (NumberFormatException e) {
			System.out.println(textCaesarKey + " is not a valid int");
		}
			final int caesarKey = Integer.parseInt(textCaesarKey);
			return caesarKey;
	}
	
	public static String aesEncrypt(String inputAES, String aesKey) {
		try {
			byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec keyspec = new PBEKeySpec(aesKey.toCharArray(), SALT.getBytes(), 65536, 256);
			SecretKey sk = factory.generateSecret(keyspec);
			SecretKeySpec secretKeySpec = new SecretKeySpec(sk.getEncoded(), "AES");
			
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivspec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(inputAES.getBytes(StandardCharsets.UTF_8)));
		} catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            System.out.println(e);
        }
        return null;
    }
	
	public static String aesDecrypt (String aesPlainText, String aesKey) {
        try {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec keyspec = new PBEKeySpec(aesKey.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey sk = factory.generateSecret(keyspec);
            SecretKeySpec secretKeyspec = new SecretKeySpec(sk.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKeyspec, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(aesPlainText)));
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            System.out.println(e);
        }
        return null;
    }
	
	public String desEncrypt() throws Exception {
		String desInput = enterText.getText().toString();
		String desKey = enterKey.getText().toString();
        byte[] encryptKey = desKey.getBytes();
        DESedeKeySpec spec = new DESedeKeySpec(encryptKey);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey theKey = keyFactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        IvParameterSpec IvParameters = new IvParameterSpec(new byte[]{12, 34, 56, 78, 90, 87, 65, 43});
        cipher.init(Cipher.ENCRYPT_MODE, theKey, IvParameters);
        byte[] encrypted = cipher.doFinal(desInput.getBytes());
        String desEncrypted = Base64.getEncoder().encodeToString(encrypted);
        resultText.setText(desEncrypted);
        return null;
	}
	
	public String desDecrypt() throws Exception {
		String desEncrypted = enterText.getText().toString();
		String desKey = enterKey.getText().toString();
        byte[] encryptKey = desKey.getBytes();
        DESedeKeySpec spec = new DESedeKeySpec(encryptKey);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey theKey = keyFactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        IvParameterSpec ivParameters = new IvParameterSpec(new byte[]{12, 34, 56, 78, 90, 87, 65, 43});
        cipher.init(Cipher.DECRYPT_MODE, theKey, ivParameters);
        byte[] original = cipher.doFinal(Base64.getDecoder().decode(desEncrypted.getBytes()));
        String desPlainText = new String(original);
        resultText.setText(desPlainText);
        return null;
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
		} else if (enterKey.getText().trim().isEmpty()) {
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
				// confirming in console
				System.out.println("The encrypted message is: " + caesarCipherText);
				System.out.println("The decrypted message is: " + (caesarDecrypt(caesarCipherText, textCaesarKey)));
			} else if (radioAES.isSelected()) {
				String aesInput = enterText.getText().toString();
				String aesKey = enterKey.getText().toString();
				String aesEncryptResult = aesEncrypt(aesInput, aesKey);
				resultText.setText(aesEncryptResult);

			} else if (radioDES.isSelected()) {
                if (enterKey.getText().length() <= 23) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please make your key at least 24 characters");
                    alert.setHeaderText(null);
                    alert.setTitle("Encryption Error");
                    alert.showAndWait();
                } else {
                    desEncrypt();
                }
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
		} else if (enterKey.getText().trim().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Key can't be empty");
			alert.setHeaderText(null);
			alert.setTitle("Encryption Error");
			alert.showAndWait();
		} else {
			if (radioCC.isSelected()) {

				String caesarEncodeResult = enterText.getText().toString();
				String textCaesarKey = enterKey.getText().toString();
				int caesarKey = Integer.parseInt(textCaesarKey);
				String caesarCipherPlainText = caesarDecrypt(caesarEncodeResult, textCaesarKey).toString();
				resultText.setText(caesarCipherPlainText);
				// confirming in console
				System.out.println("The decrypted message is: " + caesarCipherPlainText);
				System.out
						.println("The encrypted message is: " + (caesarEncrypt(caesarCipherPlainText, textCaesarKey)));
			} else if (radioAES.isSelected()) {
				String aesEncrypted = enterText.getText().toString();
				String aesKey = enterKey.getText().toString();
				String aesPlainText = aesDecrypt(aesEncrypted, aesKey);
				resultText.setText(aesPlainText);

			} else if (radioDES.isSelected()) {
                if (enterKey.getText().length() <= 23) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please make your key at least 24 characters");
                    alert.setHeaderText(null);
                    alert.setTitle("Encryption Error");
                    alert.showAndWait();
                } else {
                    desDecrypt();
                }

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

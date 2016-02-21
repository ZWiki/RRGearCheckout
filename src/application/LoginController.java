package application;


import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	
	@FXML private TextField fx_tf_username;
	@FXML private PasswordField fx_pf_password;
	@FXML private Button fx_btn_login;
	
	private Stage newMemberStage = null;
	
	@FXML
	protected void handleCreateAccountHyperLink() {
		// If the new member screen is not open yet, then we need to open it
		if (newMemberStage == null) {
			try {
				Parent parent = FXMLLoader.load(getClass().getResource("new_member.fxml"));
				newMemberStage = new Stage();
				newMemberStage.setTitle("New Member Creation");
				newMemberStage.setScene(new Scene(parent));
				newMemberStage.showAndWait(); // We want to wait for it to close
				newMemberStage = null; // No longer opened, so reset
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			newMemberStage.toFront();
		}
	}
	
	@FXML
	protected void handleLoginBtnClicked() {
		
	}

}

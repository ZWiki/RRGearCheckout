package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class ApplicationController {
	@FXML private MenuItem fx_mi_login;

	private boolean isLoggedIn = false;
	private Stage loginStage= null;

	@FXML
	protected void handleLoginMenuItemAction() {
		// If the login screen is not open yet, then we need to open it
		if (loginStage == null) {
			try {
				Parent parent = FXMLLoader.load(getClass().getResource("login.fxml"));
				loginStage = new Stage();
				loginStage.setTitle("Ridge Roamers Login");
				loginStage.setScene(new Scene(parent));
				loginStage.showAndWait(); // We want to wait for it to close
				loginStage = null; // No longer opened, so reset

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			loginStage.toFront();
		}
	}
}

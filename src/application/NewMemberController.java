package application;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class NewMemberController implements Initializable{
	@FXML private TextField fx_tf_first_name;
	@FXML private TextField fx_tf_last_name;
	@FXML private TextField fx_tf_email;
	@FXML private PasswordField fx_pf_password;
	@FXML private PasswordField fx_pf_reconfirm_password;
	@FXML private Button fx_btn_submit;
	@FXML private Pane fx_p_check;
	@FXML private ComboBox<String> fx_cb_gender;
	@FXML private ComboBox<String> fx_cb_signed_waiver;
	@FXML private ComboBox<String> fx_cb_spring_mem;
	@FXML private ComboBox<String> fx_cb_fall_mem;
	@FXML private ComboBox<String> fx_cb_summer_mem;
	@FXML private ComboBox<String> fx_cb_climbing_officer;
	@FXML private ComboBox<String> fx_cb_belay_certified;
	
	private static ImageView checkmark;
	private static ImageView x;
	
	@FXML
	protected void handleSubmitBtnAction() {
		String firstName = fx_tf_first_name.getText();
		String lastName = fx_tf_last_name.getText();
		String password = fx_pf_password.getText();
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("email", fx_tf_email.getText());
		String response = RidgeRoamersAPI.add_climber(firstName, lastName, password, data);
		System.out.println(response);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		checkmark = new ImageView(new Image(getClass().getResourceAsStream("../images/CheckMark.png")));
		x = new ImageView(new Image(getClass().getResourceAsStream("../images/X.png")));
		
		fx_cb_gender.getItems().addAll("Male", "Female", "N/A");
		fx_cb_gender.setValue("N/A");
		fx_cb_signed_waiver.getItems().addAll("Yes", "No");
		fx_cb_signed_waiver.setValue("No");
		fx_cb_spring_mem.getItems().addAll("Yes", "No");
		fx_cb_spring_mem.setValue("No");
		fx_cb_fall_mem.getItems().addAll("Yes", "No");
		fx_cb_fall_mem.setValue("No");
		fx_cb_summer_mem.getItems().addAll("Yes", "No");
		fx_cb_summer_mem.setValue("No");
		
		// Validate password for "reconfirm"
		fx_pf_reconfirm_password.textProperty().addListener((observable, oldValue, newValue) -> {
			fx_p_check.getChildren().clear();
			// We only need to check the passwords if the "reconfirm" is not empty
			if (newValue.length() != 0) {
				if (newValue.equals(fx_pf_password.getText())) {
					fx_p_check.getChildren().add(checkmark);
				} else {
					fx_p_check.getChildren().add(x);
				}
			}
		});
		
		// Validate password for "password"
		fx_pf_password.textProperty().addListener((observable, oldValue, newValue) -> {
			fx_p_check.getChildren().clear();
			if (fx_pf_reconfirm_password.getText().length() != 0 && newValue.length() != 0) {
				if (newValue.equals(fx_pf_reconfirm_password.getText())) {
					fx_p_check.getChildren().add(checkmark);
				} else {
					fx_p_check.getChildren().add(x);
				}
			}
		});
		
	}
	
	

}

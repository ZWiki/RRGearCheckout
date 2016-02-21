package application;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
	
	private static ImageView checkmark;
	private static ImageView x;
	
	@FXML
	protected void handleSubmitBtnAction() {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("first_name", fx_tf_first_name.getText());
		data.put("last_name", fx_tf_last_name.getText());
		data.put("email", fx_tf_email.getText());
		data.put("password", fx_pf_password.getText());
		System.out.println(RidgeRoamersAPI.basicRequest("add_climber", data));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		checkmark = new ImageView(new Image(getClass().getResourceAsStream("../images/CheckMark.png")));
		x = new ImageView(new Image(getClass().getResourceAsStream("../images/X.png")));
		fx_pf_reconfirm_password.textProperty().addListener((observable, oldValue, newValue) -> {
			fx_p_check.getChildren().clear();
			if (newValue.length() != 0) {
				if (newValue.equals(fx_pf_password.getText())) {
					fx_p_check.getChildren().add(checkmark);
				} else {
					fx_p_check.getChildren().add(x);
				}
			}
		});
		
	}
	
	

}

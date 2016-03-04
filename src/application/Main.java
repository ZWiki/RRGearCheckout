package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("application.fxml"));
			Scene scene = new Scene(root);
			stage.setTitle("Ridge Roamers Gear Checkout");
			stage.setScene(scene);
			stage.setOnCloseRequest(e -> System.exit(0));
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
//		RidgeRoamersAPI.add_climber(null, null, null);
//		System.out.println(RidgeRoamersAPI.basicRequest("get_co_list"));
//		System.out.println(RidgeRoamersAPI.view_available_items());
		launch(args);
	}
}

package application;

import java.awt.Checkbox;
import java.awt.event.TextEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ApplicationController implements Initializable {
	@FXML private MenuItem fx_mi_login;
	@FXML private Pane fx_p_header;
	@FXML private TreeTableView<Gear> fx_ttv_gear;
	@FXML private TreeTableColumn<Gear, String> fx_ttc_id;
	@FXML private TreeTableColumn<Gear, String> fx_ttc_name;
	@FXML private TreeTableColumn<Gear, String> fx_ttc_description;
	@FXML private TreeTableColumn<Gear, String> fx_ttc_color;
	@FXML private TreeTableColumn<Gear, String> fx_ttc_size;
	@FXML private TreeTableColumn<Gear, String> fx_ttc_quantity;
	@FXML private TreeTableColumn<Gear, String> fx_ttc_image;
	@FXML private TreeTableColumn<Gear, String> fx_ttc_checkout_date;
	@FXML private TreeTableColumn<Gear, Boolean> fx_ttc_checkout;

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
	
	
	@FXML
	protected void handleViewAvailableGear() {
		String jsonResponse = RidgeRoamersAPI.view_available_items();
		JsonObject jsonObject = new JsonParser().parse(jsonResponse).getAsJsonObject();
		JsonArray items = jsonObject.get("data").getAsJsonArray();
		TreeItem<Gear> dummyRoot = null;
		
		String currentId = "";
		TreeItem<Gear> root = null; // used for items with the same base id
		
		boolean ready = false;
		
		// for each item in the gear list, group the items into their respective groups
		for (JsonElement item : items) {
			Gear gear = new Gear(item);
			String gearId = gear.getId().split("-")[0]; // only need the base id
			// If a dummy root hasn't been created yet then we need one
			if (dummyRoot == null) {
				dummyRoot = new TreeItem<Gear>(gear);
			}
			// If the base id does not match then we have a new item
			if (!gearId.equals(currentId)) {
				currentId = gearId;
				gear.setId(gearId); // Get rid of the specific id and only use the generic
				
				root = new TreeItem<Gear>(gear);
				dummyRoot.getChildren().add(root);
			}
			root.getChildren().add(new TreeItem<Gear>(new Gear(item)));
			
		}
		
		fx_ttv_gear.setRoot(dummyRoot);
		fx_ttv_gear.setShowRoot(false);
		
		
		
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Set the cell factories
		//id
		fx_ttc_id.setCellValueFactory((TreeTableColumn.CellDataFeatures<Gear, String> p) -> new ReadOnlyStringWrapper(
				p.getValue().getValue().getId()));
		//name
		fx_ttc_name.setCellValueFactory((TreeTableColumn.CellDataFeatures<Gear, String> p) -> new ReadOnlyStringWrapper(
				p.getValue().getValue().getName()));
		//description
		fx_ttc_description.setCellValueFactory((TreeTableColumn.CellDataFeatures<Gear, String> p) -> new ReadOnlyStringWrapper(
				p.getValue().getValue().getDescription()));
		//color
		fx_ttc_color.setCellValueFactory((TreeTableColumn.CellDataFeatures<Gear, String> p) -> new ReadOnlyStringWrapper(
				p.getValue().getValue().getColor()));
		//size
		fx_ttc_size.setCellValueFactory((TreeTableColumn.CellDataFeatures<Gear, String> p) -> new ReadOnlyStringWrapper(
				p.getValue().getValue().getSize()));
		//quantity
		fx_ttc_quantity.setCellValueFactory((TreeTableColumn.CellDataFeatures<Gear, String> p) -> new ReadOnlyStringWrapper(
				p.getValue().getValue().getTotalQuantity()));
		//image
		fx_ttc_image.setCellValueFactory((TreeTableColumn.CellDataFeatures<Gear, String> p) -> new ReadOnlyStringWrapper(
				p.getValue().getValue().getImage()));
		//checkout date
		fx_ttc_checkout_date.setCellValueFactory((TreeTableColumn.CellDataFeatures<Gear, String> p) -> new ReadOnlyStringWrapper(
				p.getValue().getValue().getCheckoutDate()));
		//checkbox
		fx_ttc_checkout.setCellFactory(CheckBoxTreeTableCell.forTreeTableColumn(fx_ttc_checkout));
		
	}
}

package application;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

public class Gear implements Cloneable{

	private StringProperty idProperty;
	private StringProperty nameProperty;
	private StringProperty descriptionProperty;
	private StringProperty colorProperty;
	private StringProperty sizeProperty;
	private StringProperty totalQuantityProperty;
	private StringProperty imageProperty;
	private StringProperty checkoutDateProperty;
	private CheckBox checkbox;
	private JsonElement ele;

	public Gear(JsonElement ele) {
		this.ele = ele;
		checkbox = new CheckBox();
		JsonObject obj = ele.getAsJsonObject();;
		idProperty = new SimpleStringProperty();
		nameProperty = new SimpleStringProperty();
		descriptionProperty = new SimpleStringProperty();
		colorProperty = new SimpleStringProperty();
		sizeProperty = new SimpleStringProperty();
		totalQuantityProperty = new SimpleStringProperty();
		imageProperty = new SimpleStringProperty();
		checkoutDateProperty = new SimpleStringProperty();

		idProperty.set(strOrNull(obj.get("id")));
		nameProperty.set(strOrNull(obj.get("name")));
		descriptionProperty.set(strOrNull(obj.get("description")));
		colorProperty.set(strOrNull(obj.get("color")));
		sizeProperty.set(strOrNull(obj.get("size")));
		totalQuantityProperty.set(strOrNull(obj.get("totalQuantity")));
		imageProperty.set(strOrNull(obj.get("image")));
		checkoutDateProperty.set(strOrNull(obj.get("checkoutDate")));
	}
	
	protected Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void setId(String id) {
		idProperty.set(id);
	}

	public String getId() {
		return idProperty.get();
	}

	public String getName() {
		return nameProperty.get();
	}

	public String getDescription() {
		return descriptionProperty.get();
	}

	public String getColor() {
		return colorProperty.get();
	}

	public String getSize() {
		return sizeProperty.get();
	}

	public String getTotalQuantity() {
		return totalQuantityProperty.get();
	}

	public String getImage() {
		return imageProperty.get();
	}

	public String getCheckoutDate() {
		return checkoutDateProperty.get();
	}
	
	public JsonElement getEle() {
		return ele;
	}

	private String strOrNull(JsonElement ele) {
		if (ele.isJsonNull()) 
			return "";
		else
			return ele.getAsString();
	}
}

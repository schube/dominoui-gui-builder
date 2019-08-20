package com.schubec.dominoui.guibuilder.client.model.editor.elements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dominokit.domino.ui.utils.BaseDominoElement;

import com.google.gwt.core.client.GWT;
import com.schubec.dominoui.guibuilder.client.ui.screen01.Datatype;
import com.schubec.dominoui.guibuilder.client.ui.screen01.EditorProperty;

import elemental2.core.JsArray;
import jsinterop.base.JsPropertyMap;

public class SchubecTreeElement {

	private boolean isRemoveable = true;
	private boolean hasSourcecode = true;

	@SuppressWarnings("rawtypes")
	BaseDominoElement element;
	private String name = null;

	@SuppressWarnings("rawtypes")
	public SchubecTreeElement(BaseDominoElement element, String name) {
		this.element = element;
		this.name = name;
	}

	@SuppressWarnings("rawtypes")
	public BaseDominoElement getElement() {
		return element;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, EditorProperty> getProperties() {
		Map<String, EditorProperty> properties = new HashMap<>();
		return properties;
	}

	public String getLabel() {
		return "<not implemented>";
	};

	public void setLabel(String label) {

	};

	public String getType() {
		return "generic";
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getType() + " with ID " + element.getDominoId());
		return sb.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toSourcecode() {
		return "";
	}
	public JsPropertyMap<Object> toJson() {
		
		JsPropertyMap<Object> result = JsPropertyMap.of();
		result.set("type", getType());
		if(getName()!=null && !getName().isEmpty()) {
			result.set("name", getName());
		}
		if(getLabel()!=null && !getLabel().equals("<not implemented>")) {
			result.set("label", getLabel());
		}
		JsArray<Object> propertiesArray = new JsArray<>();
		Map<String, EditorProperty> properties = getProperties();
		for(EditorProperty propery: properties.values()) {
			if (propery.getDatatype() == Datatype.STRING && propery.getValue()!=null) {
				JsPropertyMap<Object> propertyJson = JsPropertyMap.of(propery.getName(), propery.getValue()); 
				propertiesArray.push(propertyJson);
			}
			if (propery.getDatatype() == Datatype.ENUM && propery.getValue()!=null) {
				JsPropertyMap<Object> propertyJson = JsPropertyMap.of(propery.getName(), propery.getValue()); 
				propertiesArray.push(propertyJson);
			}
			if (propery.getDatatype() == Datatype.INTEGER) {
				int value = (Integer)propery.getValue();
				
				JsPropertyMap<Object> propertyJson = JsPropertyMap.of(propery.getName(), value); 
				propertiesArray.push(propertyJson);
			}
			if (propery.getDatatype() == Datatype.BOOLEAN) {
				JsPropertyMap<Object> propertyJson = JsPropertyMap.of(propery.getName(), (Boolean)propery.getValue()); 
				propertiesArray.push(propertyJson);
			}			
		}
		result.set("properties", propertiesArray);
		return result;
	}
	public void setProperty(String key, String newValue) {
		GWT.log("setProperty/String");
	}

	public void setProperty(String key, boolean newValue) {
		GWT.log("setProperty/Boolean");
	}

	public boolean isRemoveable() {
		return isRemoveable;
	}

	public void setRemoveable(boolean isRemoveable) {
		this.isRemoveable = isRemoveable;
	}


	public boolean hasSourcecode() {
		return hasSourcecode;
	}

	public void setHasSourcecode(boolean hasSourcecode) {
		this.hasSourcecode = hasSourcecode;
	}

	public void setProperty(String key, Integer newValue) {
		GWT.log("setProperty/Integer");
		
	}

	public void setShowVisualHelpers(Boolean showVisualAids) {
		if(showVisualAids) {
			element.style().add("showVisualAids");
		} else {
			element.style().remove("showVisualAids");
		}
	}

}

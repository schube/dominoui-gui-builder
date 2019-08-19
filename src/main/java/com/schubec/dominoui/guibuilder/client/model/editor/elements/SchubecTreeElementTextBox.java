package com.schubec.dominoui.guibuilder.client.model.editor.elements;
import java.util.Map;

import org.dominokit.domino.ui.forms.TextBox;

import com.google.gwt.core.client.GWT;
import com.schubec.dominoui.guibuilder.client.model.ElementCounter;
import com.schubec.dominoui.guibuilder.client.model.UUID;
import com.schubec.dominoui.guibuilder.client.ui.screen01.Datatype;
import com.schubec.dominoui.guibuilder.client.ui.screen01.EditorProperty;

public class SchubecTreeElementTextBox extends SchubecTreeElement {
	public static final String TYPE = "TextBox";
	private TextBox element; 
	public SchubecTreeElementTextBox(TextBox element) {
		super(element, "textBox_"+ ElementCounter.get());
		this.element = element;
		
	}
	public String getType() {
		return TYPE;
	}
	public Map<String, EditorProperty> getProperties() {
		Map<String, EditorProperty> properties = super.getProperties();
		properties.put("placeholder", new EditorProperty<String>("placeholder", Datatype.STRING, element.getPlaceholder()));
		properties.put("readonly", new EditorProperty<Boolean>("readonly", Datatype.BOOLEAN, element.isReadOnly()));
		return properties;
	}
	
	@Override
	public void setProperty(String key, String newValue) {
		if(key.equals("placeholder")) {
			element.setPlaceholder(newValue);
		}
	}
	@Override
	public void setProperty(String key, boolean newValue) {
		if(key.equals("readonly")) {
			element.setReadOnly(newValue);
		}
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("TextBox with ID " + element.getDominoId() + "\n");
		return sb.toString();
	}
	@Override
	public String toSourcecode() {
		StringBuilder sb = new StringBuilder();
		sb.append("TextBox "+getName()+" = TextBox.create();\n");
		return sb.toString();
	}
}

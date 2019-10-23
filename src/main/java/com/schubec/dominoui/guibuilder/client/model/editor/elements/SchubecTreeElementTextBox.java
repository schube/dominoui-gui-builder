package com.schubec.dominoui.guibuilder.client.model.editor.elements;
import java.util.Map;

import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.forms.TextBox;

import com.schubec.dominoui.guibuilder.client.model.ElementCounter;
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
		properties.put("helpertext", new EditorProperty<String>("helpertext", Datatype.STRING, element.getHelperText()));
		properties.put("readonly", new EditorProperty<Boolean>("readonly", Datatype.BOOLEAN, element.isReadOnly()));
		properties.put("required", new EditorProperty<Boolean>("required", Datatype.BOOLEAN, element.isRequired()));
		properties.put("requiredErrorMessage", new EditorProperty<String>("requiredErrorMessage", Datatype.STRING, element.getRequiredErrorMessage()));
		return properties;
	}
	
	@Override
	public String getLabel() {
		return ((TextBox)getElement()).getLabel();
	}

	@Override
	public void setLabel(String label) {
		((TextBox)getElement()).setLabel(label);
	}
	
	@Override
	public void setProperty(String key, String newValue) {
		if(key.equals("placeholder")) {
			element.setPlaceholder(newValue);
		}
		if(key.equals("helpertext")) {
			element.setHelperText(newValue);
		}
		if(key.equals("requiredErrorMessage")) {
			element.setRequiredErrorMessage(newValue);
		}		
	}
	@Override
	public void setProperty(String key, boolean newValue) {
		if(key.equals("readonly")) {
			element.setReadOnly(newValue);
		}
		if(key.equals("required")) {
			element.setRequired(newValue);
		}
	}

	@Override
	public String toSourcecode() {
		StringBuilder sb = new StringBuilder();
		sb.append("TextBox "+getName()+" = TextBox.create();\n");
		return sb.toString();
	}
}

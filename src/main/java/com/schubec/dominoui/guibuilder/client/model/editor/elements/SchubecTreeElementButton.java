package com.schubec.dominoui.guibuilder.client.model.editor.elements;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.button.ButtonSize;
import org.dominokit.domino.ui.style.StyleType;

import com.schubec.dominoui.guibuilder.client.model.ElementCounter;
import com.schubec.dominoui.guibuilder.client.ui.screen01.Datatype;
import com.schubec.dominoui.guibuilder.client.ui.screen01.EditorProperty;

import jsinterop.base.JsPropertyMap;

public class SchubecTreeElementButton extends SchubecTreeElement {
	private Button element; 
	public static final String TYPE = "Button";
	
	public SchubecTreeElementButton(Button element) {
		super(element, "button_"+ ElementCounter.get());
		this.element = element;
		
	}
	public String getType() {
		return TYPE;
	}
	
	public Map<String, EditorProperty> getProperties() {
		Map<String, EditorProperty> properties = super.getProperties();
		List<String> buttonSizevalues = new LinkedList<>();
		List<ButtonSize> buttonSizes = Arrays.asList(ButtonSize.values());
		buttonSizes.forEach(element -> buttonSizevalues.add(element.toString()));
		properties.put("size", new EditorProperty<String>("size", Datatype.ENUM, buttonSizevalues, ButtonSize.SMALL.toString()));
		
		List<String> buttonStyleValues = new LinkedList<>();
		List<StyleType> buttonStyles = Arrays.asList(StyleType.values());
		buttonStyles.forEach(element -> buttonStyleValues.add(element.toString()));
		properties.put("style", new EditorProperty<String>("style", Datatype.ENUM, buttonStyleValues, StyleType.DANGER.toString()));
		
		return properties;
	}
	@Override
	public void setProperty(String key, String newValue) {
		if(key.equals("size")) {
			element.setSize(ButtonSize.valueOf(newValue));
		}
		if(key.equals("style")) {
			element.setButtonType(StyleType.valueOf(newValue));
		}
	}
	
	@Override
	public String getLabel() {
		return ((Button)getElement()).getTextContent();
	}

	@Override
	public void setLabel(String label) {
		((Button)getElement()).setTextContent(label);
	}
	
	@Override
	public String toSourcecode() {
		StringBuilder sb = new StringBuilder();
		sb.append("Button "+getName()+" = Button.create(\""+getLabel()+"\");\n");
		return sb.toString();
	}
		
	
}

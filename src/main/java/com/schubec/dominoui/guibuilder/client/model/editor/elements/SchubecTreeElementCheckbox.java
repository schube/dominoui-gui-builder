package com.schubec.dominoui.guibuilder.client.model.editor.elements;
import org.dominokit.domino.ui.forms.CheckBox;

import com.schubec.dominoui.guibuilder.client.model.ElementCounter;

public class SchubecTreeElementCheckbox extends SchubecTreeElement {
	public static final String TYPE = "CheckBox";
	public SchubecTreeElementCheckbox(CheckBox element) {
		super(element, "checkbox_"+ ElementCounter.get());
	}

	@Override
	public String getLabel() {
		return ((CheckBox)getElement()).getTextContent();
	}
	public String getType() {
		return TYPE;
	}
	@Override
	public void setLabel(String label) {
		((CheckBox)getElement()).setTextContent(label);
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Button with ID " + element.getDominoId() + "\n");
		return sb.toString();
	}
	@Override
	public String toSourcecode() {
		StringBuilder sb = new StringBuilder();
		sb.append("Chip "+getName()+" = Chip.create(\""+getLabel()+"\");\n");
		return sb.toString();
	}
}

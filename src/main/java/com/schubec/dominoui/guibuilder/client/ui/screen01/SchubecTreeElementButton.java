package com.schubec.dominoui.guibuilder.client.ui.screen01;
import org.dominokit.domino.ui.button.Button;

import com.schubec.dominoui.guibuilder.client.model.UUID;

public class SchubecTreeElementButton extends SchubecTreeElement {

	public SchubecTreeElementButton(Button element) {
		super(element, "button_"+ UUID.get());
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
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Button with ID " + element.getDominoId() + "\n");
		return sb.toString();
	}
	@Override
	public String toSourcecode() {
		StringBuilder sb = new StringBuilder();
		sb.append("Button "+getName()+" = Button.create(\""+getLabel()+"\");\n");
		return sb.toString();
	}
}

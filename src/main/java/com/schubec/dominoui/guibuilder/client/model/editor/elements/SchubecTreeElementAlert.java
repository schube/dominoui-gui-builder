package com.schubec.dominoui.guibuilder.client.model.editor.elements;
import org.dominokit.domino.ui.alerts.Alert;

import com.schubec.dominoui.guibuilder.client.model.ElementCounter;

public class SchubecTreeElementAlert extends SchubecTreeElement {
	public static final String TYPE = "Alert";
	public SchubecTreeElementAlert(Alert element) {
		super(element, "badge_"+ ElementCounter.get());
	}
	public String getType() {
		return TYPE;
	}
	@Override
	public String getLabel() {
		return ((Alert)getElement()).getTextContent();
	}

	@Override
	public void setLabel(String label) {
		((Alert)getElement()).setTextContent(label);
	}
	
	@Override
	public String toSourcecode() {
		StringBuilder sb = new StringBuilder();
		sb.append("Alert "+getName()+" = Alert.create(\""+getLabel()+"\");\n");
		return sb.toString();
	}
	

	
}

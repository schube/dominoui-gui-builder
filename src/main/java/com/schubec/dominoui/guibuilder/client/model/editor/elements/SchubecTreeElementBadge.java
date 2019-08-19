package com.schubec.dominoui.guibuilder.client.model.editor.elements;
import org.dominokit.domino.ui.badges.Badge;
import org.dominokit.domino.ui.utils.BaseDominoElement;

import com.schubec.dominoui.guibuilder.client.model.ElementCounter;
import com.schubec.dominoui.guibuilder.client.model.UUID;

public class SchubecTreeElementBadge extends SchubecTreeElement {
	public static final String TYPE = "Badge";
	public SchubecTreeElementBadge(Badge element) {
		super(element, "badge_"+ ElementCounter.get());
	}
	public String getType() {
		return TYPE;
	}
	@Override
	public String getLabel() {
		return ((Badge)getElement()).getTextContent();
	}

	@Override
	public void setLabel(String label) {
		((Badge)getElement()).setText(label);
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Badge with ID " + element.getDominoId() + "\n");
		return sb.toString();
	}
	@Override
	public String toSourcecode() {
		StringBuilder sb = new StringBuilder();
		sb.append("Badge "+getName()+" = Badge.create(\""+getLabel()+"\");\n");
		return sb.toString();
	}
	
	@Override
	public BaseDominoElement createInstance() {
		return Badge.create("New Badge");
	}
	
	
}

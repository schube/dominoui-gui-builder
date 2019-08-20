package com.schubec.dominoui.guibuilder.client.model.editor.elements;

import org.dominokit.domino.ui.utils.DominoElement;

import elemental2.dom.HTMLDivElement;

public class SchubecTreeElementCardHeader extends SchubecTreeElement {

	public static final String TYPE = "CardHeader";
	public SchubecTreeElementCardHeader(DominoElement<HTMLDivElement> element) {
		super(element, null);
		setRemoveable(false);
		setHasSourcecode(false);
	}
	public String getType() {
		return TYPE;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Cardheader with ID " + element.getDominoId() + "\n");
		return sb.toString();
	}

}

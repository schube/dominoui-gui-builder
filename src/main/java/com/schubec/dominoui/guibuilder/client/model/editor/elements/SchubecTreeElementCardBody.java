package com.schubec.dominoui.guibuilder.client.model.editor.elements;

import org.dominokit.domino.ui.utils.DominoElement;

import elemental2.dom.HTMLDivElement;

public class SchubecTreeElementCardBody extends SchubecTreeElement {

	public static final String TYPE = "CardBody";
	public SchubecTreeElementCardBody(DominoElement<HTMLDivElement> element) {
		super(element, null);
		setRemoveable(false);
		
	}
	public String getType() {
		return TYPE;
	}
	

}

package com.schubec.dominoui.guibuilder.client.model.editor.elements;

import org.dominokit.domino.ui.utils.DominoElement;

import elemental2.dom.HTMLElement;

public class SchubecTreeElementCardHeaderDescription extends SchubecTreeElement {

	public static final String TYPE = "CardHeaderDescription";
	public SchubecTreeElementCardHeaderDescription(DominoElement<HTMLElement> dominoElement) {
		super(dominoElement, null);
		setRemoveable(false);
		setHasSourcecode(false);
	}
	public String getType() {
		return TYPE;
	}
	
}

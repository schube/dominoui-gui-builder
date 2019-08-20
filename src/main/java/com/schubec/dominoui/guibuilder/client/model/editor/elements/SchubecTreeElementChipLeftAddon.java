package com.schubec.dominoui.guibuilder.client.model.editor.elements;

import org.dominokit.domino.ui.utils.DominoElement;

import elemental2.dom.HTMLDivElement;

public class SchubecTreeElementChipLeftAddon extends SchubecTreeElement {

	public static final String TYPE = "LeftAddon";
	public SchubecTreeElementChipLeftAddon(DominoElement<HTMLDivElement> dominoElement) {
		super(dominoElement, null);
		setRemoveable(false);
		setHasSourcecode(false);
	}
	public String getType() {
		return TYPE;
	}
	
}

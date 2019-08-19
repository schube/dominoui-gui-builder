package com.schubec.dominoui.guibuilder.client.model.editor.elements;

import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.utils.DominoElement;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;

public class SchubecTreeElementCardHeaderDescription extends SchubecTreeElement {

	public SchubecTreeElementCardHeaderDescription(DominoElement<HTMLElement> dominoElement) {
		super(dominoElement, "div");
		setRemoveable(false);
		setHasSourcecode(false);
	}
	public String getType() {
		return "CardHeaderDescription";
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CardHeaderDescription with ID " + element.getDominoId() + "\n");
		return sb.toString();
	}

}

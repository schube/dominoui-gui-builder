package com.schubec.dominoui.guibuilder.client.model.editor.elements;

import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.utils.DominoElement;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLUListElement;

public class SchubecTreeElementCardHeaderBar extends SchubecTreeElement {

	public static final String TYPE = "CardHeaderBar";
	public SchubecTreeElementCardHeaderBar(DominoElement<HTMLUListElement> dominoElement) {
		super(dominoElement, null);
		setRemoveable(false);
		setHasSourcecode(false);
	}
	public String getType() {
		return TYPE;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CardHeaderBar with ID " + element.getDominoId() + "\n");
		return sb.toString();
	}

}

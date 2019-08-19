package com.schubec.dominoui.guibuilder.client.ui.screen01;

import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.utils.DominoElement;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLHeadingElement;

public class SchubecTreeElementCardHeaderTitle extends SchubecTreeElement {

	public SchubecTreeElementCardHeaderTitle(DominoElement<HTMLHeadingElement> dominoElement) {
		super(dominoElement, "div");
		setRemoveable(false);
		setHasSourcecode(false);
	}
	public String getType() {
		return "CardHeaderTitle";
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CardHeaderTitle with ID " + element.getDominoId() + "\n");
		return sb.toString();
	}

}

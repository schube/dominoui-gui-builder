package com.schubec.dominoui.guibuilder.client.ui.screen01;

import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.utils.DominoElement;

import elemental2.dom.HTMLDivElement;

public class SchubecTreeElementCardHeader extends SchubecTreeElement {

	public SchubecTreeElementCardHeader(DominoElement<HTMLDivElement> element) {
		super(element, "div");
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Cardheader with ID " + element.getDominoId() + "\n");
		return sb.toString();
	}

}

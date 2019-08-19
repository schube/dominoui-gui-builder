package com.schubec.dominoui.guibuilder.client.ui.screen01;

import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.utils.DominoElement;

import elemental2.dom.HTMLDivElement;

public class SchubecTreeElementCardBody extends SchubecTreeElement {

	public SchubecTreeElementCardBody(DominoElement<HTMLDivElement> element) {
		super(element, "div");
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CardBody with ID " + element.getDominoId() + "\n");
		return sb.toString();
	}

}

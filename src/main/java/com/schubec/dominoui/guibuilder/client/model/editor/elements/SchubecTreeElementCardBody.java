package com.schubec.dominoui.guibuilder.client.model.editor.elements;

import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.utils.DominoElement;

import com.schubec.dominoui.guibuilder.client.model.ElementCounter;

import elemental2.dom.HTMLDivElement;

public class SchubecTreeElementCardBody extends SchubecTreeElement {

	public SchubecTreeElementCardBody(DominoElement<HTMLDivElement> element) {
		super(element, "div_" + ElementCounter.get());
		setRemoveable(false);
		
	}
	public String getType() {
		return "CardBody";
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CardBody with ID " + element.getDominoId() + "\n");
		return sb.toString();
	}

}

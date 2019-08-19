package com.schubec.dominoui.guibuilder.client.model.editor.elements;

import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.utils.DominoElement;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLHeadingElement;

public class SchubecTreeElementCardHeaderTitleText extends SchubecTreeElement {

	public SchubecTreeElementCardHeaderTitleText(DominoElement<HTMLHeadingElement> dominoElement) {
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

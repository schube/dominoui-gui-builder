package com.schubec.dominoui.guibuilder.client.model.editor.elements;

import org.dominokit.domino.ui.utils.DominoElement;

import elemental2.dom.HTMLHeadingElement;

public class SchubecTreeElementCardHeaderTitleText extends SchubecTreeElement {

	public SchubecTreeElementCardHeaderTitleText(DominoElement<HTMLHeadingElement> dominoElement) {
		super(dominoElement, null);
		setRemoveable(false);
		setHasSourcecode(false);
	}
	public String getType() {
		return "CardHeaderTitle";
	}
	

}

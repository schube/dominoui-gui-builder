package com.schubec.dominoui.guibuilder.client.model.editor.elements;
import org.dominokit.domino.ui.utils.DominoElement;

import com.schubec.dominoui.guibuilder.client.model.ElementCounter;

import elemental2.dom.HTMLHeadingElement;

public class SchubecTreeElementHTMLHeadingElement extends SchubecTreeElement {
	
	DominoElement<HTMLHeadingElement> element;
	


	public static final String TYPE = "h";
	public SchubecTreeElementHTMLHeadingElement(DominoElement<HTMLHeadingElement> h) {
		super(h, "h_"+ ElementCounter.get());
		this.element = h;
	}

	public String getType() {
		return TYPE;
	}
	@Override
	public String getLabel() {
		return element.getTextContent();
	}

	@Override
	public void setLabel(String label) {
		element.setTextContent(label);
	}
}

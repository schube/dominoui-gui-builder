package com.schubec.dominoui.guibuilder.client.model.editor.elements;
import org.dominokit.domino.ui.utils.DominoElement;

import com.schubec.dominoui.guibuilder.client.model.ElementCounter;
import com.schubec.dominoui.guibuilder.client.model.UUID;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLHeadingElement;

public class SchubecTreeElementHTMLHeadingElement extends SchubecTreeElement {
	public static final String TYPE = "h";
	public SchubecTreeElementHTMLHeadingElement(DominoElement<HTMLHeadingElement> h) {
		super(h, "h_"+ ElementCounter.get());
	}

	public String getType() {
		return TYPE;
	}
	
}

package com.schubec.dominoui.guibuilder.client.model.editor.elements;
import org.dominokit.domino.ui.utils.DominoElement;

import com.schubec.dominoui.guibuilder.client.model.ElementCounter;

import elemental2.dom.HTMLDivElement;

public class SchubecTreeElementDiv extends SchubecTreeElement {
	public static final String TYPE = "div";
	public SchubecTreeElementDiv(DominoElement<HTMLDivElement> div) {
		super(div, "div_"+ ElementCounter.get());
	}

	public String getType() {
		return TYPE;
	}

	@Override
	public String toSourcecode() {
		StringBuilder sb = new StringBuilder();
		sb.append("DominoElement<HTMLDivElement> "+getName()+" = DominoElement.div();\n");
		return sb.toString();
	}
}

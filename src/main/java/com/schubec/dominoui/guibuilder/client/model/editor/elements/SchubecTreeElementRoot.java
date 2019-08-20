package com.schubec.dominoui.guibuilder.client.model.editor.elements;
import org.dominokit.domino.ui.utils.DominoElement;

import com.schubec.dominoui.guibuilder.client.model.ElementCounter;

import elemental2.dom.HTMLDivElement;

public class SchubecTreeElementRoot extends SchubecTreeElement {
	public static final String TYPE = "root";
	public SchubecTreeElementRoot(DominoElement<HTMLDivElement> div) {
		super(div, null);
		setRemoveable(false);
	}

	public String getType() {
		return TYPE;
	}

	@Override
	public String toSourcecode() {
		StringBuilder sb = new StringBuilder();
		sb.append("DominoElement<HTMLDivElement> root = DominoElement.div();\n");
		return sb.toString();
	}
}

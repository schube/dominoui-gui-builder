package com.schubec.dominoui.guibuilder.client.ui.screen01;
import org.dominokit.domino.ui.utils.DominoElement;

import com.schubec.dominoui.guibuilder.client.model.UUID;

import elemental2.dom.HTMLDivElement;

public class SchubecTreeElementDiv extends SchubecTreeElement {

	public SchubecTreeElementDiv(DominoElement<HTMLDivElement> div) {
		super(div, "div_"+ UUID.get());
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("DIV with ID " + element.getDominoId() + "\n");
		return sb.toString();
	}
	@Override
	public String toSourcecode() {
		StringBuilder sb = new StringBuilder();
		sb.append("DominoElement<HTMLDivElement> "+getName()+" = DominoElement.div();\n");
		return sb.toString();
	}
}

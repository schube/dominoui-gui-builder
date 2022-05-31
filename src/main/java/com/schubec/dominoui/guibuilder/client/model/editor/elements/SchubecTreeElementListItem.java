package com.schubec.dominoui.guibuilder.client.model.editor.elements;

import org.dominokit.domino.ui.utils.BaseDominoElement;

import com.schubec.dominoui.guibuilder.client.model.ElementCounter;

public class SchubecTreeElementListItem extends SchubecTreeElement {
	private BaseDominoElement element;
	public static final String TYPE = "SimpleListItem";

	public SchubecTreeElementListItem(BaseDominoElement element) {
		super(element, "button_" + ElementCounter.get());
		this.element = element;

	}

	public String getType() {
		return TYPE;
	}

	@Override
	public String toSourcecode() {
		StringBuilder sb = new StringBuilder();
		sb.append("Button " + getName() + " = Button.create(\"" + getLabel() + "\");\n");
		return sb.toString();
	}
}

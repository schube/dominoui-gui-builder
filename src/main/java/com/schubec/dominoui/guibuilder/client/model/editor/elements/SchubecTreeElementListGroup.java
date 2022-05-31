package com.schubec.dominoui.guibuilder.client.model.editor.elements;

import org.dominokit.domino.ui.lists.ListGroup;

import com.schubec.dominoui.guibuilder.client.model.ElementCounter;

public class SchubecTreeElementListGroup extends SchubecTreeElement {
	private ListGroup element;
	public static final String TYPE = "SimpleListGroup";

	public SchubecTreeElementListGroup(ListGroup element) {
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

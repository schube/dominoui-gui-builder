package com.schubec.dominoui.guibuilder.client.model.editor.elements;
import org.dominokit.domino.ui.tabs.Tab;

import com.schubec.dominoui.guibuilder.client.model.ElementCounter;

public class SchubecTreeElementTab extends SchubecTreeElement {
	public static final String TYPE = "Tab";
	public SchubecTreeElementTab(Tab element) {
		super(element, "tab_"+ ElementCounter.get());
	}

	public String getType() {
		return TYPE;
	}

	@Override
	public String toSourcecode() {
		StringBuilder sb = new StringBuilder();
		sb.append("Tab "+getName()+" = Tab.create(\"New Tab\");\n");
		return sb.toString();
	}
}

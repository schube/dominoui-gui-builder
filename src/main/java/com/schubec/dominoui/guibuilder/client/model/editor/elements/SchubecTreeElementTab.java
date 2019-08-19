package com.schubec.dominoui.guibuilder.client.model.editor.elements;
import org.dominokit.domino.ui.tabs.Tab;
import org.dominokit.domino.ui.tabs.TabsPanel;

import com.schubec.dominoui.guibuilder.client.model.ElementCounter;
import com.schubec.dominoui.guibuilder.client.model.UUID;

public class SchubecTreeElementTab extends SchubecTreeElement {
	public static final String TYPE = "Tab";
	public SchubecTreeElementTab(Tab element) {
		super(element, "tab_"+ ElementCounter.get());
	}

	public String getType() {
		return TYPE;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Tab with ID " + element.getDominoId() + "\n");
		return sb.toString();
	}
	@Override
	public String toSourcecode() {
		StringBuilder sb = new StringBuilder();
		sb.append("Tab "+getName()+" = Tab.create();\n");
		return sb.toString();
	}
}

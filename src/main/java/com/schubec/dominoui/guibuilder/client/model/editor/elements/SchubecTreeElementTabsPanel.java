package com.schubec.dominoui.guibuilder.client.model.editor.elements;
import org.dominokit.domino.ui.tabs.TabsPanel;

import com.schubec.dominoui.guibuilder.client.model.ElementCounter;
import com.schubec.dominoui.guibuilder.client.model.UUID;

public class SchubecTreeElementTabsPanel extends SchubecTreeElement {
	public static final String TYPE = "TabsPanel";
	
	
	public SchubecTreeElementTabsPanel(TabsPanel element) {
		super(element, "tabsPanel_"+ ElementCounter.get());
	}
	@Override
	public String getType() {
		return TYPE;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("TabsPanel with ID " + element.getDominoId() + "\n");
		return sb.toString();
	}
	@Override
	public String toSourcecode() {
		StringBuilder sb = new StringBuilder();
		sb.append("TabsPanel "+getName()+" = TabsPanel.create();\n");
		return sb.toString();
	}
}

package com.schubec.dominoui.guibuilder.client.ui.screen01;

import java.util.List;

import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.tree.TreeItem;
import org.dominokit.domino.ui.utils.BaseDominoElement;

import com.schubec.dominoui.guibuilder.client.model.UUID;

public class SchubecTreeElement {
	
	BaseDominoElement element;
	private String name = null;

	public SchubecTreeElement(BaseDominoElement element, String name) {
		this.element = element;
		this.name = name;
	}

	public BaseDominoElement getElement() {
		return element;
	}
	
	
	public String getLabel() {
		return "<not implemented>";
	};
	public void setLabel(String label) {
		
	};
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("BaseDominoElement with ID " + element.getDominoId());
		return sb.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toSourcecode() {
		return "";
	}
	
	
}

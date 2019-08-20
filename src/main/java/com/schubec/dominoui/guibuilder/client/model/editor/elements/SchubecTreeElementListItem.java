package com.schubec.dominoui.guibuilder.client.model.editor.elements;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.button.ButtonSize;
import org.dominokit.domino.ui.lists.SimpleListItem;
import org.dominokit.domino.ui.style.StyleType;

import com.schubec.dominoui.guibuilder.client.model.ElementCounter;
import com.schubec.dominoui.guibuilder.client.ui.screen01.Datatype;
import com.schubec.dominoui.guibuilder.client.ui.screen01.EditorProperty;

public class SchubecTreeElementListItem extends SchubecTreeElement {
	private SimpleListItem element; 
	public static final String TYPE = "SimpleListItem";
	
	public SchubecTreeElementListItem(SimpleListItem element) {
		super(element, "button_"+ ElementCounter.get());
		this.element = element;
		
	}
	public String getType() {
		return TYPE;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Button with ID " + element.getDominoId() + "\n");
		return sb.toString();
	}
	@Override
	public String toSourcecode() {
		StringBuilder sb = new StringBuilder();
		sb.append("Button "+getName()+" = Button.create(\""+getLabel()+"\");\n");
		return sb.toString();
	}
}

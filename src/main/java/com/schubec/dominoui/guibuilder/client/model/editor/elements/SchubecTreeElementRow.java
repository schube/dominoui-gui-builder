package com.schubec.dominoui.guibuilder.client.model.editor.elements;
import org.dominokit.domino.ui.badges.Badge;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.utils.BaseDominoElement;

import com.schubec.dominoui.guibuilder.client.model.ElementCounter;
import com.schubec.dominoui.guibuilder.client.model.UUID;

public class SchubecTreeElementRow extends SchubecTreeElement {
	public static final String TYPE = "Row";
	public SchubecTreeElementRow(Row element) {
		super(element, "row_"+ ElementCounter.get());
	}
	public String getType() {
		return TYPE;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Row with ID " + element.getDominoId() + "\n");
		return sb.toString();
	}
	@Override
	public String toSourcecode() {
		StringBuilder sb = new StringBuilder();
		sb.append("Badge "+getName()+" = Badge.create(\""+getLabel()+"\");\n");
		return sb.toString();
	}
	
	@Override
	public BaseDominoElement createInstance() {
		return Badge.create("New Badge");
	}
	
	
}

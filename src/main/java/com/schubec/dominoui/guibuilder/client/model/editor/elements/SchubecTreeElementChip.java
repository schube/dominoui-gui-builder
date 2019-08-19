package com.schubec.dominoui.guibuilder.client.model.editor.elements;
import org.dominokit.domino.ui.chips.Chip;

import com.schubec.dominoui.guibuilder.client.model.ElementCounter;
import com.schubec.dominoui.guibuilder.client.model.UUID;

public class SchubecTreeElementChip extends SchubecTreeElement {
	public static final String TYPE = "Chip";
	public SchubecTreeElementChip(Chip element) {
		super(element, "chip_"+ ElementCounter.get());
	}

	@Override
	public String getLabel() {
		return ((Chip)getElement()).getValue();
	}
	public String getType() {
		return TYPE;
	}
	@Override
	public void setLabel(String label) {
		((Chip)getElement()).setValue(label);
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
		sb.append("Chip "+getName()+" = Chip.create(\""+getLabel()+"\");\n");
		return sb.toString();
	}
}

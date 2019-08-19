package com.schubec.dominoui.guibuilder.client.model.editor.elements;
import java.util.LinkedList;
import java.util.List;

import org.dominokit.domino.ui.cards.Card;

import com.schubec.dominoui.guibuilder.client.model.ElementCounter;
import com.schubec.dominoui.guibuilder.client.model.UUID;

import elemental2.dom.HTMLDivElement;

public class SchubecTreeElementCard extends SchubecTreeElement {
	public static final String TYPE = "Card";

	
	public SchubecTreeElementCard(Card element, String name) {
		super(element, name);
	}
	public SchubecTreeElementCard(Card element) {
		super(element, "card_"+ElementCounter.get());
	}
	public String getType() {
		return TYPE;
	}
	@Override
	public String getLabel() {
		return ((Card)getElement()).getHeaderTitle().getTextContent();
	}

	@Override
	public void setLabel(String label) {
		((Card)getElement()).setTitle(label);
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Card with ID " + element.getDominoId() + "\n");
		sb.append("Card Header with ID " + ((Card)getElement()).getHeader().getDominoId() + "\n");
		sb.append("Card Body with ID " + ((Card)getElement()).getBody().getDominoId() + "\n");
		return sb.toString();
	}
	
	@Override
	public String toSourcecode() {
		StringBuilder sb = new StringBuilder();
		sb.append("Card "+getName()+" = Card.create(\""+getLabel()+"\");\n");
		return sb.toString();
	}
	
	
}

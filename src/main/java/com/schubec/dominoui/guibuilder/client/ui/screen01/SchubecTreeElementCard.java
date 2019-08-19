package com.schubec.dominoui.guibuilder.client.ui.screen01;
import java.util.LinkedList;
import java.util.List;

import org.dominokit.domino.ui.cards.Card;

import com.schubec.dominoui.guibuilder.client.model.UUID;

import elemental2.dom.HTMLDivElement;

public class SchubecTreeElementCard extends SchubecTreeElement {

	private SchubecTreeElementCardHeader header;
	private SchubecTreeElementCardBody body;
	
	public SchubecTreeElementCard(Card element, String name) {
		super(element, name);
	}
	public SchubecTreeElementCard(Card element) {
		super(element, "card_"+UUID.get());
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
		List<HTMLDivElement> children = new LinkedList<>();
		children.forEach(child -> sb.append(getName()+".appendChild();\n"));
		
		sb.append(getName()+".getHeader().appendChild();\n");
		return sb.toString();
	}
	public SchubecTreeElementCardHeader getHeader() {
		return header;
	}
	public void setHeader(SchubecTreeElementCardHeader header) {
		this.header = header;
	}
	public SchubecTreeElementCardBody getBody() {
		return body;
	}
	public void setBody(SchubecTreeElementCardBody body) {
		this.body = body;
	}
	
	
}

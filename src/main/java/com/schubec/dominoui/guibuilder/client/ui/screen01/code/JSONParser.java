package com.schubec.dominoui.guibuilder.client.ui.screen01.code;

import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.tree.TreeItem;
import org.dominokit.domino.ui.utils.BaseDominoElement;

import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElement;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementCard;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementCardBody;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementCardHeader;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementCardHeaderBar;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementCardHeaderDescription;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementCardHeaderTitle;
import com.schubec.dominoui.guibuilder.client.ui.screen01.Factory;

import elemental2.core.JsArray;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

public class JSONParser {
	private final Factory factory;
	private BaseDominoElement currentParseObject;
	private JSONParser parser;
	
	public JSONParser(Factory factory) {
		this.factory = factory;
		this.parser = this;
	}
	
	
	public void parseChildren(BaseDominoElement parentElement, JsPropertyMap<Object> child) {
		String type = (String)child.get("type");
		String name = (String)child.get("name");
		TreeItem<SchubecTreeElement> newElement = factory.createElementAndAddToCanvas(parentElement, type, false);
		BaseDominoElement appendToElement = null;
		if(newElement != null) {
			newElement.getValue().setName(name);
			appendToElement = newElement.getValue().getElement();
			
			currentParseObject = appendToElement;
			
		}
		if(type.equals(SchubecTreeElementCard.TYPE)) {
			parser = new JSONParser(factory);
			parser.currentParseObject = currentParseObject;
		} else if(type.equals(SchubecTreeElementCardBody.TYPE)) {
				appendToElement = currentParseObject;
		} else if(type.equals(SchubecTreeElementCardHeader.TYPE)) {
				appendToElement = ((Card)currentParseObject).getHeader();
		} else if(type.equals(SchubecTreeElementCardHeaderBar.TYPE)) {
				appendToElement = ((Card)currentParseObject).getHeaderBar();
		} else if(type.equals(SchubecTreeElementCardHeaderDescription.TYPE)) {
				appendToElement = ((Card)currentParseObject).getHeaderDescription();
		} else if(type.equals(SchubecTreeElementCardHeaderTitle.TYPE)) {
				appendToElement = ((Card)currentParseObject).getHeaderTitle();
		}
		JsArray<JsPropertyMap<Object>> children = Js.uncheckedCast(child.get("children"));
		if(children!=null) {
			for(JsPropertyMap<Object> subchild : children.asList()) {
				
				parser.parseChildren(appendToElement, subchild);
			}
		}
		
	}
}
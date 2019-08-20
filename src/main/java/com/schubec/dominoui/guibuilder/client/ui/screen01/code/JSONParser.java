package com.schubec.dominoui.guibuilder.client.ui.screen01.code;

import java.util.Map;

import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.chips.Chip;
import org.dominokit.domino.ui.tree.TreeItem;
import org.dominokit.domino.ui.utils.BaseDominoElement;

import com.google.gwt.core.client.GWT;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElement;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementCard;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementCardBody;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementCardHeader;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementCardHeaderBar;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementCardHeaderDescription;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementCardHeaderTitle;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementChip;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementChipLeftAddon;
import com.schubec.dominoui.guibuilder.client.ui.screen01.Factory;

import elemental2.core.JsArray;
import jsinterop.base.Js;
import jsinterop.base.JsForEachCallbackFn;
import jsinterop.base.JsPropertyMap;

public class JSONParser {
	private final Factory factory;
	private BaseDominoElement currentParseObject;
	private JSONParser parser;
	
	
	public JSONParser(Factory factory) {
		this.factory = factory;
		this.parser = this;

	}
	
	//name / sourcecode
	public SourcecodeResult parseChildren(BaseDominoElement parentElement, JsPropertyMap<Object> child) {
		SourcecodeResult sourcecode =  new SourcecodeResult();		
		String type = (String)child.get("type");
		String name = (String)child.get("name");
		String label = (String)child.get("label");
		TreeItem<SchubecTreeElement> newElement = factory.createElementAndAddToCanvas(parentElement, type, false);
		JsArray<Object> properties = (JsArray<Object>)child.get("properties");
		if(properties!=null) {
			for(Object o : properties.asList()) {
				JsPropertyMap<Object> p = (JsPropertyMap<Object>)o;
				p.forEach(new JsForEachCallbackFn() {
					
					@Override
					public void onKey(String key) {
						Object value = p.get(key);
						if(value instanceof String) {
							GWT.log("String");
							newElement.getValue().setProperty(key, (String)value);
						}
						if(value instanceof Boolean) {
							GWT.log("Boolean");
							newElement.getValue().setProperty(key, (Boolean)value);
						}
						if(value instanceof Integer) {
							GWT.log("Integer");
							newElement.getValue().setProperty(key, (Integer)value);
						}
						
					}
				});
			}
		}
		
		BaseDominoElement appendToElement = null;
		if(newElement != null) {
			
			newElement.getValue().setName(name);
			newElement.getValue().setLabel(label);
			appendToElement = newElement.getValue().getElement();
			if(newElement.getValue().hasSourcecode()) {
				sourcecode.setName(name);
				sourcecode.appendSourcecode(newElement.getValue().toSourcecode());				
			}
			
			
		}
		boolean createNewParser = false;
		if(type.equals(SchubecTreeElementCard.TYPE)) {
			currentParseObject = appendToElement;
			createNewParser=true; //Card with SubElements needs new Parser
		}else if(type.equals(SchubecTreeElementChip.TYPE)) {
			currentParseObject = appendToElement;
			createNewParser=true; //Chip with LeftAddon needs new Parser
		} else if(type.equals(SchubecTreeElementChipLeftAddon.TYPE)) {
			appendToElement = ((Chip)currentParseObject).getLeftAddonContainer();			
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
				if(createNewParser) {
					parser = new JSONParser(factory);
					parser.currentParseObject = currentParseObject;
				}
				SourcecodeResult sourcecodeResult = parser.parseChildren(appendToElement, subchild);
				sourcecode.appendSourcecode(sourcecodeResult.getSourcecode());
				if(name!=null && sourcecodeResult.getName()!=null) {
					sourcecode.appendSourcecode(name + ".appendChild("+sourcecodeResult.getName()+");\n");
				}
			}
		}
		return sourcecode;
		
	}
}

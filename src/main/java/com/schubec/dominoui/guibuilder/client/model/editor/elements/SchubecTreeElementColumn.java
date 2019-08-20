package com.schubec.dominoui.guibuilder.client.model.editor.elements;
import java.util.Map;

import org.dominokit.domino.ui.badges.Badge;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Column.OnLarge;
import org.dominokit.domino.ui.grid.Column.OnXLarge;
import org.dominokit.domino.ui.utils.BaseDominoElement;

import com.schubec.dominoui.guibuilder.client.model.ElementCounter;
import com.schubec.dominoui.guibuilder.client.ui.screen01.Datatype;
import com.schubec.dominoui.guibuilder.client.ui.screen01.EditorProperty;

public class SchubecTreeElementColumn extends SchubecTreeElement {
	public static final String TYPE = "Column";
	private Column element; 
	public SchubecTreeElementColumn(Column element) {
		super(element, "column_"+ ElementCounter.get());
		this.element = element;
	}
	public String getType() {
		return TYPE;
	}

	public Map<String, EditorProperty> getProperties() {
		Map<String, EditorProperty> properties = super.getProperties();
		properties.put("spanOnXLarge", new EditorProperty<Integer>("spanOnXLarge", Datatype.INTEGER, 1));
		properties.put("spanOnLarge", new EditorProperty<Integer>("spanOnLarge", Datatype.INTEGER, 1));
		return properties;
	}
	@Override
	public void setProperty(String key, Integer newValue) {
		if(key.equals("spanOnXLarge")) {
			element.onXLarge(OnXLarge.of(newValue));
		}
		if(key.equals("spanOnLarge")) {
			element.onLarge(OnLarge.of(newValue));
		}
	}

	@Override
	public String toSourcecode() {
		StringBuilder sb = new StringBuilder();
		sb.append("Column "+getName()+" = Column.span();\n");
		return sb.toString();
	}
	

	
}

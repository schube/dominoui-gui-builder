package com.schubec.dominoui.guibuilder.client.ui.screen01;

import static org.jboss.gwt.elemento.core.Elements.h;

import org.dominokit.domino.ui.animations.Animation;
import org.dominokit.domino.ui.animations.Transition;
import org.dominokit.domino.ui.badges.Badge;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.chips.Chip;
import org.dominokit.domino.ui.forms.CheckBox;
import org.dominokit.domino.ui.forms.TextBox;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.lists.SimpleListGroup;
import org.dominokit.domino.ui.lists.SimpleListItem;
import org.dominokit.domino.ui.tabs.Tab;
import org.dominokit.domino.ui.tabs.TabsPanel;
import org.dominokit.domino.ui.tree.TreeItem;
import org.dominokit.domino.ui.utils.BaseDominoElement;
import org.dominokit.domino.ui.utils.DominoElement;
import org.jboss.gwt.elemento.core.EventType;

import com.google.gwt.core.client.GWT;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElement;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementBadge;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementButton;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementCard;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementCardBody;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementCardHeader;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementCardHeaderBar;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementCardHeaderDescription;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementCardHeaderTitle;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementCheckbox;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementChip;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementColumn;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementDiv;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementHTMLHeadingElement;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementListGroup;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementListItem;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementRow;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementTab;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementTabsPanel;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementTextBox;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLHeadingElement;

public class Factory {
	private final Treehelper treehelper;
	
	public Factory(Treehelper treehelper) {
		this.treehelper = treehelper;
	}
	
	public TreeItem<SchubecTreeElement> createElementAndAddToCanvas(BaseDominoElement parentElement, String type, boolean createHelperObjects) {
		switch (type) {
			case SchubecTreeElementListGroup.TYPE:
				return createListGroup(parentElement, createHelperObjects);
			case SchubecTreeElementListItem.TYPE:
				return createListItem(parentElement, createHelperObjects);
			case SchubecTreeElementTextBox.TYPE:
				return createTextbox(parentElement, createHelperObjects);
			case SchubecTreeElementColumn.TYPE:
				return createColumn(parentElement, createHelperObjects);
			case SchubecTreeElementRow.TYPE:
				return createRow(parentElement, createHelperObjects);
			case SchubecTreeElementButton.TYPE:
				return createButton(parentElement, createHelperObjects);
			case SchubecTreeElementCheckbox.TYPE:
				return createCheckbox(parentElement, createHelperObjects);
			case SchubecTreeElementChip.TYPE:
				return createChip(parentElement, createHelperObjects);
			case SchubecTreeElementBadge.TYPE:
				return createBadge(parentElement, createHelperObjects);
			case SchubecTreeElementTabsPanel.TYPE:
				return createTabsPanel(parentElement, createHelperObjects);
			case SchubecTreeElementTab.TYPE:
				return createTab(parentElement, createHelperObjects);
			case SchubecTreeElementDiv.TYPE:
				return createDiv(parentElement, createHelperObjects);
			case SchubecTreeElementHTMLHeadingElement.TYPE:
				return createH(parentElement, createHelperObjects);
			case SchubecTreeElementCard.TYPE:
				return createCard(parentElement, createHelperObjects);
		}
		GWT.log("Unable to create unkown type : " + type);
		return null;
	}
	private TreeItem createListGroup(BaseDominoElement parent, boolean createHelperObjects) {
		SimpleListGroup newElement = SimpleListGroup.create();
		x(newElement, parent);
		
			TreeItem newTreeItem = TreeItem.create("SimpleListGroup", new SchubecTreeElementListGroup(newElement));
			treehelper.find(parent.getDominoId(), parentTreeItem -> {
				parentTreeItem.appendChild(newTreeItem);	
			});
			if(createHelperObjects) {
				createListItem(newElement, true);
			}
			return newTreeItem;
		
	}
	private TreeItem createListItem(BaseDominoElement parent, boolean createHelperObjects) {
		SimpleListItem newElement = SimpleListItem.create("New SimpleListItem");
		x(newElement, parent);
		
			TreeItem newTreeItem = TreeItem.create("SimpleListItem", new SchubecTreeElementListItem(newElement));
			treehelper.find(parent.getDominoId(), parentTreeItem -> {
				parentTreeItem.appendChild(newTreeItem);	
			});
			return newTreeItem;
	}
	private TreeItem createChip(BaseDominoElement parent, boolean createHelperObjects) {
		Chip newElement = Chip.create("New Chip");
		x(newElement, parent);
		
			TreeItem newTreeItem = TreeItem.create("Chip", new SchubecTreeElementChip(newElement));
			treehelper.find(parent.getDominoId(), parentTreeItem -> {
				parentTreeItem.appendChild(newTreeItem);	
			});
			return newTreeItem;
	}

	private TreeItem createCard(BaseDominoElement parent, boolean createHelperObjects) {
		Card newElement = Card.create("New Card");
		addDnDHandlerFormElement(newElement.getHeader());
		addDnDHandlerFormElement(newElement.getBody());
		x(newElement, parent);

		

		TreeItem<SchubecTreeElement> newTreeItemCard = TreeItem.create("Card", new SchubecTreeElementCard(newElement));
		TreeItem<SchubecTreeElement> newTreeItemHeader = TreeItem.create("Header",
				new SchubecTreeElementCardHeader(newElement.getHeader()));
		addDnDHandler(newTreeItemHeader);
		newTreeItemCard.appendChild(newTreeItemHeader);

		TreeItem<SchubecTreeElement> newTreeItemHeaderBar = TreeItem.create("HeaderBar",
				new SchubecTreeElementCardHeaderBar(newElement.getHeaderBar()));
		addDnDHandler(newTreeItemHeaderBar);
		newTreeItemHeader.appendChild(newTreeItemHeaderBar);

		TreeItem<SchubecTreeElement> newTreeItemHeaderDescription = TreeItem.create("HeaderDescription",
				new SchubecTreeElementCardHeaderDescription(newElement.getHeaderDescription()));
		addDnDHandler(newTreeItemHeaderDescription);
		newTreeItemHeader.appendChild(newTreeItemHeaderDescription);

		TreeItem<SchubecTreeElement> newTreeItemHeaderTitle = TreeItem.create("HeaderTitle",
				new SchubecTreeElementCardHeaderTitle(newElement.getHeaderTitle()));
		addDnDHandler(newTreeItemHeaderTitle);
		newTreeItemHeader.appendChild(newTreeItemHeaderTitle);

		TreeItem<SchubecTreeElement> newTreeItemBody = TreeItem.create("Body",
				new SchubecTreeElementCardBody(newElement.getBody()));
		addDnDHandler(newTreeItemBody);
		newTreeItemCard.appendChild(newTreeItemBody);

		treehelper.find(parent.getDominoId(), parentTreeItem -> {
			parentTreeItem.appendChild(newTreeItemCard);	
		});
		return newTreeItemCard;
	}

	private TreeItem createBadge(BaseDominoElement parent, boolean createHelperObjects) {
		Badge newElement = Badge.create("New badge");
		x(newElement, parent);
		TreeItem newTreeItem = TreeItem.create("Badge", new SchubecTreeElementBadge(newElement));
		treehelper.find(parent.getDominoId(), parentTreeItem -> {
			parentTreeItem.appendChild(newTreeItem);	
		});
		return newTreeItem;
	}

	private TreeItem createTextbox(BaseDominoElement parent, boolean createHelperObjects) {
		TextBox newElement = TextBox.create("New Textbox");
		// addDndHandler(textbox);
		parent.appendChild(newElement);
		TreeItem newTreeItem = TreeItem.create("Textbox", new SchubecTreeElementTextBox(newElement));
		treehelper.find(parent.getDominoId(), parentTreeItem -> {
			parentTreeItem.appendChild(newTreeItem);	
		});
		return newTreeItem;
	}

	private TreeItem createCheckbox(BaseDominoElement parent, boolean createHelperObjects) {
		CheckBox newElement = CheckBox.create("New Button");
		x(newElement, parent);
		TreeItem newTreeItem = TreeItem.create("Checkbox", new SchubecTreeElementCheckbox(newElement));
		treehelper.find(parent.getDominoId(), parentTreeItem -> {
			parentTreeItem.appendChild(newTreeItem);	
		});
		return newTreeItem;
	}

	private TreeItem createButton(BaseDominoElement parent, boolean createHelperObjects) {
		Button newElement = Button.create("New Button");
		x(newElement, parent);
		TreeItem newTreeItem = TreeItem.create("Button", new SchubecTreeElementButton(newElement));
		treehelper.find(parent.getDominoId(), parentTreeItem -> {
			parentTreeItem.appendChild(newTreeItem);	
		});
		return newTreeItem;
	}

	private TreeItem createRow(BaseDominoElement parent, boolean createHelperObjects) {
		Row newElement = Row.create();
		newElement.style().setMinHeight("200px");
		x(newElement, parent);

		TreeItem newTreeItem = TreeItem.create("Row", new SchubecTreeElementRow(newElement));
		treehelper.find(parent.getDominoId(), parentTreeItem -> {
			parentTreeItem.appendChild(newTreeItem);	
		});
		initEditorElement2(newTreeItem, "Row");
		addDnDHandler(newTreeItem);
		if(createHelperObjects) {
		createColumn(newElement, true);
		}
		return newTreeItem;
	}

	private TreeItem createColumn(BaseDominoElement parent, boolean createHelperObjects) {
		Column newElement = Column.span();

		x(newElement, parent);

		TreeItem newTreeItem = TreeItem.create("Column", new SchubecTreeElementColumn(newElement));
		treehelper.find(parent.getDominoId(), parentTreeItem -> {
			parentTreeItem.appendChild(newTreeItem);	
		});
		initEditorElement2(newTreeItem, "Column");
		addDnDHandler(newTreeItem);
		// createDiv(column, "columBody");
		return newTreeItem;
	}

	private TreeItem createH(BaseDominoElement parent, boolean createHelperObjects) {
		DominoElement<HTMLHeadingElement> newElement = DominoElement.of(h(1));
		newElement.setTextContent("Title");
		x(newElement, parent);
		TreeItem<SchubecTreeElement> newTreeItem = TreeItem.create("Heading",
				new SchubecTreeElementHTMLHeadingElement(newElement));
		treehelper.find(parent.getDominoId(), parentTreeItem -> {
			parentTreeItem.appendChild(newTreeItem);	
		});
		addDnDHandler(newTreeItem);
		return newTreeItem;
	}

	private TreeItem createDiv(BaseDominoElement parent, boolean createHelperObjects) {
		return createDiv(parent, "div", createHelperObjects);
	}

	private TreeItem createDiv(BaseDominoElement parent, String label, boolean createHelperObjects) {
		DominoElement<HTMLDivElement> newElement = DominoElement.div();
		newElement.style().setMinHeight("200px");
		x(newElement, parent);
		TreeItem<SchubecTreeElement> newTreeItem = TreeItem.create(label, new SchubecTreeElementDiv(newElement));
		treehelper.find( parent.getDominoId(), parentTreeItem -> {
			parentTreeItem.appendChild(newTreeItem);
		});

		addDnDHandler(newTreeItem);
		return newTreeItem;
	}
	private void initEditorElement2(TreeItem element, String type) {
		element.asElement().draggable = true;
		element.addEventListener(EventType.dragstart, evt -> {
			((elemental2.dom.DragEvent) evt).dataTransfer.setData("text/plain", type);
		});
	}
	private TreeItem createTabsPanel(BaseDominoElement parent, boolean createHelperObjects) {
		TabsPanel newElement = TabsPanel.create();
		x(newElement, parent);
		TreeItem newTreeItem = TreeItem.create("TabsPanel", new SchubecTreeElementTabsPanel(newElement));
		treehelper.find(parent.getDominoId(), parentTreeItem -> {
			parentTreeItem.appendChild(newTreeItem);	
		});
		initEditorElement2(newTreeItem, "TabsPanel");
		addDnDHandlerFormElement(newTreeItem);
		if(createHelperObjects) {
			createTab(newElement, true);
		}
		return newTreeItem;
	}

	private TreeItem createTab(BaseDominoElement parent, boolean createHelperObjects) {
		Tab newElement = Tab.create("New Tab");
		x(newElement, parent);

		TreeItem newTreeItem = TreeItem.create("Tab", new SchubecTreeElementTab(newElement));
		treehelper.find(parent.getDominoId(), parentTreeItem -> {
			parentTreeItem.appendChild(newTreeItem);
			if (parentTreeItem.getValue().getType().equals("TabsPanel")) {
				((TabsPanel) parent).appendChild(newElement);
			} else {
				GWT.log("error");
				parent.appendChild(newElement);
			}
		});
		if(createHelperObjects) {
			createDiv(newElement, "TabBody", true);
		}
		return newTreeItem;

	}

	private void x(BaseDominoElement newElement, BaseDominoElement parent) {
		addDnDHandlerFormElement(newElement);
		//if(checkboxShowVisualHelpers.getValue()) {
			
		//}
		parent.appendChild(newElement);
		
	}
	private void addDnDHandler(TreeItem<SchubecTreeElement> treeItem) {
		treeItem.addEventListener(EventType.dragover, evt -> {
			Animation.create(treeItem.getValue().getElement()).transition(Transition.PULSE)
					.duration(100).animate();

			evt.preventDefault();
		});
		treeItem.addEventListener(EventType.drop, evt -> {
			evt.preventDefault();
			evt.stopImmediatePropagation();
			String type = ((elemental2.dom.DragEvent) evt).dataTransfer.getData("text/plain");
			createElementAndAddToCanvas(treeItem.getValue().getElement(), type, true);
		});
	}
	private void addDnDHandler2(TreeItem element) {
		element.addEventListener(EventType.dragover, evt -> {
			evt.preventDefault();
		});
		element.addEventListener(EventType.drop, evt -> {
			evt.preventDefault();
			evt.stopImmediatePropagation();
		});
	}

	

	
	
	



	void addDnDHandlerFormElement(BaseDominoElement element) {
		element.addEventListener(EventType.mouseover, evt -> {

		});
		element.addEventListener(EventType.mouseout, evt -> {

		});
		element.addEventListener(EventType.dragover, evt -> {
			evt.preventDefault();
		});
		element.addEventListener(EventType.drop, evt -> {
			evt.preventDefault();
			evt.stopImmediatePropagation();
			String type = ((elemental2.dom.DragEvent) evt).dataTransfer.getData("text/plain");
			createElementAndAddToCanvas(element, type, true);
		});
	}
}


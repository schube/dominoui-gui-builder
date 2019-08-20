package com.schubec.dominoui.guibuilder.client.ui.screen01;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import com.google.gwt.core.client.GWT;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLHeadingElement;
import elemental2.dom.NodeList.ForEachCallbackFn;
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
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementRow;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementTab;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementTabsPanel;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementTextBox;
import com.schubec.dominoui.guibuilder.client.ui.screen01.Datatype;
import com.schubec.dominoui.guibuilder.client.ui.screen01.code.CodeCard;

import static org.jboss.gwt.elemento.core.Elements.h;

import java.lang.Override;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.dominokit.domino.ui.animations.Animation;
import org.dominokit.domino.ui.animations.Transition;
import org.dominokit.domino.ui.badges.Badge;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.chips.Chip;
import org.dominokit.domino.ui.collapsible.Accordion;
import org.dominokit.domino.ui.collapsible.AccordionPanel;
import org.dominokit.domino.ui.dialogs.MessageDialog;
import org.dominokit.domino.ui.forms.CheckBox;
import org.dominokit.domino.ui.forms.IntegerBox;
import org.dominokit.domino.ui.forms.Select;
import org.dominokit.domino.ui.forms.SelectOption;
import org.dominokit.domino.ui.forms.TextBox;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.GridLayout;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.grid.SectionSpan;
import org.dominokit.domino.ui.icons.Icons;
import org.dominokit.domino.ui.notifications.Notification;
import org.dominokit.domino.ui.style.Color;
import org.dominokit.domino.ui.tabs.Tab;
import org.dominokit.domino.ui.tabs.TabsPanel;
import org.dominokit.domino.ui.tree.Tree;
import org.dominokit.domino.ui.tree.TreeItem;
import org.dominokit.domino.ui.utils.BaseDominoElement;
import org.dominokit.domino.ui.utils.DominoElement;
import org.dominokit.domino.ui.utils.TextNode;
import org.jboss.gwt.elemento.core.EventType;

public class Screen01Component extends AbstractComponent<IScreen01Component.Controller, HTMLElement>
		implements IScreen01Component {
	private Card cardCanvas;
	private Tree<SchubecTreeElement> elementsTree;
	private TextBox name;
	private TextBox label;
	private SchubecTreeElement selectedFormElement;
	private DominoElement<HTMLDivElement> root = DominoElement.div();
	private DominoElement<HTMLDivElement> extendedProperties = DominoElement.div();
	private AccordionPanel propertiesPanel;

	public Screen01Component() {
		super();
	}

	

	@Override
	public void render() {

		GridLayout gridLayout = initGridLayout();

		cardCanvas = Card.create("Canvas", "Place add elements with drag & drop to the canvas!");
		CheckBox checkboxBackgroundcolors = CheckBox.create("Show Backgroundcolors");
		checkboxBackgroundcolors.setValue(true);
		checkboxBackgroundcolors.addChangeHandler(changeHandler -> {
			if (changeHandler) {
				root.style().setBackgroundColor("lightgrey");
			} else {
				root.style().setBackgroundColor("white");
			}
		});
		cardCanvas.getHeaderDescription().appendChild(checkboxBackgroundcolors);

		CheckBox checkboxBorders = CheckBox.create("Show Borders");
		checkboxBorders.setValue(true);
		// cardCanvas.getHeaderDescription().appendChild(checkboxBorders);

		cardCanvas.addHeaderAction(Icons.ALL.more_vert(), click -> {
			StringBuffer javaSourcecode = new StringBuffer();
			StringBuffer jsonSourcecode = new StringBuffer();
			iterateTree(treeItem -> {
				javaSourcecode.append(treeItem.getValue().toSourcecode());
				jsonSourcecode.append(treeItem.getValue().toJson());
				return false;
			});
			javaSourcecode.append(
					"initElement(root.asElement()); //init Element for use with Nalu. Remove, if you are not using Nalu.\n");

			CodeCard javaCodeCard = CodeCard.createCodeCard("Java Source Code", javaSourcecode.toString(), "java");
			CodeCard jsonCodeCard = CodeCard.createCodeCard("JSON", jsonSourcecode.toString(), "javascript");
			MessageDialog
					.createMessage(DominoElement.div().appendChild(javaCodeCard).appendChild(jsonCodeCard).asElement())
					.open();
		});

		root.style().setMinHeight("200px");
		root.style().setBackgroundColor("lightgrey");

		addDnDHandlerFormElement(root);
		cardCanvas.appendChild(root);

		gridLayout.getLeftElement().appendChild(initElementsTree());
		gridLayout.getLeftElement().appendChild(initElementChooser());
		gridLayout.getRightElement().appendChild(initPropertiesPanel());
		gridLayout.getContentElement().appendChild(cardCanvas.asElement());

		Button btnRefresh = Button.create("Repaint/Refresh");
		btnRefresh.addClickListener(listener -> rebuildCanvas());
		gridLayout.getRightElement().appendChild(btnRefresh);

		initElement(gridLayout.asElement());
	}

	private GridLayout initGridLayout() {
		GridLayout gridLayout = GridLayout.create()
				.style()
				.setHeight("500px").get();

		gridLayout.setLeftSpan(SectionSpan._2, false, false);
		gridLayout.setRightSpan(SectionSpan._2, false, false);
		return gridLayout;
	}

	

	private Tree<SchubecTreeElement> initElementsTree() {
		elementsTree = Tree.create("Elements", new SchubecTreeElement(root, "root")).enableFolding()
				.setAutoCollapse(false);
		elementsTree.appendChild(TreeItem.create("ROOT", new SchubecTreeElementDiv(root)));
		elementsTree.addItemClickListener(itemClickListener -> {

			// Notification.create("Selected").show();
			iterateTree(treeItem -> {
				treeItem.getValue().getElement().style().remove("elementSelected");
				return false;
			});

			selectedFormElement = itemClickListener.getValue();
			selectedFormElement.getElement().style().add("elementSelected");
			label.setValue(selectedFormElement.getLabel());
			name.setValue(selectedFormElement.getName());
			extendedProperties.clearElement();
			Button btnRemove = Button.createDanger("Remove with Children");
			btnRemove.addClickListener(listener -> {
				find(selectedFormElement.getElement().getDominoId(), parentTreeItem-> {
							parentTreeItem.getValue().getElement().remove();
							parentTreeItem.remove();			
						});
				
				rebuildTree();
				rebuildCanvas();
			});

			propertiesPanel.appendChild(extendedProperties);

			selectedFormElement.getProperties().forEach((key, value) -> {
				if (value.getDatatype() == Datatype.STRING) {
					TextBox propertyBox = TextBox.create(key);
					propertyBox.setValue((String) value.getValue());
					propertyBox.addChangeHandler(newValue -> {
						selectedFormElement.setProperty(key, newValue);
					});
					extendedProperties.appendChild(propertyBox);
				}
				if (value.getDatatype() == Datatype.INTEGER) {
					IntegerBox propertyBox = IntegerBox.create(key);
					propertyBox.setValue((Integer) value.getValue());
					propertyBox.addChangeHandler(newValue -> {
						selectedFormElement.setProperty(key, newValue);
					});
					extendedProperties.appendChild(propertyBox);
				}
				if (value.getDatatype() == Datatype.BOOLEAN) {
					CheckBox propertyBox = CheckBox.create(key);
					propertyBox.setValue((Boolean) value.getValue());
					propertyBox.addChangeHandler(newValue -> {
						selectedFormElement.setProperty(key, newValue);
					});
					extendedProperties.appendChild(propertyBox);
				}
				if (value.getDatatype() == Datatype.ENUM) {
					EditorProperty<String> theValue = (EditorProperty<String>) value;
					Select<String> propertyBox = Select.create(key);
					for (String i : theValue.getAllowedValues()) {
						propertyBox.appendChild(SelectOption.<String>create(i, i));
					}
					propertyBox.setValue(theValue.getValue(), true);
					propertyBox.addChangeHandler(newValue -> {
						selectedFormElement.setProperty(key, newValue);
					});
					extendedProperties.appendChild(propertyBox);
				}

			});
			extendedProperties.appendChild(btnRemove);
		});
		return elementsTree;
	}

	private void rebuildTree() {
		// TODO Auto-generated method stub

	}

	private void rebuildCanvas() {
		// Delete everything
		cardCanvas.getBody().clearElement();
		// Add elements recursively
		iterateTree(treeItem -> {
			addChildrenTo(cardCanvas, treeItem);
			return false;
		});
	}

	private void addChildrenTo(BaseDominoElement parentElement, TreeItem<SchubecTreeElement> elementToBeAppended) {
		GWT.log("addChildrenTo " + parentElement.toString());
		BaseDominoElement elementToAdd = elementToBeAppended.getValue().getElement();
		parentElement.appendChild(elementToAdd);
		if (!elementToBeAppended.getSubItems().isEmpty()) {
			elementToBeAppended.getSubItems().forEach(child -> {
				GWT.log("addChildrenTo " + parentElement.toString() + " has child " + child.toString() + ". BTQ: I am "
						+ child.getValue().toString());
				addChildrenTo(elementToAdd, child);
			});
		}
	}

	private Accordion initElementChooser() {
		AccordionPanel accordionPanel1 = AccordionPanel.create(
				"Domino-UI Elements")
				.setIcon(Icons.ALL.perm_contact_calendar())
				.setHeaderBackground(Color.PINK)
				.show();
		AccordionPanel accordionPanel2 = AccordionPanel.create(
				"Elemento Elements")
				.setIcon(Icons.ALL.cloud_download())
				.setHeaderBackground(Color.CYAN);
		Accordion accordion = Accordion.create()
				.appendChild(
						accordionPanel1)
				.appendChild(
						accordionPanel2)
				.appendChild(
						AccordionPanel.create(
								"Info", TextNode.of("Alpha/Proof of concept"))
								.setIcon(Icons.ALL.contact_phone())
								.setHeaderBackground(Color.TEAL));

		List<Button> builderButtons = new LinkedList<>();
		builderButtons.add(Button.create(SchubecTreeElementButton.TYPE));
		builderButtons.add(Button.create(SchubecTreeElementCheckbox.TYPE));
		builderButtons.add(Button.create(SchubecTreeElementBadge.TYPE));
		builderButtons.add(Button.create(SchubecTreeElementCard.TYPE));
		builderButtons.add(Button.create(SchubecTreeElementChip.TYPE));
		builderButtons.add(Button.create(SchubecTreeElementTextBox.TYPE));
		builderButtons.add(Button.create(SchubecTreeElementTab.TYPE));
		builderButtons.add(Button.create(SchubecTreeElementTabsPanel.TYPE));
		builderButtons.add(Button.create(SchubecTreeElementDiv.TYPE));
		builderButtons.add(Button.create(SchubecTreeElementRow.TYPE));
		builderButtons.add(Button.create(SchubecTreeElementColumn.TYPE));
		builderButtons.add(Button.create(SchubecTreeElementHTMLHeadingElement.TYPE));

		builderButtons.forEach(button -> {
			initEditorElement(button);
			accordionPanel1.appendChild(button);
		});

		return accordion;
	}

	private Accordion initPropertiesPanel() {
		propertiesPanel = AccordionPanel.create(
				"Basic Elements")
				.setIcon(Icons.ALL.perm_contact_calendar())
				.setHeaderBackground(Color.PINK)
				.show();
		Accordion accordion2 = Accordion.create()
				.appendChild(
						propertiesPanel)
				.appendChild(
						AccordionPanel.create(
								"Collapsible item 2", TextNode.of("TEST"))
								.setIcon(Icons.ALL.cloud_download())
								.setHeaderBackground(Color.CYAN))
				.appendChild(
						AccordionPanel.create(
								"Collapsible item 3", TextNode.of("TEST"))
								.setIcon(Icons.ALL.contact_phone())
								.setHeaderBackground(Color.TEAL));

		name = TextBox.create("Name").small().floating();
		name.addChangeHandler(changeHandler -> {
			selectedFormElement.setName(changeHandler);
		});
		label = TextBox.create("Label").small().floating();
		label.addChangeHandler(changeHandler -> {
			selectedFormElement.setLabel(changeHandler);
		});
		propertiesPanel.appendChild(name);
		propertiesPanel.appendChild(label);
		return accordion2;
	}

	private void initEditorElement(Button button) {
		button.asElement().draggable = true;
		button.addEventListener(EventType.dragstart, evt -> {
			((elemental2.dom.DragEvent) evt).dataTransfer.setData("text/plain", button.getTextContent());
		});
	}

	private void initEditorElement2(TreeItem element, String type) {
		element.asElement().draggable = true;
		element.addEventListener(EventType.dragstart, evt -> {
			((elemental2.dom.DragEvent) evt).dataTransfer.setData("text/plain", type);
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

	private void addElement(BaseDominoElement element, String type) {
		switch (type) {
			case "TextBox":
				createTextbox(element);
				break;
			case "Column":
				createColumn(element);
				break;
			case "Row":
				createRow(element);
				break;
			case "Button":
				createButton(element);
				break;
			case "CheckBox":
				createCheckbox(element);
				break;
			case "Chip":
				createChip(element);
				break;
			case "Badge":
				createBadge(element);
				break;
			case "TabsPanel":
				createTabsPanel(element);
				break;
			case "Tab":
				createTab(element);
				break;
			case "div":
				createDiv(element);
				break;
			case "h":
				createH(element);
				break;

			case "Card":
				createCard(element);
				break;
		}
	}

	private void createTabsPanel(BaseDominoElement element) {
		TabsPanel tabsPanel = TabsPanel.create();
		addDnDHandlerFormElement(tabsPanel);
		element.appendChild(tabsPanel);
		TreeItem newTreeItem = TreeItem.create("TabsPanel", new SchubecTreeElementTabsPanel(tabsPanel));
		find(element.getDominoId(), parentTreeItem -> {
			parentTreeItem.appendChild(newTreeItem);	
		});
		initEditorElement2(newTreeItem, "TabsPanel");
		addDnDHandlerFormElement(newTreeItem);
		
		createTab(tabsPanel);
	}

	private void createTab(BaseDominoElement parent) {
		Tab tab = Tab.create("New Tab");
		tab.appendChild(TextNode.of("Body"));
		addDnDHandlerFormElement(tab);

		TreeItem newTreeItem = TreeItem.create("Tab", new SchubecTreeElementTab(tab));
		find(parent.getDominoId(), parentTreeItem -> {
			parentTreeItem.appendChild(newTreeItem);
			if (parentTreeItem.getValue().getType().equals("TabsPanel")) {
				((TabsPanel) parent).appendChild(tab);
			} else {
				GWT.log("error");
				parent.appendChild(tab);
			}
		});
		createDiv(tab, "TabBody");

	}

	private void createChip(BaseDominoElement parent) {
		Chip chip = Chip.create("New Chip");
		addDnDHandlerFormElement(chip);
		parent.appendChild(chip);
		
			TreeItem newTreeItem = TreeItem.create("Chip", new SchubecTreeElementChip(chip));
			find(parent.getDominoId(), parentTreeItem -> {
				parentTreeItem.appendChild(newTreeItem);	
			});
		
	}

	private void createCard(BaseDominoElement parent) {
		Card card = Card.create("New Card");
		addDnDHandlerFormElement(card.getHeader());
		addDnDHandlerFormElement(card.getBody());
		parent.appendChild(card);

		TreeItem<SchubecTreeElement> newTreeItemCard = TreeItem.create("Card", new SchubecTreeElementCard(card));
		TreeItem<SchubecTreeElement> newTreeItemHeader = TreeItem.create("Header",
				new SchubecTreeElementCardHeader(card.getHeader()));
		addDnDHandler(newTreeItemHeader);
		newTreeItemCard.appendChild(newTreeItemHeader);

		TreeItem<SchubecTreeElement> newTreeItemHeaderBar = TreeItem.create("HeaderBar",
				new SchubecTreeElementCardHeaderBar(card.getHeaderBar()));
		addDnDHandler(newTreeItemHeaderBar);
		newTreeItemHeader.appendChild(newTreeItemHeaderBar);

		TreeItem<SchubecTreeElement> newTreeItemHeaderDescription = TreeItem.create("HeaderDescription",
				new SchubecTreeElementCardHeaderDescription(card.getHeaderDescription()));
		addDnDHandler(newTreeItemHeaderDescription);
		newTreeItemHeader.appendChild(newTreeItemHeaderDescription);

		TreeItem<SchubecTreeElement> newTreeItemHeaderTitle = TreeItem.create("HeaderTitle",
				new SchubecTreeElementCardHeaderTitle(card.getHeaderTitle()));
		addDnDHandler(newTreeItemHeaderTitle);
		newTreeItemHeader.appendChild(newTreeItemHeaderTitle);

		TreeItem<SchubecTreeElement> newTreeItemBody = TreeItem.create("Body",
				new SchubecTreeElementCardBody(card.getBody()));
		addDnDHandler(newTreeItemBody);
		newTreeItemCard.appendChild(newTreeItemBody);

		find(parent.getDominoId(), parentTreeItem -> {
			parentTreeItem.appendChild(newTreeItemCard);	
		});
	}

	private void createBadge(BaseDominoElement parent) {
		Badge badge = Badge.create("New badge");
		addDnDHandlerFormElement(badge);
		parent.appendChild(badge);
		TreeItem newTreeItem = TreeItem.create("Badge", new SchubecTreeElementBadge(badge));
		find(parent.getDominoId(), parentTreeItem -> {
			parentTreeItem.appendChild(newTreeItem);	
		});
	}

	private void createTextbox(BaseDominoElement parent) {
		TextBox textbox = TextBox.create("New Textbox");
		// addDndHandler(textbox);
		parent.appendChild(textbox);
		TreeItem newTreeItem = TreeItem.create("Textbox", new SchubecTreeElementTextBox(textbox));
		find(parent.getDominoId(), parentTreeItem -> {
			parentTreeItem.appendChild(newTreeItem);	
		});
	}

	private void createCheckbox(BaseDominoElement parent) {
		CheckBox checkbox = CheckBox.create("New Button");
		addDnDHandlerFormElement(checkbox);
		parent.appendChild(checkbox);
		TreeItem newTreeItem = TreeItem.create("Checkbox", new SchubecTreeElementCheckbox(checkbox));
		find(parent.getDominoId(), parentTreeItem -> {
			parentTreeItem.appendChild(newTreeItem);	
		});

	}

	private void createButton(BaseDominoElement parent) {
		Button button = Button.create("New Button");
		addDnDHandlerFormElement(button);
		parent.appendChild(button);
		TreeItem newTreeItem = TreeItem.create("Button", new SchubecTreeElementButton(button));
		find(parent.getDominoId(), parentTreeItem -> {
			parentTreeItem.appendChild(newTreeItem);	
		});

	}

	private void createRow(BaseDominoElement parent) {
		Row row = Row.create();
		row.style().setMinHeight("200px");
		addDnDHandlerFormElement(row);
		parent.appendChild(row);

		TreeItem newTreeItem = TreeItem.create("Row", new SchubecTreeElementRow(row));
		find(parent.getDominoId(), parentTreeItem -> {
			parentTreeItem.appendChild(newTreeItem);	
		});
		initEditorElement2(newTreeItem, "Row");
		addDnDHandler(newTreeItem);
		createColumn(row);
	}

	private void createColumn(BaseDominoElement parent) {
		Column column = Column.span();

		addDnDHandlerFormElement(column);
		parent.appendChild(column);

		TreeItem newTreeItem = TreeItem.create("Column", new SchubecTreeElementColumn(column));
		find(parent.getDominoId(), parentTreeItem -> {
			parentTreeItem.appendChild(newTreeItem);	
		});
		initEditorElement2(newTreeItem, "Column");
		addDnDHandler(newTreeItem);
		// createDiv(column, "columBody");
	}

	private void createH(BaseDominoElement parent) {
		DominoElement<HTMLHeadingElement> h = DominoElement.of(h(1));
		h.setTextContent("Title");
		addDnDHandlerFormElement(h);
		parent.appendChild(h);
		TreeItem<SchubecTreeElement> newTreeItem = TreeItem.create("Heading",
				new SchubecTreeElementHTMLHeadingElement(h));
		find(parent.getDominoId(), parentTreeItem -> {
			parentTreeItem.appendChild(newTreeItem);	
		});
		addDnDHandler(newTreeItem);

	}

	private void createDiv(BaseDominoElement parent) {
		createDiv(parent, "div");
	}

	private void createDiv(BaseDominoElement parent, String label) {
		DominoElement<HTMLDivElement> div = DominoElement.div();
		div.setHeight("200px");
		addDnDHandlerFormElement(div);
		parent.appendChild(div);
		TreeItem<SchubecTreeElement> newTreeItem = TreeItem.create(label, new SchubecTreeElementDiv(div));
		find( parent.getDominoId(), parentTreeItem -> {
			parentTreeItem.appendChild(newTreeItem);
		});

		addDnDHandler(newTreeItem);

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
			addElement(treeItem.getValue().getElement(), type);
		});
	}

	private void addDnDHandlerFormElement(BaseDominoElement element) {
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
			addElement(element, type);
		});
	}

	public void find(String dominoId, IFound<TreeItem<SchubecTreeElement>> callback) {
		iterateTree(item -> {
			if (item.getValue().getElement().getDominoId().equals(dominoId)) {
				callback.found(item);
				return true; //Stop, we found the element!
			}
			return false; //Do not stop
		});
	}

	private void iterateTree(IYield<TreeItem<SchubecTreeElement>> callback) {
		iterateSubitems(callback, elementsTree.getTreeRoot().getSubItems());
	}

	private void iterateSubitems(IYield<TreeItem<SchubecTreeElement>> callback, List<TreeItem<SchubecTreeElement>> list) {
		for (TreeItem<SchubecTreeElement> treeItem : list) {
			if (!callback.yield(treeItem)) { // Should we continue?
				if (!treeItem.getSubItems().isEmpty()) {
					iterateSubitems(callback, treeItem.getSubItems());
				}
			} 
		}
	}

}

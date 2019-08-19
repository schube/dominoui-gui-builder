package com.schubec.dominoui.guibuilder.client.ui.screen01;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import com.google.gwt.core.client.GWT;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.NodeList.ForEachCallbackFn;

import java.lang.Override;
import java.util.List;

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
import org.dominokit.domino.ui.forms.TextBox;
import org.dominokit.domino.ui.grid.GridLayout;
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
	private Card root;
	private AccordionPanel propertiesPanel;

	public Screen01Component() {
		super();
	}

	public String getSourcecode(TreeItem<SchubecTreeElement> item) {
		StringBuffer sourcecode = new StringBuffer();
		sourcecode.append(item.getValue().toSourcecode());
		if (!item.getSubItems().isEmpty()) {
			for (TreeItem<SchubecTreeElement> subitem : item.getSubItems()) {
				if(subitem.getValue().hasSourcecode()) {
					sourcecode.append(getSourcecode(subitem));
					sourcecode.append(item.getValue().getName() + ".appendChild("+subitem.getValue().getName()+");\n");
				}
			}
		}
		return sourcecode.toString();
	}
	public String getJson(TreeItem<SchubecTreeElement> item) {
		StringBuffer sourcecode = new StringBuffer();
		sourcecode.append(item.getValue().toJson());
		if (!item.getSubItems().isEmpty()) {
			for (TreeItem<SchubecTreeElement> subitem : item.getSubItems()) {
				if(subitem.getValue().hasSourcecode()) {
					sourcecode.append(getJson(subitem));
				}
			}
		}
		return sourcecode.toString();
	}
	@Override
	public void render() {

		GridLayout gridLayout = initGridLayout();

		cardCanvas = Card.create("Canvas", "Place add elements with drag & drop to the canvas!");

		cardCanvas.addHeaderAction(Icons.ALL.more_vert(), click -> {
			StringBuffer javaSourcecode = new StringBuffer();
			for (TreeItem<SchubecTreeElement> item : elementsTree.getTreeRoot().getSubItems()) {
				javaSourcecode.append(getSourcecode(item));
			}
			javaSourcecode.append("initElement(root.asElement()); //init Element for use with Nalu. Remove, if you are not using Nalu.\n");
			
			StringBuffer jsonSourcecode = new StringBuffer();
			for (TreeItem<SchubecTreeElement> item : elementsTree.getTreeRoot().getSubItems()) {
				jsonSourcecode.append(getJson(item));
			}
			
			CodeCard javaCodeCard = CodeCard.createCodeCard("Java Source Code", javaSourcecode.toString(), "java");
			CodeCard jsonCodeCard = CodeCard.createCodeCard("JSON", jsonSourcecode.toString(), "javascript");
			MessageDialog.createMessage(DominoElement.div().appendChild(javaCodeCard).appendChild(jsonCodeCard).asElement()).open();
		});

		root = Card.create("Form");
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

	private void removeElementSelectedMarker(TreeItem<SchubecTreeElement> listItem) {
		listItem.getValue().getElement().style().remove("elementSelected");
		if (!listItem.getSubItems().isEmpty()) {
			listItem.getSubItems().forEach(i -> removeElementSelectedMarker(i));
		}
	}

	private Tree initElementsTree() {
		elementsTree = Tree.create("Elements", new SchubecTreeElement(root, "root")).enableFolding()
				.setAutoCollapse(false);
		elementsTree.appendChild(TreeItem.create("ROOT", new SchubecTreeElementCard(root, "root")));
		elementsTree.addItemClickListener(itemClickListener -> {

			// Notification.create("Selected").show();

			elementsTree.getTreeRoot().getSubItems().forEach(listItem -> removeElementSelectedMarker(listItem));

			selectedFormElement = itemClickListener.getValue();
			selectedFormElement.getElement().style().add("elementSelected");
			label.setValue(selectedFormElement.getLabel());
			name.setValue(selectedFormElement.getName());

			Button btnRemove = Button.createDanger("Remove with Children");
			btnRemove.addClickListener(listener -> {
				TreeItem<SchubecTreeElement> parentTreeItem = find(elementsTree.getTreeRoot().getSubItems(),
						selectedFormElement.getElement().getDominoId());
				parentTreeItem.getValue().getElement().remove();
				parentTreeItem.remove();
				rebuildTree();
				rebuildCanvas();
			});
			propertiesPanel.appendChild(btnRemove);

			selectedFormElement.getProperties().forEach((key, value) -> {
				if (value.getDatatype() == Datatype.STRING) {
					TextBox propertyBox = TextBox.create(key);
					propertyBox.setValue((String) value.getValue());
					propertyBox.addChangeHandler(newValue -> {
						selectedFormElement.setProperty(key, newValue);
					});
					propertiesPanel.appendChild(propertyBox);
				}
				if (value.getDatatype() == Datatype.BOOLEAN) {
					CheckBox propertyBox = CheckBox.create(key);
					propertyBox.setValue((Boolean) value.getValue());
					propertyBox.addChangeHandler(newValue -> {
						selectedFormElement.setProperty(key, newValue);
					});
					propertiesPanel.appendChild(propertyBox);
				}

			});

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
		elementsTree.getTreeRoot().getSubItems().forEach(item -> addChildrenTo(cardCanvas, item));
	}

	private void addChildrenTo(BaseDominoElement me, TreeItem<SchubecTreeElement> element) {
		GWT.log("addChildrenTo " + me.toString());
		BaseDominoElement elementToAdd = element.getValue().getElement();
		me.appendChild(elementToAdd);
		if (!element.getSubItems().isEmpty()) {
			element.getSubItems().forEach(child -> {
				GWT.log("addChildrenTo " + me.toString() + " has child " + child.toString() + ". BTQ: I am "
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

		Button btnButton = Button.create("Button");
		initEditorElement(btnButton, "Button");

		Button btnBadge = Button.create("Badge");
		initEditorElement(btnBadge, "Badge");

		Button btnCard = Button.create("Card");
		initEditorElement(btnCard, "Card");

		Button btnChip = Button.create("Chip");
		initEditorElement(btnChip, "Chip");

		Button btnTextbox = Button.create("Textbox");
		initEditorElement(btnTextbox, "Textbox");

		Button btnTabsPanel = Button.create("TabsPanel");
		initEditorElement(btnTabsPanel, "TabsPanel");

		Button btnTab = Button.create("Tab");
		initEditorElement(btnTab, "Tab");

		accordionPanel1.appendChild(btnButton);
		accordionPanel1.appendChild(btnCard);
		accordionPanel1.appendChild(btnChip);
		accordionPanel1.appendChild(btnBadge);
		accordionPanel1.appendChild(btnTextbox);
		accordionPanel1.appendChild(btnTabsPanel);
		accordionPanel1.appendChild(btnTab);

		Button btnDiv = Button.create("div");
		initEditorElement(btnDiv, "div");

		Button btnA = Button.create("a");
		initEditorElement(btnA, "a");

		Button btnP = Button.create("p");
		initEditorElement(btnP, "p");

		Button btnHr = Button.create("hr");
		initEditorElement(btnHr, "hr");

		accordionPanel2.appendChild(btnDiv);
		accordionPanel2.appendChild(btnA);
		accordionPanel2.appendChild(btnP);
		accordionPanel2.appendChild(btnHr);

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

	private void initEditorElement(BaseDominoElement element, String type) {
		element.asElement().draggable = true;
		element.addEventListener(EventType.dragstart, evt -> {
			((elemental2.dom.DragEvent) evt).dataTransfer.setData("text/plain", type);
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
			case "Textbox":
				TextBox textbox = TextBox.create("New Textbox");
				// addDndHandler(textbox);
				element.appendChild(textbox); {
				TreeItem newTreeItem = TreeItem.create("Textbox", new SchubecTreeElementTextBox(textbox));
				TreeItem<SchubecTreeElement> parentTreeItem = find(elementsTree.getTreeRoot().getSubItems(),
						element.getDominoId());
				parentTreeItem.appendChild(newTreeItem);

			}
				break;
			case "Button":
				Button button = Button.create("New Button");
				addDnDHandlerFormElement(button);
				element.appendChild(button); {
				TreeItem newTreeItem = TreeItem.create("Button", new SchubecTreeElementButton(button));
				TreeItem<SchubecTreeElement> parentTreeItem = find(elementsTree.getTreeRoot().getSubItems(),
						element.getDominoId());
				parentTreeItem.appendChild(newTreeItem);
			}
				break;
			case "Chip":
				Chip chip = Chip.create("New Chip");
				addDnDHandlerFormElement(chip);
				element.appendChild(chip); {
				TreeItem newTreeItem = TreeItem.create("Chip", new SchubecTreeElementChip(chip));
				TreeItem<SchubecTreeElement> parentTreeItem = find(elementsTree.getTreeRoot().getSubItems(), element.getDominoId());
				parentTreeItem.appendChild(newTreeItem);
			}
				break;
			case "Badge":
				Badge badge = Badge.create("New badge");
				addDnDHandlerFormElement(badge);
				element.appendChild(badge); {
				TreeItem newTreeItem = TreeItem.create("Badge", new SchubecTreeElementBadge(badge));
				TreeItem<SchubecTreeElement> parentTreeItem = find(elementsTree.getTreeRoot().getSubItems(),
						element.getDominoId());
				parentTreeItem.appendChild(newTreeItem);
			}
				break;
			case "TabsPanel":
				TabsPanel tabsPanel = TabsPanel.create();
				addDnDHandlerFormElement(tabsPanel);
				element.appendChild(tabsPanel); {
				TreeItem newTreeItem = TreeItem.create("TabsPanel", new SchubecTreeElementTabsPanel(tabsPanel));
				TreeItem<SchubecTreeElement> parentTreeItem = find(elementsTree.getTreeRoot().getSubItems(),
						element.getDominoId());
				initEditorElement2(newTreeItem, "TabsPanel");
				addDnDHandlerFormElement(newTreeItem);
				parentTreeItem.appendChild(newTreeItem);

				Tab tab = Tab.create("New Tab");
				TreeItem newTreeSubItem = TreeItem.create("Tab", new SchubecTreeElementTab(tab));
				newTreeItem.appendChild(newTreeSubItem);
				tabsPanel.appendChild(tab);

				DominoElement<HTMLDivElement> div = DominoElement.div();
				addDnDHandlerFormElement(div);
				div.setHeight("100px");
				tab.appendChild(div);
				TreeItem<SchubecTreeElement> newTreeSubItem2 = TreeItem.create("TabBody",
						new SchubecTreeElementDiv(div));
				newTreeSubItem.appendChild(newTreeSubItem2);

			}
				break;
			case "Tab":
				Tab tab = Tab.create("New Tab");
				tab.appendChild(TextNode.of("Body"));
				addDnDHandlerFormElement(tab);

			{
				TreeItem newTreeItem = TreeItem.create("Tab", new SchubecTreeElementTab(tab));
				TreeItem<SchubecTreeElement> parentTreeItem = find(elementsTree.getTreeRoot().getSubItems(),
						element.getDominoId());
				parentTreeItem.appendChild(newTreeItem);
				GWT.log("Parent: " + parentTreeItem.getValue().getType());
				if (parentTreeItem.getValue().getType().equals("TabsPanel")) {
					((TabsPanel) element).appendChild(tab);
				} else {
					GWT.log("error");
					element.appendChild(tab);
				}

				DominoElement<HTMLDivElement> div = DominoElement.div();
				addDnDHandlerFormElement(div);
				div.setHeight("100px");
				tab.appendChild(div);
				TreeItem<SchubecTreeElement> newTreeSubItem2 = TreeItem.create("TabBody",
						new SchubecTreeElementDiv(div));
				newTreeItem.appendChild(newTreeSubItem2);

			}

				break;
			case "div":
				DominoElement<HTMLDivElement> div = DominoElement.div();
				div.setHeight("200px");
				addDnDHandlerFormElement(div);
				element.appendChild(div); {
				TreeItem<SchubecTreeElement> newTreeItem = TreeItem.create("Div", new SchubecTreeElementDiv(div));
				TreeItem<SchubecTreeElement> parentTreeItem = find(elementsTree.getTreeRoot().getSubItems(),
						element.getDominoId());
				parentTreeItem.appendChild(newTreeItem);
				addDnDHandler(newTreeItem);
			}
				break;
			case "Card":
				Card card = Card.create("New Card");
				addDnDHandlerFormElement(card.getHeader());
				addDnDHandlerFormElement(card.getBody());
				element.appendChild(card);

				TreeItem<SchubecTreeElement> newTreeItemCard = TreeItem.create("Card", new SchubecTreeElementCard(card));
				TreeItem<SchubecTreeElement> newTreeItemHeader = TreeItem.create("Header", new SchubecTreeElementCardHeader(card.getHeader()));
				addDnDHandler(newTreeItemHeader);
				newTreeItemCard.appendChild(newTreeItemHeader);
				
				TreeItem<SchubecTreeElement> newTreeItemHeaderBar = TreeItem.create("HeaderBar", new SchubecTreeElementCardHeaderBar(card.getHeaderBar()));
				addDnDHandler(newTreeItemHeaderBar);
				newTreeItemHeader.appendChild(newTreeItemHeaderBar);
				
				TreeItem<SchubecTreeElement> newTreeItemHeaderDescription = TreeItem.create("HeaderDescription", new SchubecTreeElementCardHeaderDescription(card.getHeaderDescription()));
				addDnDHandler(newTreeItemHeaderDescription);
				newTreeItemHeader.appendChild(newTreeItemHeaderDescription);
				
				TreeItem<SchubecTreeElement> newTreeItemHeaderTitle = TreeItem.create("HeaderTitle", new SchubecTreeElementCardHeaderTitle(card.getHeaderTitle()));
				addDnDHandler(newTreeItemHeaderTitle);
				newTreeItemHeader.appendChild(newTreeItemHeaderTitle);
				
								
				TreeItem<SchubecTreeElement> newTreeItemBody = TreeItem.create("Body", new SchubecTreeElementCardBody(card.getBody()));
				addDnDHandler(newTreeItemBody);
				newTreeItemCard.appendChild(newTreeItemBody);
				
				
				
				
				

				TreeItem<SchubecTreeElement> parentTreeItem = find(elementsTree.getTreeRoot().getSubItems(),
						element.getDominoId());
				parentTreeItem.appendChild(newTreeItemCard);
				break;
		}
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

	private TreeItem<SchubecTreeElement> find(List<TreeItem<SchubecTreeElement>> list, String dominoId) {

		for (TreeItem<SchubecTreeElement> e : list) {
			// GWT.log("Checking " + e.getValue().toString());
			GWT.log(e.getDominoId() + "/" + e.getValue().getElement().getDominoId() + "==" + dominoId);
			if (e.getValue().getElement().getDominoId().equals(dominoId)) {
				GWT.log("Element found!");
				return e;
			}
			if (!e.getSubItems().isEmpty()) {
				GWT.log("Element " + e.getValue().getElement().getDominoId() + " has Subelements. Checking now");
				TreeItem<SchubecTreeElement> foundElement = find(e.getSubItems(), dominoId);
				if(foundElement!=null) {
					return foundElement;
				}
			} else {
				GWT.log("Element " + e.getValue().getElement().getDominoId() + " has no Subelements.");
			}
		}

		GWT.log("Returning null");
		return null;
	}

}

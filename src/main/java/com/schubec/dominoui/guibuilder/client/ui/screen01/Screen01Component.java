package com.schubec.dominoui.guibuilder.client.ui.screen01;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import com.google.gwt.core.client.GWT;
import com.schubec.dominoui.guibuilder.client.model.MyModel;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.NodeList.ForEachCallbackFn;

import java.lang.Override;
import java.util.List;

import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.chips.Chip;
import org.dominokit.domino.ui.collapsible.Accordion;
import org.dominokit.domino.ui.collapsible.AccordionPanel;
import org.dominokit.domino.ui.dialogs.MessageDialog;
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

/**
 * Copyright (C) 2018 - 2019 Frank Hossfeld <frank.hossfeld@googlemail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
public class Screen01Component extends AbstractComponent<IScreen01Component.Controller, HTMLElement>
		implements IScreen01Component {
	private Card cardCanvas;
	private Tree<SchubecTreeElement> elementsTree;
	private TextBox name;
	private TextBox label;
	private SchubecTreeElement selectedFormElement;
	private Card root;

	public Screen01Component() {
		super();
	}

	
	public String getSourcecode(TreeItem<SchubecTreeElement> item) {
		StringBuffer sourcecode = new StringBuffer();
		sourcecode.append(item.getValue().toSourcecode());
		if(!item.getSubItems().isEmpty()) {
			for(TreeItem<SchubecTreeElement> subitem : item.getSubItems()) {
				sourcecode.append(getSourcecode(subitem));
			}
		}
		return sourcecode.toString();
	}
	

	@Override
	public void render() {

		GridLayout gridLayout = initGridLayout();

		cardCanvas = Card.create("Canvas");
		
		cardCanvas.addHeaderAction(Icons.ALL.more_vert(), click -> {
			StringBuffer sourcecode = new StringBuffer();
			for(TreeItem<SchubecTreeElement> item : elementsTree.getTreeRoot().getSubItems()) {
				sourcecode.append(getSourcecode(item));
			}
			
			CodeCard codeCard = CodeCard.createCodeCard(sourcecode.toString(), "java");
			codeCard.show();
			MessageDialog.createMessage(codeCard.asElement()).open();
		});
		
		root = Card.create("Form");
		addDnDHandler(root);
		cardCanvas.appendChild(root);

		gridLayout.getLeftElement().appendChild(initElementsTree());
		gridLayout.getLeftElement().appendChild(initElementChooser());
		gridLayout.getRightElement().appendChild(initPropertiesPanel());
		gridLayout.getContentElement().appendChild(cardCanvas.asElement());

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
				if(!listItem.getSubItems().isEmpty()) {
					listItem.getSubItems().forEach(i -> removeElementSelectedMarker(i));
				}
	}
	
	private Tree initElementsTree() {
		elementsTree = Tree.create("Elements", new SchubecTreeElement(root, "root")).enableFolding().setAutoCollapse(false);
		elementsTree.appendChild(TreeItem.create("ROOT", new SchubecTreeElementCard(root, "root")));
		elementsTree.addItemClickListener(itemClickListener -> {

			//Notification.create("Selected").show();

			elementsTree.getTreeRoot().getSubItems().forEach(listItem -> removeElementSelectedMarker(listItem));

			selectedFormElement = itemClickListener.getValue();
			selectedFormElement.getElement().style().add("elementSelected");
			label.setValue(selectedFormElement.getLabel());
			name.setValue(selectedFormElement.getName());
		});
		return elementsTree;
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
		AccordionPanel accordionPanel2 = AccordionPanel.create(
				"Basic Elements")
				.setIcon(Icons.ALL.perm_contact_calendar())
				.setHeaderBackground(Color.PINK)
				.show();
		Accordion accordion2 = Accordion.create()
				.appendChild(
						accordionPanel2)
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
		accordionPanel2.appendChild(name);
		accordionPanel2.appendChild(label);
		return accordion2;
	}

	private void initEditorElement(BaseDominoElement element, String type) {
		element.asElement().draggable = true;
		element.addEventListener(EventType.dragstart, evt -> {
			((elemental2.dom.DragEvent) evt).dataTransfer.setData("text/plain", type);
		});
	}

	private void addDnDHandler(BaseDominoElement element) {
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

			switch (type) {
				case "Textbox":
					TextBox textbox = TextBox.create("New Textbox");
					// addDndHandler(textbox);
					element.appendChild(textbox);
					break;
				case "Button":
					Button button = Button.create("New Button");
					addDnDHandler(button);
					element.appendChild(button);
					{
						TreeItem newTreeItem = TreeItem.create("Button", new SchubecTreeElementButton(button));
						TreeItem<SchubecTreeElement> parentTreeItem = find(elementsTree.getTreeRoot().getSubItems(), element.getDominoId());
						parentTreeItem.appendChild(newTreeItem);
					}
					break;
				case "Chip":
					Chip chip = Chip.create("New Chip");
					addDnDHandler(chip);
					element.appendChild(chip);
					{
						TreeItem newTreeItem= TreeItem.create("Chip", new SchubecTreeElementChip(chip));
						TreeItem<SchubecTreeElement> parentTreeItem = find(elementsTree.getTreeRoot().getSubItems(), element.getDominoId());
						parentTreeItem.appendChild(newTreeItem);
					}
					break;
				case "TabsPanel":
					TabsPanel tabsPanel = TabsPanel.create();
					addDnDHandler(tabsPanel);
					element.appendChild(tabsPanel);
					break;			
				case "Tab":
					Tab tab = Tab.create("New Tab");
					addDnDHandler(tab);
					element.appendChild(tab);
					break;	
				case "div":
					DominoElement<HTMLDivElement> div = DominoElement.div();
					div.setHeight("200px");
					addDnDHandler(div);
					element.appendChild(div);
					{
						TreeItem newTreeItem= TreeItem.create("Div", new SchubecTreeElementDiv(div));
						TreeItem<SchubecTreeElement> parentTreeItem = find(elementsTree.getTreeRoot().getSubItems(), element.getDominoId());
						parentTreeItem.appendChild(newTreeItem);
					}
					break;					
				case "Card":
					Card card = Card.create("New Card");
					addDnDHandler(card.getHeader());
					addDnDHandler(card.getBody());
					element.appendChild(card);

					TreeItem newTreeItemCard = TreeItem.create("Card", new SchubecTreeElementCard(card));
					newTreeItemCard.addEventListener(EventType.mouseover, mouseOverEvent -> {
						card.style().add("highlightElement");
					});
					newTreeItemCard.addEventListener(EventType.mouseout, mouseLeaveEvent -> {
						card.style().remove("highlightElement");
					});
					
					TreeItem newTreeItemHeader = TreeItem.create("Header", new SchubecTreeElementCardHeader(card.getHeader()));
					newTreeItemHeader.addEventListener(EventType.mouseover, mouseOverEvent -> {
						card.style().add("highlightElement");
					});
					newTreeItemHeader.addEventListener(EventType.mouseout, mouseLeaveEvent -> {
						card.style().remove("highlightElement");
					});
					
					TreeItem newTreeItemBody = TreeItem.create("Body", new SchubecTreeElementCardBody(card.getBody()));
					newTreeItemBody.addEventListener(EventType.mouseover, mouseOverEvent -> {
						card.style().add("highlightElement");
					});
					newTreeItemBody.addEventListener(EventType.mouseout, mouseLeaveEvent -> {
						card.style().remove("highlightElement");
					});
					
					
					newTreeItemCard.appendChild(newTreeItemHeader);
					newTreeItemCard.appendChild(newTreeItemBody);
					
					TreeItem<SchubecTreeElement> parentTreeItem = find(elementsTree.getTreeRoot().getSubItems(), element.getDominoId());
					parentTreeItem.appendChild(newTreeItemCard);
					break;
			}

		});

	}
	
	private TreeItem<SchubecTreeElement> find(List<TreeItem<SchubecTreeElement>> list, String dominoId) {
		
			for(TreeItem<SchubecTreeElement> e: list ) {
				//GWT.log("Checking " + e.getValue().toString());
				GWT.log(e.getDominoId() + "/" + e.getValue().getElement().getDominoId() + "==" + dominoId);
				if (e.getValue().getElement().getDominoId().equals(dominoId)) {
					GWT.log("Element found!");
					return e;
				}
				if(!e.getSubItems().isEmpty()) {
					GWT.log("Element "+e.getValue().getElement().getDominoId()+" has Subelements. Checking now");
					return find(e.getSubItems(), dominoId);
				} else {
					GWT.log("Element "+e.getValue().getElement().getDominoId()+" has no Subelements.");
				}
			}
		
		GWT.log("Returning null");
		return null;
	}
	
}

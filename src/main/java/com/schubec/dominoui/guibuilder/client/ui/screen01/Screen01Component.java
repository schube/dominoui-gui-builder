package com.schubec.dominoui.guibuilder.client.ui.screen01;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import com.google.gwt.core.client.GWT;

import elemental2.core.Global;
import elemental2.core.JsArray;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLHeadingElement;
import elemental2.dom.NodeList.ForEachCallbackFn;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

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
import com.schubec.dominoui.guibuilder.client.ui.screen01.Datatype;
import com.schubec.dominoui.guibuilder.client.ui.screen01.code.CodeCard;
import com.schubec.dominoui.guibuilder.client.ui.screen01.code.JSONParser;

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
import org.dominokit.domino.ui.forms.TextArea;
import org.dominokit.domino.ui.forms.TextBox;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.GridLayout;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.grid.SectionSpan;
import org.dominokit.domino.ui.icons.Icons;
import org.dominokit.domino.ui.lists.SimpleListGroup;
import org.dominokit.domino.ui.lists.SimpleListItem;
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
	private CheckBox checkboxShowVisualHelpers;
	private final Factory factory;
	private final Treehelper treehelper;
	
	public Screen01Component() {
		super();
		elementsTree = Tree.create("Elements", new SchubecTreeElement(root, "root")).enableFolding()
				.setAutoCollapse(false);
		treehelper = new Treehelper(elementsTree);
		factory = new Factory(treehelper);
	}

	
	public JsPropertyMap<Object> createJsonRecursively(TreeItem<SchubecTreeElement> rootItem) {
		JsPropertyMap<Object> json = rootItem.getValue().toJson();
		if(!rootItem.getSubItems().isEmpty()) {
			JsArray<Object> children = new JsArray<>();
			for(TreeItem<SchubecTreeElement> subitem : rootItem.getSubItems()) {
				children.push(createJsonRecursively(subitem));
			}
			json.set("children", children);
		}
		return json;
	}
	

	@Override
	public void render() {

		GridLayout gridLayout = initGridLayout();

		cardCanvas = Card.create("Canvas", "Place add elements with drag & drop to the canvas!");
		checkboxShowVisualHelpers = CheckBox.create("Show visual helpers");
		checkboxShowVisualHelpers.setValue(true);
		checkboxShowVisualHelpers.addChangeHandler(newValue -> {
			treehelper.iterateTree(item -> {
				item.getValue().setShowVisualHelpers(newValue);
				return false;
			});
			
		});
		cardCanvas.getHeaderDescription().appendChild(checkboxShowVisualHelpers);

		CheckBox checkboxBorders = CheckBox.create("Show Borders");
		checkboxBorders.setValue(true);
		// cardCanvas.getHeaderDescription().appendChild(checkboxBorders);

		
		
			CodeCard javaCodeCard = CodeCard.createCodeCard("Java Source Code", "", "java");
			javaCodeCard.getCard().getBody().addShowHandler(() -> {
				StringBuffer javaSourcecode = new StringBuffer();
				treehelper.iterateTree(treeItem -> {
					javaSourcecode.append(treeItem.getValue().toSourcecode());
					return false;
				});
				javaSourcecode.append(
						"initElement(root.asElement()); //init Element for use with Nalu. Remove, if you are not using Nalu.\n");
				javaCodeCard.setCode(javaSourcecode.toString());
			});
			CodeCard jsonCodeCard = CodeCard.createCodeCard("JSON", "", "javascript");
			jsonCodeCard.getCard().getBody().addShowHandler(() -> {
				
				TreeItem<SchubecTreeElement> rootItem = elementsTree.getTreeRoot().getSubItems().get(0);
				JsPropertyMap<Object> json = createJsonRecursively(rootItem);
				
				
				
				JsArray<Object> json2 = new JsArray<>();
				
				treehelper.iterateTree(treeItem -> {
					treeItem.getParent();
					
					json2.push(treeItem.getValue().toJson());
					return false;
				});

				String jsonSourcecode = Global.JSON.stringify(json);
				jsonCodeCard.setCode(jsonSourcecode.replace("},", "},\n"));
			});
		

		root.style().setMinHeight("200px");
		root.style().setBackgroundColor("lightgrey");

		factory.addDnDHandlerFormElement(root);
		cardCanvas.appendChild(root);


		gridLayout.getLeftElement().appendChild(initElementsTree());
		gridLayout.getLeftElement().appendChild(initElementChooser());
		gridLayout.getRightElement().appendChild(initPropertiesPanel());
		gridLayout.getContentElement().appendChild(cardCanvas.asElement());
		gridLayout.getContentElement().appendChild(javaCodeCard);
		gridLayout.getContentElement().appendChild(jsonCodeCard);
		
		Card inputCard = Card.create("Paste JSON");
		TextArea textArea = TextArea.create("JSON");
		inputCard.appendChild(textArea);
		String j = "{\n" + 
				"  \"name\" : \"div_1000\",\n" + 
				"  \"type\" : \"div\",\n" + 
				"  \"children\" : [\n" + 
				"    {\n" + 
				"      \"name\" : \"card_1001\",\n" + 
				"      \"type\" : \"Card\",\n" + 
				"      \"children\" : [\n" + 
				"        {\n" + 
				"          \"name\" : \"div\",\n" + 
				"          \"type\" : \"CardHeader\",\n" + 
				"          \"children\" : [\n" + 
				"            {\n" + 
				"              \"name\" : \"div\",\n" + 
				"              \"type\" : \"CardHeaderBar\"\n" + 
				"            },\n" + 
				"            {\n" + 
				"              \"name\" : \"div\",\n" + 
				"              \"type\" : \"CardHeaderDescription\"\n" + 
				"            },\n" + 
				"            {\n" + 
				"              \"name\" : \"div\",\n" + 
				"              \"type\" : \"CardHeaderTitle\"\n" + 
				"            }\n" + 
				"          ]\n" + 
				"        },\n" + 
				"        {\n" + 
				"          \"name\" : \"div_1002\",\n" + 
				"          \"type\" : \"CardBody\",\n" + 
				"          \"children\" : [\n" + 
				"            {\n" + 
				"              \"name\" : \"tabsPanel_1003\",\n" + 
				"              \"type\" : \"TabsPanel\",\n" + 
				"              \"children\" : [\n" + 
				"                {\n" + 
				"                  \"name\" : \"tab_1004\",\n" + 
				"                  \"type\" : \"Tab\",\n" + 
				"                  \"children\" : [\n" + 
				"                    {\n" + 
				"                      \"name\" : \"div_1005\",\n" + 
				"                      \"type\" : \"div\"\n" + 
				"                    },\n" + 
				"                    {\n" + 
				"                      \"name\" : \"tabsPanel_1006\",\n" + 
				"                      \"type\" : \"TabsPanel\",\n" + 
				"                      \"children\" : [\n" + 
				"                        {\n" + 
				"                          \"name\" : \"tab_1007\",\n" + 
				"                          \"type\" : \"Tab\",\n" + 
				"                          \"children\" : [\n" + 
				"                            {\n" + 
				"                              \"name\" : \"div_1008\",\n" + 
				"                              \"type\" : \"div\"\n" + 
				"                            }\n" + 
				"                          ]\n" + 
				"                        }\n" + 
				"                      ]\n" + 
				"                    },\n" + 
				"                    {\n" + 
				"                      \"name\" : \"tab_1012\",\n" + 
				"                      \"type\" : \"Tab\",\n" + 
				"                      \"children\" : [\n" + 
				"                        {\n" + 
				"                          \"name\" : \"div_1013\",\n" + 
				"                          \"type\" : \"div\"\n" + 
				"                        }\n" + 
				"                      ]\n" + 
				"                    }\n" + 
				"                  ]\n" + 
				"                },\n" + 
				"                {\n" + 
				"                  \"name\" : \"tabsPanel_1009\",\n" + 
				"                  \"type\" : \"TabsPanel\",\n" + 
				"                  \"children\" : [\n" + 
				"                    {\n" + 
				"                      \"name\" : \"tab_1010\",\n" + 
				"                      \"type\" : \"Tab\",\n" + 
				"                      \"children\" : [\n" + 
				"                        {\n" + 
				"                          \"name\" : \"div_1011\",\n" + 
				"                          \"type\" : \"div\"\n" + 
				"                        }\n" + 
				"                      ]\n" + 
				"                    }\n" + 
				"                  ]\n" + 
				"                },\n" + 
				"                {\n" + 
				"                  \"name\" : \"tab_1014\",\n" + 
				"                  \"type\" : \"Tab\",\n" + 
				"                  \"children\" : [\n" + 
				"                    {\n" + 
				"                      \"name\" : \"div_1015\",\n" + 
				"                      \"type\" : \"div\",\n" + 
				"                      \"children\" : [\n" + 
				"                        {\n" + 
				"                          \"name\" : \"checkbox_1016\",\n" + 
				"                          \"type\" : \"CheckBox\"\n" + 
				"                        }\n" + 
				"                      ]\n" + 
				"                    }\n" + 
				"                  ]\n" + 
				"                }\n" + 
				"              ]\n" + 
				"            }\n" + 
				"          ]\n" + 
				"        }\n" + 
				"      ]\n" + 
				"    }\n" + 
				"  ]\n" + 
				"}";
		textArea.setValue(j);
		Button btnParse = Button.create("Parse");
		inputCard.appendChild(btnParse);
		btnParse.addClickListener(evt -> parse(textArea.getValue()));
		gridLayout.getContentElement().appendChild(inputCard);

		Button btnRefresh = Button.create("Repaint/Refresh");
		btnRefresh.addClickListener(listener -> rebuildCanvas());
		gridLayout.getRightElement().appendChild(btnRefresh);

		initElement(gridLayout.asElement());
	}

	private void parse(String json) {
		
		JsPropertyMap<Object> map= Js.uncheckedCast(Global.JSON.parse(json));
		String type = (String)map.get("type");
		String name = (String)map.get("name");
		root.remove();
		TreeItem<SchubecTreeElement> newElement = factory.createElementAndAddToCanvas(root, type, true);
		newElement.getValue().setName(name);
		
		root = (DominoElement<HTMLDivElement>) newElement.getValue().getElement();
		cardCanvas.appendChild(root);
        JsArray<JsPropertyMap<Object>> children = Js.uncheckedCast(map.get("children"));
        for(JsPropertyMap<Object> child : children.asList()) {
        	JSONParser parser = new JSONParser(factory);
        	parser.parseChildren(newElement.getValue().getElement(), child);
        }

		
	}




	private GridLayout initGridLayout() {
		GridLayout gridLayout = GridLayout.create()
				.style()
				.setHeight("500px").get();
		gridLayout.setGap("10px");
		gridLayout.setLeftSpan(SectionSpan._2, false, false);
		gridLayout.setRightSpan(SectionSpan._2, false, false);
		return gridLayout;
	}

	

	private Tree<SchubecTreeElement> initElementsTree() {
		
		elementsTree.appendChild(TreeItem.create("ROOT", new SchubecTreeElementDiv(root)));
		elementsTree.addItemClickListener(itemClickListener -> {

			// Notification.create("Selected").show();
			treehelper.iterateTree(treeItem -> {
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
				treehelper.find(selectedFormElement.getElement().getDominoId(), parentTreeItem-> {
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
		treehelper.iterateTree(treeItem -> {
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
		builderButtons.add(Button.create(SchubecTreeElementListGroup.TYPE));
		builderButtons.add(Button.create(SchubecTreeElementListItem.TYPE));

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





	

}

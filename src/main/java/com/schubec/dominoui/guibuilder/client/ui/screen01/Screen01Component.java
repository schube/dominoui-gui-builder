package com.schubec.dominoui.guibuilder.client.ui.screen01;

import java.util.LinkedList;
import java.util.List;

import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.collapsible.Accordion;
import org.dominokit.domino.ui.collapsible.AccordionPanel;
import org.dominokit.domino.ui.forms.CheckBox;
import org.dominokit.domino.ui.forms.IntegerBox;
import org.dominokit.domino.ui.forms.Select;
import org.dominokit.domino.ui.forms.SelectOption;
import org.dominokit.domino.ui.forms.TextArea;
import org.dominokit.domino.ui.forms.TextBox;
import org.dominokit.domino.ui.grid.GridLayout;
import org.dominokit.domino.ui.grid.SectionSpan;
import org.dominokit.domino.ui.icons.Icons;
import org.dominokit.domino.ui.notifications.Notification;
import org.dominokit.domino.ui.style.Color;
import org.dominokit.domino.ui.tree.ToggleTarget;
import org.dominokit.domino.ui.tree.Tree;
import org.dominokit.domino.ui.tree.TreeItem;
import org.dominokit.domino.ui.utils.BaseDominoElement;
import org.dominokit.domino.ui.utils.DominoElement;
import org.dominokit.domino.ui.utils.TextNode;
import org.jboss.gwt.elemento.core.EventType;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import com.google.gwt.core.client.GWT;
import com.schubec.dominoui.guibuilder.client.model.ElementCounter;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElement;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementBadge;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementButton;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementCard;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementCheckbox;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementChip;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementColumn;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementDiv;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementHTMLHeadingElement;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementListGroup;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementListItem;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementRoot;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementRow;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementTab;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementTabsPanel;
import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElementTextBox;
import com.schubec.dominoui.guibuilder.client.ui.screen01.code.CodeCard;
import com.schubec.dominoui.guibuilder.client.ui.screen01.code.JSONParser;
import com.schubec.dominoui.guibuilder.client.ui.screen01.code.SourcecodeResult;

import elemental2.core.Global;
import elemental2.core.JsArray;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

public class Screen01Component extends AbstractComponent<IScreen01Component.Controller, HTMLElement>
		implements IScreen01Component {
	private Card cardCanvas;
	private Tree<SchubecTreeElement> elementsTree;
	private TextBox name;
	private TextBox label;
	private SchubecTreeElement selectedFormElement;
	private DominoElement<HTMLDivElement> root = DominoElement.div();
	private DominoElement<HTMLDivElement> extendedProperties = DominoElement.div();
	private Card propertiesPanel;
	private CheckBox checkboxShowVisualHelpers;
	private final Factory factory;
	private final Treehelper treehelper;
	private SchubecTreeElementRoot rootTreeElement;
	
	public Screen01Component() {
		super();
		elementsTree = Tree.create("Elements", new SchubecTreeElement(root, "root")).enableFolding()
				.setAutoCollapse(false);
		elementsTree.setToggleTarget(ToggleTarget.ICON);
		treehelper = new Treehelper(elementsTree);
		factory = new Factory(treehelper);
		factory.setShowVisualHelpers(true);
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
			factory.setShowVisualHelpers(newValue);
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
				
				String jsonSourcecode = getCanvasAsJson();
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
		
		Card inputCard = Card.create("Paste JSON", "Paste JSON from clipboard to restore a layout you build earlier.");
		inputCard.setCollapsible();
		inputCard.hide();
		TextArea textArea = TextArea.create("JSON");
		inputCard.appendChild(textArea);
		String j = "{\"type\":\"root\",\"children\":[{\"type\":\"TabsPanel\",\"name\":\"tabsPanel_1000\",\"children\":[{\"type\":\"Tab\",\"name\":\"tab_1001\",\"children\":[{\"type\":\"div\",\"name\":\"div_1002\",\"children\":[{\"type\":\"Card\",\"name\":\"card_1005\",\"children\":[{\"type\":\"CardHeader\",\"children\":[{\"type\":\"CardHeaderBar\",\"children\":[{\"type\":\"Badge\",\"name\":\"badge_1007\"}]},\n" + 
				"{\"type\":\"CardHeaderDescription\"},\n" + 
				"{\"type\":\"CardHeaderTitle\"}]},\n" + 
				"{\"type\":\"CardBody\",\"children\":[{\"type\":\"TabsPanel\",\"name\":\"tabsPanel_1008\",\"children\":[{\"type\":\"Tab\",\"name\":\"tab_1009\",\"children\":[{\"type\":\"div\",\"name\":\"div_1010\"}]}]}]}]}]}]},\n" + 
				"{\"type\":\"Tab\",\"name\":\"tab_1003\",\"children\":[{\"type\":\"div\",\"name\":\"div_1004\",\"children\":[{\"type\":\"Chip\",\"name\":\"chip_1006\"}]}]}]}]}";
		textArea.setValue(j);
		Button btnParse = Button.create("Parse");
		inputCard.appendChild(btnParse);
		btnParse.addClickListener(evt -> {
			ElementCounter.reset();
			String src = parse(textArea.getValue());
			GWT.log(src);
		});
		gridLayout.getContentElement().appendChild(inputCard);

		Button btnRefresh = Button.create("Repaint/Refresh");
		btnRefresh.addClickListener(listener -> rebuildCanvas());
		gridLayout.getRightElement().appendChild(btnRefresh);

		initElement(gridLayout.asElement());
	}


	private String getCanvasAsJson() {
		TreeItem<SchubecTreeElement> rootItem = elementsTree.getTreeRoot().getSubItems().get(0);
		JsPropertyMap<Object> json = createJsonRecursively(rootItem);
		
		
		
		
		

		String jsonSourcecode = Global.JSON.stringify(json);
		return jsonSourcecode;
	}

	private String parse(String json) {
		StringBuffer javaSourcecode = new StringBuffer();
		JsPropertyMap<Object> map= Js.uncheckedCast(Global.JSON.parse(json));
		String type = (String)map.get("type");
		if(!type.equals("root")) {
			Notification.createDanger("JSON does not begin with root node").show();
			return "";
		}
		javaSourcecode.append(rootTreeElement.toSourcecode());
		root.clearElement();
		elementsTree.getSubItems().get(0).getSubItems().forEach(subitem -> subitem.remove());
		cardCanvas.appendChild(root);
        JsArray<JsPropertyMap<Object>> children = Js.uncheckedCast(map.get("children"));
        if(children!=null) {
        for(JsPropertyMap<Object> child : children.asList()) {
        	JSONParser parser = new JSONParser(factory);
        	SourcecodeResult javaSource = parser.parseChildren(root, child);
        	javaSourcecode.append(javaSource.getSourcecode());
        	if(javaSource.getName()!=null) {
        		javaSourcecode.append("root.appendChild("+javaSource.getName()+");\n");
			}
        }
        }
        return javaSourcecode.toString();
		
	}




	private GridLayout initGridLayout() {
		GridLayout gridLayout = GridLayout.create()
				.style()
				.setMinHeight("500px").get();
		gridLayout.setGap("10px");
		gridLayout.setLeftSpan(SectionSpan._4, false, false);
		gridLayout.setRightSpan(SectionSpan._2, false, false);
		return gridLayout;
	}

	

	private Tree<SchubecTreeElement> initElementsTree() {
		rootTreeElement = new SchubecTreeElementRoot(root);
		elementsTree.appendChild(TreeItem.create("ROOT", rootTreeElement));
		
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
			if(selectedFormElement.isRemoveable()) {
				extendedProperties.appendChild(btnRemove);
			}
		});
		return elementsTree;
	}

	private void rebuildTree() {
		// TODO Auto-generated method stub

	}

	private void rebuildCanvas() {
		ElementCounter.reset();
		parse(getCanvasAsJson());
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

		List<Button> builderButtons1 = new LinkedList<>();
		builderButtons1.add(Button.create(SchubecTreeElementButton.TYPE));
		builderButtons1.add(Button.create(SchubecTreeElementCheckbox.TYPE));
		builderButtons1.add(Button.create(SchubecTreeElementBadge.TYPE));
		builderButtons1.add(Button.create(SchubecTreeElementCard.TYPE));
		builderButtons1.add(Button.create(SchubecTreeElementChip.TYPE));
		builderButtons1.add(Button.create(SchubecTreeElementTextBox.TYPE));
		builderButtons1.add(Button.create(SchubecTreeElementTab.TYPE));
		builderButtons1.add(Button.create(SchubecTreeElementTabsPanel.TYPE));
		builderButtons1.add(Button.create(SchubecTreeElementRow.TYPE));
		builderButtons1.add(Button.create(SchubecTreeElementColumn.TYPE));
		builderButtons1.add(Button.create(SchubecTreeElementListGroup.TYPE));
		builderButtons1.add(Button.create(SchubecTreeElementListItem.TYPE));

		builderButtons1.forEach(button -> {
			initEditorElement(button);
			accordionPanel1.appendChild(button);
		});

		List<Button> builderButtons2 = new LinkedList<>();
		builderButtons2.add(Button.create(SchubecTreeElementDiv.TYPE));
		builderButtons2.add(Button.create(SchubecTreeElementHTMLHeadingElement.TYPE));
		
		builderButtons2.forEach(button -> {
			initEditorElement(button);
			accordionPanel2.appendChild(button);
		});
		
		return accordion;
	}

	private Card initPropertiesPanel() {
		propertiesPanel = Card.create("Properties");

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
		return propertiesPanel;
	}

	private void initEditorElement(Button button) {
		button.asElement().draggable = true;
		button.addEventListener(EventType.dragstart, evt -> {
			((elemental2.dom.DragEvent) evt).dataTransfer.setData("text/plain", button.getTextContent());
		});
	}





	

}

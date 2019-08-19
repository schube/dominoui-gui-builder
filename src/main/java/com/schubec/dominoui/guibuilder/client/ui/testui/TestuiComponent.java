package com.schubec.dominoui.guibuilder.client.ui.testui;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import java.lang.Override;
import java.lang.String;

import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.style.Style;
import org.dominokit.domino.ui.tabs.Tab;
import org.dominokit.domino.ui.tabs.TabsPanel;
import org.dominokit.domino.ui.utils.DominoElement;
import org.jboss.gwt.elemento.core.Elements;


public class TestuiComponent extends AbstractComponent<ITestuiComponent.Controller, HTMLElement> implements ITestuiComponent {

  public TestuiComponent() {
    super();
  }



  @Override
  public void render() {
	  Card root = Card.create("Form");
	  TabsPanel tabsPanel_1000 = TabsPanel.create();
	  Tab tab_1001 = Tab.create("");
	  DominoElement<HTMLDivElement> div_1002 = DominoElement.div();
	  Card card_1003 = Card.create("New Card");
	  Button button_1004 = Button.create("New Button");
	  div_1002.appendChild(button_1004);
	  card_1003.appendChild(div_1002);
	  div_1002.appendChild(card_1003);
	  tab_1001.appendChild(div_1002);
	  tabsPanel_1000.appendChild(tab_1001);
	  root.appendChild(tabsPanel_1000);

    initElement(root.asElement());
  }
}

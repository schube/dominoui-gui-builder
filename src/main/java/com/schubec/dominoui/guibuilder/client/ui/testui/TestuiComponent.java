package com.schubec.dominoui.guibuilder.client.ui.testui;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import java.lang.Override;
import java.lang.String;

import org.dominokit.domino.ui.badges.Badge;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.chips.Chip;
import org.dominokit.domino.ui.forms.CheckBox;
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
	  DominoElement<HTMLDivElement> div_1000 = DominoElement.div();
	  TabsPanel tabsPanel_1001 = TabsPanel.create();
	  Tab tab_1002 = Tab.create("New Tab");
	  DominoElement<HTMLDivElement> div_1003 = DominoElement.div();
	  Card card_1006 = Card.create("New Card");
	  Badge row_1007 = Badge.create("<not implemented>");
	  Column column_1008 = Column.span();
	  row_1007.appendChild(column_1008);
	  div_1003.appendChild(card_1006);
	  tab_1002.appendChild(div_1003);
	  tabsPanel_1001.appendChild(tab_1002);
	  Tab tab_1004 = Tab.create("New Tab");
	  DominoElement<HTMLDivElement> div_1005 = DominoElement.div();
	  tab_1004.appendChild(div_1005);
	  tabsPanel_1001.appendChild(tab_1004);
	  div_1000.appendChild(tabsPanel_1001);
	  
    initElement(div_1000.asElement());
  }
}

package com.schubec.dominoui.guibuilder.client.ui.testui;

import org.dominokit.domino.ui.badges.Badge;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.chips.Chip;
import org.dominokit.domino.ui.tabs.Tab;
import org.dominokit.domino.ui.tabs.TabsPanel;
import org.dominokit.domino.ui.utils.DominoElement;

import com.github.nalukit.nalu.client.component.AbstractComponent;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;


public class TestuiComponent extends AbstractComponent<ITestuiComponent.Controller, HTMLElement> implements ITestuiComponent {

  public TestuiComponent() {
    super();
  }



  @Override
  public void render() {
	  DominoElement<HTMLDivElement> root = DominoElement.div();
	  TabsPanel tabsPanel_1000 = TabsPanel.create();
	  Tab tab_1001 = Tab.create("New Tab");
	  DominoElement<HTMLDivElement> div_1002 = DominoElement.div();
	  Card card_1005 = Card.create("New Card");
	  Badge badge_1007 = Badge.create("New badge");
	  TabsPanel tabsPanel_1008 = TabsPanel.create();
	  Tab tab_1009 = Tab.create("New Tab");
	  DominoElement<HTMLDivElement> div_1010 = DominoElement.div();
	  tab_1009.appendChild(div_1010);
	  tabsPanel_1008.appendChild(tab_1009);
	  div_1002.appendChild(card_1005);
	  tab_1001.appendChild(div_1002);
	  tabsPanel_1000.appendChild(tab_1001);
	  Tab tab_1003 = Tab.create("New Tab");
	  DominoElement<HTMLDivElement> div_1004 = DominoElement.div();
	  Chip chip_1006 = Chip.create("New Chip");
	  div_1004.appendChild(chip_1006);
	  tab_1003.appendChild(div_1004);
	  tabsPanel_1000.appendChild(tab_1003);
	  root.appendChild(tabsPanel_1000);
	  
    initElement(tabsPanel_1000.element());
  }
}

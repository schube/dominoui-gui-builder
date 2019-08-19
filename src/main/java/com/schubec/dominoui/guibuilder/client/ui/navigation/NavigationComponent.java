package com.schubec.dominoui.guibuilder.client.ui.navigation;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import elemental2.dom.HTMLElement;
import java.lang.Override;
import org.dominokit.domino.ui.icons.Icons;
import org.dominokit.domino.ui.tree.Tree;
import org.dominokit.domino.ui.tree.TreeItem;


public class NavigationComponent extends AbstractComponent<INavigationComponent.Controller, HTMLElement> implements INavigationComponent {
  private TreeItem Screen01Item;
  private TreeItem Screen02Item;

  public NavigationComponent() {
    super();
  }

  @Override
  public void render() {
    this.Screen01Item = TreeItem.create("GUI Builder", Icons.ALL.list())
                    .addClickListener(e -> getController().doNavigateTo("screen01"));
    this.Screen02Item = TreeItem.create("Test UI", Icons.ALL.list())
            .addClickListener(e -> getController().doNavigateTo("testui"));
    Tree tree = Tree.create("Navigation");
    tree.appendChild(Screen01Item);
    tree.appendChild(Screen02Item);
    initElement(tree.asElement());
  }
}

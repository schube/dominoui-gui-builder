package com.schubec.dominoui.guibuilder.client.ui.screen01;

import java.util.List;

import org.dominokit.domino.ui.tree.Tree;
import org.dominokit.domino.ui.tree.TreeItem;

import com.schubec.dominoui.guibuilder.client.model.editor.elements.SchubecTreeElement;

public class Treehelper {
	private final Tree<SchubecTreeElement> elementsTree;
	
	public Treehelper(Tree<SchubecTreeElement> elementsTree) {
		
		if(elementsTree==null) {
			throw new RuntimeException("elementsTree is null");
		}
		this.elementsTree = elementsTree;
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

	void iterateTree(IYield<TreeItem<SchubecTreeElement>> callback) {
		iterateSubitems(callback, elementsTree.getSubItems());
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

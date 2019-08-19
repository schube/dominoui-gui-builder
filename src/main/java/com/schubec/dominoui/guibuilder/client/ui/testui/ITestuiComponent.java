package com.schubec.dominoui.guibuilder.client.ui.testui;

import com.github.nalukit.nalu.client.component.IsComponent;

import elemental2.dom.HTMLElement;

public interface ITestuiComponent extends IsComponent<ITestuiComponent.Controller, HTMLElement> {

  interface Controller extends IsComponent.Controller {
  }
}

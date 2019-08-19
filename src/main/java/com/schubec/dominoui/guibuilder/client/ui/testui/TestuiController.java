package com.schubec.dominoui.guibuilder.client.ui.testui;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import com.schubec.dominoui.guibuilder.client.GuiBuilderContext;

import elemental2.dom.HTMLElement;

@Controller(
    route = "/application/testui",
    selector = "content",
    componentInterface = ITestuiComponent.class,
    component = TestuiComponent.class
)
public class TestuiController extends AbstractComponentController<GuiBuilderContext, ITestuiComponent, HTMLElement> implements ITestuiComponent.Controller {

  public TestuiController() {
  }

  
}

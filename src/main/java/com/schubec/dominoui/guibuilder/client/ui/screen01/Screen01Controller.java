package com.schubec.dominoui.guibuilder.client.ui.screen01;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import com.schubec.dominoui.guibuilder.client.GuiBuilderContext;

import elemental2.dom.HTMLElement;

/**
 * Copyright (C) 2018 - 2019 Frank Hossfeld <frank.hossfeld@googlemail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@Controller(
    route = "/application/guibuilder",
    selector = "content",
    componentInterface = IScreen01Component.class,
    component = Screen01Component.class
)
public class Screen01Controller extends AbstractComponentController<GuiBuilderContext, IScreen01Component, HTMLElement> implements IScreen01Component.Controller {

  public Screen01Controller() {
  }

  @Override
  public void start() {
  }
}

package com.schubec.dominoui.guibuilder.client.ui.shell.application;

import com.github.nalukit.nalu.client.component.AbstractShell;
import com.github.nalukit.nalu.client.component.annotation.Shell;
import com.schubec.dominoui.guibuilder.client.GuiBuilderContext;
import elemental2.dom.CSSProperties;
import java.lang.Override;
import org.dominokit.domino.ui.layout.Layout;
import org.dominokit.domino.ui.style.ColorScheme;

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
@Shell("application")
public class ApplicationShell extends AbstractShell<GuiBuilderContext> {
  private Layout layout;

  public ApplicationShell() {
    super();
  }

  @Override
  public void attachShell() {
    layout = Layout.create("Domino-UI GUI Builder")
                                  .show(ColorScheme.INDIGO);
    layout.showFooter()
                  .fixFooter()
                  .getFooter()
                  .element().style.minHeight = CSSProperties.MinHeightUnionType.of("42px");
    layout.getFooter().setId("footer");
    layout.getLeftPanel().setId("navigation");
    layout.getContentPanel().setId("content");
  }

  @Override
  public void detachShell() {
    this.layout.remove();
  }
}

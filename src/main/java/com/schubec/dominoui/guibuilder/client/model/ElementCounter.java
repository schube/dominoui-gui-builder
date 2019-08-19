package com.schubec.dominoui.guibuilder.client.model;

import java.lang.String;


public class ElementCounter {
  private static int currentElement = 1000;


  public static String get() {
    return new String(""+currentElement++);
  }
}

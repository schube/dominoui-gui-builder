package com.schubec.dominoui.guibuilder.client.model;

import java.lang.String;

public class ElementCounter {
	private static final int initialValue = 1000;
	private static int currentElement = initialValue;

	public static String get() {
		return new String("" + currentElement++);
	}

	public static void reset() {
		currentElement = initialValue;

	}
}

package com.schubec.dominoui.guibuilder.client.ui.screen01.code;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class PR {
    public static native String prettyPrintOne(String code, String langExtension, boolean lineNumbers);
}
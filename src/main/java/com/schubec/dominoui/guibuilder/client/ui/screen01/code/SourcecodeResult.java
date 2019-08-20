package com.schubec.dominoui.guibuilder.client.ui.screen01.code;

public class SourcecodeResult {
	private String name;
	private StringBuffer sourcecode = new StringBuffer();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public StringBuffer getSourcecode() {
		return sourcecode;
	}
	public void appendSourcecode(String sourcecode) {
		this.sourcecode.append(sourcecode);
		
	}
	public void appendSourcecode(StringBuffer sourcecode) {
		this.sourcecode.append(sourcecode);
		
	}
}

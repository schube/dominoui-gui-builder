package com.schubec.dominoui.guibuilder.client.ui.screen01;

public class EditorProperty<T> {
	private String name;
	private T value;
	private Datatype datatype;
	private boolean isDefaultValue = true;
	
	public EditorProperty(String name, Datatype datatype, T value) {
		this.name=name;
		this.datatype=datatype;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Datatype getDatatype() {
		return datatype;
	}
	public void setDatatype(Datatype datatype) {
		this.datatype = datatype;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	public boolean isDefaultValue() {
		return isDefaultValue;
	}
	public void setDefaultValue(boolean isDefaultValue) {
		this.isDefaultValue = isDefaultValue;
	}
}

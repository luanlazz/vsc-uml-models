package com.vsc.demo.uml.models._class;

public class ClassAttribute {
	private String id;
	private String name;
    private String type;
    private String visibility;
    private Object value;
    private Object values[];
    private boolean isClass;
    private boolean isEnum;
    private boolean Static;
    private boolean isCollection;


    public ClassAttribute() {

    }

    public ClassAttribute(String name, String type, Object value, boolean isClass, boolean isEnum, boolean isCollection) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.isClass = isClass;
        this.isEnum = isEnum;
        this.isCollection = isCollection;
    }


    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean getIsCollection() {
        return isCollection;
    }

    public boolean isStatic() {
        return Static;
    }

    public void setStatic(boolean aStatic) {
        Static = aStatic;
    }

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
    }

    public boolean isClass() {
        return isClass;
    }

    public void setClass(boolean aClass) {
        isClass = aClass;
    }

    public boolean getIsClass() {
        return isClass;
    }

    public boolean isEnum() {
        return isEnum;
    }

    public void setEnum(boolean anEnum) {
        isEnum = anEnum;
    }

    public boolean getIsEnum() {
        return isEnum;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }

    @Override
    public String toString() {
    	String output =
    		  "\nId: " + this.getId()
    		+ "\nName: " + this.getName()
    		+ "\ntype: " + this.getType()
    		+ "\nvisibility: " + this.getVisibility()
    		+ "\nvalue: " + this.getValue()
    		+ "\nisClass: " + this.isClass()
    		+ "\nisEnum: " + this.isEnum()
    		+ "\nStatic: " + this.isStatic()
    		+ "\nisCollection: " + this.isCollection();

        return output;
    }
}

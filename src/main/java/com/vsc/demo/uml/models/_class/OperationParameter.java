package com.vsc.demo.uml.models._class;

public class OperationParameter {
	private String id;
    private String name;
    private String type;
    private String visibility;
    private String direction;
    private Object value;
    private boolean Class;
    private boolean collection;

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

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isClass() {
        return Class;
    }

    public void setClass(boolean aClass) {
        Class = aClass;
    }

    public boolean isCollection() {
        return collection;
    }

    public void setCollection(boolean collection) {
        this.collection = collection;
    }

    @Override
    public String toString() {
    	String output =
    		  "\nId: " + this.getId()
    		+ "\nname: " + this.getName()
    		+ "\ntype: " + this.getType()
    		+ "\nvisibility: " + this.getVisibility()
    		+ "\ndirection: " + this.getDirection()
    		+ "\nvalue: " + this.getValue()
    		+ "\nClass: " + this.isClass()
    		+ "\ncollection: " + this.isCollection();

        return output;
    }
}

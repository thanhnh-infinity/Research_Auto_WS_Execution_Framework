package nmsu.edu;

import java.util.ArrayList;

public class ComplexDataType {
	private String name;
	private ArrayList<Parameter> complexObjectList;

	public ComplexDataType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Parameter> getComplexObjectList() {
		return complexObjectList;
	}

	public void setComplexObjectList(ArrayList<Parameter> complexObjectList) {
		this.complexObjectList = complexObjectList;
	}

	public ComplexDataType(String name, ArrayList<Parameter> complexObjectList) {
		super();
		this.name = name;
		this.complexObjectList = complexObjectList;
	}

}

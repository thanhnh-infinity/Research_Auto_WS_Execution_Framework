package nmsu.edu;

public class SimpleDataType {
	private String name;
	private String prefixType;
	private String subfixType;
	private String finalType;
	public SimpleDataType() {
		this.name = "";
		this.prefixType = "";
		this.subfixType = "";
		this.finalType = "";
	}
	
	
	public SimpleDataType(String name, String prefixType, String subfixType, String finalType) {
		super();
		this.name = name;
		this.prefixType = prefixType;
		this.subfixType = subfixType;
		this.finalType = finalType;
	}
	
	public String getFinalType() {
		return finalType;
	}


	public void setFinalType(String finalType) {
		this.finalType = finalType;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrefixType() {
		return prefixType;
	}
	public void setPrefixType(String prefixType) {
		this.prefixType = prefixType;
	}
	public String getSubfixType() {
		return subfixType;
	}
	public void setSubfixType(String subfixType) {
		this.subfixType = subfixType;
	}
	
	
}
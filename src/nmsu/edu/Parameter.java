package nmsu.edu;

public class Parameter {
	private String name;
	private String type;
	
	public Parameter(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}
	public Parameter() {
		super();
		// TODO Auto-generated constructor stub
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
	
}



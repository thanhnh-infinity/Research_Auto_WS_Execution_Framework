package nmsu.edu;

import java.awt.List;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class WebServiceMethod {
	private String methodName;
	private String API_ROOT;
	private String API_ENDPOINT;
	private int numberParams;
	private String content_type;
	private String method_type;
	private ArrayList<Parameter> params;
	private ArrayList<String> paramsType;
	private ArrayList<Parameter> outputs;
	private String paramSeparator;
	private String inputContentType;
	private String outputContentType;

	private ArrayList<ComplexDataType> detailComplexDataType;

	public ArrayList<SimpleDataType> getDetailSimpleDataType() {
		return detailSimpleDataType;
	}

	public void setDetailSimpleDataType(
			ArrayList<SimpleDataType> detailSimpleDataType) {
		this.detailSimpleDataType = detailSimpleDataType;
	}

	private ArrayList<SimpleDataType> detailSimpleDataType;
	
	public JSONObject buildJSONOperation() {
		JSONObject obj = new JSONObject();
	    obj.put("operation_name", this.methodName);
	    obj.put("number_input_params", new Integer(this.numberParams));
	    obj.put("whttp:contentEncodingDefault", this.content_type);
	    obj.put("whttp:inputSerialization", this.inputContentType);
	    obj.put("whttp:outputSerialization", this.outputContentType);
	    obj.put("whttp:method", this.method_type);
	    obj.put("whttp:method", this.method_type);
	    obj.put("whttp:method", this.method_type);
	    obj.put("whttp:method", this.method_type);
	    
	    JSONArray jsonArrayParams = new JSONArray();
	    for (int i = 0; i < params.size(); i++) {
	    	JSONObject input = new JSONObject();
	    	input.put("name", params.get(i).getName());
	    	input.put("type", params.get(i).getType());
	    	
	    	String source = "XMLSchema";
	    	if (isComplexType(params.get(i).getType())){
	    		source = "complexType";
	    	} else if (isSimpleType(params.get(i).getType())){
	    		source = "simpleType";
	    	}
	    	input.put("source", source);
	    	jsonArrayParams.add(input);
		}
	    
	    obj.put("input_params", jsonArrayParams);
	    
	    
	    jsonArrayParams = new JSONArray();
	    for (int i = 0; i < outputs.size(); i++) {
	    	JSONObject output = new JSONObject();
	    	output.put("name", outputs.get(i).getName());
	    	output.put("type", outputs.get(i).getType());
	    	String source = "XMLSchema";
	    	if (isComplexType(outputs.get(i).getType())){
	    		source = "complexType";
	    	} else if (isSimpleType(outputs.get(i).getType())){
	    		source = "simpleType";
	    	}
	    	output.put("source", source);
	    	jsonArrayParams.add(output);
		}
	    
	    obj.put("output_params", jsonArrayParams);
	    
	    return obj;
	}

	public void print() {
		System.out.println("==================");
		System.out.println("Name : " + this.methodName);
		System.out.println("Number Input Params : " + this.numberParams);
		System.out.println("Content-Type Default : " + this.content_type);
		System.out.println("Content-Type INPUT : " + this.inputContentType);
		System.out.println("Content-Type OUTPUT : " + this.outputContentType);
		System.out.println("Method-Type : " + this.method_type);
		System.out.println("INPUT Parameter and Type : ");
		for (int i = 0; i < params.size(); i++) {
			System.out.println("     " + params.get(i).getName() + "    "
					+ params.get(i).getType());
		}
		// System.out.println("   Parameter Separator : " +
		// this.paramSeparator);
		System.out.println("OUTPUT Parameter and Type : ");
		for (int i = 0; i < outputs.size(); i++) {
			System.out.println("     " + outputs.get(i).getName() + "    "
					+ outputs.get(i).getType());
			if (isComplexType(outputs.get(i).getType())) {
				printDetailComplexTypeByNameType(outputs.get(i).getType(),8);
			}
		}
	}
	
	public boolean isSimpleType(String type){
		for (int k = 0; k < detailSimpleDataType.size(); k++) {
			if (type.toUpperCase()
					.trim()
					.equalsIgnoreCase(
							detailSimpleDataType.get(k).getFinalType().toString()
									.toUpperCase())) {
				return true;
			}
		}
		return false;
	}

	public boolean isComplexType(String type) {
		for (int k = 0; k < detailComplexDataType.size(); k++) {
			if (type.toUpperCase()
					.trim()
					.equalsIgnoreCase(
							detailComplexDataType.get(k).getName().toString()
									.toUpperCase())) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Parameter> getDetailComplexTypeByNameType(String nameType) {
		for (int k = 0; k < detailComplexDataType.size(); k++) {
			if (nameType
					.toUpperCase()
					.trim()
					.equalsIgnoreCase(
							detailComplexDataType.get(k).getName().toString()
									.toUpperCase())) {
				return detailComplexDataType.get(k).getComplexObjectList();
			}
		}
		return null;
	}

	public void printDetailComplexTypeByNameType(String nameType, int numberSpace) {
		ArrayList<Parameter> pList = getDetailComplexTypeByNameType(nameType);
		System.out.println();
		for(int i = 0 ; i < numberSpace ; i++){
			System.out.print(" ");
		}
		
		System.out.print("ComplexType : " + nameType);
		System.out.println();
		for (int i = 0; i < pList.size(); i++) {
			String name_Type = pList.get(i).getName();
			String type_Type = pList.get(i).getType();
			System.out.println();
			for(int j = 0 ; j < numberSpace + 5 ; j++){
				System.out.print(" ");
			}
			System.out.print(name_Type + "    " + type_Type);
			if (isComplexType(type_Type) == true) {
			    printDetailComplexTypeByNameType(type_Type,numberSpace+2);
			} 
		}
		System.out.println();
	}

	public ArrayList<Parameter> getOutputs() {
		return outputs;
	}

	public void setOutputs(ArrayList<Parameter> outputs) {
		this.outputs = outputs;
	}

	public ArrayList<ComplexDataType> getDetailComplexDataType() {
		return detailComplexDataType;
	}

	public void setDetailComplexDataType(
			ArrayList<ComplexDataType> detailComplexDataType) {
		this.detailComplexDataType = detailComplexDataType;
	}

	public String getInputContentType() {
		return inputContentType;
	}

	public void setInputContentType(String inputContentType) {
		this.inputContentType = inputContentType;
	}

	public String getOutputContentType() {
		return outputContentType;
	}

	public void setOutputContentType(String outputContentType) {
		this.outputContentType = outputContentType;
	}

	public String getParamSeparator() {
		return paramSeparator;
	}

	public void setParamSeparator(String paramSeparator) {
		this.paramSeparator = paramSeparator;
	}

	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	public String getMethod_type() {
		return method_type;
	}

	public void setMethod_type(String method_type) {
		this.method_type = method_type;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getAPI_ROOT() {
		return API_ROOT;
	}

	public void setAPI_ROOT(String aPI_ROOT) {
		API_ROOT = aPI_ROOT;
	}

	public String getAPI_ENDPOINT() {
		return API_ENDPOINT;
	}

	public void setAPI_ENDPOINT(String aPI_ENDPOINT) {
		API_ENDPOINT = aPI_ENDPOINT;
	}

	public int getNumberParams() {
		return numberParams;
	}

	public void setNumberParams(int numberParams) {
		this.numberParams = numberParams;
	}

	public ArrayList<Parameter> getParams() {
		return params;
	}

	public void setParams(ArrayList<Parameter> params) {
		this.params = params;
	}

	public ArrayList<String> getParamsType() {
		return paramsType;
	}

	public void setParamsType(ArrayList<String> paramsType) {
		this.paramsType = paramsType;
	}

}

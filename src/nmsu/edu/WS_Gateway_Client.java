package nmsu.edu;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.woden.WSDLException;
import org.apache.woden.WSDLFactory;
import org.apache.woden.WSDLReader;
import org.apache.woden.XMLElement;
import org.apache.woden.schema.Schema;
import org.apache.woden.wsdl20.Binding;
import org.apache.woden.wsdl20.Description;
import org.apache.woden.wsdl20.ElementDeclaration;
import org.apache.woden.wsdl20.Interface;
import org.apache.woden.wsdl20.InterfaceMessageReference;
import org.apache.woden.wsdl20.Service;
import org.apache.woden.wsdl20.TypeDefinition;
import org.apache.woden.wsdl20.xml.DescriptionElement;
import org.apache.woden.wsdl20.xml.ServiceElement;
import org.apache.woden.wsdl20.xml.TypesElement;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;

public class WS_Gateway_Client {
	private String WSDL_URL;
	private ArrayList<WebServiceMethod> webServicesMethod;
	
	private static boolean checkTypeInXMLSchemal(String type){
		
		if (type.substring(0, 3).toUpperCase().trim().equalsIgnoreCase(SYSTEM_PARAM.XML_SCHEMAL_PREFIX)){
			return true;
		} else {
			return false;
		}
	}
	
	public static int checkComplexType(XMLElement element){
		XMLElement[] elements = element.getChildElements();
		if (elements.length == 1 && elements[0].getLocalName().equalsIgnoreCase(SYSTEM_PARAM.EL_COMPLEX_TYPE)){
			XMLElement[] complexTypeElements = elements[0].getChildElements();
			if (complexTypeElements.length == 1 && complexTypeElements[0].getLocalName().equalsIgnoreCase(SYSTEM_PARAM.EL_SEQUENCE)){
				XMLElement[] sequenceElements = complexTypeElements[0].getChildElements();
				if (sequenceElements.length == 1 && sequenceElements[0].getLocalName().equalsIgnoreCase(SYSTEM_PARAM.EL_ELEMENT)){
					if (sequenceElements[0].getAttributeValue("type") != null && !sequenceElements[0].getAttributeValue("type").equalsIgnoreCase("")){
						if (sequenceElements[0].getAttributeValue("type").equalsIgnoreCase(SYSTEM_PARAM.TYPE_LONG)){
							return 1; // List of long
						} else if (sequenceElements[0].getAttributeValue("type").equalsIgnoreCase(SYSTEM_PARAM.TYPE_INT)) {
							return 2; // List of int
						} else if (sequenceElements[0].getAttributeValue("type").equalsIgnoreCase(SYSTEM_PARAM.TYPE_STRING)){
							return 3; // List of string
						} else if (sequenceElements[0].getAttributeValue("type").equalsIgnoreCase(SYSTEM_PARAM.TYPE_DOUBLE)){
							return 4; // List of double
						}
					} else {
						return -1;
					}
				}
			}
		} else {
			return -1;
		}
		return -1;
	}
	
	public static boolean checkExistedInArrayListSimpleType(String name, ArrayList<SimpleDataType> list){
		for(int k = 0 ; k < list.size() ; k++){
			  if (name.toUpperCase().trim().equalsIgnoreCase(list.get(k).getName().toString().toUpperCase())){
				  return true;
			  }
		}
		return false;
	}
	
	public static boolean checkExistedInArrayListComplexType(String name, ArrayList<ComplexDataType> list){
		for(int k = 0 ; k < list.size() ; k++){
			  if (name.toUpperCase().trim().equalsIgnoreCase(list.get(k).getName().toString().toUpperCase())){
				  return true;
			  }
		}
		return false;
	}
	
	public static String getFinalTypeInSimpleTypeLost(String name, ArrayList<SimpleDataType> list){
		for(int k = 0 ; k < list.size() ; k++){
			  if (name.toUpperCase().trim().equalsIgnoreCase(list.get(k).getName().toString().toUpperCase())){
				  return list.get(k).getFinalType();
			  }
		}
		return SYSTEM_PARAM.TYPE_UNKNOWN;
	}
	
	public static String getFinalTypeInComplexTypeList(String name, ArrayList<ComplexDataType> list){
		for(int k = 0 ; k < list.size() ; k++){
			  if (name.toUpperCase().trim().equalsIgnoreCase(list.get(k).getName().toString().toUpperCase())){
				  String aName = list.get(k).getName().toString();
				  return aName;
			  }
		}
		return null;
	}
	
	
	
	public static String getTypeOfInputParameter(String paramName, DescriptionElement descElem, ArrayList<SimpleDataType> simpleTypeArrayList,ArrayList<ComplexDataType> complexTypeArrayList ){
		 TypesElement types = descElem.getTypesElement(); 
		 Schema[] schemas = types.getSchemas(); 
		 for(int i = 0 ; i < schemas.length ; i++){
		      XMLElement xmlelement = schemas[i].getXMLElement(); 
		      XMLElement[] elements = xmlelement.getChildElements();
		      /* Parser and Read XML Schemal data */
		      for(int j = 0 ; j < elements.length ; j++){
		    	  if (elements[j].getLocalName().toString().equalsIgnoreCase(SYSTEM_PARAM.XML_SCHEMAL_ELEMENT_LOCAL_NAME)){
			          if(elements[j].getAttributeValue("name").equalsIgnoreCase(paramName)){  
			        	  if (elements[j].getAttributeValue("type") != null && !elements[j].getAttributeValue("type").equalsIgnoreCase("")) {
			        		  String xmlType =  elements[j].getAttributeValue("type").toString().trim().toUpperCase();
			        		  /* Case 1 : type is xs XML Schemal type */
			        		  if (checkTypeInXMLSchemal(xmlType)==true){
			        			  return elements[j].getAttributeValue("type"); 
			        		  } else {
			        			  /* Case 2 : type is simpleType */
			        			  if (checkExistedInArrayListSimpleType(xmlType, simpleTypeArrayList) == true){
			        				  return getFinalTypeInSimpleTypeLost(xmlType,simpleTypeArrayList);
			        			  } else {
			        				  /* Case 3 : Type is complexType */
			        				  if (checkExistedInArrayListComplexType(xmlType, complexTypeArrayList) == true){
				        				  return getFinalTypeInComplexTypeList(xmlType,complexTypeArrayList);
			        				  }
			        			  }
			        		  }
			        	  
			        	  } else {
			        		 return SYSTEM_PARAM.TYPE_UNKNOWN;
			        	  }
			          }
		    	  } 
		      } 
		 }
		 return null;
	}
	
	public static ArrayList<WebServiceMethod> parse(String wsdlurl) {
		ArrayList<WebServiceMethod> lstMethod = new ArrayList<WebServiceMethod>();
		ArrayList<SimpleDataType> simpleTypeArrayList = new ArrayList<SimpleDataType>();
	    ArrayList<ComplexDataType> complexTypeArrayList = new ArrayList<ComplexDataType>();
	    
	    
		try {
			WSDLFactory factory = WSDLFactory.newInstance();
			WSDLReader reader = factory.newWSDLReader();
			reader.setFeature(WSDLReader.FEATURE_VALIDATION, true);
			Description descComp = reader.readWSDL(wsdlurl);
			DescriptionElement descElem = descComp.toElement();
			
			/* Parser Paramas */
			TypesElement types = descElem.getTypesElement(); 
			Schema[] schemas = types.getSchemas(); 
			for(int i = 0 ; i < schemas.length ; i++){
			      XMLElement xmlelement = schemas[i].getXMLElement(); 
			      XMLElement[] elements = xmlelement.getChildElements();
			      
			      /* Parser and Read Simple Type firstly */
			      for (int j = 0 ; j < elements.length ; j++){
			    	  try {
				    	  if (elements[j].getLocalName().toString().equalsIgnoreCase(SYSTEM_PARAM.XML_SCHEMAL_SIMPLE_TYPE_LOCAL_NAME)){
				    		  XMLElement[] smallXML  = elements[j].getChildElements();
				    		  String name = elements[j].getAttributeValue("name");
				    		  String firstType = smallXML[0].getLocalName();
				    		  String secondType = smallXML[0].getAttributeValue("itemType");
				    		  String finalType = "";
				    		  if (firstType.equalsIgnoreCase(SYSTEM_PARAM.EL_LIST)){
				    			  if (secondType.equalsIgnoreCase(SYSTEM_PARAM.TYPE_INT)){
				    				  finalType = SYSTEM_PARAM.TYPE_LIST_OF_INT;
				    			  } else if (secondType.equalsIgnoreCase(SYSTEM_PARAM.TYPE_BOOLEAN)) {
				    				  finalType = SYSTEM_PARAM.TYPE_LIST_OF_BOOLEAN;
				    			  } else if (secondType.equalsIgnoreCase(SYSTEM_PARAM.TYPE_DOUBLE)) {
				    				  finalType = SYSTEM_PARAM.TYPE_LIST_OF_DOUBLE; 
				    			  } else if (secondType.equalsIgnoreCase(SYSTEM_PARAM.TYPE_LONG)) {
				    				  finalType = SYSTEM_PARAM.TYPE_LIST_OF_LONG;
				    			  } else if (secondType.equalsIgnoreCase(SYSTEM_PARAM.TYPE_STRING)) {
				    				  finalType = SYSTEM_PARAM.TYPE_LIST_OF_STRING;
				    			  } else {
				    				  finalType = SYSTEM_PARAM.TYPE_UNKNOWN;
				    			  }
				    		  } else {
				    			  finalType = SYSTEM_PARAM.TYPE_UNKNOWN;
				    		  }
				    		  SimpleDataType simpleType = new SimpleDataType(name, firstType, secondType,finalType);
				    		  simpleTypeArrayList.add(simpleType);
				    	  }
			    	  } catch(Exception ex){
			    		  continue;
			    	  }
			      }
			     
			      /* Parser and Read Simple Type firstly */
			      for (int j = 0 ; j < elements.length ; j++){
			    	  try {
				    	  if (elements[j].getLocalName().toString().equalsIgnoreCase(SYSTEM_PARAM.XML_SCHEMAL_COMPLEX_TYPE_LOCAL_NAME)){
				    		  ComplexDataType complextTypeMain = new ComplexDataType(); 		  
				    		  String ctName = elements[j].getAttributeValue("name");
				    		 
				    		  XMLElement[] smallElementsXML = elements[j].getChildElements()[0].getChildElements();
				    		  ArrayList<Parameter> lstParams = new ArrayList<Parameter>();
				    		  for(int k = 0 ; k < smallElementsXML.length ; k++){
				    			  Parameter param = new Parameter();
				    			  param.setName(smallElementsXML[k].getAttributeValue("name"));
				    			  param.setType(smallElementsXML[k].getAttributeValue("type"));
				    			  lstParams.add(param);
				    		  }
				    		  complextTypeMain.setComplexObjectList(lstParams);
				    		  complextTypeMain.setName(ctName);
				    		  complexTypeArrayList.add(complextTypeMain);
				    	  }
			    	  } catch(Exception  ex){
			    		  continue;
			    	  }
			      }
			      
			 }
			 /* End parser params */
			

			ServiceElement[] services = descElem.getServiceElements();
			ServiceElement ser = services[0];
			System.out.println("Service Name : " + ser.getName().toString());

			Service[] allServices = descComp.getServices();
			
			String API_ROOT = allServices[0].getEndpoints()[0].getAddress()
					.toString();
			
			Binding[] allBindings = descComp.getBindings();

			int numberBindings = allBindings.length;
			for (int i = 0; i < numberBindings; i++) {
				Binding binding = allBindings[i];
				Interface bInterface = binding.getInterface();
				int numberOperations = binding.getBindingOperations().length;
				for (int j = 0; j < numberOperations; j++) {
					// System.out.println("=====================");
					WebServiceMethod method = new WebServiceMethod();
					ArrayList<Parameter> params = new ArrayList<Parameter>();
					ArrayList<Parameter> outputs = new ArrayList<Parameter>();
					String API_ENDPOINT = "";
					try {
						API_ENDPOINT = binding.getBindingOperations()[j]
								.getExtensionProperties()[0].getContent()
								.toString();
					} catch (Exception ex) {
						API_ENDPOINT = ser.getEndpointElements()[0]
								.getAddress().toString();
					}
					String methodName = binding.getBindingOperations()[j]
							.getInterfaceOperation().getName().getLocalPart();
					String methodtype = "";
					String contentType = "";
					String inputContentType = "";
					String outputContentType = "";
					String parameterSeparator = "";
					int numberProperties = binding.getBindingOperations()[j]
							.getExtensionProperties().length;
					for (int k = 0; k < numberProperties; k++) {
						
						
						if (binding.getBindingOperations()[j]
								.getExtensionProperties()[k].getName()
								.toString().equalsIgnoreCase("HTTP METHOD")) {
							methodtype = binding.getBindingOperations()[j]
									.getExtensionProperties()[k].getContent()
									.toString();
						}
						if (binding.getBindingOperations()[j]
								.getExtensionProperties()[k]
								.getName()
								.toString()
								.equalsIgnoreCase(
										"HTTP CONTENT ENCODING DEFAULT")) {
							contentType = binding.getBindingOperations()[j]
									.getExtensionProperties()[k].getContent()
									.toString();
						}
						if (binding.getBindingOperations()[j]
								.getExtensionProperties()[k]
								.getName()
								.toString()
								.equalsIgnoreCase(
										"HTTP INPUT SERIALIZATION")) {
							inputContentType = binding.getBindingOperations()[j]
									.getExtensionProperties()[k].getContent()
									.toString();
							
							
						}
						if (binding.getBindingOperations()[j]
								.getExtensionProperties()[k]
								.getName()
								.toString()
								.equalsIgnoreCase(
										"HTTP OUTPUT SERIALIZATION")) {
							outputContentType = binding.getBindingOperations()[j]
									.getExtensionProperties()[k].getContent()
									.toString();
						}
						if (binding.getBindingOperations()[j]
								.getExtensionProperties()[k]
								.getName()
								.toString()
								.equalsIgnoreCase(
										"HTTP QUERY PARAMETER SEPARATOR")) {
							parameterSeparator = binding.getBindingOperations()[j]
									.getExtensionProperties()[k].getContent()
									.toString();
						}
					}

					InterfaceMessageReference[] in_out = binding
							.getBindingOperations()[j].getInterfaceOperation()
							.getInterfaceMessageReferences();
					int numberIn_Out = in_out.length;
					int numberInput = 0;
					int numberOutput = 0;
					if (in_out != null) {
						for (int k = 0; k < numberIn_Out; k++) {
							if (in_out[k] != null) {
								try {
									Parameter param;
									Parameter output;
									if (in_out[k].getDirection().toString()
											.equalsIgnoreCase("IN")) {
										numberInput++;
										String name = in_out[k]
												.getElementDeclaration()
												.getName().getLocalPart();
										String type = getTypeOfInputParameter(name, descElem,simpleTypeArrayList,complexTypeArrayList);
										param = new Parameter(name,type);
										params.add(param);
									}
									if (in_out[k].getDirection().toString()
											.equalsIgnoreCase("OUT")) {
										numberOutput++;
										String name = in_out[k]
												.getElementDeclaration()
												.getName().getLocalPart();
										String type = getTypeOfInputParameter(name, descElem,simpleTypeArrayList,complexTypeArrayList);
										
										output = new Parameter(name,type);
										outputs.add(output);
									}
								} catch (Exception ex) {
									continue;
								}
							}
						}
					}

					method.setAPI_ENDPOINT(API_ENDPOINT);
					method.setAPI_ROOT(API_ROOT);
					method.setMethodName(methodName);
					method.setNumberParams(numberInput);
					method.setParams(params);
					method.setMethod_type(methodtype);
					method.setContent_type(contentType);
					if (inputContentType == null || inputContentType.equalsIgnoreCase("")){
						inputContentType = contentType;
					} 
					//System.out.println("ABC " + inputContentType + ":" + contentType);
					method.setInputContentType(inputContentType);
					if (outputContentType == null || outputContentType.equalsIgnoreCase("")){
						outputContentType = contentType;
					} 
					method.setOutputContentType(outputContentType);
					method.setParamSeparator(parameterSeparator);
					method.setOutputs(outputs);
					method.setDetailComplexDataType(complexTypeArrayList);
					method.setDetailSimpleDataType(simpleTypeArrayList);
					lstMethod.add(method);
				}

			}

			return lstMethod;
		} catch (WSDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstMethod;
	}

	public WS_Gateway_Client(String wsdl) {
		this.WSDL_URL = wsdl;

		// Parser WSDL to get method information
		this.webServicesMethod = parse(this.WSDL_URL);
	}
	
	public void explore(String operation_name){
		if (operation_name == "" || operation_name == null){
			for (int i = 0; i < this.webServicesMethod.size(); i++) {
				JSONObject obj = this.webServicesMethod.get(i).buildJSONOperation();
				System.out.println(obj.toJSONString());
			}
		} else {
			System.out.println("\n Delay");
		}
	}

	public void discovery() {
		for (int i = 0; i < this.webServicesMethod.size(); i++) {
			this.webServicesMethod.get(i).print();
		}
	}

	public String runPOST(WebServiceMethod method, ArrayList<String> params) {
		
		try {
			String url = method.getAPI_ROOT() + method.getAPI_ENDPOINT();
			@SuppressWarnings("deprecation")
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(
					url);
			
			//if (method.getContent_type().equalsIgnoreCase("APPLICATION/JSON")){
			if (method.getInputContentType().equalsIgnoreCase("APPLICATION/JSON")){
				int numberParams1 = method.getNumberParams();
				int numberParams2 = params.size();
				int numberParams = 0;
				if (numberParams1 < numberParams2){
					numberParams = numberParams1;
				} else {
					numberParams = numberParams2;
				}
				String inputS = "{";
				System.out.println("Params " + params);
				for(int i = 0 ; i < numberParams ; i++){
					if (i < numberParams - 1) {
						if (method.getParams().get(i).getType().equalsIgnoreCase(SYSTEM_PARAM.TYPE_LONG)) {
							inputS += "\"" + method.getParams().get(i).getName() + "\"" + ":" + params.get(i) + ", ";
						} else if  (method.getParams().get(i).getType().equalsIgnoreCase(SYSTEM_PARAM.TYPE_INT)){
							inputS += "\"" + method.getParams().get(i).getName() + "\"" + ":" + params.get(i) + ", ";
						} else if (method.getParams().get(i).getType().equalsIgnoreCase(SYSTEM_PARAM.TYPE_DOUBLE)) {
							inputS += "\"" + method.getParams().get(i).getName() + "\"" + ":" + params.get(i) + ", ";
						} else if (method.getParams().get(i).getType().equalsIgnoreCase(SYSTEM_PARAM.TYPE_BOOLEAN)) {
							inputS += "\"" + method.getParams().get(i).getName() + "\"" + ":" + params.get(i) + ", ";
						} else if (method.getParams().get(i).getType().equalsIgnoreCase(SYSTEM_PARAM.TYPE_UNKNOWN)) {
							inputS = "{}";
						} else if (method.getParams().get(i).getType().equalsIgnoreCase(SYSTEM_PARAM.TYPE_STRING)) {
							inputS += "\"" + method.getParams().get(i).getName() + "\"" + ":\"" + params.get(i).trim() + "\"" + ", ";
						} else if (method.getParams().get(i).getType().equalsIgnoreCase(SYSTEM_PARAM.TYPE_LIST_OF_LONG)) {
							inputS += "\"" + method.getParams().get(i).getName() + "\"" + ":" + params.toString() + ", ";
						} else if (method.getParams().get(i).getType().equalsIgnoreCase(SYSTEM_PARAM.TYPE_LIST_OF_INT)) {
							inputS += "\"" + method.getParams().get(i).getName() + "\"" + ":" + params.toString() + ", ";
						} else if (method.getParams().get(i).getType().equalsIgnoreCase(SYSTEM_PARAM.TYPE_LIST_OF_DOUBLE)) {
							inputS += "\"" + method.getParams().get(i).getName() + "\"" + ":" + params.toString() + ", ";
						} else if (method.getParams().get(i).getType().equalsIgnoreCase(SYSTEM_PARAM.TYPE_LIST_OF_STRING)) {
							for(int j = 0 ; j < params.size() ; j++){
								params.set(i, "\"" + params.get(i) + "\"");
							}
							inputS += "\"" + method.getParams().get(i).getName() + "\"" + ":" + params.toString() + ", ";
						} else {
							inputS += "Chua co gi";
						}
					} else {
						if (method.getParams().get(i).getType().equalsIgnoreCase(SYSTEM_PARAM.TYPE_LONG)) {
							inputS += "\"" + method.getParams().get(i).getName() + "\"" + ":" + params.get(i);
						} else if  (method.getParams().get(i).getType().equalsIgnoreCase(SYSTEM_PARAM.TYPE_INT)){
							inputS += "\"" + method.getParams().get(i).getName() + "\"" + ":" + params.get(i);
						} else if (method.getParams().get(i).getType().equalsIgnoreCase(SYSTEM_PARAM.TYPE_DOUBLE)) {
							inputS += "\"" + method.getParams().get(i).getName() + "\"" + ":" + params.get(i);
						} else if (method.getParams().get(i).getType().equalsIgnoreCase(SYSTEM_PARAM.TYPE_BOOLEAN)) {
							inputS += "\"" + method.getParams().get(i).getName() + "\"" + ":" + params.get(i);
						} else if (method.getParams().get(i).getType().equalsIgnoreCase(SYSTEM_PARAM.TYPE_UNKNOWN)) {
							inputS = "{}";
						} else if (method.getParams().get(i).getType().equalsIgnoreCase(SYSTEM_PARAM.TYPE_STRING)) {
							inputS += "\"" + method.getParams().get(i).getName() + "\"" + ":\"" + params.get(i) + "\"";
						} else if (method.getParams().get(i).getType().equalsIgnoreCase(SYSTEM_PARAM.TYPE_LIST_OF_LONG)) {
							inputS += "\"" + method.getParams().get(i).getName() + "\"" + ":" + params.toString() ;
						} else if (method.getParams().get(i).getType().equalsIgnoreCase(SYSTEM_PARAM.TYPE_LIST_OF_INT)) {
							inputS += "\"" + method.getParams().get(i).getName() + "\"" + ":" + params.toString();
						} else if (method.getParams().get(i).getType().equalsIgnoreCase(SYSTEM_PARAM.TYPE_LIST_OF_DOUBLE)) {
							inputS += "\"" + method.getParams().get(i).getName() + "\"" + ":" + params.toString();
						} else if (method.getParams().get(i).getType().equalsIgnoreCase(SYSTEM_PARAM.TYPE_LIST_OF_STRING)) {
							for(int j = 0 ; j < params.size() ; j++){
								params.set(j, "\"" + params.get(j) + "\"");
							}
							inputS += "\"" + method.getParams().get(i).getName() + "\"" + ":" + params.toString();
							
						} else {
							inputS += "Chua co gi";
						}
					}
				}
				inputS += "}";
				System.out.println("Request : " + inputS);
				StringEntity input = new StringEntity(
						inputS);
				input.setContentType("application/json");
				postRequest.setEntity(input);
			} else if (method.getInputContentType().equalsIgnoreCase("APPLICATION/X-WWW-FORM-URLENCODED")) { 
				int numberParams1 = method.getNumberParams();
				int numberParams2 = params.size();
				int numberParams = 0;
				if (numberParams1 < numberParams2){
					numberParams = numberParams1;
				} else {
					numberParams = numberParams2;
				}
				String inputFormat = "";
				ArrayList<Parameter> lstParams = method.getParams();
				if (numberParams  == 1){
					inputFormat = lstParams.get(0).getName() + "=" + params.get(0);
				} else if (method.getNumberParams() >= 2){
					for(int i = 0 ; i < numberParams ; i++){
						String content = params.get(i);
						content = content.replaceAll(" ", "%20");
						if (i < numberParams - 1){
							inputFormat += lstParams.get(i).getName() + "=" + content + "&";
						} else if (i == method.getNumberParams() - 1){
							inputFormat += lstParams.get(i).getName() + "=" + content;
						}
					}
				} else {
					inputFormat = "";
				}
				
				System.out.println("Request : " + url);
				StringEntity input = new StringEntity(
						inputFormat);
				System.out.println("Params : " + inputFormat);
				input.setContentType("application/x-www-form-urlencoded");
				postRequest.setEntity(input);
			}

			HttpResponse response = httpClient.execute(postRequest);

			if (response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode() + " === " + response.toString());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));

			String output;
			String responseString = "";
			//System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				responseString += output;
				//System.out.println(output);
			}
			
			
			httpClient.getConnectionManager().shutdown();
			return responseString ;
		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return null;
	}

	public String runGET(WebServiceMethod method, ArrayList<String> params) {
		System.out.println(method.getNumberParams() + ":::" + params.size());
		if (method.getNumberParams() <= params.size()) {
			try {
				String url = method.getAPI_ROOT() + method.getAPI_ENDPOINT();

				if (method.getInputContentType().equalsIgnoreCase(
						"APPLICATION/X-WWW-FORM-URLENCODED")) {
					if (!url.contains("?")){
						url = url + "?";
					}
					
					ArrayList<Parameter> lstParam = method.getParams();
					String inputFormat = "";
					if (method.getNumberParams()  == 1){
						inputFormat = lstParam.get(0).getName() + "=" + params.get(0);
					} else if (method.getNumberParams() >= 2){
						for(int i = 0 ; i < method.getNumberParams() ; i++){
							String content = params.get(i);
							content = content.replaceAll(" ", "%20");
							if (i < method.getNumberParams() - 1){
								inputFormat += lstParam.get(i).getName() + "=" + content + "&";
							} else if (i == method.getNumberParams() - 1){
								inputFormat += lstParam.get(i).getName() + "=" + content;
							}
						}
					}
					url = url + inputFormat;
				} else {
					for (int j = 0; j < params.size(); j++) {
						url = url.replaceFirst("%s", params.get(j));
					}
				}
				System.out.println("Request : " + url);
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj
						.openConnection();

				// optional default is GET
				con.setRequestMethod("GET");

				// add request header
				con.setRequestProperty("User-Agent", "ACD");

				int  responseCode = con.getResponseCode();
				System.out.println("Response Code : " + responseCode);

				if (responseCode != 200 && responseCode != 201 &&  responseCode != 302) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ con.getResponseMessage());
				}
				
				
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));

				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				return response.toString();
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		} 
		return null;
	}

	public String runFunction(String name, ArrayList<String> params) {
		int size = this.webServicesMethod.size();
		for (int i = 0; i < size; i++) {
			if (this.webServicesMethod.get(i).getMethodName()
					.equalsIgnoreCase(name)) {
				
				WebServiceMethod method = this.webServicesMethod.get(i);
				if (method.getMethod_type().trim().equalsIgnoreCase("GET")) {
					System.out.println("WSExecution : go to GET");
					return runGET(method, params);
				} else if (method.getMethod_type().trim()
						.equalsIgnoreCase("POST")) {
					System.out.println("WSExecution : go to GET");
					return runPOST(method, params);
				}
			}
		}
		return null;
	}
}

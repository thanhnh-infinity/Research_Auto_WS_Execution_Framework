package nmsu.edu;

import java.util.ArrayList;



public class WS_Client_Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String wsdlurl = args[0];
		
		try {
			WS_Gateway_Client tnrs = new WS_Gateway_Client(wsdlurl);
			if (args[1].equalsIgnoreCase("-DISCOVERY")){
				tnrs.discovery();
			} else if (args[1].equalsIgnoreCase("-RUN")) {
		    	System.out.println("Run Function : " + args[2].toString());
		    	ArrayList<String> params = new ArrayList<String>();
		    	if (args.length >= 4 && args[3] != null){
		    		for(int i = 3 ; i < args.length ; i++){
		    			//String content = args[i].replaceAll(",", " ");
		    			String content = args[i].toString();
		    			params.add(content);
		    		}
		    	}
		    	//System.out.println("ABC " + params);
		    	System.out.println("FINAL_RESULT_JSON_OUTPUT:" + tnrs.runFunction(args[2].toString(), params));
		    	
			} else if (args[1].equalsIgnoreCase("-EXPLORE")){
				tnrs.explore(null);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
	    
	}

}

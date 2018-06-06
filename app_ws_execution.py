import ws_execution_engine
from pprint import pprint
import dictionary

EX_SERVICE_DIC_ONTO_WSDL = dictionary.SERVICE_DIC_ONTO_WSDL

inputParams = []
inputParams.append("Crabronidae|Ophiocordyceps|Megalyridae|Formica%20polyctena|Tetramorium caespitum|Pseudomyrmex|Carebara diversa|Formicinae")
json_result = ws_execution_engine.execute_Single_Operation("phylotastic_GetPhylogeneticTree_OT_GET",inputParams,None)
#print(json_result)


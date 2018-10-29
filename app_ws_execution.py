import ws_execution_engine
from pprint import pprint
import dictionary

EX_SERVICE_DIC_ONTO_WSDL = dictionary.SERVICE_DIC_ONTO_WSDL

inputParams = []
inputParams.append("Crabronidae|Ophiocordyceps|Megalyridae|Formica%20polyctena|Tetramorium caespitum|Pseudomyrmex|Carebara diversa|Formicinae")
expectedOutput = ['resource_speciesTree']
json_result = ws_execution_engine.execute_single_operation("phylotastic_GetPhylogeneticTree_OT_GET",inputParams,expectedOutput)

#inputParam = "http://en.wikipedia.org/wiki/Mustelidae"
#ws_execution_engine.execute_name_extraction_operation("phylotastic_GetScientificNames_GNRD_GET", inputParam, None)


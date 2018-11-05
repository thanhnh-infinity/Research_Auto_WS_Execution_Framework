import ws_execution_engine
from pprint import pprint
import dictionary

EX_SERVICE_DIC_ONTO_WSDL = dictionary.SERVICE_DIC_ONTO_WSDL

# Running GET phylomatic tree
inputParams = []
inputParams.append("Crabronidae|Ophiocordyceps|Megalyridae|Formica%20polyctena|Tetramorium caespitum|Pseudomyrmex|Carebara diversa|Formicinae")
expectedOutput = ['resource_speciesTree']
json_result = ws_execution_engine.execute_single_operation("phylotastic_GetPhylogeneticTree_OT_GET",inputParams,expectedOutput)


# Running GET scientifics names from URL
inputParams = []
inputParams.append("http://en.wikipedia.org/wiki/Mustelidae")
expectedOutput = ['resource_SetOfScientificNames']
json_result = ws_execution_engine.execute_single_operation("phylotastic_GetScientificNames_GNRD_GET",inputParams,expectedOutput)

# Running GET scientifics names from TEXT
'''
inputParams = []
inputParams.append("Columba larvata is a species of bird in the pigeon family Columbidae")
expectedOutput = ['resource_SetOfScientificNames']
json_result = ws_execution_engine.execute_single_operation("phylotastic_GetScientificNamesText_GNRD_POST",inputParams,expectedOutput)
'''


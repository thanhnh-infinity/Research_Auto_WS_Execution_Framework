import ws_execution_engine
from pprint import pprint
import dictionary
import json

EX_SERVICE_DIC_ONTO_WSDL = dictionary.SERVICE_DIC_ONTO_WSDL

# Running GET phylomatic tree
#inputParams = []
#inputParams.append("Crabronidae|Ophiocordyceps|Megalyridae|Formica%20polyctena|Tetramorium caespitum|Pseudomyrmex|Carebara diversa|Formicinae")
#expectedOutput = ['resource_speciesTree']
#json_result = ws_execution_engine.execute_single_operation("phylotastic_GetPhylogeneticTree_OT_GET",inputParams,expectedOutput)


# Running GET scientifics names from URL
#inputParams = []
#inputParams.append("http://en.wikipedia.org/wiki/Mustelidae")
#expectedOutput = ['resource_SetOfScientificNames']
#json_result = ws_execution_engine.execute_single_operation("phylotastic_GetScientificNames_GNRD_GET",inputParams,expectedOutput)

# Running GET scientifics names from TEXT
'''
inputParams = []
inputParams.append("Columba larvata is a species of bird in the pigeon family Columbidae")
expectedOutput = ['resource_SetOfScientificNames']
json_result = ws_execution_engine.execute_single_operation("phylotastic_GetScientificNamesText_GNRD_POST",inputParams,expectedOutput)
'''
# Running GET resolve names
#inputParams = []
#inputParams.append("CCrabronidae|Ophiocordyceps|Megalyridae|Formica%20polyctena|Tetramorium caespitum|Pseudomyrmex|Carebara diversa|Formicinae")
#expectedOutput = ['resource_SetOfResolvedName']
#json_result = ws_execution_engine.execute_single_operation("phylotastic_ResolveNames_OToL_GET",inputParams,expectedOutput)

# Running POST scaling tree
inputParams = []
inputParams.append(json.dumps({'newick':'(Aulacopone_relicta,(((Myrmecia_gulosa,(Aneuretus_simoni,Dolichoderus_mariae)),((Ectatomma_ruidum,Huberia_brounii),Formica_rufa)),Apomyrma_stygia),Martialis_heureka)Formicidae;'}))
expectedOutput = ['resource_speciesTree']
json_result = ws_execution_engine.execute_single_operation("phylotastic_ScaleTree_OT_POST",inputParams,expectedOutput)


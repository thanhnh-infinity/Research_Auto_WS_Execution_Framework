import dictionary
import json

try:
    EX_SERVICE_DIC_ONTO_WSDL = dictionary.SERVICE_DIC_ONTO_WSDL
except Exception as inst:
    print("out")
    print(inst)

def execute_single_operation(OperationName, InputParams, OutputParams):
    try:
        print "executing single operation.."
        result = EX_SERVICE_DIC_ONTO_WSDL[OperationName]['execution.call'](InputParams)
        print "got result"
        json_data = json.loads(result)
        what_want_to_get = EX_SERVICE_DIC_ONTO_WSDL[OperationName]['outputs']['resource_speciesTree']
        print(json_data[what_want_to_get])
        return json_data
    except Exception as inst:
        print("Error : ")
        print(inst)
        return None

def  execution_a_workflow():
    return Nine

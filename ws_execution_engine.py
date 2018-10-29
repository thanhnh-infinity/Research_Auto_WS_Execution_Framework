import dictionary
import json

try:
    EX_SERVICE_DIC_ONTO_WSDL = dictionary.SERVICE_DIC_ONTO_WSDL
except Exception as inst:
    print("out")
    print(inst)

def execute_single_operation(OperationName, InputParams, OutputParams):
    try:
        print("================================================")
        print("==Input Params : ")
        print(InputParams)
        print("================================================")
        result = EX_SERVICE_DIC_ONTO_WSDL[OperationName]['execution.call'](InputParams)
        json_data = json.loads(result)
        what_want_to_get = EX_SERVICE_DIC_ONTO_WSDL[OperationName]['outputs'][OutputParams[0]]
        print(json_data[what_want_to_get])
        return json_data
    except Exception as inst:
        print("Error : ")
        print(inst)
        return None

def  execution_a_workflow():
    return None
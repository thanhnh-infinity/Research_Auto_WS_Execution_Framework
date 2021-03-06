import dictionary
import json

try:
    EX_SERVICE_DIC_ONTO_WSDL = dictionary.SERVICE_DIC_ONTO_WSDL
except Exception as inst:
    print("out")
    print(inst)

####
# ABU : this function will be unnecesary. We can use func execute_single_operation for all single operation. Just update the change name and parameters in app_ws_execution
####
def execute_name_extraction_operation(OperationName, InputParams, OutputParams):
    try:
        print("executing name_extraction operation..")
        result = EX_SERVICE_DIC_ONTO_WSDL[OperationName]['execution.call'](InputParams)
        print("got result")
        json_data = json.loads(result)
        what_want_to_get = EX_SERVICE_DIC_ONTO_WSDL[OperationName]['outputs']['resource_SetOfScientificNames']
        print(json_data[what_want_to_get])
        return json_data
    except Exception as inst:
        print("Error : ")
        print(inst)
        return None



def execute_single_operation(OperationName, InputParams, OutputParams):
    try:
        print("----------------------------")
        print("---- Execute Service : %s" %(OperationName))
        print("---- Input Params : ")
        print(InputParams)
        result = EX_SERVICE_DIC_ONTO_WSDL[OperationName]['execution.call'](InputParams)
       
        json_data = json.loads(result)
        what_want_to_get = EX_SERVICE_DIC_ONTO_WSDL[OperationName]['outputs'][OutputParams[0]]
        print("---- Ouput data : ")
        print(json_data[what_want_to_get])
        return json_data
    except Exception as inst:
        print("Error : ")
        print(inst)
        return None

def  execution_a_workflow():
    return None


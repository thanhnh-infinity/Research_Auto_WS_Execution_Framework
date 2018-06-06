import dictionary

EX_SERVICE_DIC_ONTO_WSDL = dictionary.SERVICE_DIC_ONTO_WSDL

def execute_Single_Operation(OperationName, InputParams, OutputParams):
    try:
        result = EX_SERVICE_DIC_ONTO_WSDL['phylotastic_GetPhylogeneticTree_OT_GET']['execution.call'](InputParams)
        #print result
        return result
    except Exception as inst:
        print(inst)
        return None
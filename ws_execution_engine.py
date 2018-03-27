from zeep import Client

def execute_Single_Operation(WSDLFile, OperationName, InputObjectData, OutputObjectTemplate, ServiceObject_Composition):
    try:
        client = Client(WSDLFile)
        result = client.service.ConvertSpeed_Get(100, 'kilometersPerhour', 'milesPerhour')
        return result
    except:
        return None
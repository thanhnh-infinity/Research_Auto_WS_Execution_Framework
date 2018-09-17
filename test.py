from zeep import Client

#client = Client('./WSDL/example_convert.wsdl')
#client = Client('./WSDL/try.wsdl')
#result = client.service.ConvertSpeed_Get(100, 'kilometersPerhour', 'milesPerhour')
try:
    client = Client('./WSDL/Phylotastic_Tree_Generation.wsdl')
    #client = Client('./WSDL/try.wsdl')
    result = client.service.phylotastic_GetPhylogeneticTree_OT_GET('"Crabronidae|Ophiocordyceps|Megalyridae|Formica%20polyctena|Tetramorium caespitum|Pseudomyrmex|Carebara diversa|Formicinae"')
except Exception as inst:
    print(inst)
    pass

print result
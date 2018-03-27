import ws_execution_engine
from zeep import Client
from pprint import pprint

#print ws_execution_engine.execute_Single_Operation("./convert.wsdl","",[],[],{})

client = Client("./convert.wsdl")
result = client.service.ConvertSpeed_Get(100, 'kilometersPerhour', 'milesPerhour')
print result


service2 = client.bind('ConvertSpeeds', 'ConvertSpeedsHttpGet')
print service2.ConvertSpeed_Get(100, 'kilometersPerhour', 'milesPerhour')

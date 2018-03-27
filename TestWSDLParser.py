from zeep import Client

#client = Client('http://www.webservicex.net/ConvertSpeed.asmx?WSDL')
#result = client.service.ConvertSpeed(100, 'kilometersPerhour', 'milesPerhour')
#print result

#assert result == 62.137

client = Client('./convert.wsdl')
result = client.service.ConvertSpeed_Get(100, 'kilometersPerhour', 'milesPerhour') 
print result
from zeep import Client

client = Client('./WSDL/example_convert.wsdl')
result = client.service.ConvertSpeed_Get(
    100, 'kilometersPerhour', 'milesPerhour')

print result
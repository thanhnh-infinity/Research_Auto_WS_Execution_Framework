import csv
import lxml.etree

file = "./WSDL/Phylotastic_Tree_Generation.wsdl"

with open(file, 'r') as f:
    data = f.read()
    #print(data)
    root = lxml.etree.fromstring(data)
    print(root)
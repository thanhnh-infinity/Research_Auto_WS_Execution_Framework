from zeep import Client

# Registration part
TreeGeneration_Client = Client("./WSDL/Phylotastic_Tree_Generation.wsdl")
Test_Client = Client("./WSDL/try.wsdl")

# Binding part
test_service2 = Test_Client.bind("ConvertSpeeds","ConvertSpeedsHttpPost")
test_service1 = Test_Client.bind("ConvertSpeeds","ConvertSpeedsHttpGet")
TreeGeneration_Operation = TreeGeneration_Client.bind("phylotastic_GetPhylogeneticTree","phylotastic_GetPhylogeneticTree_OT_GET_HttpGet")

# Mapping part
SERVICE_DIC_ONTO_WSDL = {
    'phylotastic_GetPhylogeneticTree_OT_GET' : {
        'execution.call' : TreeGeneration_Operation['phylotastic_GetPhylogeneticTree_OT_GET'],
        'inputs' : {
            'resource_SetOfTaxon' : 'taxa'
        },
        'outputs' : {
            'resouce_HTTPCode' : 'status_code',
            'resource_speciesTree' : 'newick'
        }
    },
    'ConvertSpeend_Get' : {
        'execution.call' : test_service1['ConvertSpeed_Get'],
        'inputs' : [
            {
                'ontology_id_1' : 'wsdl_comp_id_1'
            },
            {
                'ontology_id_2' : 'wsdl_comp_id_2'
            }
        ],
        'outputs' : [
            {
                'ontology_id_1' : 'wsdl_comp_id_1'
            },
            {
                'ontology_id_2' : 'wsdl_comp_id_2'
            }
        ]
    },
    'ConvertSpeend_Post' : {
        'execution.call' : test_service2['ConvertSpeed_Post'],
        'inputs' : [
            {
                'ontology_id_1' : 'wsdl_comp_id_1'
            },
            {
                'ontology_id_2' : 'wsdl_comp_id_2'
            }
        ],
        'outputs' : [
            {
                'ontology_id_1' : 'wsdl_comp_id_1'
            },
            {
                'ontology_id_2' : 'wsdl_comp_id_2'
            }
        ]
    },
}

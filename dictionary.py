from zeep import Client

try:
    # 1. Registration part
    TreeGeneration_Client = Client("./WSDL/Phylotastic_Tree_Generation.wsdl")
    Test_Client = Client("./WSDL/try.wsdl")
    NameExtraction_Client = Client("./WSDL/Phylotastic_Name_Extraction.wsdl")

    # 1. FOR UBUNTU : Registration part
    #TreeGeneration_Client = Client("/opt/app/execution/WSDL/Phylotastic_Tree_Generation.wsdl")
    #Test_Client = Client("/opt/app/execution/WSDL/try.wsdl")

    # 2. Binding part
    test_service2 = Test_Client.bind("ConvertSpeeds","ConvertSpeedsHttpPost")
    test_service1 = Test_Client.bind("ConvertSpeeds","ConvertSpeedsHttpGet")
    TreeGeneration_Operation_OT_GET = TreeGeneration_Client.bind("phylotastic_GetPhylogeneticTree","phylotastic_GetPhylogeneticTree_OT_GET_HttpGet")
    TreeGeneration_Operation_OT_POST_V1 = TreeGeneration_Client.bind("phylotastic_GetPhylogeneticTree","phylotastic_GetPhylogeneticTree_OT_GET_HttpPost_V1")
    Name_Extraction_Operation_GNRD_GET = NameExtraction_Client.bind("phylotastic_GetScientificNames_GNRD","phylotastic_GetScientificNames_GNRD_GET_HttpGet")
    

    # 3. Mapping part
    SERVICE_DIC_ONTO_WSDL = {
        'phylotastic_GetPhylogeneticTree_OT_GET' : {
            'execution.call' : TreeGeneration_Operation_OT_GET['phylotastic_GetPhylogeneticTree_OT_GET'],
            'inputs' : {
                'resource_SetOfTaxon' : 'taxa'
            },
            'outputs' : {
                'resouce_HTTPCode' : 'status_code',
                'resource_speciesTree' : 'newick'
            }
        },
        'phylotastic_GetPhylogeneticTree_OT_POST' : {
            'execution.call' : TreeGeneration_Operation_OT_POST_V1['phylotastic_GetPhylogeneticTree_OT_POST'],
            'inputs' : {
                'resource_SetOfTaxon' : 'taxa'
            },
            'outputs' : {
                'resouce_HTTPCode' : 'status_code',
                'resource_speciesTree' : 'newick'
            }
        },
        'phylotastic_GetScientificNames_GNRD_GET' : {
            'execution.call' : Name_Extraction_Operation_GNRD_GET['phylotastic_GetScientificNames_GNRD_GET'],
            'inputs' : {
                'resource_WebURL' : 'url'
            },
            'outputs' : {
                'resouce_HTTPCode' : 'status_code',
                'resource_SetOfScientificNames' : 'scientificNames'
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

    # 4. Remembering data
    WORKFLOW_WITH_DATA = []
except Exception as inst:
    print(inst)

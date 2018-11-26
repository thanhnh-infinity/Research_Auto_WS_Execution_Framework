from zeep import Client

try:
    # 1. Registration part
    TreeGeneration_Client = Client("./WSDL/Phylotastic_Tree_Generation.wsdl")
    Test_Client = Client("./WSDL/try.wsdl")
    NameExtraction_URL_Client = Client("./WSDL/Phylotastic_Name_Extraction_URL.wsdl")
    NameExtraction_TEXT_Client = Client("./WSDL/Phylotastic_Name_Extraction_TEXT.wsdl")
    NameResolution_Client = Client("./WSDL/Phylotastic_Name_Resolution_OToL.wsdl")

    # 1. FOR UBUNTU : Registration part
    #TreeGeneration_Client = Client("/opt/app/execution/WSDL/Phylotastic_Tree_Generation.wsdl")
    #Test_Client = Client("/opt/app/execution/WSDL/try.wsdl")

    # 2. Binding part
    test_service2 = Test_Client.bind("ConvertSpeeds","ConvertSpeedsHttpPost")
    test_service1 = Test_Client.bind("ConvertSpeeds","ConvertSpeedsHttpGet")
    TreeGeneration_Operation_OT_GET = TreeGeneration_Client.bind("phylotastic_GetPhylogeneticTree","phylotastic_GetPhylogeneticTree_OT_GET_HttpGet")
    TreeGeneration_Operation_OT_POST_V1 = TreeGeneration_Client.bind("phylotastic_GetPhylogeneticTree","phylotastic_GetPhylogeneticTree_OT_GET_HttpPost_V1")
    Name_Extraction_Operation_GNRD_URL_GET = NameExtraction_URL_Client.bind("phylotastic_GetScientificNames_GNRD","phylotastic_GetScientificNames_GNRD_GET_HttpGet")
    Name_Extraction_Operation_GNRD_TEXT_POST = NameExtraction_TEXT_Client.bind("phylotastic_GetScientificNamesText_GNRD","phylotastic_GetScientificNamesText_GNRD_HttpPOST")
    Name_Resolution_Operation_OToL_GET = NameResolution_Client.bind("phylotastic_ResolveNames_OToL","phylotastic_ResolveNames_OToL_GET_HttpGet")
		

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
            'execution.call' : Name_Extraction_Operation_GNRD_URL_GET['phylotastic_GetScientificNames_GNRD_GET'],
            'inputs' : {
                'resource_WebURL' : 'url'
            },
            'outputs' : {
                'resouce_HTTPCode' : 'status_code',
                'resource_SetOfScientificNames' : 'scientificNames'
            }
        },
        'phylotastic_GetScientificNamesText_GNRD_POST' : {
            'execution.call' : Name_Extraction_Operation_GNRD_TEXT_POST['phylotastic_GetScientificNamesText_GNRD_POST'],
            'inputs' : {
                'resource_FreeText' : 'text'
            },
            'outputs' : {
                'resouce_HTTPCode' : 'status_code',
                'resource_SetOfScientificNames' : 'scientificNames'
            }
        },
        'phylotastic_ResolveNames_OToL_GET' : {
            'execution.call' : Name_Resolution_Operation_OToL_GET['phylotastic_ResolveNames_OToL_GET'],
            'inputs' : {
                'resource_SetOfTaxon' : 'names'
            },
            'outputs' : {
                'resouce_HTTPCode' : 'status_code',
                'resource_SetOfResolvedName' : 'resolvedNames'
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

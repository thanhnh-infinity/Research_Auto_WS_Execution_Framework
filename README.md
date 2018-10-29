- Remember make changes in <>/site-packages/zeep/wsdl/messages/mime.py
- 1. Import JSON
- 2. Update def in MineContent class
    def deserialize(self, node):
        try:
            node = fromstring(node)
            part = list(self.abstract.parts.values())[0]
            return part.type.parse_xmlelement(node)
        except Exception as inst:
            try:
                json_object = json.loads(node)
                return node
            except Exception as inst2:
                return None

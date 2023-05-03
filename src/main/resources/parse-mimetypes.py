import json
import xml.etree.ElementTree as ElementTree

tree = ElementTree.parse("tika-mimetypes.xml")
root = tree.getroot()

result = []
for mime_type in root:
    _type = mime_type.attrib["type"]
    globs = [glob.attrib["pattern"][2:] for glob in mime_type.findall("glob")]
    if len(globs) > 0:
        result.append({"type": _type, "extensions": globs})

with open("mimetypes.json", "w") as file:
    json.dump(result, file)

from textwrap import dedent
import xml.etree.ElementTree as ElementTree

tree = ElementTree.parse("tika-mimetypes.xml")
root = tree.getroot()

result = []
for mime_type in root:
    _type = mime_type.attrib["type"]
    globs = [glob.attrib["pattern"][2:] for glob in mime_type.findall("glob")]
    if len(globs) > 0:
        result.append({"type": _type, "extensions": globs})


def write_new_line(_file, text, indent="", clean=False):
    write(_file, text, indent, clean)
    _file.write("\n")

def write(_file, text, indent="", clean=False):
    if clean:
        lines = dedent(text).splitlines()
        text = "\n".join([indent + line for line in lines])

    file.write(text)


with open("../kotlin/fe/mimetypekt/MimeTypes.kt", "w") as kt_file:
    write_new_line(kt_file, """
        package fe.mimetypekt
        object MimeTypes {
            val mimeTypeToExtensions = mapOf(
    """, clean=True)

    for mime_type in result:
        extension_str = ", ".join([f"\"{extension}\"" for extension in mime_type['extensions']])
        write_new_line(kt_file, f"\t\t\"{mime_type['type']}\" to listOf({extension_str}),")

    write_new_line(kt_file, "\t)")
    write_new_line(kt_file, """
        val extensionToMimeType = mutableMapOf<String, String>().apply {
            mimeTypeToExtensions.forEach { (mimeType, extensions) ->
                extensions.forEach { put(it, mimeType) }
            }
        }.toMap()
    """, indent="\t", clean=True)

    kt_file.write("}")

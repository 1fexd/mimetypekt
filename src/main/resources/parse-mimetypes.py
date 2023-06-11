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


def write_new_line(_file, _str, indent="", clean=False):
    write(_file, _str, indent, clean)
    _file.write("\n")


def write(_file, _str, indent="", clean=False):
    if clean:
        cleaned_str = dedent(_str)
        indented_str = ""
        lines = cleaned_str.splitlines()
        for index, line in enumerate(lines):
            indented_str += indent + line
            if index < len(lines) - 1:
                indented_str += "\n"

        _str = indented_str

    _file.write(_str)


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

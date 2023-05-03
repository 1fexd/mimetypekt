package fe.mimetypekt

import org.junit.jupiter.api.Test

class Test {
    @Test
    fun load() {
        println((MimeTypeLoader.loadBuiltInMimeTypes() as MimeTypeLoader.MimeTypeHolder.MimeTypeToExtensions).map)
        println((MimeTypeLoader.loadBuiltInMimeTypes(MimeTypeLoader.Mapping.ExtensionToMimeType) as MimeTypeLoader.MimeTypeHolder.ExtensionToMimeType).map)

    }
}

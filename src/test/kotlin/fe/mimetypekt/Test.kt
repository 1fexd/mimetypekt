package fe.mimetypekt

import org.junit.jupiter.api.Test

class Test {
    @Test
    fun load() {
        println(MimeTypeLoader.loadBuiltInMimeTypes().map)
        println(MimeTypeLoader.loadBuiltInMimeTypes(MimeTypeLoader.Mapping.ExtensionToMimeType).map)
    }
}

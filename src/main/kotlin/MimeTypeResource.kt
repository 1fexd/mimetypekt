import java.io.InputStream

object MimeTypeResource {
    fun getBuiltInMimeTypeJson(name: String = "mimetypes.json"): InputStream? =
        MimeTypeResource::class.java.getResourceAsStream(name)
}

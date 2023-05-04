package fe.mimetypekt

import MimeTypeResource
import com.google.gson.JsonArray
import fe.gson.extensions.array
import fe.gson.extensions.parseReaderAs
import fe.gson.extensions.string
import java.io.InputStream

object MimeTypeLoader {
    sealed interface MimeTypeHolder {
        class MimeTypeToExtensions(val map: Map<String, List<String>>) : MimeTypeHolder
        class ExtensionToMimeType(val map: Map<String, String>) : MimeTypeHolder
    }

    enum class Mapping {
        MimeTypeToExtensions {
            override fun map(array: JsonArray): MimeTypeHolder.MimeTypeToExtensions {
                return MimeTypeHolder.MimeTypeToExtensions(array.map { it.asJsonObject }.associate { obj ->
                    obj.string("type")!! to obj.array("extensions")!!.map { it.asString }
                })
            }
        },
        ExtensionToMimeType {
            override fun map(array: JsonArray): MimeTypeHolder.ExtensionToMimeType {
                return MimeTypeHolder.ExtensionToMimeType(array.map { it.asJsonObject }.map { obj ->
                    obj.array("extensions")!!.map { it.asString }.associateWith { obj.string("type")!! }
                }.flatMap { it.entries }.associate { it.toPair() })
            }
        };

        abstract fun map(array: JsonArray): MimeTypeHolder
    }

    fun loadBuiltInMimeTypes(): Pair<MimeTypeHolder, MimeTypeHolder> {
        val array = loadMimeTypeJson(MimeTypeResource.getBuiltInMimeTypeJson()!!)
        return Mapping.MimeTypeToExtensions.map(array) to Mapping.ExtensionToMimeType.map(array)
    }

    fun loadBuiltInMimeTypes(mapping: Mapping = Mapping.MimeTypeToExtensions) = mapping.map(
        loadMimeTypeJson(MimeTypeResource.getBuiltInMimeTypeJson()!!)
    )

    fun loadMimeTypeJson(inputStream: InputStream) = inputStream.reader().use {
        parseReaderAs<JsonArray>(it)
    }
}

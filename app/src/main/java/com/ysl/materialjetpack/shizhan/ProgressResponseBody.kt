package com.ysl.materialjetpack.shizhan

import android.util.Log
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*
import java.io.IOException

class ProgressResponseBody(private val responseBody: ResponseBody?,
                           private val progressListener: ProgressListener?) : ResponseBody() {
    private var bufferedSource: BufferedSource? = null
    override fun contentType(): MediaType? {
        return responseBody?.contentType()
    }

    override fun contentLength(): Long {
        return responseBody!!.contentLength()
    }

    override fun source(): BufferedSource {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(handleSource(responseBody!!.source()))//handleSource(responseBody!!.source()).buffer()
        }
        return bufferedSource!!
    }

    private fun handleSource(source: Source): Source {
        return object : ForwardingSource(source) {
            var totalBytesRead = 0L
            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                totalBytesRead += if (bytesRead != -1L) bytesRead else 0
                Log.d("ProgressResponseBody", "read: $totalBytesRead   ${contentLength()}")
                progressListener?.onProgress((totalBytesRead * 1.0f / /*(responseBody?.*/contentLength() /*?: 1)*/ * 100).toInt())
                return bytesRead
            }
        }
    }
}
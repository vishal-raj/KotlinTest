package kotlintest.vishu.com.kotlintest.network

/**
 * Created by Vishal on 16-12-2015.
 */

import com.squareup.okhttp.MediaType
import com.squareup.okhttp.RequestBody
import com.squareup.okhttp.ResponseBody

import java.io.IOException

import retrofit.Converter

class ToStringConverter : Converter<String> {

    @Throws(IOException::class)
    override fun fromBody(body: ResponseBody): String {
        return body.string()
    }

    override fun toBody(value: String): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), value)
    }
}
package kotlintest.vishu.com.kotlintest.network

import com.squareup.okhttp.Interceptor
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.logging.HttpLoggingInterceptor
import org.jetbrains.annotations.NotNull
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory

/**
 * Created by Vishal on 14-01-2016.
 */
open class RestClient() {
    companion object {
        val baseUrl = "http://52.26.6.81/api/index.php/"
        var gingerService: GingerService? = null
        fun getClient(): GingerService? {
            if (gingerService == null) {
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)

                val okClient = OkHttpClient()
                okClient.interceptors().add(Interceptor { chain ->
                    val response = chain.proceed(chain.request())
                    response
                })
                okClient.interceptors().add(logging)

                val client = Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverter(String::class.java, ToStringConverter())
                        .client(okClient)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())/*for rx java*/
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                gingerService = client.create<GingerService>(GingerService::class.java)
            }
            return gingerService
        }
    }
}

package kotlintest.vishu.com.kotlintest.network

import kotlintest.vishu.com.kotlintest.data.Category
import kotlintest.vishu.com.kotlintest.data.Location
import kotlintest.vishu.com.kotlintest.data.SubCategory
import kotlintest.vishu.com.kotlintest.data.SubCategoryParams
import retrofit.Call
import retrofit.http.Body
import retrofit.http.POST
import rx.Observable

/**
 * Created by Vishal on 16-01-2016.
 */
interface GingerService {
    @POST("user/getlist")
    fun getCategoryList(@Body location: Location): Observable<Category>

    @POST("user/getlist")
    fun getProductList(@Body subCategoryParams: SubCategoryParams): Observable<SubCategory>
}
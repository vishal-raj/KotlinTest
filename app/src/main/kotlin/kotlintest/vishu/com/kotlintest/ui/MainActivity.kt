package kotlintest.vishu.com.kotlintest.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlintest.vishu.com.kotlintest.R
import kotlintest.vishu.com.kotlintest.data.CategoryDetails
import kotlintest.vishu.com.kotlintest.data.Location
import kotlintest.vishu.com.kotlintest.data.SubCategory
import kotlintest.vishu.com.kotlintest.data.SubCategoryParams
import kotlintest.vishu.com.kotlintest.network.RestClient
import kotlintest.vishu.com.kotlintest.utilities.L

import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.annotations.NotNull
import retrofit.*
import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var gingerService = RestClient.getClient()
        val location = Location("28.4792943", "77.0430799")

        // rx-java, retrofit chaining two rest calls
        gingerService
                ?.getCategoryList(location)
                ?.doOnError { println(it.message) }
                //?.doOnCompleted { println("Completed Category Request") }
                ?.flatMap { Observable.from(it.categories) }
                ?.flatMap { it -> gingerService?.getProductList(SubCategoryParams("158", "4108", it.name)) }
                ?.doOnNext { it -> for(x in it.list!!){println(x)} }
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.newThread())
                ?.subscribe(object : Subscriber<SubCategory>() {
                    override fun onCompleted() {
                        L.m("Completed")
                    }

                    override fun onError(e: Throwable) {
                        if (e is HttpException) {
                            L.m("Error")
                        }
                        if (e is IOException) {
                            L.m("No Internet")
                        }
                    }

                    override fun onNext(subCategory: SubCategory) {
                        for(x in subCategory.list!!){println(x.brand)}
                    }
                })

    }

        //simple rx-java, retrofit
        /*call?.observeOn(AndroidSchedulers.mainThread())?
        .subscribeOn(Schedulers.newThread())
        ?.retry(5)
        ?.subscribe(object : Subscriber<Category>() {
                    override fun onCompleted() {
                        L.m("Completed")
                    }

                    override fun onError(e: Throwable) {
                        if (e is HttpException) {
                            L.m("Error")
                        }
                        if (e is IOException) {
                            L.m("No Internet")
                        }
                    }

                    override fun onNext(category: Category) {
                        testList(category.categories)
                    }
                })*/

        //retrofit without rx-java
        /*call?.enqueue(object : Callback<Category> {
            override fun onResponse(response: Response<Category>) {
                if (response.isSuccess) {
                    Log.d("msg", "success")
                    var category: Category = response.body()
                    testList(category.categories)
                } else
                    Log.d("msg", "errorbody")
            }

            override fun onFailure(t: Throwable) {
                Log.d("msg", "failed")
            }
        })*/


    private fun testList(category: ArrayList<CategoryDetails>?) {
        var categoryDetails: CategoryDetails = CategoryDetails("121", "test", "image")
        category?.add(categoryDetails)
        println(category?.filter { it.id == "121" })
        category?.sortBy { it.id }
        category?.map { it.img = "image" }
        for (temp in category!!) {
            println(temp.id + ":" + temp.name + ":" + temp.img)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}

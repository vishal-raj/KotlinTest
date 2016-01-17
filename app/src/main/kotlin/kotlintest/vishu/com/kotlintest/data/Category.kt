package kotlintest.vishu.com.kotlintest.data

import java.util.ArrayList

/**
 * Created by Vishal on 27-12-2015.
 */
class Category {
    var city_id: String? = null

    var error: String? = null

    var zone_id: Array<String>? = null

    var categories: ArrayList<CategoryDetails>? = null

    var msg: String? = null

    override fun toString(): String {
        return "ClassPojo [city_id = $city_id, error = $error, zone_id = $zone_id, categories = $categories, msg = $msg]"
    }
}

package kotlintest.vishu.com.kotlintest.data

/**
 * Created by Vishal on 29-12-2015.
 */
class List(var product_id: String?, var mrp: String?, var store_id: String?, var image: String?, var measure: String?, var brand: String?, var name: String?) {
    var categoryid: Array<String>? = null

    var category: Array<String>? = null

    var count: Int = 0

    init {
        this.count = 0
    }

    override fun toString(): String {
        return "ClassPojo [categoryid = $categoryid, category = $category, measure = $measure, product_id = $product_id, name = $name, mrp = $mrp, brand = $brand, image = $image, store_id = $store_id]"
    }
}

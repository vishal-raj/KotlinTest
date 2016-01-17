package kotlintest.vishu.com.kotlintest.data

/**
 * Created by Vishal on 28-12-2015.
 */
class CategoryDetails(var id:String, var name:String, var img: String) {
    /*var id: String? = null

    var name: String? = null

    var img: String? = null*/

    override fun toString(): String {
        return "ClassPojo [id = $id, name = $name, img = $img]"
    }
}

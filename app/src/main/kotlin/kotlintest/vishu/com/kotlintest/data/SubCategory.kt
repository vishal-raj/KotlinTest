package kotlintest.vishu.com.kotlintest.data

import java.util.ArrayList

/**
 * Created by Vishal on 29-12-2015.
 */
class SubCategory {
    var city_id: String? = null

    //private Subscribe[] subscribe;

    /*
    public Subscribe[] getSubscribe ()
    {
        return subscribe;
    }

    public void setSubscribe (Subscribe[] subscribe)
    {
        this.subscribe = subscribe;
    }
    */

    var error: String? = null

    var zone_id: Array<String>? = null

    var list: ArrayList<List>? = null

    var msg: String? = null

    override fun toString(): String {
        return "ClassPojo [city_id = $city_id, error = $error, zone_id = $zone_id, list = $list, msg = $msg]"
    }
}

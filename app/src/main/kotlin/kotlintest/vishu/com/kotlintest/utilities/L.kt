package kotlintest.vishu.com.kotlintest.utilities

import android.content.Context
import android.util.Log
import android.widget.Toast

object L {
    fun m(message: String) {
        Log.d("PlacerWorkOrder", message)
    }

    fun s(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
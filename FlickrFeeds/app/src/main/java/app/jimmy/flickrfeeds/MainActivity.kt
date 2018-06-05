package app.jimmy.flickrfeeds

import android.graphics.Point
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val TAG = this@MainActivity.javaClass.simpleName
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var myDataset: ArrayList<FeedItemData> = ArrayList()

    object JSONKeys {
        const val ITEMS = "items"
        const val MEDIA = "media"
        const val M="m"
        const val TITLE="title"
        const val LINK="link"
        const val DATE_TAKEN="date_taken"
        const val AUTHOR = "author"
        const val PUBLISHED = "published"
        const val DESCRIPTION = "description"
        const val AUTHOR_ID = "author_id"
        const val TAGS = "tags"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val DEVICE_WIDTH = size.x

        viewManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        viewAdapter = FeedsAdapter(myDataset,DEVICE_WIDTH/2)

        recyclerView = findViewById<RecyclerView>(R.id.feeds_rc_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        getFeeds()
    }

    /**
     * calls the api gets the result and add it to the recyclerview
     */
    private fun getFeeds() {
        val queue = Volley.newRequestQueue(this)
        val url = getString(R.string.flikr_feeds_url)

        val stringRequest = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->
                    // Display the first 500 characters of the response string.
                    val jsonObject = JSONObject(response)
                    val items = jsonObject.optJSONArray(JSONKeys.ITEMS)

                    for (i:Int in 0 until items.length()-1){
                        val item = items.get(i) as JSONObject
                        Log.d(TAG,item.optJSONObject(JSONKeys.MEDIA).optString(JSONKeys.M))

                        myDataset.add(FeedItemData(item.optString(JSONKeys.TITLE),
                                item.optString(JSONKeys.LINK),
                                item.optJSONObject(JSONKeys.MEDIA).optString(JSONKeys.M),
                                item.optString(JSONKeys.DATE_TAKEN),
                                item.optString(JSONKeys.DESCRIPTION),
                                item.optString(JSONKeys.PUBLISHED),
                                item.optString(JSONKeys.AUTHOR),
                                item.optString(JSONKeys.AUTHOR_ID),
                                item.optString(JSONKeys.TAGS)))

                    }
                    viewAdapter.notifyDataSetChanged()
                },
                Response.ErrorListener {
                    Toast.makeText(this,getString(R.string.server_error),Toast.LENGTH_LONG).show()
                })
        queue.add(stringRequest)
    }
}

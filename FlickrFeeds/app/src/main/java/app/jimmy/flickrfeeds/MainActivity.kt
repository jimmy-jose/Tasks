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

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var myDataset: ArrayList<FeedItemData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        var DEVICE_WIDTH = size.x

        viewManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        viewAdapter = FeedsAdapter(myDataset,DEVICE_WIDTH/2)

        recyclerView = findViewById<RecyclerView>(R.id.feeds_rc_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

        getFeeds();
    }

    private fun getFeeds() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1"

// Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->
                    // Display the first 500 characters of the response string.
                    val jsonObject = JSONObject(response)
                    val items = jsonObject.optJSONArray("items")

                    for (i:Int in 0..items.length()-1){
                        val item = items.get(i) as JSONObject
                        Log.d("MainActivity",item.optJSONObject("media").optString("m"));

                        myDataset.add(FeedItemData(item.optString("title"),item.optString("link"),item.optJSONObject("media").optString("m"),item.optString("date_taken"),item.optString("description"),item.optString("published"),item.optString("author"),item.optString("author_id"),item.optString("tags")))

                    }
                    viewAdapter.notifyDataSetChanged()
                },
                Response.ErrorListener {
                    Toast.makeText(this,"Server Error! Please try again.",Toast.LENGTH_LONG).show()
                })

// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}

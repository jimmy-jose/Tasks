package app.jimmy.flickrfeeds

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Switch
import android.widget.TextView
import com.squareup.picasso.Picasso


/**
 * @author Jimmy
 * Created on 5/6/18.
 */
class FeedsAdapter(private val myDataset: ArrayList<FeedItemData>,private val IMAGE_WIDTH:Int):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var selectedPosition = -1
    override fun getItemCount() = myDataset.size

    override fun getItemViewType(position: Int): Int {
        return 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            1->{
                val holderImage = holder as ViewHolderImages
                Picasso.get()
                        .load(myDataset.get(position).media)
                        .resize(IMAGE_WIDTH,IMAGE_WIDTH)
                        .centerCrop()
                        .into(holder.feedImage)
            }else->{
                val holderDetails = holder as ViewHolderDetails
            }
        }

        if (selectedPosition == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#303F9F"))
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE)
        }

        holder.itemView.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                selectedPosition = position

                notifyDataSetChanged()
            }

        })
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerView.ViewHolder {

        when(viewType){
            1 -> {
                val imageView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.feed_item, parent, false) as ImageView
                // set the view's size, margins, paddings and layout parameters
                return ViewHolderImages(imageView)
            }else->{
                val details = LayoutInflater.from(parent.context)
                        .inflate(R.layout.details_item,parent,false) as RelativeLayout
                return ViewHolderDetails(details)
            }
        }

    }

    class ViewHolderImages(val feedImage: ImageView) : RecyclerView.ViewHolder(feedImage)
    class ViewHolderDetails(val parent: RelativeLayout) : RecyclerView.ViewHolder(parent)



}
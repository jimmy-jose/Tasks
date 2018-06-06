package app.jimmy.flickrfeeds

import android.graphics.Color
import android.opengl.Visibility
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.squareup.picasso.Picasso


/**
 * @author Jimmy
 * Created on 5/6/18.
 */
class FeedsAdapter(private val myDataset: ArrayList<FeedItemData>,private val IMAGE_WIDTH:Int)
    :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    object Types{
        const val IMAGE_TYPE = 1
        const val DETAILS_TYPE = 2
    }
    var selectedPosition = -1
    override fun getItemCount() = myDataset.size

    override fun getItemViewType(position: Int): Int {
        return Types.IMAGE_TYPE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            Types.IMAGE_TYPE->{
                val holderImage = holder as ViewHolderImages
                Picasso.get()
                        .load(myDataset.get(position).media)
                        .resize(IMAGE_WIDTH,IMAGE_WIDTH)
                        .centerCrop()
                        .into(holderImage.itemImage)
                holder.itemHeader.setText(myDataset.get(position).title)
                val details: String = "Date Taken :"+myDataset.get(position).date_taken+"\n"+"Author :"+myDataset.get(position).author
                holder.itemDesc.setText(details)
            }
            else->{
                //for populating correct view in the recyclerview
                val holderDetails = holder as ViewHolderDetails
            }
        }

        if (selectedPosition == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#303F9F"))
            holder.itemView.findViewById<LinearLayout>(R.id.details_parent).visibility = View.VISIBLE
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE)
            holder.itemView.findViewById<LinearLayout>(R.id.details_parent).visibility = View.GONE
        }

        holder.itemView.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                if(selectedPosition == position){
                    selectedPosition = -1
                }else {
                    selectedPosition = position
                }
                // TODO : Add the details view and populate it with data at the correct position(current pos+1 if curr (pos+1)%2==0 else current pos + 2)
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
                        .inflate(R.layout.feed_item, parent, false) as RelativeLayout
                // set the view's size, margins, paddings and layout parameters
                return ViewHolderImages(imageView)
            }
            else->{
                val details = LayoutInflater.from(parent.context)
                        .inflate(R.layout.details_item,parent,false) as RelativeLayout
                return ViewHolderDetails(details)
            }
        }

    }

    class ViewHolderImages(val feedItemParent: RelativeLayout) : RecyclerView.ViewHolder(feedItemParent){
        var itemHeader:TextView = feedItemParent.findViewById(R.id.item_header)
        var itemDesc:TextView = feedItemParent.findViewById(R.id.item_descr)
        var itemImage:ImageView = feedItemParent.findViewById(R.id.list_item_image)
        var detailsParent:LinearLayout = feedItemParent.findViewById(R.id.details_parent)
    }
    class ViewHolderDetails(val parent: RelativeLayout) : RecyclerView.ViewHolder(parent)
}
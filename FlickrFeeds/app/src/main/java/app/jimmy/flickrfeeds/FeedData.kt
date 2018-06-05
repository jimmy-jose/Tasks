package app.jimmy.flickrfeeds

/**
 * @author Jimmy
 * Created on 5/6/18.
 */

data class FeedItemData(
    val title: String,
    val link: String,
    val media: String,
    val date_taken: String,
    val description: String,
    val published: String,
    val author: String,
    val author_id: String,
    val tags: String
)

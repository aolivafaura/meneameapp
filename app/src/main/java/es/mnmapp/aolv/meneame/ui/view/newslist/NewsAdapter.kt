package es.mnmapp.aolv.meneame.ui.view.newslist

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import es.mnmapp.aolv.meneame.R
import es.mnmapp.aolv.meneame.entity.NewUi
import es.mnmapp.aolv.meneame.ui.extensions.loadUrl


/**
 * Created by antoniojoseoliva on 02/08/2017.
 */

class NewsAdapter : ListAdapter<NewUi, NewsAdapter.Holder>(NewDiffCallback()) {

    init {
        setHasStableIds(true)
    }

    var onClickItem: ((NewUi, TextView) -> Unit) = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(layoutInflater.inflate(R.layout.row_meneo, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { onClickItem.invoke(getItem(position), holder.title) }
    }

    override fun getItemId(position: Int) = getItem(position).id ?: 0L

    fun updateList(news: List<NewUi>) {
        submitList(news)
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.iv_row_meneo)!!
        val title = itemView.findViewById<TextView>(R.id.tv_meneo_row_title)!!

        fun bind(new: NewUi) {
            image.loadUrl(new.thumb)
            title.text = new.title
        }
    }

    class NewDiffCallback : DiffUtil.ItemCallback<NewUi>() {
        override fun areItemsTheSame(oldItem: NewUi?, newItem: NewUi?): Boolean = oldItem?.id == newItem?.id
        override fun areContentsTheSame(oldItem: NewUi?, newItem: NewUi?): Boolean = oldItem?.id == newItem?.id
    }
}
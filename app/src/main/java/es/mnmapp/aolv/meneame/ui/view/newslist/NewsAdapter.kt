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
import es.mnmapp.aolv.meneame.entity.NewCellUi
import es.mnmapp.aolv.meneame.ui.extensions.getColorsSet
import es.mnmapp.aolv.meneame.ui.extensions.loadUrl


/**
 * Created by antoniojoseoliva on 02/08/2017.
 */

class NewsAdapter : ListAdapter<NewCellUi, NewsAdapter.Holder>(NewDiffCallback()) {

    // Variables -----
    var onClickItem: ((NewCellUi, TextView) -> Unit) = { _, _ -> }

    // Initializer -----
    init {
        setHasStableIds(true)
    }

    // ListAdapter overrides -----
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(layoutInflater.inflate(R.layout.row_meneo, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { onClickItem.invoke(getItem(position), holder.title) }
    }

    override fun getItemId(position: Int) = getItem(position).id

    // Class methods -----
    fun updateList(news: List<NewCellUi>) {
        submitList(news)
    }

    // Inner classes -----
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title = itemView.findViewById<TextView>(R.id.tvDescription)!!
        private val image = itemView.findViewById<ImageView>(R.id.ivRelatedImage)!!
        private val source = itemView.findViewById<TextView>(R.id.tvSource)!!
        private val sourceLogo = itemView.findViewById<ImageView>(R.id.ivSourceLogo)!!
        private val backgroundView = itemView.findViewById<View>(R.id.backgroundView)!!

        fun bind(newCell: NewCellUi) {
            val loadImageCallback: (() -> Unit) = {
                image.getColorsSet { titleColor, descriptionColor ->
                    title.setTextColor(titleColor)
                    source.setTextColor(titleColor)
                    backgroundView.setBackgroundColor(descriptionColor)
                    backgroundView.requestLayout()
                }
            }
            image.loadUrl(newCell.thumb, loadImageCallback)
            sourceLogo.loadUrl(newCell.logoUrl)
            title.text = newCell.title
            source.text = newCell.source
        }
    }

    private class NewDiffCallback : DiffUtil.ItemCallback<NewCellUi>() {
        override fun areItemsTheSame(oldItem: NewCellUi?, newCellItem: NewCellUi?): Boolean = oldItem?.id == newCellItem?.id
        override fun areContentsTheSame(oldItem: NewCellUi?, newCellItem: NewCellUi?): Boolean {
            return oldItem?.id == newCellItem?.id
                    && oldItem?.thumb == newCellItem?.thumb
                    && oldItem?.logoUrl == newCellItem?.logoUrl
        }
    }
}
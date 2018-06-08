/*
 *     Copyright 2018 @ https://github.com/aolivafaura/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import es.mnmapp.aolv.meneame.extensions.getColorsSet
import es.mnmapp.aolv.meneame.extensions.loadUrl

/**
 * Adapter for news list fragment
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
                }
            }
            image.loadUrl(newCell.thumb, loadImageCallback)
            sourceLogo.loadUrl(newCell.logoUrl)
            title.text = newCell.title
            source.text = newCell.source
        }
    }

    private class NewDiffCallback : DiffUtil.ItemCallback<NewCellUi>() {
        override fun areItemsTheSame(oldItem: NewCellUi?, newCellItem: NewCellUi?): Boolean =
            oldItem?.id == newCellItem?.id

        override fun areContentsTheSame(oldItem: NewCellUi?, newCellItem: NewCellUi?): Boolean =
            oldItem?.id == newCellItem?.id &&
                oldItem?.thumb == newCellItem?.thumb &&
                oldItem?.logoUrl == newCellItem?.logoUrl
    }
}
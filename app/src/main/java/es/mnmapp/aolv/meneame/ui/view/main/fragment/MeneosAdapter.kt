package es.mnmapp.aolv.meneame.ui.view.main.fragment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import es.mnmapp.aolv.meneame.R
import es.mnmapp.aolv.meneame.entity.MeneoUi
import es.mnmapp.aolv.meneame.extensions.loadUrl

/**
 * Created by antoniojoseoliva on 02/08/2017.
 */

class MeneosAdapter(var meneos : MutableList<MeneoUi>) : RecyclerView.Adapter<MeneosAdapter.Holder>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(layoutInflater.inflate(R.layout.row_meneo, parent, false))
    }

    override fun onBindViewHolder(holder : Holder, position : Int) {
        val meneo = meneos[position]
        holder.image.loadUrl(meneo.thumb)
        holder.title.text = meneo.title
    }

    override fun getItemId(position : Int) = meneos[position].id?: 0L

    override fun getItemCount() = meneos.size

    fun updateList(meneos : MutableList<MeneoUi>) {
        this.meneos.apply {
            clear()
            addAll(meneos)
        }
        notifyDataSetChanged()
    }

    class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.iv_row_meneo)!!
        val title = itemView.findViewById<TextView>(R.id.tv_meneo_row_title)!!
    }
}
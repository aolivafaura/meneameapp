package es.mnmapp.aolv.meneame.ui.view.main.fragment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import es.mnmapp.aolv.meneame.R
import es.mnmapp.aolv.meneame.entity.NewUi
import es.mnmapp.aolv.meneame.ui.extensions.loadUrl
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject


/**
 * Created by antoniojoseoliva on 02/08/2017.
 */

class NewsAdapter(private var news: List<NewUi>) : RecyclerView.Adapter<NewsAdapter.Holder>() {

    init {
        setHasStableIds(true)
    }

    private val onClickItem = PublishSubject.create<NewUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return Holder(layoutInflater.inflate(R.layout.row_meneo, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val new = news[position]
        holder.image.loadUrl(new.thumb)
        holder.title.text = new.title
        holder.itemView.setOnClickListener { onClickItem.onNext(news[position]) }
    }

    override fun getItemId(position: Int) = news[position].id ?: 0L

    override fun getItemCount() = news.size

    fun updateList(news: List<NewUi>) {
        this.news = news
        notifyDataSetChanged()
    }

    fun observeItemClick(): Observable<NewUi> = onClickItem.observeOn(AndroidSchedulers.mainThread())

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.iv_row_meneo)!!
        val title = itemView.findViewById<TextView>(R.id.tv_meneo_row_title)!!
    }
}
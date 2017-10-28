package es.mnmapp.aolv.meneame.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView


/**
 * Created by antonio on 10/28/17.
 */

class ObservableWebView : WebView {

    var onScrollChangeListener: (() -> Unit)? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onScrollChanged(scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
        super.onScrollChanged(scrollX, scrollY, oldScrollX, oldScrollY)

        onScrollChangeListener?.invoke()
    }
}

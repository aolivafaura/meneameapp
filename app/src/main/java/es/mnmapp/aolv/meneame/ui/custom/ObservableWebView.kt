package es.mnmapp.aolv.meneame.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView


/**
 * Created by antonio on 10/28/17.
 */

class ObservableWebView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : WebView(context, attrs, defStyleAttr) {

    var onScrollChangeListener: (() -> Unit)? = null

    override fun onScrollChanged(scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
        super.onScrollChanged(scrollX, scrollY, oldScrollX, oldScrollY)

        onScrollChangeListener?.invoke()
    }
}

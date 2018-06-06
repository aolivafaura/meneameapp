package es.mnmapp.aolv.meneame.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.telephony.TelephonyManager
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

/**
 * Check device's network connectivity and speed
 * Based on Connectivity utils of {@link http://stackoverflow.com/users/220710/emil}
 */
class Connectivity(val context: Context) {

    private val connectivityPublisher = PublishSubject.create<State>()

    private val fastConnections = arrayOf(
            TelephonyManager.NETWORK_TYPE_EVDO_0, // ~ 400-1000 kbps
            TelephonyManager.NETWORK_TYPE_EVDO_A, // ~ 600-1400 kbps
            TelephonyManager.NETWORK_TYPE_HSDPA, // ~ 2-14 Mbps
            TelephonyManager.NETWORK_TYPE_HSPA, // ~ 700-1700 kbps
            TelephonyManager.NETWORK_TYPE_HSUPA, // ~ 1-23 Mbps
            TelephonyManager.NETWORK_TYPE_UMTS, // ~ 400-7000 kbps
            TelephonyManager.NETWORK_TYPE_EHRPD, // ~ 1-2 Mbps
            TelephonyManager.NETWORK_TYPE_EVDO_B, // ~ 5 Mbps
            TelephonyManager.NETWORK_TYPE_HSPAP, // ~ 10-20 Mbps
            TelephonyManager.NETWORK_TYPE_LTE // ~ 10+ Mbps
    )

    /**
     * Check if there is any connectivity
     * *
     * @return
     */
    fun isConnected(): Boolean {
        val connected = getNetworkInfo()?.isConnected
        if (connectivityPublisher.hasObservers()) {
            connectivityPublisher.onNext(if (connected == true) State.Connected else State.Disconnected)
        }

        return connected ?: false
    }

    fun observeConnectivity(): Flowable<State> =
            connectivityPublisher
                    .distinctUntilChanged()
                    .toFlowable(BackpressureStrategy.LATEST)

    /**
     * Check if there is any connectivity to a Wifi network
     * *
     * @return
     */
    private fun isConnectedWifi(): Boolean =
            isConnected() && getNetworkInfo()!!.type == ConnectivityManager.TYPE_WIFI

    /**
     * Check if there is any connectivity to a mobile network
     * *
     * @return
     */
    private fun isConnectedMobile(): Boolean =
            isConnected() && getNetworkInfo()!!.type == ConnectivityManager.TYPE_MOBILE

    /**
     * Check if there is fast connectivity
     * *
     * @return
     */
    private fun isConnectedFast(): Boolean =
            isConnected() && isConnectionFast(getNetworkInfo()!!.type, getNetworkInfo()!!.subtype)

    /**
     * Check if the connection is fast
     * @param type
     * *
     * @param subType
     * *
     * @return
     */
    private fun isConnectionFast(type: Int, subType: Int): Boolean =
            when (type) {
                ConnectivityManager.TYPE_MOBILE -> subType in fastConnections
                else -> type == ConnectivityManager.TYPE_WIFI
            }

    /**
     * Get the network info
     * *
     * @return
     */
    private fun getNetworkInfo(): NetworkInfo? {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo
    }

    enum class State { Connected, Disconnected }
}
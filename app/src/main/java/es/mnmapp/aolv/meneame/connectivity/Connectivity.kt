/*
 *     Copyright 2018 @ https://github.com/aolivafaura
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

    // Fields -----

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

    // Class methods -----

    /**
     * Check if there is any connectivity.
     * Invoke this method will broadcast network state if there is some observer.
     *
     * @see observeConnectivity
     *
     * @return `true` if network available, `false` otherwise
     */
    fun isConnected(): Boolean {
        val connected = getNetworkInfo()?.isConnected
        if (connectivityPublisher.hasObservers()) {
            connectivityPublisher.onNext(if (connected == true) State.Connected else State.Disconnected)
        }

        return connected ?: false
    }

    /**
     * Subscribe to connectivity changes publisher
     *
     * @return flowable that notifies connectivity changes.
     */
    fun observeConnectivity(): Flowable<State> =
            connectivityPublisher
                    .distinctUntilChanged()
                    .toFlowable(BackpressureStrategy.LATEST)

    // Private methods -----

    private fun isConnectedWifi(): Boolean =
            isConnected() && getNetworkInfo()!!.type == ConnectivityManager.TYPE_WIFI

    private fun isConnectedMobile(): Boolean =
            isConnected() && getNetworkInfo()!!.type == ConnectivityManager.TYPE_MOBILE

    private fun isConnectedFast(): Boolean =
            isConnected() && isConnectionFast(getNetworkInfo()!!.type, getNetworkInfo()!!.subtype)

    private fun isConnectionFast(type: Int, subType: Int): Boolean =
            when (type) {
                ConnectivityManager.TYPE_MOBILE -> subType in fastConnections
                else -> type == ConnectivityManager.TYPE_WIFI
            }

    private fun getNetworkInfo(): NetworkInfo? {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo
    }

    // Inner classes -----

    enum class State { Connected, Disconnected }
}
package es.mnmapp.aolv.data.repository.device

import android.content.res.Configuration
import android.content.res.Resources
import es.mnmapp.aolv.domain.repository.DeviceRepository
import es.mnmapp.aolv.domain.repository.ScreenDensity
import javax.inject.Inject

class DeviceDataRepository @Inject constructor(private val resources: Resources) : DeviceRepository {

    override fun getScreenDensity(): ScreenDensity {
        val density = resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
        return when {
            density < 240 -> ScreenDensity.Low
            density < 320 -> ScreenDensity.Medium
            else -> ScreenDensity.Large
        }
    }

    override fun isTablet(): Boolean {
        val screenSize = resources.displayMetrics.density.toInt()
        return when (screenSize) {
            Configuration.SCREENLAYOUT_SIZE_LARGE -> true
            else -> false
        }
    }
}

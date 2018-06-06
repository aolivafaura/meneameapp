package es.mnmapp.aolv.data.repository.device

import android.content.res.Configuration
import es.mnmapp.aolv.domain.repository.DeviceRepository
import es.mnmapp.aolv.domain.repository.ScreenDensity

class DeviceDataRepository(
		private val densityDpi: Int,
		private val screenSize: Int
) : DeviceRepository {

	override fun getScreenDensity(): ScreenDensity =
			when {
				densityDpi < 240 -> ScreenDensity.Low
				densityDpi < 320 -> ScreenDensity.Medium
				else -> ScreenDensity.Large
			}

	override fun isTablet(): Boolean =
			when (screenSize) {
				Configuration.SCREENLAYOUT_SIZE_LARGE -> true
				else -> false
			}

}

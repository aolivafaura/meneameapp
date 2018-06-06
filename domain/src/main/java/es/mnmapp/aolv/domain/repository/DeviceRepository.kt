package es.mnmapp.aolv.domain.repository

interface DeviceRepository {

	fun getScreenDensity(): ScreenDensity

	fun isTablet(): Boolean
}

enum class ScreenDensity { Low, Medium, Large }

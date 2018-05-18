package es.mnmapp.aolv.meneame.entity

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by antoniojoseoliva on 21/07/2017.
 */

data class NewUi(
        val id: Long?,
        val url: String?,
        val title: String?,
        val thumb: String?
) : Parcelable {

    constructor(source: Parcel) : this(
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(url)
        writeString(title)
        writeString(thumb)
    }

    companion object {
        @Suppress("unused")
        @JvmField
        val CREATOR: Parcelable.Creator<NewUi> = object : Parcelable.Creator<NewUi> {
            override fun createFromParcel(source: Parcel): NewUi = NewUi(source)
            override fun newArray(size: Int): Array<NewUi?> = arrayOfNulls(size)
        }
    }
}

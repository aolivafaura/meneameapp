package es.mnmapp.aolv.data.entity

import com.google.gson.annotations.SerializedName
import es.mnmapp.aolv.domain.entity.New

/**
 * Created by antoniojoseoliva on 21/07/2017.
 */

data class NewDto(@SerializedName("id") val id: Long?,
                  @SerializedName("permalink") val permalink: String?,
                  @SerializedName("go") val go: String?,
                  @SerializedName("url") val url: String?,
                  @SerializedName("from") val from: String?,
                  @SerializedName("sub") val sub: String?,
                  @SerializedName("status") val status: String?,
                  @SerializedName("user") val user: String?,
                  @SerializedName("clicks") val clicks: Int?,
                  @SerializedName("votes") val votes: Int?,
                  @SerializedName("negatives") val negatives: Int?,
                  @SerializedName("karma") val karma: Int?,
                  @SerializedName("comments") val comments: Int?,
                  @SerializedName("title") val title: String?,
                  @SerializedName("tags") val tags: String?,
                  @SerializedName("sent_date") val sentDate: Long?,
                  @SerializedName("date") val date: Long?,
                  @SerializedName("content") val content: String?,
                  @SerializedName("thumb") val thumb: String?)

fun mapToNew(newDto: NewDto) = New(
        newDto.id ?: 0L,
        newDto.url ?: "",
        newDto.title ?: "",
        newDto.sub ?: "",
        newDto.thumb ?: "",
        newDto.from ?: "",
        newDto.votes ?: -1,
        newDto.negatives ?: -1,
        newDto.karma ?: -1,
        newDto.comments ?: -1,
        newDto.sentDate ?: -1L,
        newDto.date ?: -1L,
        newDto.tags ?: ""
)
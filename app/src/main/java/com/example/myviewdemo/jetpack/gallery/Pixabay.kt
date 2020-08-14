package com.example.myviewdemo.jetpack.gallery

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Pixabay(
        val hits: Array<PhotoItem>,
        val total: Int,
        val totalHits: Int
) {
    @Parcelize
    data class PhotoItem(
            val comments: Int,
            val downloads: Int,
            val favorites: Int,
            val id: Int,
            val imageHeight: Int,
            val imageSize: Int,
            val imageWidth: Int,
            val largeImageURL: String,
            val likes: Int,
            val pageURL: String,
            val previewHeight: Int,
            val previewURL: String,
            val previewWidth: Int,
            val tags: String,
            val type: String,
            val user: String,
            val userImageURL: String,
            val user_id: Int,
            val views: Int,
            val webformatHeight: Int,
            val webformatURL: String,
            val webformatWidth: Int
    ) : Parcelable

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pixabay

        if (!hits.contentEquals(other.hits)) return false
        if (total != other.total) return false
        if (totalHits != other.totalHits) return false

        return true
    }

    override fun hashCode(): Int {
        var result = hits.contentHashCode()
        result = 31 * result + total
        result = 31 * result + totalHits
        return result
    }
}
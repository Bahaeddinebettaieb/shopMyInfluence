package com.smi.test.presentation.entites

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


data class Brands(
    val offerId: Int? = null,
    val pic: String? = null,
    val displayName: String? = null,
    val description: String? = null,
) : Serializable {
    override fun toString(): String {
        return "Brands(offerId=$offerId, pic=$pic, displayName=$displayName, description=$description)"
    }
}
package com.smi.test.presentation.home.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.smi.test.R
import com.smi.test.presentation.entites.Brands
import java.util.*


class PremiumBrandsAdapter(var context: Context, var brandsPremiumList: List<Brands>) :
    RecyclerView.Adapter<PremiumBrandsAdapter.MyHolder?>() {
    private val TAG = "PremiumBrandsAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.adapter_premium_brands, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(myHolder: MyHolder, i: Int) {
        val brand: Brands = brandsPremiumList[i]
        val imageBrand: String? = brandsPremiumList[i].image

//        Glide.with(context)
//            .load(imageBrand)
//            .placeholder(R.drawable.logo_shop)
//            .into(myHolder.imageBrandImg)

        Glide.with(context)
            .load(imageBrand)
            .apply(
            RequestOptions()
                .placeholder(R.drawable.logo_shop)
                .fitCenter()
        ).into(myHolder.imageBrandImg)
    }

    override fun getItemCount(): Int {
        return brandsPremiumList.size
    }

    inner class MyHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var imageBrandImg: ImageView = itemView.findViewById(R.id.image_brand)
    }



}

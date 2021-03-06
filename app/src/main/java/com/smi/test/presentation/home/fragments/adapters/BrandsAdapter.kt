package com.smi.test.presentation.home.fragments.adapters

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.smi.test.R
import com.smi.test.presentation.detailsBrand.DetailsBrandActivity
import com.smi.test.presentation.entites.Brands
import com.smi.test.presentation.utils.GlobalUtils
import java.util.*

class BrandsAdapter(var context: Context, var brandsList: List<Brands>) :
    RecyclerView.Adapter<BrandsAdapter.MyHolder?>() {
    private val TAG = "BrandsAdapter"
    var idBrand: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.adapter_premium_brands, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(myHolder: MyHolder, i: Int) {
        val brand: Brands = brandsList[i]
        val imageBrand: String? = brandsList[i].pic

        Glide.with(context)
            .load(imageBrand)
            .placeholder(R.drawable.logo_shop)
            .into(myHolder.imageBrandImg)

        myHolder.itemBrand.setOnClickListener {
            navigate(brand)
        }

    }

    override fun getItemCount(): Int {
        return brandsList.size
    }

    inner class MyHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var imageBrandImg: ImageView = itemView.findViewById(R.id.image_brand)
        var itemBrand: ConstraintLayout = itemView.findViewById(R.id.item)
    }

    fun navigate(itemBrand:Brands){
        val intent = Intent(context, DetailsBrandActivity::class.java)
        intent.putExtra("item", itemBrand)
        context.startActivity(intent)
    }

}

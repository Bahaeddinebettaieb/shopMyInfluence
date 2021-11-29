package com.smi.test.presentation.detailsBrand

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.smi.test.R
import com.smi.test.presentation.App.Companion.context
import com.smi.test.presentation.entites.Brands
import com.smi.test.presentation.home.fragments.adapters.BrandsAdapter

class DetailsBrandActivity : AppCompatActivity() {
    private val TAG = "DetailsBrandActivity"
    var nameBrandTv: TextView? = null
    var descriptionBrandTv: TextView? = null
    var turnoverValueTv: TextView? = null
    var commissionValueTv: TextView? = null
    var numberSalesTv: TextView? = null
    var imgBrand: ImageView? = null
    var progressBar: ProgressBar? = null
    var brandItem: Brands? = null
    var brandId: Int? = null
    var dialogLoadingProgress: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_brand)

        brandItem = intent.getSerializableExtra("item") as Brands
        Log.e(TAG, "onCreate: $brandItem")
        brandId = brandItem!!.offerId

        nameBrandTv = findViewById<TextView>(R.id.name_brand)
        descriptionBrandTv = findViewById<TextView>(R.id.description_brand)
        turnoverValueTv = findViewById<TextView>(R.id.turnover_value_tv)
        commissionValueTv = findViewById<TextView>(R.id.commission_value_tv)
        numberSalesTv = findViewById<TextView>(R.id.number_sales_value_tv)
        imgBrand = findViewById<ImageView>(R.id.item_image)
        progressBar = findViewById<ProgressBar>(R.id.progress_news)

        nameBrandTv!!.text = brandItem!!.displayName
        descriptionBrandTv!!.text = brandItem!!.description
        Glide.with(this)
            .load(brandItem!!.pic)
            .error(R.drawable.logo_shop)
            .apply(
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
            )
            .transform(RoundedCorners(40))
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    @Nullable e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar!!.visibility =
                        View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar!!.visibility =
                        View.GONE
                    return false
                }
            }).into(imgBrand!!)

    }

    override fun onResume() {
        super.onResume()
        getDetailsBrands()
    }


    private fun getDetailsBrands() {
        showProgressLoadingDialog()
        val reference = FirebaseDatabase.getInstance().getReference("conversions")
            .child("purchase")
        reference.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                hideProgressLoadingDialog()
                for (ds in dataSnapshot.children) {
                    val x = ds.child("offerId").value.toString()
                    val y = brandId.toString()
                    if (x.contains(y)) {
                        Log.e(TAG, "onDataChange: ds $ds")
                        turnoverValueTv!!.text = ds.child("amount").value.toString() + "€"
                        commissionValueTv!!.text = ds.child("commission").value.toString() + "€"
                    }
                }
            }

            override fun onCancelled(@NonNull databaseError: DatabaseError) {
                hideProgressLoadingDialog()
                Toast.makeText(context, databaseError.message, Toast.LENGTH_SHORT).show()
            }
        })
    }


    fun showProgressLoadingDialog() {
        try {
            val view = LayoutInflater.from(this)
                .inflate(R.layout.progress_bar_dialog, null, false)
            val alertDialogBuilder = AlertDialog.Builder(this, R.style.CustomDialog)
            alertDialogBuilder.setView(view)
            dialogLoadingProgress = alertDialogBuilder.create()
            dialogLoadingProgress!!.setCanceledOnTouchOutside(false)
            dialogLoadingProgress!!.setCancelable(false)
            dialogLoadingProgress!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogLoadingProgress!!.show()
            dialogLoadingProgress!!.window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        } catch (e: Exception) {
            Log.e(TAG, "hideProgressLoadingDialog: $e")
        }
    }

    fun hideProgressLoadingDialog() {
        try {
            (dialogLoadingProgress as AlertDialog?)!!.dismiss()
        } catch (e: Exception) {
            Log.e(TAG, "hideProgressLoadingDialog: $e")
        }
    }
}
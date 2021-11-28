package com.smi.test.presentation.detailsBrand

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.smi.test.R
import com.smi.test.presentation.entites.Brands

class DetailsBrandActivity : AppCompatActivity() {
    private val TAG = "DetailsBrandActivity"
    var nameBrandTv: TextView? = null
    var descriptionBrandTv: TextView? = null
    var turnoverValueTv: TextView? = null
    var commissionValueTv: TextView? = null
    var numberSalesTv: TextView? = null
    var imgBrand: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_brand)

        val brandItem: Brands = intent.getSerializableExtra("item") as Brands
        Log.e(TAG, "onCreate: $brandItem")

        nameBrandTv = findViewById<TextView>(R.id.name_brand)
        descriptionBrandTv = findViewById<TextView>(R.id.description_brand)
        turnoverValueTv = findViewById<TextView>(R.id.turnover_value_tv)
        commissionValueTv = findViewById<TextView>(R.id.commission_value_tv)
        numberSalesTv = findViewById<TextView>(R.id.number_sales_value_tv)
        imgBrand = findViewById<ImageView>(R.id.item_image)

    }
}
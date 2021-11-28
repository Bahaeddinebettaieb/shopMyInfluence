package com.smi.test.presentation.home.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.smi.test.R
import com.smi.test.presentation.entites.Brands
import com.smi.test.presentation.home.fragments.adapters.PremiumBrandsAdapter
import java.util.*

class PremiumBrandsFragment : Fragment() {
    private val TAG = "PremiumBrandsFragment"
    var recyclerViewPremiumBrand: RecyclerView? = null
    var premiumBrandList: ArrayList<Brands>? = null
    var adapterPremiumBrands: PremiumBrandsAdapter? = null
    var brandPremiumImage: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_premium_brands, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerViewPremiumBrand = view.findViewById<RecyclerView>(R.id.recycler_premium_brands)
//        val layoutManager = LinearLayoutManager(context)
//        recyclerViewPremiumBrand!!.layoutManager = layoutManager
//        premiumBrandList = ArrayList<Brands>()
//        recyclerViewPremiumBrand?.adapter = adapterPremiumBrands

        recyclerViewPremiumBrand!!.setHasFixedSize(true)
        recyclerViewPremiumBrand!!.setLayoutManager(LinearLayoutManager(context))
        premiumBrandList = ArrayList<Brands>()
        getListPremiumBrands()
    }


    private fun getListPremiumBrands() {
        val reference = FirebaseDatabase.getInstance().getReference("brands")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    Log.e(TAG, "onDataChange: $ds.")
                    val brandModel: Brands? = ds.getValue(Brands::class.java)
                    brandModel?.let { premiumBrandList!!.add(it) }
                    adapterPremiumBrands = premiumBrandList?.let {
                        activity?.let { it1 -> PremiumBrandsAdapter(it1, it) }
                    }
                    recyclerViewPremiumBrand!!.adapter = adapterPremiumBrands
                }
            }

            override fun onCancelled(@NonNull databaseError: DatabaseError) {
                Toast.makeText(context, databaseError.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

}
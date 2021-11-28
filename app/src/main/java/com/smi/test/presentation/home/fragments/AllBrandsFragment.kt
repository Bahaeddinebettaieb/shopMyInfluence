package com.smi.test.presentation.home.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.smi.test.R
import com.smi.test.presentation.entites.Brands
import com.smi.test.presentation.home.fragments.adapters.BrandsAdapter
import java.util.ArrayList

class AllBrandsFragment : Fragment() {
    private val TAG = "AllBrandsFragment"
    var recyclerViewAllBrand: RecyclerView? = null
    var allBrandList: ArrayList<Brands>? = null
    var adapterAllBrands: BrandsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_brands, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerViewAllBrand = view.findViewById<RecyclerView>(R.id.recycler_all_brands)
        recyclerViewAllBrand!!.layoutManager = GridLayoutManager(context, 4)
        adapterAllBrands = context?.let {
            allBrandList?.let { it1 ->
                BrandsAdapter(it, it1)
            }
        }
//        recyclerViewAllBrand!!.adapter = adapterAllBrands
        allBrandList = ArrayList<Brands>()
        getListAllBrands()
    }

    private fun getListAllBrands() {
        val reference = FirebaseDatabase.getInstance().getReference("brands")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val brandModel: Brands? = ds.getValue(Brands::class.java)
                    Log.e(TAG, "onDataChange: $ds")
                    brandModel?.let { allBrandList!!.add(it) }
                    adapterAllBrands = allBrandList?.let {
                        activity?.let { it1 -> BrandsAdapter(it1, it) }
                    }
                    recyclerViewAllBrand!!.adapter = adapterAllBrands
                }
            }

            override fun onCancelled(@NonNull databaseError: DatabaseError) {
                Toast.makeText(context, databaseError.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
package com.smi.test.presentation.home

import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.smi.test.R
import com.smi.test.presentation.App.Companion.context
import com.smi.test.presentation.customView.ClickableViewPager
import com.smi.test.presentation.customView.DotsIndicator
import com.smi.test.presentation.entites.Brands
import com.smi.test.presentation.home.adapters.SlideImageAdapter
import com.smi.test.presentation.home.fragments.AllBrandsFragment
import com.smi.test.presentation.home.fragments.PremiumBrandsFragment
import com.smi.test.presentation.home.fragments.adapters.BrandsAdapter
import com.smi.test.presentation.utils.GlobalUtils
import kotlinx.android.synthetic.main.activity_home.*
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.WindowManager


class HomeActivity : AppCompatActivity() {
    private val TAG = "HomeActivity"
    var newBrandsList = ArrayList<Brands>()
    var sliderImage: SlideImageAdapter? = null
    var viewPager: ClickableViewPager? = null
    var dotsIndicator: DotsIndicator? = null
    var dialogLoadingProgress: Dialog? = null
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewPager = findViewById<ClickableViewPager>(R.id.viewPager)
        dotsIndicator = findViewById<DotsIndicator>(R.id.dotsIndicator)
        progressDialog = ProgressDialog(this);

        sliderImage = SlideImageAdapter(context, newBrandsList)
        viewPager?.adapter = sliderImage
        sliderImage?.notifyDataSetChanged()
        dotsIndicator?.setViewPager(viewPager!!)
        setNewBrandData(newBrandsList)
        getListNewBrands()


        setFragment(R.id.frame, PremiumBrandsFragment())

        when {
            intent.extras == null -> {
            }
            intent.extras!!.getString("id").equals("0") -> {
                Log.e(TAG, "init: 0")
                setFragment(R.id.frame, PremiumBrandsFragment())
                val tab: TabLayout.Tab = tab_brands.getTabAt(0)!!
                tab_brands.selectTab(tab)
            }
            intent.extras!!.getString("id").equals("1") -> {
                Log.e(TAG, "init: 1")
                setFragment(R.id.frame, AllBrandsFragment())
                val tab: TabLayout.Tab = tab_brands.getTabAt(1)!!
                tab_brands.selectTab(tab)
            }
        }

        tab_brands.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onTabSelected(tab: TabLayout.Tab) {

                when (tab.position) {
                    0 -> {
                        setFragment(R.id.frame, PremiumBrandsFragment())
                    }
                    1 -> {
                        setFragment(R.id.frame, AllBrandsFragment())
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }



    private fun getListNewBrands() {
        showProgressLoadingDialog()
        val reference = FirebaseDatabase.getInstance().getReference("brands")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                hideProgressLoadingDialog();
                for (ds in dataSnapshot.children) {
                    val brandModel: Brands? = ds.getValue(Brands::class.java)
                    if (ds.child("isNew").value == true) {
                        Log.e(TAG, "onDataChange: $ds")
                        brandModel?.let {
                            newBrandsList.add(it)
                            setNewBrandData(newBrandsList)
                            scrollBrandTo(0)
                        }
//                        sliderImage = SlideImageAdapter(context, newBrandsList)

                    }
                }
            }

            override fun onCancelled(@NonNull databaseError: DatabaseError) {
                hideProgressLoadingDialog();
                Toast.makeText(context, databaseError.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun setFragment(id: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(id, fragment).commit()
    }

    fun setNewBrandData(list: ArrayList<Brands>) {
        sliderImage?.setList(list)
        viewPager?.let { dotsIndicator!!.setViewPager(it) }
        sliderImage?.notifyDataSetChanged()
    }

    fun scrollBrandTo(pos: Int) {
        viewPager?.setCurrentItem(pos, true)
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

    override fun onBackPressed() {

    }

}
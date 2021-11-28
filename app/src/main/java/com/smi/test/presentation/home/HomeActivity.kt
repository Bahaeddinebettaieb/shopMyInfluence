package com.smi.test.presentation.home

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.android.material.tabs.TabLayout
import com.smi.test.R
import com.smi.test.presentation.home.fragments.AllBrandsFragment
import com.smi.test.presentation.home.fragments.PremiumBrandsFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private val TAG = "HomeActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportFragmentManager.beginTransaction().replace(R.id.frame, PremiumBrandsFragment()).commit()

        when {
            intent.extras == null -> {
            }
            intent.extras!!.getString("id").equals("0") -> {
                Log.e(TAG, "init: 0")
                supportFragmentManager.beginTransaction().replace(R.id.frame, PremiumBrandsFragment()).commit()
                val tab: TabLayout.Tab = tab_brands.getTabAt(0)!!
                tab_brands.selectTab(tab)
            }
            intent.extras!!.getString("id").equals("1") -> {
                Log.e(TAG, "init: 1")
                supportFragmentManager.beginTransaction().replace(R.id.frame, AllBrandsFragment()).commit()
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
                        supportFragmentManager.beginTransaction().replace(R.id.frame, PremiumBrandsFragment()).commit()
                    }
                    1 -> {
                        supportFragmentManager.beginTransaction().replace(R.id.frame, AllBrandsFragment()).commit()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}
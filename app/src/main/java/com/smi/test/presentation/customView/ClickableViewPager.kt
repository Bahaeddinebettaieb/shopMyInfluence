package com.smi.test.presentation.customView

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class ClickableViewPager : ViewPager {
    private var mOnClickListener: OnClickListener? =
        null

    constructor(context: Context?) : super(context!!) {
        setup()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) {
        setup()
    }

    private fun setup() {
        val tapGestureDetector =
            GestureDetector(context, TapGestureListener())
        setOnTouchListener { v, event ->
            tapGestureDetector.onTouchEvent(event)
            false
        }
    }

    fun setOnViewPagerClickListener(onClickListener: OnClickListener?) {
        mOnClickListener = onClickListener
    }

    interface OnClickListener {
        fun onViewPagerClick(viewPager: ViewPager?)
    }

    private inner class TapGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            if (mOnClickListener != null) {
                mOnClickListener!!.onViewPagerClick(this@ClickableViewPager)
            }
            return true
        }
    }
}

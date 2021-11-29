package com.smi.test.presentation.home.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.media.AudioManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.smi.test.R
import com.smi.test.presentation.entites.Brands
import com.squareup.picasso.Picasso

class SlideImageAdapter(
    private val context: Context,
    private var newBrandsList: ArrayList<Brands>
) :
    PagerAdapter() {
    private var layoutInflater: LayoutInflater? = null
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return newBrandsList.size
    }

    lateinit var videoURL: String
    lateinit var mAudioManager: AudioManager
    var current_volume = 0
    lateinit var soundManage: ImageView
    lateinit var videoConst: ConstraintLayout
    private val TAG = "SlideImageAdapter"

    @SuppressLint("InflateParams")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        Log.e(TAG, "instantiateItem: position= $position")
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater!!.inflate(R.layout.slide_image_home, null)
        val image = view.findViewById<View>(R.id.imageView) as ImageView
        val progressBar = view.findViewById<View>(R.id.progress_bar) as ProgressBar

        Thread {
            (Runnable {
                Picasso.get().load(newBrandsList[position].pic).into(image)
            })
        }.start()

        if (newBrandsList[position].pic != null)
            Glide.with(view.rootView.context)
                .load(newBrandsList[position].pic)
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
                        progressBar.visibility =
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
                        progressBar.visibility =
                            View.GONE
                        return false
                    }
                }).into(image)

//        image.setOnClickListener {
//            when (newBrandsList[position].type) {         //0: coupon 1:offer 2:supplier
//                0 -> {
//                    val intent = Intent(view.context, DetailsCouponActivity::class.java)
//                    intent.putExtra("id", adsList[position].coupon?.id)
//                    view.context.startActivity(intent)
//                }
//                1 -> {
//                    val intent = Intent(view.context, DetailsOffersActivity::class.java)
//                    intent.putExtra("item", adsList[position].offers?.id)
//                    view.context.startActivity(intent)
//                }
//                2 -> {
//                    val intent = Intent(view.context, DetailsCenterActivity::class.java)
//                    intent.putExtra("id", adsList[position].suppliers?.id)
//                    view.context.startActivity(intent)
//                }
//            }
//        }

        val vp = container as ViewPager
        vp.addView(view, 0)
        return view

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val v = `object` as View
        vp.removeView(v)
    }

    fun setList(list: ArrayList<Brands>) {
        newBrandsList = list
//        notifyDataSetChanged()
    }
}

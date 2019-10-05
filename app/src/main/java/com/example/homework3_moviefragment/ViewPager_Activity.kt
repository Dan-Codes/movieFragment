package com.example.homework3_moviefragment

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.pagerviewdemo.FirstFragment
import com.example.pagerviewdemo.SecondFragment
import com.example.pagerviewdemo.ThirdFragment
import kotlinx.android.synthetic.main.activity_view_pager.*

class ViewPager_Activity : AppCompatActivity(), FirstFragment.OnFragmentInteractionListener,
    SecondFragment.OnFragmentInteractionListener, ThirdFragment.OnFragmentInteractionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        viewP3.adapter = SimplePagerAdapter(supportFragmentManager)

        viewP3.currentItem = 0
    }

    override fun onFragmentInteraction(uri: Uri) {
        // save some data from the fragment...
        // other business logic...
    }

    class SimplePagerAdapter(FragmentManager: FragmentManager) :
        FragmentPagerAdapter(FragmentManager) {
        override fun getItem(p0: Int): Fragment {
            Log.i("SimplePagerAdapter"
                , "item index $p0" )
            when (p0){
                0 -> {
                    return FirstFragment()
                }
                1 -> {
                    return SecondFragment()
                }
                2 -> {
                    return ThirdFragment()
                }
            }
            return FirstFragment()
        }

        override fun getCount(): Int {
            return 3
        }


    }

//    class SimplePagerAdapter(fragmentManager: androidx.fragment.app.FragmentManager)
//        : androidx.fragment.app.FragmentStatePagerAdapter(fragmentManager) {
//        override fun getItem(p0: Int): Fragment {
//            Log.i("SimplePagerAdapter"
//                , "item index $p0" )
//            when (p0){
//                0 -> {
//                    return FirstFragment()
//                }
//                1 -> {
//                    return SecondFragment()
//                }
//                2 -> {
//                    return ThirdFragment()
//                }
//            }
//            return null
//        }
//        override fun getCount(): Int {
//            return 3
//        }
//    }

    class ViewPagerActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
// View Pager
            viewP3.adapter = SimplePagerAdapter(supportFragmentManager)
            viewP3.currentItem = 0

// Page Transform
            viewP3.setPageTransformer(false, MyPageTransformer())
        }
    }

    private class MyPageTransformer: androidx.viewpager.widget.ViewPager.PageTransformer {
        private val MIN_SCALE = 0.85F
        private val MIN_ALPHA = 0.5F
        override fun transformPage(p0: View, p1: Float) {
            val pageW = p0.width
            val pageH = p0.height
            if (p1 < -1) { // way off-screen to the left!
                p0.alpha = 0f
            }
            else if (p1 <= 0) { //[-1 0]
                val scaleFactor = Math.max(MIN_SCALE, 1 -Math.abs(p1))
                val verMargin = pageH * (1-scaleFactor)/2
                val horMargin = pageW * (1-scaleFactor)/2
                if (p1 < 0)
                    p0.translationX = horMargin - verMargin/2
                else
                    p0.translationX = verMargin/2 - horMargin
                p0.scaleX = scaleFactor
                p0.scaleY = scaleFactor
                p0.alpha = MIN_ALPHA + (scaleFactor - MIN_SCALE)/(1 - MIN_SCALE)*(1 - MIN_ALPHA)
            }
            else { // (1, +infinity
                p0.alpha = 0F
            }
        }
    }
}



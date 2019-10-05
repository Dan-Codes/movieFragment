package com.example.homework3_moviefragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.core.view.GestureDetectorCompat
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_movie.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private const val DEBUG_TAG = "Gestures"
class MovieFragment : Fragment(), GestureDetector.OnGestureListener {
    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: FrontPageFragment.OnFragmentInteractionListener? = null
    lateinit var movieList: List<MovieData>
    lateinit var posterTable:MutableMap<String, Int>
    private var movieIndex: Int = 0
    private lateinit var snack: Snackbar
    //private const val DEBUG_TAG = "Gestures"
    var downX:Float = 0.0f
    private lateinit var mDetector: GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDetector = GestureDetectorCompat(this.context, this)

        this.movieList = Gson().fromJson(movies, Array<MovieData>::class.java).asList()
        posterTable = mutableMapOf()
        posterTable["It Chapter Two"] = R.drawable.it
        posterTable["Spider-Man: Far from Home"] = R.drawable.spiderman
        posterTable["The Old Man & the Gun"] = R.drawable.old
        posterTable["Hustlers"] = R.drawable.hustlers
        posterTable["John Wick: Chapter 3 – Parabellum"] = R.drawable.johnwick
        loadMovieInfo()
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int,
                                           fromUser: Boolean) {

                val w:Int = movieFrag.width
                val h = movieFrag.height
                var width = w*progress/100
                var height = h*progress/100
                movieView.layoutParams=ConstraintLayout.LayoutParams(width, height)
//                    movieView.scaleType = ImageView.ScaleType.CENTER_CROP
//                Toast.makeText(applicationContext, "seekbar progress: $progress", Toast.LENGTH_SHORT).show()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

//            override fun onStopTrackingTouch(seekBar: SeekBar) {
//                Toast.makeText(applicationContext, "seekbar touch stopped at ${seekBar.progress}%", Toast.LENGTH_SHORT).show()
//            }
        })

//        movieView.setOnClickListener {
//            Toast.makeText(this, "Click detected", Toast.LENGTH_SHORT).show()
//            snack=Snackbar.make(seekbar, "Hello", Snackbar.LENGTH_LONG)
//            snack.show()
//
//        }
//        movieView.setOnLongClickListener{
//            seekBar.progress = 50
//            movieView.setImageResource(posterTable.get(movieList[movieIndex].title)!!)
//            Toast.makeText(this, "Long click detected", Toast.LENGTH_SHORT).show()
//            true
//        }


        movieView.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    downX = event.x // starting point
                }
                MotionEvent.ACTION_UP -> {
                    val deltaX = downX - event.x // calculate distance
// horizontal swipe action detection
                    if (kotlin.math.abs(deltaX) > 20F) {
// left to right or right to next?
                        if ( deltaX > 0 ){
// right to left (move to next movie)
                            movieIndex++
                            if (movieIndex >= movieList.size){ // last movie, stay!!
                                movieIndex = movieList.size - 1
                            } else {
                                loadMovieInfo()
                                Log.d("DEBUG", "onFling: $downX ${event.x}")
                            }
                        } else {
// left to right (move to previous movie)
                            movieIndex--
                            if (movieIndex < 0) { // first movie, stay!!
                                movieIndex = 0
                            } else {
                                loadMovieInfo()
                                Log.d("DEBUG", "onFling: $downX ${event.x}")
                            }
                        }
                    }
                }
            }
            v?.onTouchEvent(event) ?: true
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }






    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        Log.d(DEBUG_TAG, "onSingleTapUp: $e")
        return true
    }



    override fun onDown(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onDown: $event")
        return true
    }

    override fun onFling(
        event1: MotionEvent,
        event2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        Log.d(DEBUG_TAG, "onFling: $event1 $event2")

        when (event1?.action) {
            MotionEvent.ACTION_UP -> {
                //downX = event.x // starting point

            }
            MotionEvent.ACTION_DOWN -> {
                val deltaX = event1.x - event2.x // calculate distance
// horizontal swipe action detection
                if (kotlin.math.abs(deltaX) > 20F) {
// left to right or right to next?
                    if ( deltaX > 0 ){
// right to left (move to next movie)
                        movieIndex++
                        if (movieIndex >= movieList.size){ // last movie, stay!!
                            movieIndex = movieList.size - 1
                        } else {
                            loadMovieInfo()
                        }
                    } else {
// left to right (move to previous movie)
                        movieIndex--
                        if (movieIndex < 0) { // first movie, stay!!
                            movieIndex = 0
                        } else {
                            loadMovieInfo()
                        }
                    }
                }
            }
        }
        return true
    }

    private fun loadMovieInfo() {
// load image information with movieIndex
        movieView.setImageResource(posterTable.get(movieList[movieIndex].title)!!)
        movieTitle.text = movieList[movieIndex].title
//        movieID.text = movieList[movieIndex].id.toString()
        ratingBar.rating = ((movieList[movieIndex].vote_average.toFloat())*5)/10
        rating.text = (movieList[movieIndex].vote_average.toFloat()).toString()
        movieOverview.text = movieList[movieIndex].overview
//        imageHeight = `@+id/movieView`.drawable.intrinsicHeight
//        imageWidth = `@+id/movieView`.drawable.intrinsicWidth
//        changeImageViewSize(imageHeight, imageWidth)
    }



    }


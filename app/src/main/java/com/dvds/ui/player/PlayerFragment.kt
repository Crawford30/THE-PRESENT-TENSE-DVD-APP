package com.dvds.ui.player

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.dvds.MyApp
import com.dvds.R
import com.dvds.databinding.FragmentPlayerBinding
import com.dvds.helpers.Constants
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.HttpDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import kotlinx.android.synthetic.main.custom_controller.*
import kotlinx.android.synthetic.main.fragment_player.*


class PlayerFragment : Fragment() {
    private lateinit var binding : FragmentPlayerBinding
    lateinit var handler: Handler
  //  lateinit var bt_fullscreen: ImageView
    private var videoUrl: String? = null
    private lateinit var httpDataSourceFactory: HttpDataSource.Factory
    private lateinit var defaultDataSourceFactory: DefaultDataSourceFactory
    private lateinit var cacheDataSourceFactory: DataSource.Factory
    private lateinit var simpleExoPlayer: SimpleExoPlayer
    private val simpleCache: SimpleCache = MyApp.simpleCache

    companion object {
        var isFullScreen = false
        var isLock = false
        fun newInstance() = PlayerFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentPlayerBinding.inflate(inflater, container, false)

       // bt_fullscreen = findViewById(R.id.btn_fullscreen);


//        val playerView = findViewById<PlayerView>(R.id.player)
//        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
//        bt_fullscreen =  findViewById<ImageView>(R.id.bt_fullscreen)
//        val bt_lockscreen = findViewById<ImageView>(R.id.exo_lock)

        val progressBar = view?.findViewById<ProgressBar>(R.id.progress_bar);
        //bt_fullscreen = view?.findViewById<ImageView>(R.id.btnFullscreen)!!
       // val bt_lockscreen =  view?.findViewById<ImageView>(R.id.exoLock)


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        videoUrl = arguments?.getString(Constants.VIDEO_URL)

        initPlayer()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler = Handler(Looper.getMainLooper())

        bt_fullscreen.setOnClickListener {

                if(!isFullScreen)
                {
                    bt_fullscreen.setImageDrawable(activity?.let { it1 ->
                        ContextCompat.getDrawable(
                            it1.applicationContext, R.drawable.ic_baseline_fullscreen_exit)
                    })


                    activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                }
                else{
                    bt_fullscreen.setImageDrawable(activity?.let { it1 ->
                        ContextCompat.getDrawable(
                            it1.applicationContext, R.drawable.ic_baseline_fullscreen)
                    })
                    activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                }
                isFullScreen = !isFullScreen


            }


        exo_lock.setOnClickListener {
            if(!isLock)
            {
                exo_lock.setImageDrawable(activity?.let { it1 -> ContextCompat.getDrawable(it1.applicationContext, R.drawable.ic_baseline_lock) })
            }
            else
            {
                exo_lock.setImageDrawable(activity?.let { it1 -> ContextCompat.getDrawable(it1.applicationContext, R.drawable.ic_baseline_lock_open) })
            }
            isLock = !isLock
            lockScreen(isLock)
        }


    }



    private fun lockScreen(lock: Boolean) {
//        val sec_mid = findViewById<LinearLayout>(R.id.sec_controlvid1)
//        val sec_bottom = findViewById<LinearLayout>(R.id.sec_controlvid2)
        if(lock)
        {
            sec_controlvid1.visibility = View.INVISIBLE
            sec_controlvid2.visibility = View.INVISIBLE
        }
        else
        {
            sec_controlvid1.visibility = View.VISIBLE
            sec_controlvid2.visibility = View.VISIBLE
        }
    }
    private fun initPlayer() {
        httpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)

        defaultDataSourceFactory = DefaultDataSourceFactory(
            requireContext(), httpDataSourceFactory
        )

        cacheDataSourceFactory = CacheDataSource.Factory()
            .setCache(simpleCache)
            .setUpstreamDataSourceFactory(httpDataSourceFactory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)

        simpleExoPlayer = SimpleExoPlayer.Builder(requireContext())
            .setMediaSourceFactory(DefaultMediaSourceFactory(cacheDataSourceFactory)).build()

        playerView.keepScreenOn = true
        simpleExoPlayer.addListener(object: Player.Listener{
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int)
            {
                if(playbackState == Player.STATE_BUFFERING)
                {
                    progress_bar.visibility = View.VISIBLE
                }
                else if(playbackState == Player.STATE_READY)
                {
                    progress_bar.visibility = View.GONE
                }

                if(!simpleExoPlayer.playWhenReady)
                {
                    handler.removeCallbacks(updateProgressAction)
                }
                else
                {
                    onProgress()
                }
            }
        })

        val videoUri = Uri.parse("http://10.0.2.2:8000/storage/presenttense/videodvds/c9e288-2a0789-ac574a-c4367b-27c6a2.mp4")
            //Uri.parse("http://10.0.2.2:8000/storage/presenttense/videodvds/9914bc-1fe20c-4fd4a2-3e3f70-681fbe.mp4")

            ///presenttense/videodvds/9914bc-1fe20c-4fd4a2-3e3f70-681fbe.mp4

            //Uri.parse("http://10.0.2.2:8000/storage/presenttense/videodvds/c9e288-2a0789-ac574a-c4367b-27c6a2.mp4")
        val mediaItem = MediaItem.fromUri(videoUri)
        val mediaSource = ProgressiveMediaSource.Factory(cacheDataSourceFactory).createMediaSource(mediaItem)

        playerView.player = simpleExoPlayer
        simpleExoPlayer.playWhenReady = true
        simpleExoPlayer.seekTo(0, 0)
        simpleExoPlayer.repeatMode = Player.REPEAT_MODE_OFF
        simpleExoPlayer.setMediaSource(mediaSource, true)
        simpleExoPlayer.prepare()
    }

    private val updateProgressAction = Runnable { onProgress() }


    fun onProgress()
    {
        val player= simpleExoPlayer
        val position :Long =if(player == null) 0 else player.currentPosition
        handler.removeCallbacks(updateProgressAction)
        val playbackState = if(player ==null) Player.STATE_IDLE else player.playbackState
        if(playbackState != Player.STATE_IDLE && playbackState!= Player.STATE_ENDED)
        {
            var delayMs :Long
            if(player.playWhenReady && playbackState == Player.STATE_READY)
            {
                delayMs  = 1000 - position % 1000
                if(delayMs < 200)
                {
                    delayMs+=1000
                }
            }
            else{
                delayMs = 1000
            }


            handler.postDelayed(updateProgressAction,delayMs)
        }
    }

//    override fun onBackPressed() {
//        if(isLock) return
//        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
//        {
//            btn_fullscreen.performClick()
//        }
//        else super.onBackPressed()
//
//    }

    override fun onStop() {
        super.onStop()
        simpleExoPlayer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        simpleExoPlayer.release()
    }

    override fun onPause() {
        super.onPause()
        simpleExoPlayer.pause()
    }

}

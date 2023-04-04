package com.dvds.ui.player

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dvds.MyApp
import com.dvds.R
import com.dvds.helpers.Constants
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.HttpDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import kotlinx.android.synthetic.main.fragment_player.*


class PlayerFragment : Fragment() {
    private var videoUrl: String? = null
    private lateinit var httpDataSourceFactory: HttpDataSource.Factory
    private lateinit var defaultDataSourceFactory: DefaultDataSourceFactory
    private lateinit var cacheDataSourceFactory: DataSource.Factory
    private lateinit var simpleExoPlayer: SimpleExoPlayer
    private val simpleCache: SimpleCache = MyApp.simpleCache

    companion object {
        fun newInstance() = PlayerFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        videoUrl = arguments?.getString(Constants.VIDEO_URL)

        initPlayer()
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

}

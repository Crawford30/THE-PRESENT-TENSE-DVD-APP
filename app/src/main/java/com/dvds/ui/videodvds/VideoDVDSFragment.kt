package com.dvds.ui.videodvds

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dvds.R
import com.dvds.data.adapters.PresentTenseVideoDVDAdapter
import com.dvds.data.adapters.VideoDVDItemClickListener
import com.dvds.data.db.presenttenseaudiodvd.PresentTenseAudioDVDDatabase
import com.dvds.data.db.presenttensevideodvd.PresentTenseVideoDVDDatabase
import com.dvds.data.model.VideoDVD
import com.dvds.data.network.DVDApi
import com.dvds.data.network.Resource
import com.dvds.data.respository.VideoDVDRepository
import com.dvds.data.service.VideoPreLoadingService
import com.dvds.databinding.FragmentVideoDVDSBinding
import com.dvds.helpers.Constants
import com.dvds.helpers.TopSpacingItemDecoration
import com.dvds.helpers.handleApiError
import com.dvds.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_audio_d_v_d_s.*
import kotlinx.android.synthetic.main.fragment_video_d_v_d_s.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.json.JSONArray


class VideoDVDSFragment : BaseFragment<VideoDVDViewModel, FragmentVideoDVDSBinding, VideoDVDRepository>(),
    VideoDVDItemClickListener {


    private var videoList = arrayListOf<String>()
    private lateinit var presentTenseVideoDVDAdapter: PresentTenseVideoDVDAdapter


    private  var  presentTenseList = ArrayList<VideoDVD>()



    private val presentTenseVideoDVDSList = mutableListOf<VideoDVD>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchIcon = search_video_dvd_recycler_fragment.findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.WHITE)


        //color of cancel btn
        val cancelIcon = search_video_dvd_recycler_fragment.findViewById<ImageView>(R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.WHITE)

        //color of text view
        val textView = search_video_dvd_recycler_fragment.findViewById<TextView>(R.id.search_src_text)
        textView.setTextColor(Color.WHITE)

        viewModel.getPresentTenseVideoDVDLists()

        //observer the songs
        viewModel.presentTenseVideoDVD.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    print("LIST DATA BEFORE MODIFICATION ${it.value.results}")
                    // binding.progressbar.visible(false)
                    Toast.makeText(context?.applicationContext, "Present Tense Songs: ${it.value.results}" , Toast.LENGTH_LONG).show()


                    val mainObject = JSONArray(it.value.results)

                    // val myJsonArray: JSONArray = mainObject.getJSONArray("results")

                    var list = (it.value.results.toMutableList())

                    var mutableArray = presentTenseList.toMutableList()


                    Log.d("HomeFragment", "Present Tense LIST VALUE ARRAY: ${list}")

                    PresentTenseVideoDVDDatabase.invoke(activity!!.applicationContext).presentTenseVideoDVDDao().deleteAllPresentTenseVideoDVD()
                    for(element in list) {
                        val videoDVDData = VideoDVD(
                            videoDVDName = element.videoDvdName,
                            videoDVDPath = element.videoDvdPath,
                            creationDate = element.creationDate,
                        )

                        presentTenseVideoDVDSList.add(videoDVDData)

                        mutableArray.add(VideoDVD(element.videoDvdName, element.videoDvdPath, element.creationDate))
                        Log.d("HomeFragment", "Present Tense VIDEO DVD LIST VALUE: ${element.videoDvdName}")
                        //print(" ${element.songBody}")
                    }

                    PresentTenseVideoDVDDatabase.invoke(activity!!.applicationContext).presentTenseVideoDVDDao().insertPresentTenseVideoDVD(presentTenseVideoDVDSList)

                    print("LIST DATA CHANGE MODIFIED ${presentTenseVideoDVDSList}")



                    Log.d("HomeFragment", "Present Tense LIST: ${it.value.results}")

                    binding.videoDvdRecyclerView.apply {

                        layoutManager = LinearLayoutManager(activity)

                        val topSPacingDecoration = TopSpacingItemDecoration(20)
                        addItemDecoration(topSPacingDecoration)


                        val videoDVDList =   PresentTenseVideoDVDDatabase.invoke(activity!!.applicationContext).presentTenseVideoDVDDao().getPresentTenseVideoDVDLists()

                        presentTenseVideoDVDAdapter = PresentTenseVideoDVDAdapter((videoDVDList as ArrayList<VideoDVD>),this@VideoDVDSFragment, findNavController())
                            //PresentTenseVideoDVDAdapter((videoDVDList as ArrayList<VideoDVD>), this@VideoDVDSFragment ,findNavController())

                        adapter =  presentTenseVideoDVDAdapter

                    }


                    Toast.makeText(context?.applicationContext, "Present Tense Songs: ${presentTenseList}" , Toast.LENGTH_LONG).show()

                    binding.searchVideoDvdRecyclerFragment.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            presentTenseVideoDVDAdapter.filter.filter(newText)
                            return false
                        }

                    })

                }

                is Resource.Loading -> {
                    //binding.progressbar.visible(true)
                }

                is Resource.Failure -> {
                    handleApiError(it)
                }
            }
        })




    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
    override fun getViewModel() = VideoDVDViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentVideoDVDSBinding.inflate(inflater, container, false)
    override fun getFragmentRepository(): VideoDVDRepository {
        //create an instance of song repository which needs an instance of a SongApi
        val token = runBlocking {
            userPreferences.authToken.first()
        }

        //1. api instance
        val api = remoteDataSource.buildApi(DVDApi::class.java, token)
        return VideoDVDRepository(api)
    }

    override fun onItemClick(videoDVDItem: VideoDVD, position: Int, view: View) {
        videoList.clear()

        val VIDEO_URL = Constants.BASE_URL + "storage/" + videoDVDItem.videoDVDPath

        //Declare a bundle to pass data to player screen
        val bundle = Bundle()
        bundle.putString(Constants.VIDEO_URL, VIDEO_URL)
        bundle.putString(Constants.DVD_NAME, videoDVDItem.videoDVDName)

        //Load the player service
        startPreLoadingService()

        //Navigate to the player fragment
        requireView().findNavController().navigate(R.id.action_to_player, bundle)
        //requireActivity().startNewActivity(DetailActivity::class.java)

        Log.d("HomeFragment", "ITEM CLICKED VIDEO LIST: ${videoList}")
    }

    private fun startPreLoadingService() {
        val preloadingServiceIntent = Intent(context, VideoPreLoadingService::class.java)
        preloadingServiceIntent.putStringArrayListExtra(Constants.VIDEO_LIST, videoList)
        context?.startService(preloadingServiceIntent)
    }




}
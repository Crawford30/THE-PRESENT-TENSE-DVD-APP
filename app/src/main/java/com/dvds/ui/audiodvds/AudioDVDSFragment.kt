package com.dvds.ui.audiodvds

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.dvds.R
import com.dvds.data.adapters.AudioDVDItemClickListener
import com.dvds.data.adapters.PresentTenseAudioDVDAdapter
import com.dvds.data.callback.HomeScreenCallback
import com.dvds.data.db.presenttenseaudiodvd.PresentTenseAudioDVDDatabase
import com.dvds.data.model.AudioDVD
import com.dvds.data.network.DVDApi
import com.dvds.data.network.Resource
import com.dvds.data.respository.AudioDVDRepository
import com.dvds.data.service.VideoPreLoadingService
import com.dvds.databinding.FragmentAudioDVDSBinding
import com.dvds.helpers.Constants
import com.dvds.helpers.TopSpacingItemDecoration
import com.dvds.helpers.handleApiError
import com.dvds.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_audio_d_v_d_s.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.json.JSONArray

class AudioDVDSFragment : BaseFragment<AudioDVDViewModel,FragmentAudioDVDSBinding,AudioDVDRepository>(), AudioDVDItemClickListener{

    private var homeScreenCallback: HomeScreenCallback? = null
    private var videoList = arrayListOf<String>()
    private lateinit var presentTenseAudioDVDAdapter: PresentTenseAudioDVDAdapter


    private  var  presentTenseList = ArrayList<AudioDVD>()



    private val presentTenseAudioDVDSList = mutableListOf<AudioDVD>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchIcon = search_recycler_fragment.findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.WHITE)


        //color of cancel btn
        val cancelIcon = search_recycler_fragment.findViewById<ImageView>(R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.WHITE)

        //color of text view
        val textView = search_recycler_fragment.findViewById<TextView>(R.id.search_src_text)
        textView.setTextColor(Color.WHITE)

        viewModel.getPresentTenseAudioDVDLists()

        //observer the songs
        viewModel.presentTenseAudioDVD.observe(viewLifecycleOwner, Observer {
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

                   PresentTenseAudioDVDDatabase.invoke(activity!!.applicationContext).presentTenseAudioDVDDao().deleteAllPresentTenseAudioDVD()
                    for(element in list) {
                        val audioDVDData = AudioDVD(
                            audioDVDName = element.audioDvdName,
                            audioDVDPath = element.audioDvdPath,
                            creationDate = element.creationDate,
                        )

                        presentTenseAudioDVDSList.add(audioDVDData)

                        mutableArray.add(AudioDVD(element.audioDvdName, element.audioDvdPath, element.creationDate))
                        Log.d("HomeFragment", "Present Tense AUDIO DVD LIST VALUE: ${element.audioDvdName}")
                        //print(" ${element.songBody}")
                    }

                    PresentTenseAudioDVDDatabase.invoke(activity!!.applicationContext).presentTenseAudioDVDDao().insertPresentTenseAudioDVD(presentTenseAudioDVDSList)

                    print("LIST DATA CHANGE MODIFIED ${presentTenseAudioDVDSList}")



                    Log.d("HomeFragment", "Present Tense LIST: ${it.value.results}")

                    binding.dvdRecyclerView.apply {

                        layoutManager = LinearLayoutManager(activity)

                        val topSPacingDecoration = TopSpacingItemDecoration(20)
                        addItemDecoration(topSPacingDecoration)


                        val audioDVDList =   PresentTenseAudioDVDDatabase.invoke(activity!!.applicationContext).presentTenseAudioDVDDao().getPresentTenseAudioDVDLists()

                      presentTenseAudioDVDAdapter = PresentTenseAudioDVDAdapter((audioDVDList as ArrayList<AudioDVD>), this@AudioDVDSFragment ,findNavController())

                        adapter =  presentTenseAudioDVDAdapter

                    }


                    Toast.makeText(context?.applicationContext, "Present Tense Songs: ${presentTenseList}" , Toast.LENGTH_LONG).show()

                    binding.searchRecyclerFragment.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            presentTenseAudioDVDAdapter.filter.filter(newText)
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
    override fun getViewModel() = AudioDVDViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentAudioDVDSBinding.inflate(inflater, container, false)
    override fun getFragmentRepository(): AudioDVDRepository {
        //create an instance of song repository which needs an instance of a SongApi
        val token = runBlocking {
            userPreferences.authToken.first()
        }

        //1. api instance
        val api = remoteDataSource.buildApi(DVDApi::class.java, token)
        return AudioDVDRepository(api)
    }

    override fun onItemClick(audioDVDItem: AudioDVD, position: Int, view: View) {
        videoList.clear()

        val VIDEO_URL = Constants.BASE_URL + "storage/" + audioDVDItem.audioDVDPath

        //Declare a bundle to pass data to player screen
        val bundle = Bundle()
        bundle.putString(Constants.VIDEO_URL, VIDEO_URL)
        bundle.putString(Constants.DVD_NAME, audioDVDItem.audioDVDName)

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
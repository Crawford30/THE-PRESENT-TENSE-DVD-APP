package com.dvds.ui.audiodvds

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dvds.R
import com.dvds.data.adapters.PresentTenseAudioDVDAdapter
import com.dvds.data.db.presenttenseaudiodvd.PresentTenseAudioDVDDatabase
import com.dvds.data.model.AudioDVD
import com.dvds.data.network.DVDApi
import com.dvds.data.network.Resource
import com.dvds.data.respository.AudioDVDRepository
import com.dvds.databinding.FragmentAudioDVDSBinding
import com.dvds.helpers.TopSpacingItemDecoration
import com.dvds.helpers.handleApiError
import com.dvds.helpers.visible
import com.dvds.ui.base.BaseFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.json.JSONArray

class AudioDVDSFragment : BaseFragment<AudioDVDViewModel,FragmentAudioDVDSBinding,AudioDVDRepository>(){
    private lateinit var presentTenseAudioDVDAdapter: PresentTenseAudioDVDAdapter


    private  var  presentTenseList = ArrayList<AudioDVD>()



    private val presentTenseAudioDVDSList = mutableListOf<AudioDVD>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPresentTenseAudioDVDLists()

        //observer the songs
        viewModel.presentTenseAudioDVD.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    print("LIST DATA BEFORE MODIFICATION ${it.value.audioDVDResults}")
                    binding.progressbar.visible(false)
                    Toast.makeText(context?.applicationContext, "Present Tense Songs: ${it.value.audioDVDResults}" , Toast.LENGTH_LONG).show()


                    val mainObject = JSONArray(it.value.audioDVDResults)

                    // val myJsonArray: JSONArray = mainObject.getJSONArray("results")

                    var list = (it.value.audioDVDResults)

                    //var mutableArray = presentTenseList.toMutableList()


                    Log.d("HomeFragment", "Present Tense LIST VALUE ARRAY: ${list}")

//                    PresentTenseAudioDVDDatabase.invoke(activity!!.applicationContext).presentTenseAudioDVDDao().deleteAllPresentTenseAudioDVD()
//                    for(element in list) {
//                        val audioDVDData = AudioDVD(
//                            audioDVDName = element.audioDvdName,
//                            audioDVDPath = element.audioDvdPath,
//                            creationDate = element.creationDate,
//                        )
//
//                        presentTenseAudioDVDSList.add(audioDVDData)
//
//                        mutableArray.add(AudioDVD(element.audioDvdName, element.audioDvdPath, element.creationDate))
//                        Log.d("HomeFragment", "Present Tense AUDIO DVD LIST VALUE: ${element.audioDvdName}")
//                        //print(" ${element.songBody}")
//                    }

                    PresentTenseAudioDVDDatabase.invoke(activity!!.applicationContext).presentTenseAudioDVDDao().insertPresentTenseAudioDVD(presentTenseAudioDVDSList)

                    print("LIST DATA CHANGE MODIFIED ${list}")



                    Log.d("HomeFragment", "Present Tense LIST: ${list}")
//
//                    binding.audioDvdRecyclerView.apply {
//
//                        layoutManager = LinearLayoutManager(activity)
//
//                        val topSPacingDecoration = TopSpacingItemDecoration(20)
//                        addItemDecoration(topSPacingDecoration)
//
//
////                        val audioDVDList =   PresentTenseAudioDVDDatabase.invoke(activity!!.applicationContext).presentTenseAudioDVDDao().getPresentTenseAudioDVDLists()
////
//                      presentTenseAudioDVDAdapter = PresentTenseAudioDVDAdapter((list as ArrayList<AudioDVD>))
//
//
//                        adapter =  presentTenseAudioDVDAdapter
//
//
//
//
//
//                    }


                    Toast.makeText(context?.applicationContext, "Present Tense Songs: ${presentTenseList}" , Toast.LENGTH_LONG).show()

                    binding.searchAudioRecyclerFragment.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
                    binding.progressbar.visible(true)
                }

                is Resource.Failure -> {
                    handleApiError(it)
                }
            }
        })

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

}
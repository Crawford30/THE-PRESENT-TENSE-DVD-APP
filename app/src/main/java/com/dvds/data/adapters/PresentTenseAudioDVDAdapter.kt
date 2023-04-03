package com.dvds.data.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.dvds.R
import com.dvds.data.model.AudioDVD
import kotlinx.android.synthetic.main.dvd_items_layout.view.*



class PresentTenseAudioDVDAdapter(private var presentTenseAudioDVDList: ArrayList<AudioDVD>, var clickListener: AudioDVDItemClickListener, val findNavController: NavController): RecyclerView.Adapter<CustomPresentTenseVH>(),
    Filterable {

    private var appContext: Context? = null
    //creating Array Filter list
    private  var presentTenseItemsFilteredAudioDVDList = ArrayList<AudioDVD>()



    init {
        presentTenseItemsFilteredAudioDVDList = (presentTenseAudioDVDList)
    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomPresentTenseVH {
        val layoutInflater = LayoutInflater.from(parent?.context)

        val cellForRow = layoutInflater.inflate(R.layout.dvd_items_layout, parent, false)
        appContext = parent.getContext();

        // return  cellForRow, but in a constructor  CustomViewHolder(cellForRow)
        return  CustomPresentTenseVH(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomPresentTenseVH, position: Int) {
        var presentTenseAudioDVD = presentTenseItemsFilteredAudioDVDList[position]



        holder?.view?.dvd_name_title.text = presentTenseAudioDVD.audioDVDName

        holder?.view.dvd_creation_date.text = "Date: " + presentTenseAudioDVD.creationDate

        holder?.presentTenseAudioDVDS = presentTenseAudioDVD


        holder?.initialize(presentTenseAudioDVD, clickListener)


//        holder.view?.setOnClickListener { view ->
//
//            val context = view.context
//
//
//            print("CONTEXT ON CLICK LISTENER: ${context}")
//
//            Toast.makeText(context, ("message"),Toast.LENGTH_LONG).show()
//
//            companion object{
//            const val VIDEOURL = "http://10.0.2.2:8000/storage/presenttense/videodvds/c9e288-2a0789-ac574a-c4367b-27c6a2.mp4"
//            //"http://10.0.2.2:8000/storage/presenttense/audiodvds/5dd448-dc51b1-ffdf29-e223c5-02d87c.m4a"
//            //"http://10.0.2.2:8000/storage/presenttense/videodvds/9914bc-1fe20c-4fd4a2-3e3f70-681fbe.mp4"
//            //"http://10.0.2.2:8000/storage/presenttense/videodvds/c9e288-2a0789-ac574a-c4367b-27c6a2.mp4"
//            //"http://10.0.2.2:8000/storage/presenttense/audiodvds/f1e27c-e8eb61-13e289-d01c2f-45056d.m4a"
//            //"http://10.0.2.2:8000/storage/presenttense/audiodvds/5dd448-dc51b1-ffdf29-e223c5-02d87c.m4a"
//            //"http://10.0.2.2:8000/storage/presenttense/audiodvds/5dd448-dc51b1-ffdf29-e223c5-02d87c.m4a"
//
//            //"http://10.0.2.2:8000/storage/presenttense/audiodvds/d4da88-233845-21f0e3-e411c0-f39726.m4a"
//
//            //"https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mov-file.mov"
//
//            //"http://10.0.2.2:8000/storage/presenttense/audiodvds/d4da88-233845-21f0e3-e411c0-f39726.m4a"
//
//
//            //"http://10.0.2.2:8000/storage/presenttense/videodvds/9914bc-1fe20c-4fd4a2-3e3f70-681fbe.mp4"
//            //"http://10.0.2.2:8000/presenttense/videodvds/896436-7653a4-4cb3d1-0834f0-41bc8c.mov"
//            // const val URL =           "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd"
//            //const val URL = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
//            //const val  URL = "https://file-examples.com/storage/fef1706276640fa2f99a5a4/2017/04/file_example_MP4_480_1_5MG.mp4"
//        }

//            val intent = Intent(context, DetailActivity::class.java).apply{
//
//                 val VIDEOURL = "http://10.0.2.2:8000/storage/presenttense/videodvds/c9e288-2a0789-ac574a-c4367b-27c6a2.mp4"
//                //"http://10.0.2.2:8000/storage/presenttense/audiodvds/5dd448-dc51b1-ffdf29-e223c5-02d87c.m4a"
//
//
//                val workManager = WorkManager.getInstance(context)
//                val videoPreloadWorker = VideoPreloadWorker.buildWorkRequest(VIDEOURL)
//                workManager.enqueueUniqueWork(
//                    "VideoPreloadWorker",
//                    ExistingWorkPolicy.KEEP,
//                    videoPreloadWorker
//                )
//
//
//                Toast.makeText(context, ("message"),Toast.LENGTH_LONG).show()
//
////                Toast.makeText(context,
////                    presentTenseSongs?.songTitle,
////                    Toast.LENGTH_LONG).show()
//
//
//                //send data using intent
//
//
//                putExtra("VIDEO_URL", VIDEOURL)
//
//
//
//            }
//
//            context.startActivity(intent)
            //onClickListener.invoke(view, presentTense)
      //  }




    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val charSearch = constraint.toString()

                if (charSearch.isEmpty()) {

                    presentTenseItemsFilteredAudioDVDList = presentTenseAudioDVDList

                } else {

                    var resultList = ArrayList<AudioDVD>()

                    for (row in presentTenseAudioDVDList) {
                        if (row.audioDVDName
                                .toLowerCase()
                                .contains(charSearch.toLowerCase()) || row.creationDate.toLowerCase()
                                .contains(charSearch.toLowerCase())
                        ) {

                            resultList.add(row)
                        }

                    }

                    presentTenseItemsFilteredAudioDVDList = resultList

                }


                val filterResults = FilterResults()

                filterResults.values = presentTenseItemsFilteredAudioDVDList

                return filterResults


            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                presentTenseItemsFilteredAudioDVDList = results?.values as ArrayList<AudioDVD>
                notifyDataSetChanged()


            }



        }

    }

    override fun getItemCount(): Int {

        return if (presentTenseItemsFilteredAudioDVDList == null) 0 else presentTenseItemsFilteredAudioDVDList.size

    }


}


private fun schedulePreloadWork(videoUrl: String) {


}



class CustomPresentTenseVH(val view: View, var presentTenseAudioDVDS: AudioDVD? = null):
    RecyclerView.ViewHolder(view) {


    //we will access our fields for the layout
    var dvd_name = itemView.dvd_name_title
    var dvd_date_created = itemView.dvd_creation_date

    fun initialize(audioDVDItem: AudioDVD, action: AudioDVDItemClickListener ){

        dvd_name.text = audioDVDItem.audioDVDName
        dvd_date_created.text = "Date: " + audioDVDItem.creationDate

        itemView.setOnClickListener {
            action.onItemClick(audioDVDItem, bindingAdapterPosition, itemView)
        }



    }



}

//=======INTERFACE TO IMPLEMENT ON CLICK
interface  AudioDVDItemClickListener {
    fun onItemClick(audioDVDItem: AudioDVD, position: Int, view: View)

}

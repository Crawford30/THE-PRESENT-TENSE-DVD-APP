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

package com.dvds.data.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.dvds.R
import com.dvds.data.model.VideoDVD
import kotlinx.android.synthetic.main.dvd_items_layout.view.*



class PresentTenseVideoDVDAdapter(private var presentTenseVideoDVDList: ArrayList<VideoDVD>, var clickListener: VideoDVDItemClickListener, val findNavController: NavController): RecyclerView.Adapter<CustomPresentTenseVideoDVDVH>(),
    Filterable {

    private var appContext: Context? = null
    //creating Array Filter list
    private  var presentTenseItemsFilteredVideoDVDList = ArrayList<VideoDVD>()



    init {
        presentTenseItemsFilteredVideoDVDList = (presentTenseVideoDVDList)
    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomPresentTenseVideoDVDVH {
        val layoutInflater = LayoutInflater.from(parent?.context)

        val cellForRow = layoutInflater.inflate(R.layout.dvd_items_layout, parent, false)
        appContext = parent.context;

        // return  cellForRow, but in a constructor  CustomViewHolder(cellForRow)
        return  CustomPresentTenseVideoDVDVH(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomPresentTenseVideoDVDVH, position: Int) {
        var presentTenseVideoDVD = presentTenseItemsFilteredVideoDVDList.get(position)

        holder?.view?.dvd_name_title.text = presentTenseVideoDVD.videoDVDName
        holder?.view.dvd_creation_date.text = "Date: " + presentTenseVideoDVD.creationDate

        holder?.presentTenseVideoDVDS = presentTenseVideoDVD
        holder?.initialize(presentTenseVideoDVD, clickListener)

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val charSearch = constraint.toString()

                if (charSearch.isEmpty()) {

                    presentTenseItemsFilteredVideoDVDList = presentTenseVideoDVDList

                } else {

                    var resultList = ArrayList<VideoDVD>()

                    for (row in presentTenseVideoDVDList) {
                        if (row.videoDVDName
                                .toLowerCase()
                                .contains(charSearch.toLowerCase()) || row.creationDate.toLowerCase()
                                .contains(charSearch.toLowerCase())
                        ) {

                            resultList.add(row)
                        }

                    }

                    presentTenseItemsFilteredVideoDVDList = resultList

                }


                val filterResults = FilterResults()

                filterResults.values = presentTenseItemsFilteredVideoDVDList

                return filterResults


            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                presentTenseItemsFilteredVideoDVDList = results?.values as ArrayList<VideoDVD>
                notifyDataSetChanged()


            }



        }

    }

    override fun getItemCount(): Int {

        return if (presentTenseItemsFilteredVideoDVDList == null) 0 else presentTenseItemsFilteredVideoDVDList.size

    }


}




class CustomPresentTenseVideoDVDVH(val view: View, var presentTenseVideoDVDS: VideoDVD? = null):
    RecyclerView.ViewHolder(view) {

    //we will access our fields for the layout
    private var video_dvd_name = itemView.dvd_name_title
    private var video_dvd_date_created = itemView.dvd_creation_date

    fun initialize(videoDVDItem: VideoDVD, action: VideoDVDItemClickListener ){

        video_dvd_name.text = videoDVDItem.videoDVDName
        video_dvd_date_created.text = "Date: " + videoDVDItem.creationDate

        itemView.setOnClickListener {
            action.onItemClick(videoDVDItem, bindingAdapterPosition, itemView)
        }

    }



}

//=======INTERFACE TO IMPLEMENT ON CLICK
interface  VideoDVDItemClickListener {
    fun onItemClick(videoDVDItem: VideoDVD, position: Int, view: View)

}

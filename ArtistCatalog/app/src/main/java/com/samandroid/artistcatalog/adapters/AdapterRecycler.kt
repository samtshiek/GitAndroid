package com.samandroid.artistcatalog.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samandroid.artistcatalog.R
import com.samandroid.artistcatalog.data.Result
import kotlinx.android.synthetic.main.row_catalog_item_adapter_list.view.*

class AdapterRecycler(var mList: List<Result>?, var mContext: Context): RecyclerView.Adapter<AdapterRecycler.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_catalog_item_adapter_list, parent, false)
        var myViewHolder = MyViewHolder(view)
        return myViewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var catalogItem = mList?.get(position)
        if (catalogItem != null) {
            holder.bind(catalogItem)
        }
    }

    override fun getItemCount(): Int {
        return mList?.size!!
    }

    fun setData(mList: List<Result>?) {
        this.mList = mList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(result: Result) {
            itemView.text_view_artist_name.text = result.artistName
            itemView.text_view_track_name.text = result.trackName
            itemView.text_view_primary_genre.text = result.primaryGenreName
            itemView.text_view_release_date.text = result.releaseDate
            itemView.text_view_track_price.text = result.trackPrice.toString()
        }
    }
}
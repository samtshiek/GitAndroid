package com.samandroid.SelfieApp.adapters

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.StorageReference
import com.samandroid.SelfieApp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_image_list_adapter.view.*

class ImageListAdapter(var mContext: Context, var mList: ArrayList<StorageReference>): RecyclerView.Adapter<ImageListAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageListAdapter.MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_image_list_adapter, parent, false)
        var myViewHolder = MyViewHolder(view)
        return myViewHolder
    }

    override fun onBindViewHolder(holder: ImageListAdapter.MyViewHolder, position: Int) {
        var image = mList[position]
        holder.bind(image)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(mList: ArrayList<StorageReference>) {
        this.mList = mList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(image: StorageReference) {
            var uri = image.downloadUrl
            while (!uri.isComplete) {

            }
            var result = uri.getResult()
            Picasso.get().load(result).into(itemView.image_view_preview)

        }
    }
}
package com.samandroid.volleypostproject.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.samandroid.volleypostproject.Models.Address
import com.samandroid.volleypostproject.Models.Categories
import com.samandroid.volleypostproject.PaymentActivity
import com.samandroid.volleypostproject.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_address_adapter.view.*
import kotlinx.android.synthetic.main.row_categories_adapter.view.*

class AdapterAddressList(var mContext: Context, var mList: ArrayList<Address>): RecyclerView.Adapter<AdapterAddressList.MyViewHolder>(){

    var listener: onAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_address_adapter, parent, false)
        var myViewHolder = MyViewHolder(view)
        return myViewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var addresses = mList[position]
        holder.bind(addresses)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(mList: ArrayList<Address>) {
        this.mList = mList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(address: Address){

            itemView.text_view_type.text = address._type
            itemView.text_view_city.text = address._city
            itemView.text_view_street_name.text = address._streetName
            itemView.text_view_house_no.text = address._houseNo.toString()
            itemView.text_view_pin_code.text = address._pinCode.toString()

            itemView.setOnClickListener {
                listener?.onItemClicked(address)
            }

            itemView.button_delete.setOnClickListener {
                listener?.onDeleteClicked(address)
                mList.remove(address)
                notifyDataSetChanged()
            }

            itemView.button_edit.setOnClickListener {
                listener?.onEditClicked(address)
            }
        }
    }

    fun setOnAdapterListener(onAdapterListener: onAdapterListener){
        listener = onAdapterListener
    }

    interface onAdapterListener{
        fun onItemClicked(address: Address)
        fun onDeleteClicked(address: Address)
        fun onEditClicked(address: Address)
    }

}
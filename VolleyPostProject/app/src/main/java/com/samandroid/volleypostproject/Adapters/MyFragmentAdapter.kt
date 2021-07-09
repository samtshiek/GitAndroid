package com.samandroid.volleypostproject.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.samandroid.volleypostproject.Fragments.Fragment1
import com.samandroid.volleypostproject.Fragments.Fragment2
import com.samandroid.volleypostproject.Fragments.Fragment3
import com.samandroid.volleypostproject.Models.SubCategories

class MyFragmentAdapter(fragmentManager: FragmentManager, catId:Int?, myList: ArrayList<SubCategories>) : FragmentPagerAdapter(fragmentManager) {

    var myList: ArrayList<SubCategories> = myList
    val catId = catId

    override fun getCount(): Int {
        //return 3
        return myList.size
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Fragment1(catId, myList[position]._subId)
            1 -> Fragment1(catId, myList[position]._subId)
            else -> Fragment1(catId, myList[position]._subId)
        }
    }

    fun setData(list: ArrayList<SubCategories>) {
        myList = list
        notifyDataSetChanged()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return  when(position){
            0 -> myList[position]._subName
            1 -> myList[position]._subName
            else -> myList[position]._subName
        }
    }
}
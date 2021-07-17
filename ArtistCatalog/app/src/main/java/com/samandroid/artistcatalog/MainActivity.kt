package com.samandroid.artistcatalog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samandroid.artistcatalog.adapters.AdapterRecycler
import com.samandroid.artistcatalog.data.Catalog
import com.samandroid.artistcatalog.data.Result
import com.samandroid.artistcatalog.ui.CatalogViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: CatalogViewModel
    lateinit var adapterRecyclerView: AdapterRecycler
    var catalogList: List<Result>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(this).get(CatalogViewModel::class.java)
        adapterRecyclerView = AdapterRecycler(catalogList, this)
        recycler_view.adapter = adapterRecyclerView
        recycler_view.layoutManager = LinearLayoutManager(this)

        button_search.setOnClickListener {
            progress_bar.visibility = View.VISIBLE
            var artist = edit_text_enter_artist.text.toString()

            viewModel.getArtistCatalog(artist, this)
            viewModel.catalogResponse.observe(this) {
                if (!it.isEmpty()) {
                    progress_bar.visibility = View.GONE
                    linear_layout_recycler_view.visibility = View.VISIBLE
                    catalogList = it
                    adapterRecyclerView.setData(catalogList)
                }
                else {
                    progress_bar.visibility = View.GONE
                }
            }
        }
    }

}
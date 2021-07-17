package com.samandroid.artistcatalog.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samandroid.artistcatalog.api.ApiClient
import com.samandroid.artistcatalog.data.Catalog
import com.samandroid.artistcatalog.data.Result
import com.samandroid.artistcatalog.utils.Config
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class CatalogViewModel: ViewModel() {

    var catalogResponse = MutableLiveData<List<Result>>()
    val TAG = "catalog_request"

    fun getArtistCatalog(artistName: String, context: Context) {

        viewModelScope.launch(IO) {
            val search_artist_url = Config.BASE_URL + Config.SEARCH_ARTIST + artistName
            val call = ApiClient.getUserApiService().getCatalog(search_artist_url)
            call.enqueue(object:retrofit2.Callback<Catalog> {
                override fun onResponse(call: Call<Catalog>, response: Response<Catalog>) {
                    if (response.isSuccessful) {
                        catalogResponse.postValue(response.body()?.results)
                        Log.d(TAG, "Response is successful. Response: ${catalogResponse.value}")
                    }
                    else {
                        Log.d(TAG, "Response is NOT successful.")
                    }

                }

                override fun onFailure(call: Call<Catalog>, t: Throwable) {
                    Log.d(TAG, "Request failed. Message: {${t.message}")
                }
            })
        }

    }

}
package com.submission.mypokemon.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.submission.mypokemon.data.remote.ApiConfig
import com.submission.mypokemon.data.response.DetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    private val _detailPokemon = MutableLiveData<DetailResponse>()
    val detailPokemon: LiveData<DetailResponse> = _detailPokemon

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setDetailPokemon(detailPokemonName: String) {
        DETAILPOKEMON_ID = detailPokemonName
        findDetailPokemonResponse()
    }

    private fun findDetailPokemonResponse() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().detailPokemon(DETAILPOKEMON_ID)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailPokemon.value = response.body()
                    Log.d(TAG, "onSuccess: ${response.body()?.name}")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }



    companion object {
        const val TAG = "DetailViewModel"
        var DETAILPOKEMON_ID = "123"
    }

}
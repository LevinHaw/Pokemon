package com.submission.mypokemon.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.submission.mypokemon.data.remote.ApiConfig
import com.submission.mypokemon.data.response.PokemonResponse
import com.submission.mypokemon.data.response.ResultsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.HttpException



class MainViewModel: ViewModel() {

    private val listPokemon = MutableLiveData<List<ResultsItem>>()
    val getListPokemon: LiveData<List<ResultsItem>> = listPokemon

    private val isLoading = MutableLiveData<Boolean>()
    val getIsLoading: LiveData<Boolean> = isLoading

    init {
        getListPokemon()
    }

    fun getListPokemon(){
        isLoading.value = true

        try {
            val client = ApiConfig.getApiService().getAllPokemon()
            client.enqueue(object : Callback<PokemonResponse>{
                override fun onResponse(call: Call<PokemonResponse>, response: Response<PokemonResponse>) {
                    isLoading.value = false
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        listPokemon.value = response.body()?.results
                    }
                }

                override fun onFailure(p0: Call<PokemonResponse>, t: Throwable) {
                    isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }

            })
        } catch (e: HttpException){
            Log.e(TAG, "onFailure: ${e.message.toString()}")
        } catch (e: Exception){
            Log.e(TAG, "onFailure: ${e.message.toString()}")
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }


}
package com.example.adminmb

import android.util.Log
import com.example.adminmb.model.DataOrException
import com.example.adminmb.model.Pricing
import com.example.adminmb.model.UserToSend
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

object Repository {
    private const val BASEURL = "http://13.200.106.15:8080/"
    private const val SECRETKEY = "3F2A1B4C5D6E7F8091A2B3C4D5E6F7A8"



    suspend fun getUsers(_getUserState: MutableStateFlow<DataOrException<List<UserToSend>, Exception>>) {
        _getUserState.value = DataOrException(loading = true)
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("${BASEURL}admin/$SECRETKEY")
            .get()
            .build()

        try {
            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        // Correctly obtain the response body as a string
                        val responseBody = response.body()?.string()

                        responseBody?.let {
                            // Log the response body to see what is being returned
                            Log.d("API Response", it)

                            // Parse the JSON response into a list of UserToSend objects
                            val packageList = Gson().fromJson(it, Array<UserToSend>::class.java).toList()
                            _getUserState.value = DataOrException(data = packageList, loading = false)
                        } ?: run {
                            _getUserState.value = DataOrException(
                                e = IOException("Empty response body"),
                                loading = false
                            )
                        }
                    } else {
                        _getUserState.value = DataOrException(
                            e = IOException("Unexpected response code: ${response.code()}"),
                            loading = false
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
            _getUserState.value = DataOrException(e = e, loading = false)
        }
    }


suspend fun subscribe(_getUserState: MutableStateFlow<DataOrException<List<UserToSend>, Exception>>,
                    id:String,
                      days:String,
                      onComlete:()->Unit) {
    _getUserState.value = DataOrException(loading = true)
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("${BASEURL}admin/subscribe/$id/$SECRETKEY/$days")
        .get()
        .build()

    try {
        withContext(Dispatchers.IO) {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    // Correctly obtain the response body as a string
                    val responseBody = response.body()?.string()

                    responseBody?.let {
                        onComlete()
                    } ?: run {
                        _getUserState.value = DataOrException(
                            e = IOException("Empty response body"),
                            loading = false
                        )
                    }
                } else {
                    _getUserState.value = DataOrException(
                        e = IOException("Unexpected response code: ${response.code()}"),
                        loading = false
                    )
                }
            }
        }
    } catch (e: Exception) {
        Log.d("Error", e.toString())
        _getUserState.value = DataOrException(e = e, loading = false)
    }
}


    suspend fun getPrice(getPriceState: MutableStateFlow<DataOrException<List<Pricing>, Exception>>) {
        getPriceState.value = DataOrException(loading = true)
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("${BASEURL}admin/prices/$SECRETKEY")
            .get()
            .build()

        try {
            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        // Correctly obtain the response body as a string
                        val responseBody = response.body()?.string()

                        responseBody?.let {
                            // Log the response body to see what is being returned
                            Log.d("API Response", it)

                            // Parse the JSON response into a list of UserToSend objects
                            val packageList = Gson().fromJson(it, Array<Pricing>::class.java).toList()
                            getPriceState.value = DataOrException(data = packageList, loading = false)
                        } ?: run {
                            getPriceState.value = DataOrException(
                                e = IOException("Empty response body"),
                                loading = false
                            )
                        }
                    } else {
                        getPriceState.value = DataOrException(
                            e = IOException("Unexpected response code: ${response.code()}"),
                            loading = false
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
            getPriceState.value = DataOrException(e = e, loading = false)
        }

    }




    suspend fun addPrice(_getUserState: MutableStateFlow<DataOrException<List<Pricing>, Exception>>,
                          time:String,
                          price:String,
                          onComlete:()->Unit) {
        _getUserState.value = DataOrException(loading = true)
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("${BASEURL}admin/$time/$price/$SECRETKEY")
            .get()
            .build()

        try {
            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        // Correctly obtain the response body as a string
                        val responseBody = response.body()?.string()

                        responseBody?.let {
                            onComlete()
                        } ?: run {
                            _getUserState.value = DataOrException(
                                e = IOException("Empty response body"),
                                loading = false
                            )
                        }
                    } else {
                        _getUserState.value = DataOrException(
                            e = IOException("Unexpected response code: ${response.code()}"),
                            loading = false
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
            _getUserState.value = DataOrException(e = e, loading = false)
        }
    }


    suspend fun deletePrice(_getUserState: MutableStateFlow<DataOrException<List<Pricing>, Exception>>,
                        time: String,
                         onComlete:()->Unit) {
        _getUserState.value = DataOrException(loading = true)
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("${BASEURL}admin/delete/$time/$SECRETKEY")
            .get()
            .build()

        try {
            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        // Correctly obtain the response body as a string
                        val responseBody = response.body()?.string()

                        responseBody?.let {
                            onComlete()
                        } ?: run {
                            _getUserState.value = DataOrException(
                                e = IOException("Empty response body"),
                                loading = false
                            )
                        }
                    } else {
                        _getUserState.value = DataOrException(
                            e = IOException("Unexpected response code: ${response.code()}"),
                            loading = false
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
            _getUserState.value = DataOrException(e = e, loading = false)
        }
    }


}



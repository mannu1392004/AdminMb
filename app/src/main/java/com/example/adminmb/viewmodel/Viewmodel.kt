package com.example.adminmb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminmb.Repository
import com.example.adminmb.model.DataOrException
import com.example.adminmb.model.Pricing
import com.example.adminmb.model.UserToSend
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class Viewmodel : ViewModel() {


    private val _getUserState = MutableStateFlow<DataOrException<List<UserToSend>, Exception>>(
        DataOrException()
    )


    val getUserState: StateFlow<DataOrException<List<UserToSend>, Exception>> = _getUserState


    fun getUsers() {
        viewModelScope.launch {
            Repository.getUsers(
                _getUserState
            )
        }
    }


    fun sendSubscription(day: String, id: String) {
        viewModelScope.launch {
            Repository.subscribe(
                _getUserState = _getUserState,
                days = day,
                id = id
            ) {
                getUsers()
            }
        }
    }


    private val _getPriceState = MutableStateFlow<DataOrException<List<Pricing>, Exception>>(
        DataOrException()
    )


    val getPriceState: StateFlow<DataOrException<List<Pricing>, Exception>> = _getPriceState


    fun getPricing() {
        viewModelScope.launch {
            Repository.getPrice(
                _getPriceState
            )
        }
    }

    fun sendprice(price: String, time: String) {
        viewModelScope.launch {
            Repository.addPrice(
                price = price,
                time = time,
                _getUserState = _getPriceState
            ){
                getPricing()
            }
        }
    }
    fun deleteprice( time: String) {
        viewModelScope.launch {
            Repository.deletePrice(
                time = time,
                _getUserState = _getPriceState
            ){
                getPricing()
            }
        }
    }



}
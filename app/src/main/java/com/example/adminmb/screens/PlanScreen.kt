package com.example.adminmb.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.adminmb.model.Pricing
import com.example.adminmb.viewmodel.Viewmodel

@Composable
fun PlanScreen(navController: NavHostController, viewmodel: Viewmodel) {

LaunchedEffect (Unit){
    viewmodel.getPricing()
}

    val price = remember {
        mutableStateOf("")
    }

    val duration = remember {
        mutableStateOf("")
    }

    val state = viewmodel.getPriceState.collectAsState()

    val list = state.value.data


    Scaffold { padding ->



        Surface(modifier = Modifier.padding(padding)) {

if(state.value.loading){
    CircularProgressIndicator()
}
            else
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedTextField(value = price.value, onValueChange = { price.value = it },
                    label = { Text(text = "Add Price") }
                )

                OutlinedTextField(value = duration.value, onValueChange = { duration.value = it },
                    label = { Text(text = "Add Duration") }
                )


                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = { viewmodel.sendprice(price = price.value, time = duration.value)
                price.value = ""
                    duration.value = ""
                }) {
                    Text(text = "Save")

                }

                    list?.forEach { listItem ->

                        Surface(modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)) {
                           Row(horizontalArrangement = Arrangement.SpaceBetween,
                               verticalAlignment = Alignment.CenterVertically) {
                               Column {

                                   Text(text = "Price :" + listItem.price)

                                   Text(text = "Duration: " + listItem.id)
                               }

                               Icon(imageVector = Icons.Default.Delete, contentDescription ="",
                                   modifier = Modifier.clickable {
                                       viewmodel.deleteprice(time = listItem.id)
                                   })
                           }
                        }
                    }

            }


        }
    }
}
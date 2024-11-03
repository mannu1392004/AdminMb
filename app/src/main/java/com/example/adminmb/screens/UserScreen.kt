package com.example.adminmb.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.adminmb.model.UserToSend
import com.example.adminmb.viewmodel.Viewmodel
import java.time.LocalDate

@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(navController: NavHostController, viewmodel: Viewmodel) {

    LaunchedEffect(Unit) {
        viewmodel.getUsers()
    }

    val search = remember {
        mutableStateOf("")
    }

    val searchActive = remember {
        mutableStateOf(false)
    }

    val state = viewmodel.getUserState.collectAsState()

    if (state.value.loading){
        CircularProgressIndicator()
    }

    else
    
    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {






        val userToSend = state.value.data

        var searchList = remember {
           mutableStateOf( userToSend)
        }


        SearchBar(
            query = search.value,
            onQueryChange = { search.value = it
                            searchList.value = userToSend?.filter {list->
                                list.name.contains(search.value,ignoreCase = true)
                            }
                            },
            onSearch = {},
            active = searchActive.value,
            onActiveChange = { searchActive.value = !searchActive.value },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Search For Name")}
        ) {

            searchList.value?.let {
                LazyColumn {
                    items(it) {
                        userCard(it, viewmodel)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(10.dp))

      userToSend?.let {
          LazyColumn {
              items(userToSend) {
                  userCard(it,viewmodel)
              }
          }
      }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun userCard(it: UserToSend, viewmodel: Viewmodel,) {

    val showDialogue = remember {
        mutableStateOf(false)
    }
    val border = if (it.subscribed) null else BorderStroke(width = 1.dp, color = Color.Black)

    Card(
        onClick = { showDialogue.value = true }, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        border = border,
        colors = CardDefaults.cardColors(
            containerColor = if (it.subscribed) Color.Green.copy(alpha = 0.4f) else Color.White
        )
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = it.email)

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = it.name)
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Remaining Days :" + it.subscribedUpTo.toString())
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Mobile Number: " + it.mobileNumber)
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Date of Sub.: " + it.dateOfSubscription)


        }

    }

    if (showDialogue.value) {
        ShowDialoge(it, showDialogue,viewmodel)
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowDialoge(userToSend: UserToSend, showDialogue: MutableState<Boolean>, viewmodel: Viewmodel) {

    val subscribedUpto = remember {
        mutableStateOf("")
    }

    Dialog(onDismissRequest = { showDialogue.value = false }) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            color = Color.White
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(10.dp)
            ) {

                Text(text = userToSend.name)

                Text(text = LocalDate.now().toString())

                OutlinedTextField(
                    value = subscribedUpto.value, onValueChange = { subscribedUpto.value = it },
                    label = { Text(text = "Subscribed Days") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )

                Spacer(modifier = Modifier.height(30.dp))
                Button(onClick = { viewmodel.sendSubscription(day = subscribedUpto.value, id =userToSend.id) }) {
                    Text(text = "Subscribe")
                }

            }
        }
    }
}

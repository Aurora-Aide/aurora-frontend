package com.example.djigit.data

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.djigit.data.model.DataModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun PostData() {
    val context = LocalContext.current

    val userName = remember {
        mutableStateOf(TextFieldValue())
    }
    val job = remember {
        mutableStateOf(TextFieldValue())
    }
    val response = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Email text field
        TextField(
            value = userName.value,
            onValueChange = { userName.value = it },
            placeholder = { Text(text = "Enter your name") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(5.dp))

        // Job text field
        TextField(
            value = job.value,
            onValueChange = { job.value = it },
            placeholder = { Text(text = "Enter your job") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {

                // API call
                //postDataUsingRetrofit(context, userName, job, response)
            }
        ) {
            Text(text = "Post Data", modifier = Modifier.padding(8.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = response.value,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold, modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}


fun postLoginData(
    ctx: Context,
    email: String,
    password: String,
    result: MutableState<String>
) {
    // base url
    val url = "http://localhost:3000/"

    // create retrofit builder and load
    // json data in model class
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // create instance of our retrofit API
    val retrofitAPI = retrofit.create(RetrofitAPI::class.java)

    // fetch data from text fields and
    // pass to our model class
    val dataModel = DataModel(email, password)

    // API call to make post request
    val call: Call<DataModel?>? = retrofitAPI.postData(dataModel)
    call!!.enqueue(object : Callback<DataModel?> {
        override fun onResponse(call: Call<DataModel?>, response: Response<DataModel?>) {

            // received a response
            Toast.makeText(ctx, "Data posted to API", Toast.LENGTH_SHORT).show()

            // pass response to our model class
            val model: DataModel? = response.body()

            // setting text to our text field
            val resp = "Response Code : " + response.code() + "\n" + "User Name : " + model!!.name + "\n" + "Job : " + model.job
            result.value = resp
        }

        override fun onFailure(call: Call<DataModel?>, t: Throwable) {
            // passing error message
            result.value = "Error found is : " + t.message
        }
    })
}
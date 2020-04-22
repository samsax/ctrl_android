package mx.ctrlpg.data

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    /*fun login(eMail: String, ePass: String) {

            try {
                val json = JSONObject()
                json.put("email", eMail)
                json.put("password", ePass)
                val requestBody: RequestBody =
                    RequestBody.create(MediaType.parse("application/json"), json.toString())
                    val call: Call<ResponseLogin> = apiService.login(requestBody)
                    call.enqueue(
                        object : Callback<ResponseLogin> {
                            @SuppressLint("CommitPrefEdits")
                            override fun onResponse(
                                call: Call<ResponseLogin>?,
                                response: Response<ResponseLogin>?
                            ) {
                                if (response!!.isSuccessful) {

                                    //startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                    //finish()
                                    //finishAndRemoveTask()
                                } else {

                                }
                            }

                            override fun onFailure(call: Call<ResponseLogin>?, t: Throwable?) {
                                Log.d("loginAuthendication", "failure--" + t.toString())


                            }
                        })

                }
             catch (e: Exception) {
                Log.d("loginAuthendication", e.toString())
            }
    }*/

    fun logout() {
        // TODO: revoke authentication
    }
}


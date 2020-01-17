package mx.ctrlpg.data

import mx.ctrlpg.APIService
import mx.ctrlpg.RetrofitUtil.BaseRetrofit
import mx.ctrlpg.data.model.LoggedInUser
import mx.ctrlpg.ui.login.LoginResult
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            val call = BaseRetrofit.getRetrofit()
                .create(APIService::class.java)
                .Login( new RequestBody()"/account/login").execute()
            return Result.Success(call.body() as LoggedInUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}


import com.google.gson.annotations.SerializedName

data class ResponseLogin(

	@SerializedName("object")val info : InfoLogin,
	val statusResponse : Int,
	val error : Boolean
)
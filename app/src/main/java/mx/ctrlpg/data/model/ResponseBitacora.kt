import com.google.gson.annotations.SerializedName
import mx.ctrlpg.data.model.Bitacora

data class ResponseBitacora(

	@SerializedName("objectClass")val bitacora : Bitacora,
	val statusResponse : Int,
	val error : Boolean
)
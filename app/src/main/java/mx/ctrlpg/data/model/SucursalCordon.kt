package mx.ctrlpg.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/*
Copyright (c) 2020 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class SucursalCordon (

	@SerializedName("sucCliId") val sucCliId : Int,
	@SerializedName("sucId") val sucId : Int,
	@SerializedName("sucNumero") val sucNumero : Int,
	@SerializedName("sucStatus") val sucStatus : Int,
	@SerializedName("sucUsuarioBit") val sucUsuarioBit : String,
	@SerializedName("sucDescripcion") val sucDescripcion : String,
	@SerializedName("sucFechaCambio") val sucFechaCambio : String,
	@SerializedName("sucCorPri") val sucCorPri : Int,
	@SerializedName("sucCorSec") val sucCorSec : Int,
	@SerializedName("sucCorTer") val sucCorTer : Int,
	@SerializedName("sucEdoId") val sucEdoId : Int,
	@SerializedName("sucMunId") val sucMunId : Int,
	@SerializedName("sucCiudad") val sucCiudad : String,
	@SerializedName("sucColonia") val sucColonia : String,
	@SerializedName("sucDireccion") val sucDireccion : String,
	@SerializedName("sucNombreContacto") val sucNombreContacto : String
):Serializable
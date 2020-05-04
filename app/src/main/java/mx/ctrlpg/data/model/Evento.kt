package mx.ctrlpg.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/*
Copyright (c) 2020 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class Evento (

    @SerializedName("cacId") val cacId : Int,
    @SerializedName("cacCliId") val cacCliId : Int,
    @SerializedName("cacSucId") val cacSucId : Long,
    @SerializedName("cacTacId") val cacTacId : Int,
    @SerializedName("cacFecha") val cacFecha : String,
    @SerializedName("cacHoraIncio") val cacHoraIncio : String,
    @SerializedName("cacHoraFin") val cacHoraFin : String,
    @SerializedName("cacCosto") val cacCosto : Int,
    @SerializedName("cacIndicaciones") val cacIndicaciones : String,
    @SerializedName("cacUsuNombre") val cacUsuNombre : String,
    @SerializedName("cacUsuarioBit") val cacUsuarioBit : String,
    @SerializedName("cacFechaCambio") val cacFechaCambio : String,
    @SerializedName("cacStatus") val cacStatus : Int,
    @SerializedName("cacTitle") val cacTitle : String,
    @SerializedName("cacTecico") val cacTecico : String,
    @SerializedName("tecnicos") val tecnicos : String,
    @SerializedName("cacVehId") val cacVehId : Int,
    @SerializedName("cacCliDescripcion") val cacCliDescripcion : String,
    @SerializedName("cacEdoDescripcion") val cacEdoDescripcion : String,
    @SerializedName("cacMunDescripcion") val cacMunDescripcion : String,
    @SerializedName("cacCliColonia") val cacCliColonia : String,
    @SerializedName("cacCliDireccion") val cacCliDireccion : String,
    @SerializedName("cacTacDescripcion") val cacTacDescripcion : String,
    @SerializedName("cacValidaTecnico") val cacValidaTecnico : Int
) : Serializable
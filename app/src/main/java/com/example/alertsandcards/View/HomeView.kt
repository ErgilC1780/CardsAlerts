package com.example.alertsandcards.View

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.alertsandcards.Components.Alert
import com.example.alertsandcards.Components.MainButton
import com.example.alertsandcards.Components.MainTextField
import com.example.alertsandcards.Components.ShowInfoCards
import com.example.alertsandcards.Components.SpaceH
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun ContentHomeView (paddingValues: PaddingValues){
    Column (

        modifier = Modifier
            .padding(paddingValues)
            .padding(10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        var montoPrestamo by remember { mutableStateOf("") }
        var cantCuotas by remember { mutableStateOf("") }
        var tasa by remember { mutableStateOf("") }
        var montoInteres by remember { mutableStateOf(0.0) }
        var montoCuota by remember { mutableStateOf(0.0) }
        var showAlert by remember { mutableStateOf(false) }

        MainTextField(value = montoPrestamo, onValueChanger = {montoPrestamo = it}, label = "Prestamo")
        SpaceH()
        MainTextField(value = cantCuotas, onValueChanger = {cantCuotas= it }, label = "Cuotas")
        SpaceH(10.dp)
        MainTextField(value = tasa, onValueChanger = {tasa= it }, label = "Tasa")
        SpaceH(10.dp)

        MainButton(text = "Calcular") {
            if (montoPrestamo != "" && cantCuotas != ""){
                montoInteres = calcularTotal( montoPrestamo.toDouble(),cantCuotas.toInt(),tasa.toDouble())
                montoCuota = calcularCuota (montoPrestamo.toDouble(), cantCuotas.toInt(), tasa.toDouble())
            } else {
                showAlert= true
            }

        }
        SpaceH()

        ShowInfoCards(
            titleInteres = "Total",
            montoInteres = montoInteres,
            titleMonto = "Cuota",
            monto = montoCuota


        )
        MainButton(text = "Limpiar", color = Color.Red) {
            montoPrestamo = ""
            cantCuotas = ""
            tasa = ""
            montoInteres = 0.0
            montoInteres = 0.0



        }
        if (showAlert){
            Alert (title = "Alerta", message = "Ingrese los datos", confirmText = "Aceptar", onConfirmClick = { showAlert = false }) {

            }
        }


    }
}

fun calcularTotal( montoPrestamo: Double, cantCuotas: Int, tasaInteresAnual: Double) : Double{
    val res = cantCuotas * calcularCuota( montoPrestamo, cantCuotas, tasaInteresAnual)
    return BigDecimal(res).setScale( 2,RoundingMode.HALF_UP).toDouble()

}

fun calcularCuota (montoPrestamo: Double, cantCuotas: Int, tasaInteresAnual: Double): Double{
    val tasaInteresMensual = tasaInteresAnual / 12/ 100

    val cuota = montoPrestamo * tasaInteresMensual * Math.pow(1+ tasaInteresMensual, cantCuotas.toDouble()) /
            (Math.pow(1+ tasaInteresMensual, cantCuotas.toDouble())-1)
    val cuotaRedondeada = BigDecimal(cuota).setScale(2, RoundingMode.HALF_UP).toDouble()
    return cuotaRedondeada
    return cuota

}


@OptIn ( ExperimentalMaterial3Api::class)
@Composable
fun HomeView (){
    Scaffold (topBar = {
        CenterAlignedTopAppBar(
            title = { Text(text = "Calculadora Prestamos", color = Color.White)},
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
            )

    }) {
        ContentHomeView(it)

    }

}

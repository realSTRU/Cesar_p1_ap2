package com.example.cesar_p1_ap2.ui.Operations

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun OperationsConsult(
    viewModel : OperationViewModel = hiltViewModel(),
    navController: NavController
)
{
    val operations by viewModel.operations.collectAsStateWithLifecycle()
    var context = LocalContext.current

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Historial", color = Color.White) },
            )
        },
        content = {
            Column {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 70.dp)
                ) {
                    items(operations) { operation ->
                        Surface(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            color = Color.DarkGray,
                            shadowElevation = 3.dp,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = "Nombre: ${operation.studentName}",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Dividendo: ${operation.dividendo}",
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "Divisor: ${operation.divisor}",
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "Cociente: ${operation.cociente}",
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "Residuo: ${operation.residuo}",
                                        fontSize = 14.sp
                                    )
                                }
                                IconButton(
                                    onClick = { viewModel.deleteOperation(operation)
                                    showToast("Eliminado correctamente ${operation.studentName}")}
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = Color.Red // Puedes cambiar el color del ícono según tu preferencia
                                    )
                                }
                            }
                        }
                        Divider()
                    }
                }
            }
        }
    )



}
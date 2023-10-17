package com.example.cesar_p1_ap2.ui.Operations



import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SaveAs
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun OperationsScreen(
    viewModel: OperationViewModel = hiltViewModel(),
    navController: NavController
)
{
    var context = LocalContext.current

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    val operations by viewModel.operations.collectAsStateWithLifecycle()
    val snackbarHostState = remember {SnackbarHostState()}

    LaunchedEffect(Unit)
    {
        viewModel.isMessageShownFlow.collectLatest {
            if(it)
            {
                snackbarHostState.showSnackbar(
                    message = "Operation saved",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
    Scaffold (
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {Text(text = "Operations App") },
                actions = {
                    IconButton(onClick = {viewModel.clean()})
                    {
                        Icon(
                            imageVector = Icons.Default.Refresh, contentDescription = "Limpiar"
                        )
                    }
                }
            )
        }
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Form()
        }
    }



}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun Form(
    viewModel: OperationViewModel = hiltViewModel()
)
{


    val camposLlenos = !viewModel.dividendo.isNullOrBlank() && !viewModel.divisor.isNullOrBlank() && !viewModel.cociente.isNullOrBlank() && !viewModel.residuo.isNullOrBlank()
    val habilitarGuardar = camposLlenos
    var isKeyboardVisible by remember { mutableStateOf(false) }
    val rootView = LocalView.current

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    DisposableEffect(isKeyboardVisible) {
        var hasExecuted = false
        val listener = ViewTreeObserver.OnPreDrawListener {
            val rect = android.graphics.Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.height
            val keypadHeight = screenHeight - rect.bottom

            // Si la altura del teclado es menor que 100 pixels, se considera que está cerrado
            isKeyboardVisible = keypadHeight > 100
            if (!isKeyboardVisible  && !hasExecuted) {

                viewModel.FillLastOne()
                hasExecuted = true
            }

            true

        }

        rootView.viewTreeObserver.addOnPreDrawListener(listener)

        onDispose {
            rootView.viewTreeObserver.removeOnPreDrawListener(listener)
        }
    }
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(bottom = 10.dp, top=60.dp)
    ){
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
            ,modifier = Modifier
                .size(width = 350.dp, height = 315.dp),

            )
        {

            Column {
                val keyboardController = LocalSoftwareKeyboardController.current
                OutlinedTextField(
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp, end = 10.dp),
                    label = { Text(text = "Name") },
                    singleLine = true,
                    maxLines = 1,
                    value = viewModel.studentName,
                    onValueChange = {viewModel.OnNameChanged(it)},
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription ="Student Name"
                        )
                    },
                    isError =  viewModel.studentNameError,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next)

                )
                if(viewModel.studentNameError)
                {
                    Text(text = "El nombre no puede estar vacío.",
                        color = Color.Red,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(start= 30.dp))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Column {
                        OutlinedTextField(
                            modifier = Modifier
                                .padding(start = 5.dp)
                                .width(145.dp),
                            label = {
                                Text(
                                    text = "Dividendo",
                                    fontSize = 11.sp,
                                )
                            },
                            singleLine = true,
                            maxLines = 1,
                            value = viewModel.dividendo,
                            onValueChange = { viewModel.OnDividendoChanged(it) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Numbers,
                                    contentDescription = "Dividendo"
                                )
                            },
                            isError = viewModel.dividendoError,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            )

                        )
                        if (viewModel.dividendo.isBlank()) {
                            Text(text = "Dividendo es Requerido",
                                fontSize = 8.sp,
                                color = Color.Red)
                        }
                        if (viewModel.dividendoError) {
                            Text(text = "Dividendo es Incorrecto",
                                fontSize = 8.sp,
                                color = Color.Red)
                        }
                    }
                    /*if(viewModel.dividendoError)
                    {
                        Text(text = "El dividendo es requerido.",
                            color = Color.Red,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(start= 30.dp))
                    }*/

                    Column {
                        OutlinedTextField(
                            modifier = Modifier
                                .padding(end = 5.dp, start = 10.dp)
                                .width(145.dp),
                            label = {
                                Text(
                                    text = "Divisor",
                                    fontSize = 12.sp
                                )
                            },
                            singleLine = true,
                            maxLines = 1,
                            value = viewModel.divisor,
                            onValueChange = { viewModel.OnDivisorChanged(it) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Numbers,
                                    contentDescription = "Divisor"
                                )
                            },
                            isError = viewModel.divisorError,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            )

                        )
                        if (viewModel.dividendo == "0") {
                            Text(text = "error division entre 0",
                                fontSize = 8.sp,
                                color = Color.Red)
                        }
                        if (viewModel.dividendoError) {
                            Text(text = "Divisor incorrecto",
                                fontSize = 8.sp,
                                color = Color.Red)
                        }
                    }
                }
                /*if(viewModel.divisorError)
                {
                    Text(text = "El divisor es requerido.",
                        color = Color.Red,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(start= 30.dp))
                }*/
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Column {
                        OutlinedTextField(
                            modifier = Modifier
                                .width(145.dp)
                                .padding(start = 5.dp),
                            label = { Text(text = "Cociente",
                                fontSize = 12.sp) },
                            singleLine = true,
                            maxLines = 1,
                            value = viewModel.cociente,
                            onValueChange = { viewModel.OnCocienteChanged(it) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Numbers,
                                    contentDescription = "Cociente"
                                )
                            },
                            isError = viewModel.cocienteError,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            )

                        )
                        if(viewModel.cocienteError)
                        {
                            Text("cociente incorrecto",
                                fontSize = 8.sp,
                                color = Color.Red)
                        }
                    }

                    Column {
                        OutlinedTextField(
                            modifier = Modifier
                                .width(145.dp)
                                .padding(end = 5.dp),
                            label = { Text(text = "Residuo",
                                fontSize = 12.sp) },
                            singleLine = true,
                            maxLines = 1,
                            value = viewModel.residuo,
                            onValueChange = { viewModel.OnResiduoChanged(it)},
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Numbers,
                                    contentDescription = "Residuo"
                                )
                            },
                            isError = viewModel.residuoError,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            )

                        )
                        if(viewModel.residuoError)
                        {
                            Text("residuo incorrecto",
                                fontSize = 8.sp,
                                color = Color.Red)
                        }
                    }

                }
                Row (
                    horizontalArrangement =Arrangement.End, modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth())
                {
                    FloatingActionButton(
                        modifier = Modifier
                            .clickable(enabled = habilitarGuardar){}
                        , onClick = {
                            if (camposLlenos) {

                                if(viewModel.saveOperation())
                                {
                                    keyboardController?.hide()
                                    viewModel.setMessageShown()
                                }
                                showToast("Guardado sin errores")


                            }
                            else
                            {
                                showToast("Errores al guardar")
                            }
                        },){
                        Icon(imageVector = Icons.Default.SaveAs, contentDescription = "Add")
                    }
                }


            }

        }

    }
}
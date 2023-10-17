package com.example.cesar_ap2_p1_at2.ui.Operations
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import android.widget.Toast
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cesar_p1_ap2.Nav.AppScreens
import com.example.cesar_p1_ap2.ui.Operations.OperationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: OperationViewModel = hiltViewModel(),
    navController: NavController
)
{
    var context = LocalContext.current

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    //showToast("Menu principal")
    Scaffold (
        topBar = { TopAppBar(title = { Text(text = "Aprende a dividir") },
            modifier = Modifier.shadow(8.dp),
            colors =TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        )
        },
        content = ({
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(top = 50.dp,start = 15.dp, end= 10.dp, bottom = 50.dp)
            ) {
                Spacer(modifier = Modifier.height(15.dp))
                OutlinedButton(modifier = Modifier.fillMaxWidth()
                    .padding(top =150.dp),onClick = {
                    navController.navigate(route = AppScreens.OperationsScreen.route)
                }) {
                    Icon(imageVector = Icons.Default.Numbers, contentDescription ="Guardar" )
                    Text(" Operations App")
                }

                Spacer(modifier = Modifier.height(15.dp))
                OutlinedButton(modifier = Modifier.fillMaxWidth(),onClick = {
                    navController.navigate(route = AppScreens.OperationsConsult.route)
                }) {
                    Icon(imageVector = Icons.Default.ListAlt, contentDescription ="Guardar" )
                    Text(" Operation Historials")
                }

            }



        })
    )

}


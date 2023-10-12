package com.example.cesar_p1_ap2.ui.Operations
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cesar_p1_ap2.data.local.entities.OperationEntity
import com.example.cesar_p1_ap2.data.repository.OperationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OperationViewModel @Inject constructor(
    private val operationRepository : OperationsRepository
): ViewModel(){

    private val _isMessageShown = MutableSharedFlow<Boolean>()
    val isMessageShownFlow = _isMessageShown.asSharedFlow()

    fun setMessageShown() {
        viewModelScope.launch {
            _isMessageShown.emit(true)
        }
    }
    /*Por el momento no estoy utilizando los OnValueChanged
    Evaluare las operaciones con el metodo correspondiente a aunto completar la division
    */


    /*Nombre del estudiante al que se le puede asociar
     una operacion*/
    var studentName by mutableStateOf("")

    //Numneros participantes en la division
    var dividendo by mutableStateOf("")
    var divisor by mutableStateOf("")
    var cociente by mutableStateOf("")
    var residuo by mutableStateOf("")

    //Validaciones
    var studentNameError by mutableStateOf(true)
    var dividendoError by mutableStateOf(true)
    var divisorError by mutableStateOf(true)
    var cocienteError by mutableStateOf(true)
    var residuoError by mutableStateOf(true)


    //Tools
    fun Float.isVacio():Boolean
    {
        return this == 0f
    }
    fun FillLastOne()
    {

        if(!dividendo.isNullOrBlank() && !divisor.isNullOrBlank() && !cociente.isNullOrBlank() )
        {
            residuo = (dividendo.toInt() - divisor.toInt() * cociente.toInt()).toString()
        }
        if(!dividendo.isNullOrBlank() && !divisor.isNullOrBlank() && !residuo.isNullOrBlank())
        {
            cociente = ((dividendo.toInt() - residuo.toInt()) / divisor.toInt()).toString()
        }
        if(!dividendo.isNullOrBlank() && !cociente.isNullOrBlank() && !residuo.isNullOrBlank())
        {
            divisor = ((dividendo.toInt() - residuo.toInt()) / cociente.toInt()).toString()
        }
        if(!divisor.isNullOrBlank() && !cociente.isNullOrBlank() && !residuo.isNullOrBlank()){
            dividendo = ((divisor.toInt() * cociente.toInt()) + residuo.toInt()).toString()
        }

    }
    //
    fun OnDividendoChanged(value: String)
    {
        //dividendo = value
        dividendoError = dividendo.isNullOrBlank()
        FillLastOne()
    }

    fun OnNameChanged(value: String)
    {
        studentName= value
        studentNameError= studentName.isNullOrBlank()

    }

    fun OnDivisorChanged(value: String)
    {
        //divisor= value
        divisorError = divisor.isNullOrBlank()
        FillLastOne()
    }

    fun OnCocienteChanged(value: String)
    {
        //cociente = value
        cocienteError = cociente.isNullOrBlank()
        FillLastOne()
    }

    fun OnResiduoChanged(value: String)
    {
        //residuo = value
        residuoError = residuo.isNullOrBlank()
        FillLastOne()
    }


    fun clean()
    {
        studentName = ""
        dividendo = ""
        divisor = ""
        cociente = ""
        residuo = ""
    }

    fun saveOperation()
    {
        viewModelScope.launch {
            val operation = OperationEntity(
                studentName = studentName,
                dividendo = dividendo.toInt(),
                divisor = divisor.toInt(),
                cociente = cociente.toInt(),
                residuo = residuo.toInt()
            )
            operationRepository.save(operation)
            clean()
        }
    }

    fun deleteOperation(operation: OperationEntity)
    {
        viewModelScope.launch {

            operationRepository.delete(operation)
            clean()
        }
    }

    val operations : StateFlow<List<OperationEntity>> = operationRepository.getALL()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )





}
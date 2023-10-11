package com.example.cesar_p1_ap2.data.repository

import com.example.cesar_p1_ap2.data.local.dao.OperationDao
import com.example.cesar_p1_ap2.data.local.entities.OperationEntity
import javax.inject.Inject


//using the repo its more cute than only use de viewModel and Dao
class OperationsRepository @Inject constructor(
    private val operationDao : OperationDao
){
    suspend fun save(operation: OperationEntity) = operationDao.saveOperation(operation)

    suspend fun delete(operation: OperationEntity)= operationDao.deleteOperation(operation)

    fun getALL() = operationDao.getAllOperations()
}
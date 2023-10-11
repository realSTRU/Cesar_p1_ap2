package com.example.cesar_p1_ap2.data.local.dao

import com.example.cesar_p1_ap2.data.local.entities.OperationEntity
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface OperationDao {

    @Upsert
    suspend fun saveOperation(operation : OperationEntity)

    @Delete
    suspend fun deleteOperation(operation : OperationEntity)

    @Query("SELECT * FROM Operations")
    fun getAllOperations(): Flow<List<OperationEntity>>
}
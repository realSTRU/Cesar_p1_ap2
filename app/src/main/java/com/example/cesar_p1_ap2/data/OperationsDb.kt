package com.example.cesar_p1_ap2.data


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cesar_p1_ap2.data.local.dao.OperationDao
import com.example.cesar_p1_ap2.data.local.entities.OperationEntity

@Database(
    entities = [OperationEntity::class],
    version = 1
)
abstract class OperationsDb : RoomDatabase()
{
    abstract fun operationDao(): OperationDao
}
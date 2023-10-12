package com.example.cesar_p1_ap2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Operations")
data class OperationEntity (

    @PrimaryKey
    val operationId : Int? = null,
    val studentName : String = "",
    val dividendo : Int? = null,
    val divisor : Int? = null,
    val cociente : Int? = null,
    val residuo : Int? = null
)
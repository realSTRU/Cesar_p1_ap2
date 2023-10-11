package com.example.cesar_p1_ap2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Operations")
data class OperationEntity (

    @PrimaryKey
    val operationId : Int? = null,
    val studentName : String = "",
    val dividendo : Float? = null,
    val divisor : Float? = null,
    val cociente : Float? = null,
    val residuo : Float? = null
)
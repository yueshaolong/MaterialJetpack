package com.ysl.myapplication.login

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    public val id: String,
    public val name: String,
    private val lastName: String
)
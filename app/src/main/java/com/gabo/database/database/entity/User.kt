package com.gabo.database.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class User(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "firstName") var firstName: String = "",
    @ColumnInfo(name = "lastName") var lastName: String = "",
    @ColumnInfo(name = "age") var age: Int = 0,
    @ColumnInfo(name = "email") var email: String = "",
    @ColumnInfo(name = "isDeleted") var isDeleted: Boolean = false
)

package com.gabo.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gabo.database.database.dao.UserDao
import com.gabo.database.database.entity.User


@Database(entities = [User::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun userDao(): UserDao
}
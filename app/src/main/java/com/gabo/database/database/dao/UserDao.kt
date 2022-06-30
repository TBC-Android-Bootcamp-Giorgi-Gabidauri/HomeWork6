package com.gabo.database.database.dao

import androidx.room.*
import com.gabo.database.database.entity.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addUser(user: User)

    @Update
    fun updateUser(user: User)

    @Query("SELECT EXISTS(SELECT*FROM Users WHERE email=:email AND isDeleted=0)")
    fun userAlreadyExist(email: String): Boolean

    @Query("SELECT EXISTS(SELECT*FROM Users WHERE email=:email AND isDeleted=1)")
    fun userIsDeleted(email: String): Boolean

    @Query("UPDATE Users SET isDeleted=:isDeleted WHERE email =:email")
    fun deleteUser(isDeleted: Boolean, email: String)

    @Query("SELECT * FROM users WHERE isDeleted=1")
    fun getDeletedUsers(): List<User>

    @Query("SELECT * FROM users WHERE isDeleted=0")
    fun getActiveUsers(): List<User>

}
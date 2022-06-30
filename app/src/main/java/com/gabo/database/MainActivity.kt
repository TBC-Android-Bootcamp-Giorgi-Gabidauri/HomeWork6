package com.gabo.database

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.gabo.database.database.Database
import com.gabo.database.database.entity.User
import com.gabo.database.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var db: Database
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(this, Database::class.java, "User Database")
            .allowMainThreadQueries().build()

        with(binding) {
            tvActiveUsers.text = "Active users: ${db.userDao().getActiveUsers().size}"
            tvDeletedUsers.text = "Deleted users: ${db.userDao().getDeletedUsers().size}"
            btnAddUser.setOnClickListener {
                val age =
                    if (etAge.text.toString().toIntOrNull() == null) 0 else etAge.text.toString()
                        .toInt()
                addUser(
                    etFirsName.text.toString(),
                    etLastName.text.toString(),
                    age,
                    etEmail.text.toString()
                )
                tvActiveUsers.text = "Active users: ${db.userDao().getActiveUsers().size}"
                tvDeletedUsers.text = "Deleted users: ${db.userDao().getDeletedUsers().size}"
            }
            btnRemoveUser.setOnClickListener {
                removeUser(etEmail.text.toString())
                tvActiveUsers.text = "Active users: ${db.userDao().getActiveUsers().size}"
                tvDeletedUsers.text = "Deleted users: ${db.userDao().getDeletedUsers().size}"
            }
            btnUpdateUser.setOnClickListener {
                val age =
                    if (etAge.text.toString().toIntOrNull() == null) 0 else etAge.text.toString()
                        .toInt()
                updateUser(
                    etFirsName.text.toString(),
                    etLastName.text.toString(),
                    age,
                    etEmail.text.toString()
                )
                tvActiveUsers.text = "Active users: ${db.userDao().getActiveUsers().size}"
                tvDeletedUsers.text = "Deleted users: ${db.userDao().getDeletedUsers().size}"
            }
            btnSeeList.setOnClickListener {
                startActivity(Intent(this@MainActivity, UserListActivity::class.java))
            }
        }
    }

    private fun updateUser(firstName: String, lastName: String, age: Int, email: String) {
        val msg: String
        if (email != "") {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (db.userDao().userAlreadyExist(email)) {
                    db.userDao().updateUser(
                        User(
                            firstName = firstName,
                            lastName = lastName,
                            age = age,
                            email = email
                        )
                    )
                    msg = "User updated successfully"
                    Snackbar.make(binding.cl, msg, Snackbar.LENGTH_SHORT)
                        .setAnchorView(binding.btnAddUser)
                        .setTextColor(resources.getColor(R.color.green)).show()
                } else {
                    msg = "User does not exist"
                    Snackbar.make(binding.cl, msg, Snackbar.LENGTH_SHORT)
                        .setAnchorView(binding.btnAddUser)
                        .setTextColor(resources.getColor(R.color.red))
                        .show()
                }
            } else {
                msg = "text in the email field does not match email format"
                Snackbar.make(binding.cl, msg, Snackbar.LENGTH_SHORT)
                    .setAnchorView(binding.btnAddUser)
                    .setTextColor(resources.getColor(R.color.red))
                    .show()
            }
        } else {
            msg = "User without email can not be updated"
            Snackbar.make(binding.cl, msg, Snackbar.LENGTH_SHORT)
                .setAnchorView(binding.btnAddUser).setTextColor(resources.getColor(R.color.red))
                .show()
        }
    }

    private fun removeUser(email: String) {
        val msg: String
        when {
            email != "" -> {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (db.userDao().userAlreadyExist(email)) {
                        db.userDao().deleteUser(true, email)
                        msg = "User removed Successfully"
                        Snackbar.make(binding.cl, msg, Snackbar.LENGTH_SHORT)
                            .setAnchorView(binding.btnAddUser)
                            .setTextColor(resources.getColor(R.color.green)).show()
                    } else {
                        msg = "User does not exist"
                        Snackbar.make(binding.cl, msg, Snackbar.LENGTH_SHORT)
                            .setAnchorView(binding.btnAddUser)
                            .setTextColor(resources.getColor(R.color.red))
                            .show()
                    }
                } else {
                    msg = "text in the email field does not match email format"
                    Snackbar.make(binding.cl, msg, Snackbar.LENGTH_SHORT)
                        .setAnchorView(binding.btnAddUser)
                        .setTextColor(resources.getColor(R.color.red))
                        .show()
                }
            }
            else -> {
                msg = "User without email can not be deleted"
                Snackbar.make(binding.cl, msg, Snackbar.LENGTH_SHORT)
                    .setAnchorView(binding.btnAddUser).setTextColor(resources.getColor(R.color.red))
                    .show()
            }
        }
    }

    private fun addUser(firstName: String, lastName: String, age: Int, email: String) {
        val msg: String
        if (email != "") {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (db.userDao().userAlreadyExist(email)) {
                    msg = "User already exist"
                    Snackbar.make(binding.cl, msg, Snackbar.LENGTH_SHORT)
                        .setAnchorView(binding.btnAddUser)
                        .setTextColor(resources.getColor(R.color.red))
                        .show()
                } else {
                    db.userDao().addUser(
                        User(
                            firstName = firstName,
                            lastName = lastName,
                            age = age,
                            email = email
                        )
                    )
                    msg = "User added successfully"
                    Snackbar.make(binding.cl, msg, Snackbar.LENGTH_SHORT)
                        .setAnchorView(binding.btnAddUser)
                        .setTextColor(resources.getColor(R.color.green)).show()
                }
            } else {
                msg = "text in the email field does not match email format"
                Snackbar.make(binding.cl, msg, Snackbar.LENGTH_SHORT)
                    .setAnchorView(binding.btnAddUser)
                    .setTextColor(resources.getColor(R.color.red))
                    .show()
            }
        } else {
            msg = "User without email can not be added"
            Snackbar.make(binding.cl, msg, Snackbar.LENGTH_SHORT)
                .setAnchorView(binding.btnAddUser).setTextColor(resources.getColor(R.color.red))
                .show()
        }
    }

}
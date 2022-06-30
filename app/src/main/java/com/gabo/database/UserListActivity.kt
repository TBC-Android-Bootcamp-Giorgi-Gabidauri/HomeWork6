package com.gabo.database

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.gabo.database.adapters.UserListAdapter
import com.gabo.database.database.Database
import com.gabo.database.databinding.ActivityUserListBinding

class UserListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserListBinding
    private lateinit var db: Database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(this, Database::class.java, "User Database")
            .allowMainThreadQueries().build()

        val rvAdapter = UserListAdapter()
        with(binding){
            rv.adapter = rvAdapter
            rv.layoutManager = LinearLayoutManager(this@UserListActivity)
            rvAdapter.submitList(db.userDao().getActiveUsers())
            btnActiveUsers.setOnClickListener {
                rv.adapter = rvAdapter
                rv.layoutManager = LinearLayoutManager(this@UserListActivity)
                rvAdapter.submitList(db.userDao().getActiveUsers())
            }
            btnDeletedUsers.setOnClickListener {
                rv.adapter = rvAdapter
                rv.layoutManager = LinearLayoutManager(this@UserListActivity)
                rvAdapter.submitList(db.userDao().getDeletedUsers())
            }
            btnBack.setOnClickListener {
                finish()
            }
        }
    }
}
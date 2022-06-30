package com.gabo.database.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabo.database.database.entity.User
import com.gabo.database.databinding.UserBinding

class UserListAdapter :
    RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {
    private var list: List<User> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<User>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class UserViewHolder(binding: UserBinding) : RecyclerView.ViewHolder(binding.root) {
        private val firstName = binding.tvFirstName
        private val lastName = binding.tvLastName
        private val age = binding.tvAge
        private val email = binding.tvEmail
        @SuppressLint("SetTextI18n")
        fun bind(model: User) {
            firstName.text = "Name: ${model.firstName}"
            lastName.text = model.lastName
            age.text = "Age:${model.age}"
            email.text = "Email:${model.email}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size
}
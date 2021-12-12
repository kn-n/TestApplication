package com.example.testapplication.models

data class Repository(
    var full_name: String,
    var description : String,
    var forks : String,
    var watchers : String,
    var created_at: String,
    var owner : Owner
)

package com.example.testapplication.models

class SavedRepository {
    var id: String = ""
    var userId: String = ""
    var fullName: String = ""
    var description : String = ""
    var forks : String = ""
    var watchers : String = ""
    var createdAt: String = ""
    var owner: String = ""
    var imgUrl: String = ""

    constructor()

    constructor(
        id: String,
        userId: String,
        fullName: String,
        description : String,
        forks : String,
        watchers : String,
        createdAt: String,
        owner: String,
        imgUrl: String
    ){
        this.id = id
        this.userId = userId
        this.fullName = fullName
        this.description = description
        this.forks = forks
        this.watchers = watchers
        this.createdAt = createdAt
        this.owner = owner
        this.imgUrl = imgUrl
    }
}